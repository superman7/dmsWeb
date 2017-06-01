/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 作为平台的上传控件使用
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/13 10:33
 */
define([
    'webuploader'
    , 'ko'
    , './FileUploadDialog'
    , './FileUploadItem'
], function (WebUploader
    , ko
    , FileUploadDialog
    , FileUploadItem) {
    'use strict';
    if (!WebUploader.Uploader.support()) {
        var err = '如果你使用的是IE浏览器，请尝试升级 flash 播放器';
        alert(err);
        throw new Error(err);
    }
    //秒传与断点续传
    /**
     * 对于秒传来说，其实就是文件上传前，
     * 把内容读取出来，算出md5值，然后通过ajax与服务端验证进行验证,
     * 然后根据结果选择继续上传还是跳过上传。
     */
    WebUploader.Uploader.register({
        'before-send-file': 'preupload'
        , 'before-send': 'checkchunk'
        , 'after-send-file': 'afterupload'
    }, {
        preupload: preupload
        , checkchunk: checkchunk
        , afterupload: afterupload
    });

    /**
     * 校验文件是否在服务器端已存在
     * @param {Stirng} path 文件存储路径
     * @param {String} name 文件名称
     * @param {String} size 文件大小
     * @param {String} type 文件类型
     * @param {Stirng} md5 文件md5
     */

    function isExistFile(path, name, size, type, md5) {
        return $.post('pan/checkFileIsExist.json', {
            path: path
            , name: name
            , size: size
            , type: type
            , md5: md5
        });
    }

    function preupload(file) {
        var owner = this.owner
            , deferred = WebUploader.Deferred();
        owner.md5File(file.source, 0, 8 * 1024 * 1024)
            .fail(function () {
                deferred.reject();
            })
            .then(function (md5) {
                //与服务端验证
                var cpath = '';
                if(file.item){
                    cpath = file.item.getPath();
                }
                isExistFile(cpath, file.name, file.size, file.type, md5).then(function (res) {
                    if (res.success) {// 如果验证已经上传过
                        owner.skipFile(file);//跳过上传文件
                        file['isOk'] = true;
                    }
                    file.fid = res.fid;
                    deferred.resolve();
                });
            });
        return deferred.promise();
    }

    function checkchunk(block) {
        var owner = this.owner
            , deferred = WebUploader.Deferred()
        // 这个肯定是异步的，需要通过FileReader读取blob后再算。
        owner.md5File(block.blob)// 及时显示进度
            .then(function (md5) {
                owner.options.formData['cmd5'] = md5;
                $.post('pan/checkChunkMd5.json', {
                    md5: md5,
                    fid: block.file.fid,
                    chunk: block.chunk,
                    chunksize: block.blob.size,
                    size: block.file.size
                }).then(function (res) {
                    if (res.success) {//如存在,返回失败给WebUploader，表明该分块不需要上传
                        deferred.reject();
                    } else {
                        deferred.resolve();
                    }
                });
            });

        return deferred.promise();
    }


    function afterupload(file) {
        updateProgress(file, 0);
    }

    function updateProgress(file, percentage) {
        var item = file.item;
        var size = file.size * (percentage - item.stopPercent);
        var speed = WebUploader.formatSize(size * 1000 / (new Date().getTime() - item.stopTime));
        item.updateProgress(percentage, speed);
    }

    /**
     * 上传构造函数
     * @param {String} id 上传触发对象
     * @param {Object} options
     * options.uploadDialogId -- 代表产生上传时产生的对话框id;
     * @returns AppUploader 上传控件
     * @constructor
     */
    var FileUploader = function (id, options) {
        var defaultSetting = {
            auto: true,  //{Boolean} [可选] [默认值：false] 设置为 true 后，不需要手动调用上传，有文件选择即开始上传。
            // swf文件路径
            swf: 'static/javascripts/libs/webuploader-0.1.5/Uploader.swf',
            // 文件接收服务端。
            server: 'pan/upload.json',
            pick: id,//{Selector, Object} [可选] [默认值：undefined] 指定选择文件的按钮容器，不指定则不创建按钮。
            disableGlobalDnd: true,//{Selector} [可选] [默认值：false] 是否禁掉整个页面的拖拽功能，如果不禁用，图片拖进来的时候会默认被浏览器打开。
            chunked: true, //分块上传
            //runtimeOrder:'flash', {Object} [可选] [默认值：html5,flash] 指定运行时启动顺序。默认会想尝试 html5 是否支持，如果支持则使用 html5, 否则则使用 flash.
            prepareNextFile: true,//{Boolean} [可选] [默认值：false] 是否允许在文件传输时提前把下一个文件准备好。 对于一个文件的准备工作比较耗时，比如图片压缩，md5序列化。 如果能提前在当前文件传输期处理，可以节省总体耗时。
            webkitdirectory: true, //是否可文件夹上传
            duplicate: true, //{Boolean} [可选] [默认值：undefined] 去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
            threads: 1, //{Boolean} [可选] [默认值：3] 上传并发数。允许同时最大上传进程数。
            chunkSize: 52428800//[可选] [默认值：5242880] 如果要分片，分多大一片？ 默认大小为5M.此处选用50M
        }
        options = options || {};
        var settings = $.extend({}, defaultSetting, options);
        // 创建上传组件
        var uploader = WebUploader.create(settings);
        if (options.uploadDialogId) {
            uploader.request('init', {}, function () {
                var input = $(id).find('input[type="file"]');
                if (settings.webkitdirectory) {
                    input.attr('webkitdirectory', '');
                }
                input.parent('div').css({width: '100%', height: '100%', left: 0, top: 0});
            });
            uploader.on('beforeFileQueued', function () {
                if (!window.uploadedDialog) {
                    window.uploadedDialog = new FileUploadDialog(options.uploadDialogId);
                }
                window.uploadedDialog.show();
            });
        }
        if (options.parentVM) {
            uploader.on('fileQueued', function (file) {
                var path = options.parentVM.currentPath();
                var webkitRelativePath = '';
                var tempFile = file;
                while (true) {
                    if ('webkitRelativePath' in tempFile) {
                        webkitRelativePath = tempFile['webkitRelativePath'];
                        break;
                    } else {
                        tempFile = tempFile.source;
                        if (typeof tempFile === 'undefined') {
                            break;
                        }
                    }
                }
                ;
                if (webkitRelativePath != '') {
                    webkitRelativePath = webkitRelativePath.substring(0, webkitRelativePath.lastIndexOf(file.name) - 1);
                    path += ('/' + webkitRelativePath);
                }

                var item = new FileUploadItem({
                    name: file.name
                    , size: WebUploader.formatSize(file.size)
                    , path: path
                    , ext: file.ext
                    , statusCss: ko.observable()
                    , remove: function () {
                        uploader.removeFile(file, true);
                    }
                    , pause: function () {
                        uploader.stop(file);
                    }
                    , continute: function () {
                        uploader.upload(file.id);
                    }
                });
                file.on('statuschange', function (status) {
                    switch (status) {
                        case  WebUploader.File.Status.INITED:
                            item.uploadPrepare();
                            break;
                        case  WebUploader.File.Status.QUEUED:
                            item.uploadWatting();
                            break;
                        case  WebUploader.File.Status.PROGRESS:
                            item.uploadIng();
                            break;
                        case  WebUploader.File.Status.COMPLETE:
                            item.uploadSuccess();
                            break;
                        case  WebUploader.File.Status.ERROR:
                            item.uploadError();
                        case  WebUploader.File.Status.INTERRUPT:
                            item.uploadStop();
                            break;
                        case  WebUploader.File.Status.CANCELLED:
                            item.domNode.remove();
                            break;
                    }
                });
                window.uploadedDialog.addItem(item);
                file.item = item;
            });
            //监控上传进度
            uploader.on('uploadBeforeSend', function (object, data) {
                data.id = object.file.fid;
            });
            //监控上传进度
            uploader.on('uploadProgress', updateProgress);
            //文件上传完成,刷新文件列表
            uploader.on('uploadComplete', function () {
                uploader.options.parentVM.refreshCurrentPath();
            });
        }
        return uploader;
    };
    return FileUploader;
});