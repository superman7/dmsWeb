/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 上传列表的子项
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/13 15:13
 */
define([
    '../../core/getCssNameByFileName'
    , 'ko'
], function (getCssNameByFileName
    , ko) {
    'use strict';
    /**
     * 创建上传的列表子项
     * @param {Object} viewModel //子项的描述信息
     * @returns {{domNode: Element}}
     * @constructor
     */
    function FileUploadviewModel(viewModel) {

        var li = document.createElement('li');
        li.className = 'file-list ';
        li.setAttribute('data-bind', 'css:statusCss')


        var process = document.createElement('div');
        process.className = 'process';
        li.appendChild(process);

        var info = document.createElement('div');
        info.className = 'info';
        li.appendChild(info);

        var fileName = document.createElement('div');
        fileName.className = 'file-name';
        fileName.title = viewModel.name;
        info.appendChild(fileName);

        var icon = document.createElement('div');
        icon.className = 'file-icon ' + getCssNameByFileName(viewModel.name);
        fileName.appendChild(icon);

        var nameSpan = document.createElement('span');
        nameSpan.className = 'name-text';
        nameSpan.innerText = viewModel.name;
        fileName.appendChild(nameSpan);

        var fileSize = document.createElement('div');
        fileSize.className = 'file-size';
        fileSize.innerText = viewModel.size;
        info.appendChild(fileSize);


        var filePath = document.createElement('div');
        filePath.className = 'file-path';
        filePath.innerText = viewModel.path || '全部文件';
        info.appendChild(filePath);

        var fileStatus = document.createElement('div');
        fileStatus.className = 'file-status';
        info.appendChild(fileStatus);
        //排队
        var waitingSpan = document.createElement('span');
        waitingSpan.className = 'waiting';
        waitingSpan.innerText = '排队中…';
        fileStatus.appendChild(waitingSpan);
        //准备
        var prepareSpan = document.createElement('span');
        prepareSpan.className = 'prepare';
        prepareSpan.innerText = '准备上传…';
        fileStatus.appendChild(prepareSpan);
        //上传中
        var uploadingSpan = document.createElement('span');
        uploadingSpan.className = 'uploading';
        fileStatus.appendChild(uploadingSpan);

        var precent = document.createElement('em');
        precent.className = 'precent';
        uploadingSpan.appendChild(precent);

        var speed = document.createElement('em');
        speed.className = 'speed';
        uploadingSpan.appendChild(speed);

        //错误
        var errorSpan = document.createElement('span');
        errorSpan.className = 'error';
        fileStatus.appendChild(errorSpan);
        var errorEm = document.createElement('em');
        errorSpan.appendChild(errorEm);
        var errorI = document.createElement('i');
        errorI.innerText = '服务器错误';
        errorSpan.appendChild(errorI);

        //暂停
        var pauseSpan = document.createElement('span');
        pauseSpan.className = 'pause';
        fileStatus.appendChild(pauseSpan);
        var pauseEm = document.createElement('em');
        pauseSpan.appendChild(pauseEm);
        var pauseI = document.createElement('i');
        pauseI.innerText = '已暂停';
        pauseSpan.appendChild(pauseI);

        //取消
        var cancelSpan = document.createElement('span');
        cancelSpan.className = 'cancel';
        fileStatus.appendChild(cancelSpan);
        var cancelEm = document.createElement('em');
        cancelSpan.appendChild(cancelEm);
        var cancelI = document.createElement('i');
        cancelI.innerText = '已取消';
        cancelSpan.appendChild(cancelI);

        //成功
        var successSpan = document.createElement('span');
        successSpan.className = 'success';
        fileStatus.appendChild(successSpan);
        var successEm = document.createElement('em');
        successSpan.appendChild(successEm);
        var successI = document.createElement('i');
        successI.innerText = '秒传';
        successSpan.appendChild(successI);

        //操作
        var fileOperate = document.createElement('div');
        fileOperate.className = 'file-operate';
        info.appendChild(fileOperate);

        //暂停
        var operatePause = document.createElement('em');
        operatePause.className = 'operate-pause';
        operatePause.setAttribute('data-bind', 'click:pause');
        fileOperate.appendChild(operatePause);

        //继续
        var operateContinue = document.createElement('em');
        operateContinue.className = 'operate-continue';
        operateContinue.setAttribute('data-bind', 'click:continute');
        fileOperate.appendChild(operateContinue);

        //重试
        var operateRetry = document.createElement('em');
        operateRetry.className = 'operate-retry';
        fileOperate.appendChild(operateRetry);

        //移除
        var operateRemove = document.createElement('em');
        operateRemove.className = 'operate-remove';
        operateRemove.setAttribute('data-bind', 'click:remove');

        fileOperate.appendChild(operateRemove);


        ko.applyBindings(viewModel, li);


        /**
         * 更新进度条
         * @param {float} percentage 0-1 ;
         * @param {float} speed 速度
         */
        function updateProgress(percentage, speedStr) {
            this.percentage = percentage;
            var percentage = Math.round(percentage * 1000);
            var percentText = percentage / 10.0 + '%';
            precent.innerText = percentText;
            process.style.width = percentText;
            speed.innerText = '(' + speedStr + '/s)';
        }

        function updateStatusCss(cssName) {
            viewModel.statusCss(cssName);
        }
        return {
            domNode: li
            , updateProgress: updateProgress

            , uploadSuccess: function () { //上传成功
                updateStatusCss('status-success');
            }
            , uploadPrepare: function () {//准备上传
                updateStatusCss('status-prepare');
            }
            , uploadWatting: function () {//排队中
                updateStatusCss('status-waiting');
            }
            , uploadError: function () {//上传失败
                updateStatusCss('status-error');
            }
            , uploadIng: function () {
                this.stopTime = new Date().getTime();
                this.stopPercent = this.percentage || 0;
                updateStatusCss('status-uploading');
            }
            , uploadStop: function () {
                updateStatusCss('status-pause');
            }
            , stopTime : null
            , stopPercent:0
            , getPath:function(){
                return viewModel.path;
            }
        };
    }

    return FileUploadviewModel;
});