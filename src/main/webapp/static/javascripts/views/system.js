/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 系统配置中心
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/8 14:12
 */
require(['../config'], function () {
    require([
            'giscloud/system/FieldConfig'
            , 'ko'
            , 'common'
            , 'datatables.net-bs'
            , 'css!stylesheets/table.css'
            , 'domReady!']
        , function (FieldConfig, ko) {
            var tableId = "#fieldConfigTable";
            var table = $(tableId).DataTable({
                bStateSave: true
                , bLengthChange: false
                , iDisplayLength: 10
                , stripeClasses: ["odd", "even"]//为奇偶行加上样式，兼容不支持CSS伪类的场合
                , serverSide: true   //启用服务器端分页
                , searching: false
                , order: []
                , "oLanguage": {
                    "sUrl": 'static/javascripts/libs/data-tables/i18n/Chinese.json'
                }
                , ajax: 'system/list.json'
                , columns: [
                    {title: '描述', orderable: false, defaultContent: ''}
                    , {title: '关联字段',orderable: false,defaultContent: ''}
                    , {title: '操作', orderable: false, className: 'ceterCell', defaultContent: ''}
                ]
                , createdRow: function (row, data) {
                    var fields = data.fields, tempArray = [];
                    for (var key in fields) {
                        tempArray.push({name: key.replace(/\*/g, '.'), remove: fields[key]});
                    }
                    var viewModel = {
                        title: ko.observable(data.title)
                        , items: ko.observableArray(tempArray || [])
                        , type: ko.observable(data.id)
                    }
                    viewModel.fields = ko.pureComputed(function () {
                        return $(this.items()).map(function (index, item) {
                            return item.name;
                        }).get().join(',');
                    }, viewModel);
                    $('td', row).eq(0).attr('data-bind', 'text:title');
                    $('td', row).eq(1).attr('data-bind', 'text:fields');

                    var $editBtn = $('<i>').addClass('fa fa-pencil').data('data', viewModel).attr('title', '编辑').css({
                        'cursor': 'pointer'
                    });
                    $editBtn.click(function () {
                        var $editPanel = $('#editPanel');
                        $editPanel.empty();
                        new FieldConfig($editPanel, $(this).data('data'));
                        resizeWrapperLayout();
                    });
                    $('td', row).eq(2).append($editBtn);
                    if (data.flag == 1) {
                        var $delBtn = $('<i>').addClass('fa fa-trash-o')
                            .attr('title', '删除').css({
                                'cursor': 'pointer'
                                , 'marginLeft': '10px'
                            });
                        $('td', row).eq(2).append($delBtn);
                    }
                    ko.applyBindings(viewModel, row);
                }
            });
            table.on('draw.dt', function () {
                resizeWrapperLayout();
            });
        });
});
