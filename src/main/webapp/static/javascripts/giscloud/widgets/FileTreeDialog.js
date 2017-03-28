/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 文件复制及移动目录树
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/27 15:02
 */
define(['ko'
    , 'jquery'
    , 'draggabilly'
    , './../core/MyToastr'
    , 'zTree'
    , 'css!./FileTreeDialog.css'
], function (ko
    , $
    , Draggabilly
    , MyToastr) {
    'use strict';
    /**
     * 文件复制及移动目录树-构造方法
     * @param {KO-ViewModel} parentViewModel 父视图模板;
     * @constructor
     */
    function FileTreeDialog(parentViewModel) {
        var width = 520;
        var dialog = document.createElement('div');
        dialog.className = 'dialog dialog-fileTreeDialog';
        document.body.appendChild(dialog);
        //对话框头
        var dialogHeader = document.createElement('div');
        dialogHeader.className = 'dialog-header';
        dialog.appendChild(dialogHeader);

        new Draggabilly(dialog, {
            handle: '.dialog-header'
            , containment: true
        });

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

        //对话框体
        var dialogBody = document.createElement('div');
        dialogBody.className = 'dialog-body';
        dialog.appendChild(dialogBody);
        //文件书容器
        var fileTreeContainer = document.createElement('div');
        fileTreeContainer.className = 'file-tree-container';
        dialogBody.appendChild(fileTreeContainer);

        var tree = document.createElement('ul');
        tree.className = 'ztree';
        fileTreeContainer.appendChild(tree);

        var treeSetting = {
            async: {
                enable: true
                , contentType: 'application/json'
                , url: 'pan/tree.json'
                , autoParam: ['id']
                , type: 'get'
            }
            , view: {
                selectedMulti: false
            }
            , callback: {
                onClick: function (event, treeId, treeNode) {
                    if (!treeNode.open) {
                        zTreeObj.expandNode(treeNode);
                    }
                }
                , onAsyncSuccess: function (event, treeId, treeNode) {
                    if (typeof treeNode === 'undefined') {
                        var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
                        var treeNode = zTreeObj.getNodes()[0];
                        //选择根节点,并展开
                        if (treeNode) {
                            zTreeObj.selectNode(treeNode);
                            zTreeObj.expandNode(treeNode);
                        }
                    }
                }
            }
        };
        var zTreeObj = null;

        function initTree() {
            zTreeObj = $.fn.zTree.init($(tree), treeSetting);
        }

        //对话框底部
        var dialogFooter = document.createElement('div');
        dialogFooter.className = 'dialog-footer';
        dialog.appendChild(dialogFooter);

        //新建文件夹按钮
        var newButton = createBootstrapBtn('', 'fa fa-plus', '新建文件夹');
        newButton.style.width = '194px';
        newButton.setAttribute('data-bind', 'click : addDirctory');
        dialogFooter.appendChild(newButton);
        //确认按钮
        var submitButton = createBootstrapBtn('btn-info', 'fa fa-circle-o', '确定');
        submitButton.style.marginLeft = '20px';
        submitButton.style.width = '128px';
        submitButton.setAttribute('data-bind', 'click : moveFiles');
        dialogFooter.appendChild(submitButton);
        //取消按钮
        var cancelButton = createBootstrapBtn('', 'fa fa-ban', '取消');
        cancelButton.style.marginLeft = '20px';
        cancelButton.style.width = '128px';
        cancelButton.setAttribute('data-bind', 'click : closeDialog');
        dialogFooter.appendChild(cancelButton);

        //模态框
        var modalDiv = document.createElement('div');
        modalDiv.style.opacity = '0';
        modalDiv.style.position = 'absolute';
        modalDiv.style.width = '100%';
        modalDiv.style.height = '100%';
        modalDiv.style.top = '0px';
        modalDiv.style.left = '0px';
        modalDiv.style.zIndex = '101';
        document.body.appendChild(modalDiv);

        //dialog的viewModel
        var viewModel = {
            closeDialog: function () {
                $(dialog).hide(function () {
                    zTreeObj.destroy();
                    $(modalDiv).hide();
                });
            }
            , addDirctory: function () {
                var nodes = zTreeObj.getSelectedNodes()
                    , selectedTreeNode = nodes[0];
                if (selectedTreeNode) {
                    var currentPath = selectedTreeNode.id;
                    //if(selectedTreeNode.parentTId){
                    //    currentPath += ('/' + selectedTreeNode.name);
                    //}
                    //查看是否已经点击了创建新文件夹按钮
                    var editObject = document.getElementsByClassName('zTree-edit-name');
                    if (editObject && editObject.length > 0) {
                        var input = editObject[0].getElementsByTagName('input');
                        input[0].select();
                    } else { //没有创建
                        var treeNode = zTreeObj.addNodes(selectedTreeNode, {name: ''});
                        if (treeNode) {
                            treeNode = treeNode[0];
                            var span = document.getElementById(treeNode.tId + '_span');
                            if (span) {

                                var editDiv = document.createElement('div');
                                editDiv.className = 'zTree-edit-name';
                                span.appendChild(editDiv);

                                var input = document.createElement('input');
                                input.style.height = '21px';
                                input.setAttribute('value', '新建文件夹');
                                editDiv.appendChild(input);
                                input.focus();
                                input.select();

                                var ok = document.createElement('span');
                                ok.className = 'sure';
                                ok.style.marginLeft = '5px';
                                ok.style.marginRight = '5px';
                                editDiv.appendChild(ok);
                                var ok_i = document.createElement('i');
                                ok_i.className = 'fa fa-check';
                                ok.appendChild(ok_i);
                                $(ok).click(function () {
                                    $.post('pan/createOrEditFile.json', {
                                        path: currentPath
                                        , name: input.value
                                        , fid: ''
                                    }, function (res) {
                                        if (res && res.success) {
                                            span.innerText = res.name;
                                            editDiv.remove();
                                            if (currentPath == parentViewModel.currentPath()) {
                                                parentViewModel.refreshCurrentPath();
                                            }
                                        } else {
                                            MyToastr.show('未创建成功!', '错误', MyToastr.ERROR);
                                        }
                                    });
                                });
                                var cancel = document.createElement('span');
                                cancel.className = 'cancel';
                                editDiv.appendChild(cancel);
                                var cancel_i = document.createElement('i');
                                cancel_i.className = 'fa fa-times';
                                cancel.appendChild(cancel_i);
                                $(cancel).click(function () {
                                    this.remove();
                                    zTreeObj.removeNode(treeNode);
                                });
                            }
                        }
                    }
                } else {
                    MyToastr.show('请选择父节点', '错误', MyToastr.ERROR);
                }
            }
            , moveFiles: function () {
                //被移动至文件夹的上层文件路径
                var selectNode = zTreeObj.getSelectedNodes()[0];
                var parentids = $(selectNode.getPath()).map(function (index, item) {
                    return item.id || '';
                });
                var moveFileIds = parentViewModel.getCheckedFileIds();
                //判断两组id是否有交集
                var repalceIds = $.grep(moveFileIds, function (item) {
                    return $.inArray(item, parentids) > -1;
                });

                if (repalceIds.length > 0) {
                    MyToastr.show('不能将文件夹移动到自身或者子文件夹下', '错误', MyToastr.ERROR);
                    return;
                } else {
                    var toPath = selectNode.id;
                    $.post('pan/moveFiles.json', {movefileids: moveFileIds, topath: toPath}, function (res) {
                        if (res && res.success) {
                            parentViewModel.refreshCurrentPath();
                            viewModel.closeDialog();
                            parentViewModel.deletePathData(toRootPath(selectNode));
                        } else {
                            MyToastr.show('删除失败!', '错误', MyToastr.ERROR);
                        }
                    });
                }
            }
        }
        ko.applyBindings(viewModel, dialog);

        function toRootPath(selectNode) {
            var nodes = selectNode.getPath();
            var result = '';
            $.each(nodes, function (i, node) {
                result += ('/' + node.name);
            });
            return result;
        }


        function createBootstrapBtn(btnClass, iconClass, labelName) {
            btnClass = btnClass || 'btn-default';
            var btn = document.createElement('button');
            btn.className = 'btn ' + btnClass;
            var icon = document.createElement('i');
            icon.className = iconClass;
            btn.appendChild(icon);
            var label = document.createElement('span');
            label.innerText = labelName;
            label.className = 'label';
            btn.appendChild(label);
            return btn;
        }

        return {
            show: function () {
                $(dialog).show(function () {
                    $(dialog).css({
                        width: width
                        , left: ($(document.body).width() - width) / 2
                        , top: ($(document.body).height() - $(dialog).height()) / 2
                    });
                    initTree();
                });
            }
        }
    }

    return FileTreeDialog;
});