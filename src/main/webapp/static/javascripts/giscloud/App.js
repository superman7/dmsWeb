/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: gis云存储的入口
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/4/27 17:12
 */
define([
    './AppViewModel'
    , './core/define'
    , './core/DeveloperError'
    , 'ko'
    , 'jquery'
    , './widgets/uploader/FileUploader'
    , 'viewer'
], function (AppViewModel
    , define
    , DeveloperError
    , ko
    , $
    , FileUploader) {
    'use strict';
    /**
     * gis云平台的主页 构造方法
     * 用来构造主页的布局及功能
     * @param {Element} container 文件列表的容器
     * @constructor
     */
    function App(container) {
        if (!define(container)) {
            throw new DeveloperError('必须指定文件列表的容器');
        }
        var viewModel = new AppViewModel();

        var uploadJson = {
            uploadDialogId: 'web-uploader'
            , parentVM: viewModel
        }
        ko.applyBindings(viewModel, container);

        $(document).keydown(function(e){
            if(e.keyCode == 8){ //按下退格键
                viewModel.returnBackPath();
            }
        });

        new FileUploader('.uplaodFileBtn', uploadJson);
        if (viewModel.canBroswerDirectory()) {
            new FileUploader('.uplaodDirectoryBtn', $.extend(true, {'webkitdirectory': true}, uploadJson));
        }

    };
    return App;
});