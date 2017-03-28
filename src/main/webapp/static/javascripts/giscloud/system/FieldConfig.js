/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 字段设置
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/15 15:01
 */
define([
    './FieldConfigViewModel'
    , '../core/DeveloperError'
    , '../core/MyToastr'
    , 'webuploader'
    , 'ko'
    , 'jquery'
    , 'jquery.validate-zh'
    , 'jquery.select-multiple'
], function (FieldConfigViewModel
    , DeveloperError
    , MyToastr
    , WebUploader
    , ko) {
    'user strict';
    /**
     * 配置解析文件的关联类型或者关联字段名称;
     * @param {JQuerySelector} container 包含组件的容器
     * @param {Json} options 配置属性
     * {
     *    className:panle的样式
     *    title:表头描述
     *    defaultFieldArray:默认的字段数组
     * }
     * @constructor
     */
    function FieldConfig(container, options) {
        if ($.isEmptyObject(container)) {
            throw new DeveloperError('必须指定组件的容器jquery选择器');
        }
        if ($(container).length < 1) {
            throw new DeveloperError('请确认页面存在选择器[' + container + ']');
        }
        if ($.isEmptyObject(options)) {
            throw new DeveloperError('必须指定必须的属性');
        }

        options = $.extend({}, options);

        var $row = $('<div>').addClass('container-fluid').appendTo($(container));


        var $selectDiv = $('<div>').appendTo($row);

        var $panelDiv = $('<div>').addClass('col-xs-12').appendTo($row);

        var $panel = $('<div>').addClass('panel').addClass(options.className || 'panel-primary').appendTo($panelDiv);
        var $panelHeading = $('<div>').addClass('panel-heading').appendTo($panel);
        $('<span>').attr('data-bind', 'text:title,visible:!isEditing()').appendTo($panelHeading);
        $('<input>').attr('data-bind', 'textInput:editInputTitle,visible:isEditing').appendTo($panelHeading);
        $('<i>').addClass('fa pull-right')
            .attr('data-bind', 'click:editTitle,css:editCss')
            .css({'cursor': 'pointer', 'marginTop': '5px'}).appendTo($panelHeading);
        var $paneBody = $('<div>').addClass('panel-body').appendTo($panel);
        var $toDoList = $('<ul>').addClass('to-do-list')
            .attr('data-bind', 'foreach:items').appendTo($paneBody);

        var $li = $('<li>').addClass('clearfix').appendTo($toDoList);
        $('<div>').addClass('todo-title').attr('data-bind', 'text:name,attr:{value:name}').appendTo($li);
        var $tools = $('<div>').addClass('todo-actionlist pull-right clearfix').attr('data-bind', 'if:remove==1').appendTo($li);
        var $remove = $('<a>').attr({
            'href': '#'
            , 'data-bind': 'click:$parent.removeItem'
        }).appendTo($tools);
        $remove.html('<i class="fa fa-times"></i>');

        var $form = $('<form>').addClass('form-inline').appendTo($paneBody);
        var $formEntry = $('<div>').addClass('form-group todo-entry').appendTo($form);
        $('<input>').addClass('form-control required').css('width', '100%').attr({
            'type': 'text'
            , placeholder: '请输入...'
            , 'data-bind': 'textInput:inputValue'
        }).appendTo($formEntry);

        var $bottomTools = $('<div>').addClass('pull-right').css('marginTop', '10px').appendTo($form);

        var $upload = $('<button>').addClass('btn btn-primary').css('marginRight', '10px').attr({
            'type': 'button',
            'title': '解析文件'
        }).html('<i class="fa fa-file-text"></i>').appendTo($bottomTools);
        $('<button>').addClass('btn btn-primary').attr({
            'type': 'submit',
            'title': '添加字段'
        }).html('<i class="fa fa-plus"></i>').appendTo($bottomTools);
        var viewModel = new FieldConfigViewModel(options);
        var $select = null;
        viewModel.addOptions = function (items) {
            $selectDiv.empty();
            $panelDiv.removeClass('col-xs-12').addClass('col-xs-8');
            $selectDiv.addClass('col-xs-4');
            $select = $('<select>').attr('multiple', 'multiple').appendTo($selectDiv);
            $select.selectMultiple({
                selectableHeader: "<input type='text' class='form-control search-input' autocomplete='off' placeholder='请输入...'>",
                afterInit: function (ms) {
                    var that = this,
                        $selectableSearch = that.$selectableUl.prev(),
                        selectableSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selectable';
                    that.qs1 = $selectableSearch.quicksearch(selectableSearchString).on('keydown', function (e) {
                        if (e.which === 40) {
                            that.$selectableUl.focus();
                            return false;
                        }
                    });
                }
                , afterSelect: function (values) {
                    $(values).each(function (i, value) {
                        viewModel.addItem(value);
                    });
                    this.qs1.cache();
                },
                afterDeselect: function (values) {
                    $(values).each(function (i, value) {
                        viewModel.removeItem(value);
                    });
                    this.qs1.cache();
                }
            });
            $(items).each(function (i, item) {
                var hasInputItem = viewModel.getItemByName(item);
                if (hasInputItem == null || hasInputItem.remove != 0) {
                    $select.selectMultiple('addOption', {value: item, text: item});
                }
                if(i==(items.length-1)){
                    $select.selectMultiple('refresh');
                }
            });
            viewModel.selectedOption($(this.items()).map(function (i, item) {
                return item.name;
            }).get());
            resizeWrapperLayout();

        }
        viewModel.selectedOption = function (value) {
            if ($select) {
                $select.selectMultiple('select', value);
            }
        }

        viewModel.deSelectedOption = function (value) {
            if ($select) {
                $select.selectMultiple('deselect', value);
            }

        }


        $form.validate({
            submitHandler: viewModel.addInputItem
            , 'errorPlacement': function (error, element) {
                $(error).addClass('control-label').insertAfter($(element));
                $(error).parent('.form-group').addClass('has-error');
            }
            , 'errorElement': 'label'
        });

        ko.applyBindings(viewModel, $row[0]);
        var uploader = WebUploader.create({
            pick: $upload
            // swf文件路径
            , swf: 'static/javascripts/libs/webuploader-0.1.5/Uploader.swf'
            // 文件接收服务端。
            , server: 'system/parseFile.json'
            , chunked: false
            , auto: true

        });

        uploader.on('uploadFinished', function () {
            this.reset();
        });
        uploader.on('uploadSuccess', function (file, response) {
            if (response && response.keys && response.keys.length > 0) {
                viewModel.addOptions(response.keys);
            } else {
                MyToastr.show('未包含任何解析信息', '提示', MyToastr.ERROR);
            }
        });
    }

    FieldConfig.prototype = {};

    return FieldConfig;
});