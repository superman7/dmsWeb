/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @包名:
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/15 16:06
 */
define([
        'ko'
        , '../core/MyToastr'
        , 'jquery'
    ], function (ko
        , MyToastr
        , $) {
        'use strict';
        function FieldConfigViewModel(options) {
            var _self = this;
            _self = $.extend(_self, options);
            this.type = options.type;
            this.inputValue = ko.observable();
            this.editInputTitle = ko.observable(this.title());
            //添加字段
            this.addInputItem = function () {
                var inputValue = _self.inputValue().toLowerCase();
                var value = inputValue.replace(/\s+/g, '');
                if (value.length < 1) {
                    MyToastr.show('字段' + inputValue + '不能为空', '提示', MyToastr.ERROR);
                } else if (value.length != inputValue.length) {
                    MyToastr.show('字段' + inputValue + '不能包含空格', '提示', MyToastr.ERROR);
                } else {
                    _self.addItem(value, 1);
                }
                //屏蔽默认的form事件
                return false;
            };
            this.addItem = function (value, formInput) {
                if (_self.isExist(value)) {
                    if (formInput) {
                        MyToastr.show('字段' + value + '已存在', '提示', MyToastr.ERROR);
                    }
                } else {
                    this.ajaxField(value, 0, function () {
                        _self.items.push({name: value, remove: 1});
                        _self.inputValue(null);
                        if (formInput) {
                            if ($.isFunction(_self.selectedOption)) {
                                _self.selectedOption(value);
                            }
                        }
                    });
                }

            }
            this.ajaxField = function (name, type, callback) {
                $.ajax('system/editField/' + name + '.json', {
                    type: "POST"
                    , data: {
                        fcid: _self.type
                        , type: type
                    }
                    , success: function (res) {
                        if (res.success) {
                            setTimeout(function(){
                                resizeWrapperLayout();
                            },200);
                            callback();
                        }else{
                            MyToastr.show((type==0?'添加':'删除' )+'失败!', '提示', MyToastr.ERROR);
                        }
                    }
                    , error: function (error) {
                        if (error) {
                            MyToastr.show('操作失败!' + (error.status == 500 ? '服务器异常' : ''), '提示', MyToastr.ERROR);
                        };
                    }

                });
            }
            //移除字段
            this.removeItem = function (item, event) {
                if ($.type(item) === 'string') {
                    item = this.getItemByName(item);
                    if (item == null || item.remove == 0) {
                        return;
                    }
                }
                _self.ajaxField(item.name, 1, function () {
                    _self.items.remove(item);
                    if (event && event.type === 'click') {
                        if ($.isFunction(_self.deSelectedOption)) {
                            _self.deSelectedOption(item.name);
                        }
                    }
                })
                return false;
            };

            this.isExist = function (name) {
                return this.getItemByName(name) != null;
            };
            //通过字段名来查看字段是否存在
            this.getItemByName = function (name) {
                var item = null;
                var items = _self.items();
                for (var i = 0, length = items.length; i < length; i++) {
                    if (name.toLowerCase() === items[i].name.toLowerCase()) {
                        item = items[i];
                        break;
                    }
                }
                return item;
            };

            this.isEditing = ko.observable(false);
            this.editCss = ko.computed(function () {
                return this.isEditing() ? 'fa-save' : 'fa-pencil';
            }, this);

            this.editTitle = function () {
                var isEditing = _self.isEditing();
                if (isEditing) {
                    var value = _self.editInputTitle().replace(/\s+/g, '');
                    if (value.length < 1) {
                        MyToastr.show('字段描述不能为空', '提示', MyToastr.ERROR);
                    }
                    $.ajax('system/editTitle.json', {
                        type: "POST"
                        , data: {
                            fcid: _self.type
                            , title: value
                        }
                        , success: function (res) {
                            if (res.success) {
                                _self.title(value);
                            }
                        }
                        , error: function (error) {
                            if (error) {
                                MyToastr.show('更新失败!' + (error.status == 500 ? '服务器异常' : ''), '提示', MyToastr.ERROR);
                            }
                            ;
                        }

                    });
                }
                _self.isEditing(!isEditing);
            }

        }

        return FieldConfigViewModel;
    }
);