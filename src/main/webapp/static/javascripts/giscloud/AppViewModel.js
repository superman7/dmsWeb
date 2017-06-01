/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 主页 ko 视图模板文件 ,主要是完成主页的界面
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/4/28 16:40
 */
define([
    './core/defaultValue'
    , './core/DeveloperError'
    , './core/DateUtils'
    , './core/getCssNameByFileName'
    , './core/MyToastr'
    , './widgets/FileTreeDialog'
    , 'jquery'
    , 'ko'
    , 'webuploader'
    , 'contextjs'
], function (defaultValue
    , DeveloperError
    , DateUtils
    , getCssNameByFileName
    , MyToastr
    , FileTreeDialog
    , $
    , ko
    , WebUploader
    , contextjs) {
    'use strict';
    /**
     * 构造方法 用来构造文件列表的视图模板
     * @param {options} options
     * @constructor
     */
    function AppViewModel(options) {
        options = defaultValue(options, {});
        var _self = this;
        var pathFileMap = {};
        var clickItemPathMap = {};
        //是否以列表显示
        this.showList = ko.observable(false);


        //是否支持文件夹选择
        this.canBroswerDirectory = function () {
            var uploader = document.createElement('input');
            return 'webkitdirectory' in uploader;
        };

        //要显示的文件集合

        this.filesToShow = ko.observableArray();


        this.filesToShow.subscribe(function () {
            resizeWrapperLayout();
        });
        var isSearch = false;

        //当前路径
        this.currentPath = ko.observable();


        this.currentPathArray = ko.observableArray();
        //当路径集合大于3时显示省略号
        this.showEllipsis = ko.observable(false);
        //最后3条路径集合
        this.last3PathArray = ko.observableArray();
        //是否在列表显示时显示路径,计划在搜索时才显示路径
        this.showPath = ko.observable(false);
        /**
         * 刷新当前路径下的文件
         */
        this.refreshCurrentPath = function () {
            var path = _self.currentPath();
            this.deletePathData(path);
            loadFilesByDir(path);
        }
        /**
         * 删除指定路径下的缓存文件信息
         * @param {Stirng} path 路径
         */
        this.deletePathData = function (path) {
            delete pathFileMap[path];
        }

        this.currentPath.subscribe(function (path) {
            var showPath = path;
            if ($.type(path) === 'string') {
                loadFilesByDir(path);
            } else {
                loadFilesByDir(path.id);
                showPath = path.name;
                path = path.ids;
            }
            var array = [], showArray = [];

            if (path) {//当前路径不为空时
                //获取路径的分割集合
                array = path.split('/');
            }
            if (showPath) {//当前路径不为空时
                //获取路径的分割集合
                showArray = showPath.split('/');
            }
            this.currentPathArray(array);
            var length = array.length
                , tempArray = []
                , index;
            this.showEllipsis(length > 3);
            for (var i = 3; i >= 1; i--) {
                index = length - i;
                if (index < 0) {
                    continue;
                }
                tempArray.push({
                    value: (isSearch ? '搜索:' : '') + showArray[index],
                    isLast: i == 1,
                    dir: 1,
                    path: getNewPathByArray(array, 0, index)
                });
            }
            this.last3PathArray(tempArray);
        }, this);

        /**
         * 根据子文件来判读需要什么图标显示
         * @param item //文件的描述
         * @returns {String} cssName  返回对应显示的css名称
         */

        this.getCssNameByType = function (item) {
            var cssName = 'default';
            if (item.dir == 1) {
                cssName = 'dir';
            } else {
                cssName = getCssNameByFileName(item.name());
            }
            return cssName;
        }
        //被选中的文件
        this.checkedItems = ko.computed(function () {
            return $(this.filesToShow()).filter(function (index, item) {
                return item.checked();
            })
        }, this);

        //获取被选中的文件的id集合
        this.getCheckedFileIds = function () {
            return $.makeArray($(this.checkedItems()).map(function (index, item) {
                return item.id;
            }).toArray());
        }
        //最后被选中的文件,主要为了重命名使用
        this.lastCheckedItem = ko.computed(function () {
            return this.checkedItems()[0];
        }, this);


        //选中的个数
        this.checkedCount = ko.computed(function () {
            return this.checkedItems().length;
        }, this);


        //是否全部选中
        this.isCheckedAll = ko.computed(function () {
            var filesCount = this.filesToShow().length;
            if (filesCount == 0) {
                return false;
            } else {
                //选中个数与总文件数相等时,全部选中
                return this.checkedCount() == filesCount;
            }
        }, this);

        //用列表显示事件
        this.showInListView = function () {
            if (canCoutinuteInCreatingOrEdit()) {
                this.showList(true);
            }
        };
        this.showInGridView = function () {
            if (canCoutinuteInCreatingOrEdit()) {
                this.showList(false);
            }
        };
        //选择子项
        this.checkitem = function (data) {
            data.checked(!data.checked());
        };
        //选中所有子项
        this.checkAllItems = function () {
            if (canCoutinuteInCreatingOrEdit()) {
                var allFiles = _self.filesToShow();
                var isCheckedAll = _self.isCheckedAll();
                for (var i = 0, length = allFiles.length; i < length; i++) {
                    allFiles[i].checked(!isCheckedAll);
                }
            }
        }
        /**
         * 修改源生方法,更好用
         * @param e
         */
        $.fn.viewer.Constructor.prototype.start = function (e) {
            var target = e.target;
            if (!$(target).is('img')) {
                target = $(target).find('img');
            }
            if ($(target).is('img')) {
                this.target = target;
                this.show();
            }
        }
        //图片查看器
        var imageViewer = null;
        /**
         * 子项被点击事件
         * @param {Object} item //子项的数据
         */
        this.itemClick = function (item) {
            if (item.cssName && item.cssName === 'pic') {
                if (imageViewer == null) {
                    imageViewer = new $.fn.viewer.Constructor('.grid-view', {
                        'url': 'max'
                    });
                }
                return;
            }
            if (canCoutinuteInCreatingOrEdit() && item.dir == 1) {
                var path = ($.type(item.path) === 'string') ? item.path : _self.currentPath();
                if ($.isFunction(item.name)) {
                    path += ('/' + item.name());
                }
                if (!(path in clickItemPathMap) && item.id) {
                    clickItemPathMap[path] = item.id;
                }
                openPath(path);
                if (imageViewer != null) {
                    imageViewer.destroy();
                    imageViewer = null;
                }
            }
        }
        //返回上一级
        this.returnBackPath = function () {
            var array = _self.currentPathArray()
                , path = getNewPathByArray(array, 0, array.length - 2);
            _self.currentPath(path);
        };
        //打开根路径
        this.openRootPath = function () {
            openPath('');
        };
        this.onImageLoad = function (data, event) {
            var target = $(event.target), parent = target.parent('.fileicon');
            target.css({
                top: (parent.height() - target.height()) / 2
                , left: (parent.width() - target.width()) / 2
            });
        }
        //鼠标移出事件
        this.itemMouseout = function (data, event) {
            if (canCoutinuteInCreatingOrEdit()) {
                $(event.currentTarget).removeClass('hover-item');
            }
        }
        //鼠标移入事件
        this.itemMouseover = function (data, event) {
            if (canCoutinuteInCreatingOrEdit()) {
                $(event.currentTarget).addClass('hover-item');
            }
        }
        /**
         * 在添加新文件夹,重命名等期间是否可进行别的操作
         * @returns {boolean}
         */
        function canCoutinuteInCreatingOrEdit() {
            var isCreate = _self.isEditFileName();
            return !isCreate;
        }

        //是否在编辑文件(夹),包括创建、重命名
        this.isEditFileName = ko.observable(false);

        //是否添加了一个新文件(夹)到显示集合,主要是为了区分新建文件夹或重命名,
        this.isAddTempToShowList = false;


        this.newFileName = ko.observable();
        //创建新文件夹
        this.createDirectory = function () {
            if (canCoutinuteInCreatingOrEdit()) {
                _self.newFileName('新建文件夹');
                var item = {
                    cssName: 'dir'
                    , dir: 1
                    , name: ''
                    , mtime: new Date().getTime()
                    , path: _self.currentPath()
                }
                var item = formatterShowItem(item);
                _self.filesToShow.unshift(item);
                _self.isAddTempToShowList = true;
                _self.isEditFileName(true);
            }
        };
        //确认创建文件夹.充满光明
        this.submitCreateDirectoryOrRename = function () {
            var name = _self.newFileName();
            var iscreate = _self.isAddTempToShowList
            if (name && name.trim() != "") {
                $.post('pan/createOrEditFile.json', {
                    path: _self.currentPath()
                    , name: name
                    , fid: iscreate ? '' : _self.lastCheckedItem().id
                }, function (res) {
                    if (res && res.success) {
                        var item = null;
                        if (iscreate) {
                            item = _self.filesToShow()[0];
                        } else {
                            item = _self.lastCheckedItem();
                        }
                        item.name(res.name);
                        if (res.fid) {
                            item.id = res.fid;
                        }
                        _self.isAddTempToShowList = false;
                        _self.isEditFileName(false);
                    } else {
                        MyToastr.show('未操作成功!', '错误', MyToastr.ERROR);
                    }
                });
            } else {
                MyToastr.show('名称不能为空!', '错误', MyToastr.ERROR);
            }

        }
        //搜索文件内容
        this.searchText = ko.observable('');
        this.searchText.subscribe(function (value) {
            this.showSearchClear(value.length > 0);
        }, this);
        //是否显示清除按钮
        this.showSearchClear = ko.observable(false);
        //搜索文本改变事件
        this.searchtTextChange = function (data, e) {
            this.searchText(e.target.value);
        }
        var lastCurrentpath = '';
        //清除搜索文本
        this.clearSearchText = function () {
            this.searchText('');
            this.currentPath(lastCurrentpath);
        }
        //搜索文件
        this.searchFiles = function (vm, event) {
            if (event.type === 'click' || (event.type === 'keypress' && event.keyCode === 13)) {
                var searchText = this.searchText();
                lastCurrentpath = this.currentPath();
                this.showPath(true);
                if (searchText.length > 0) {
                    isSearch = true;
                    this.currentPath(searchText);
                } else {
                    MyToastr.show('请输入搜索关键字', '提示', MyToastr.WARRING);
                }
            }
        }

        this.mouseoutSearchBtn = function () {
            isSearch = false;
        }
        //打开搜索的文件目录
        this.openSeachPath = function (data) {
            if (canCoutinuteInCreatingOrEdit()) {
                openPath(data.path);
            }
        }

        //删除文件
        this.delFiles = function () {
            $.post('pan/delFiles.json', {fids: this.getCheckedFileIds()}, function (res) {
                if (res && res.list) {
                    _self.filesToShow.remove(function (item) {
                        return $.inArray(item.id, res.list) > -1;
                    });
                } else {
                    MyToastr.show('删除失败!', '错误', MyToastr.ERROR);
                }
            });
        };
        //重命名
        this.rename = function () {
            _cancelCreateDirecotry();
            this.newFileName(this.lastCheckedItem().name());
            this.isEditFileName(true);
            var position = $('.item-active').position();
            if (_self.showList()) {
                $('.module-edit-name').css({
                    left: $('.module-edit-name').css('left')
                    , top: position.top + parseFloat($('.module-edit-name').css('top'))
                });
            } else {
                $('.module-edit-name').css(position);
            }
        }
        this.fileTreeDialog = null;
        //移动
        this.move = function () {
            if (this.fileTreeDialog == null) {
                this.fileTreeDialog = new FileTreeDialog(this);
            }
            this.fileTreeDialog.show();
        }
        this.formatterPath = function (data) {
            if (data.path) {
                return data.path.name;
            } else {
                return '全部文件';
            }
        }
        /**
         * 取消创建新文件夹或重命名
         */
        this.cancelCreteDirectoryOrRename = function () {
            if (!canCoutinuteInCreatingOrEdit()) {
                _self.isEditFileName(false);
                _cancelCreateDirecotry();
            }
        }

        /**
         * 由于在创建文件夹的操作时,将一个对象加入了显示数组,所以要及时删除
         */
        function _cancelCreateDirecotry() {
            if (_self.isAddTempToShowList) {
                _self.filesToShow.shift();
                _self.isAddTempToShowList = false;
            }
        }

        /**
         * 根据路径获取文件
         * @param {String} dirOrName 父路径 或搜索文件名
         * @return {Array} 父路径下的子文件
         */
        function loadFilesByDir(dirOrName) {
            //当文件集合为空时,去服务器获取
            //某路径下的文件集合
            var fileArray = pathFileMap[dirOrName];
            if (fileArray === undefined) {

                var deferred = null;
                if (isSearch) { //是否为搜索
                    deferred = $.getJSON('pan/search.json', {searchtext: dirOrName});
                } else {
                    var path = dirOrName;
                    if (dirOrName && dirOrName.length > 0 && (dirOrName in clickItemPathMap)) {
                        path = clickItemPathMap[dirOrName];
                    }
                    deferred = $.getJSON('pan/list.json', {path: path});
                }

                deferred.then(function (res) {
                    var haveSearch = this.url.indexOf('search') > -1;
                    if (haveSearch) {
                        isSearch = false;
                    }
                    _self.showPath(haveSearch);
                    if (res['errno'] === 0) {
                        fileArray = $.map(res['list'], formatterShowItem);
                        pathFileMap[dirOrName] = fileArray;
                        _self.filesToShow(fileArray);
                    } else {
                        new DeveloperError('获取文件失败!');
                    }
                });
            } else {
                _self.filesToShow(pathFileMap[dirOrName]);
            }
        }

        /**
         * 对要显示的项目进行改造
         * @param item
         * @returns {*}
         */
        function formatterShowItem(item) {
            item.name = ko.observable(item.name);
            item.cssName = _self.getCssNameByType(item);
            if (item.cssName === 'pic') {
                item.src = "pan/thumbnail?name=" + item.name() + "&path=" + item.path.id + "&size=94_96";
                item.maxSrc = "pan/thumbnail?name=" + item.name() + "&path=" + item.path.id + "&size=0_0";
            } else {
                item.src = false;
            }
            item.size = (item.dir == 1) ? '-' : WebUploader.formatSize(item.size);
            item.checked = ko.observable(false);
            item.mtime = DateUtils.getSmpFormatDateByLong(item.mtime, true);
            return item;
        }


        /**
         * 打开路径
         * @param {String} path 路径
         */
        function openPath(path) {
            _self.currentPath(path);
        }

        /**
         * 根据传入数组和起止位置获取以/连接的新路径
         * @param {Array} array 路径数组
         * @param {int} start //开始位置
         * @param {int} end //结束位置
         * @returns {String} 返回新路径
         */
        function getNewPathByArray(array, start, end) {
            return $.map(array, function (item, index) {
                //数组条件
                if (index < start || index > end) {
                    return null;
                } else {
                    return item;
                }
            }).join('/');
        }

        //初始化
        this.openRootPath();
    }

    return AppViewModel;
});