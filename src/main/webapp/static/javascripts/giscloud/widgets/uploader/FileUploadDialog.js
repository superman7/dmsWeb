/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 文件上传对话框
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/13 13:57
 */
define([
    'ko'
    , 'jquery'
    , 'css!./uploader-dialog.css'
], function (ko
    , $) {
    'use strict';
    /**
     * 创建文件上传对话框
     * @param {String} id 文件对话框的id
     * @constructor
     */
    function FileUploadDialog(id) {
        //对话框
        var dialog = document.createElement('div');
        dialog.className = 'dialog dialog-web-uploader dialog-blue';
        dialog.setAttribute('id', id);
        document.body.appendChild(dialog);
        //对话框头
        var dialogHeader = document.createElement('div');
        dialogHeader.className = 'dialog-header';
        dialog.appendChild(dialogHeader);

        var h3 = document.createElement('h3');
        dialogHeader.appendChild(h3);

        var dialogHeaderTitle = document.createElement('span');
        dialogHeaderTitle.className = 'dialog-header-title';
        dialogHeaderTitle.innerText = '文件上传';
        h3.appendChild(dialogHeaderTitle);
        //对话框控制
        var dialogControl = document.createElement('div');
        dialogControl.className = 'dialog-control';
        dialogHeader.appendChild(dialogControl);

        var closeBtn = document.createElement('span');
        closeBtn.className = 'fa fa-times';
        closeBtn.setAttribute('data-bind', 'click : closeDialog');
        dialogControl.appendChild(closeBtn);

        var minBtn = document.createElement('span');
        minBtn.className = 'fa fa-minus';
        minBtn.style.marginTop = '2px';
        minBtn.style.marginRight = '3px';
        minBtn.setAttribute('data-bind', 'click : minMaxDialog');
        dialogControl.appendChild(minBtn);

        //对话框体
        var dialogBody = document.createElement('div');
        dialogBody.className = 'dialog-body';
        dialog.appendChild(dialogBody);


        var uploaderListWrapper = document.createElement('div');
        uploaderListWrapper.className = 'uploader-list-wrapper';
        dialogBody.appendChild(uploaderListWrapper);
        //上传文件头
        var uploaderListHeader = document.createElement('div');
        uploaderListHeader.className = 'uploader-list-header';
        uploaderListWrapper.appendChild(uploaderListHeader);

        var fileName = document.createElement('div');
        fileName.className = 'file-name';
        fileName.innerText = '文件(夹)名';
        uploaderListHeader.appendChild(fileName);

        var fileSize = document.createElement('div');
        fileSize.className = 'file-size';
        fileSize.innerText = '大小';
        uploaderListHeader.appendChild(fileSize);

        var filePath = document.createElement('div');
        filePath.className = 'file-path';
        filePath.innerText = '上传目录';
        uploaderListHeader.appendChild(filePath);

        var fileStatus = document.createElement('div');
        fileStatus.className = 'file-status';
        fileStatus.innerText = '状态';
        uploaderListHeader.appendChild(fileStatus);

        var fileOperate = document.createElement('div');
        fileOperate.className = 'file-operate';
        fileOperate.innerText = '操作';
        uploaderListHeader.appendChild(fileOperate);

        //上传列表容器
        var uploaderListDiv = document.createElement('div');
        uploaderListDiv.className = 'uploader-list';
        dialogBody.appendChild(uploaderListDiv);
        //上传列表
        var fileList = document.createElement('ul');
        fileList.className = 'listcontainer';
        uploaderListDiv.appendChild(fileList);
        //滚动条
        $(uploaderListDiv).niceScroll({autohidemode: false});
        //缩放dialog
        var viewModel = {
            minMaxDialog: function () {
                if ($(minBtn).hasClass('fa-minus')) {
                    minDialog();
                } else {
                    maxDialog();
                }
            }
            , closeDialog: function () {
                $(dialog).hide();
            }
        }
        ko.applyBindings(viewModel, dialog);

        /**
         * 收起上传框
         */
        function minDialog() {
            $(dialogBody).slideUp(function () {
                $(minBtn).removeClass('fa-minus');
                $(minBtn).addClass('fa-square-o');
            });
        }

        /**
         * 展开上传框
         */
        function maxDialog() {
            $(dialogBody).slideDown(function () {
                $(minBtn).addClass('fa-minus');
                $(minBtn).removeClass('fa-square-o');
            });
        }
        return {
            show: function () {
                $(dialog).show(function () {
                    maxDialog();
                });
            }
            , filesMap: {}
            , addItem: function (fileUploadItem) {
                fileList.appendChild(fileUploadItem.domNode);
            }
        }
    }

    return FileUploadDialog;
});