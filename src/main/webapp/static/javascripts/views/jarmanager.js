/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: jar解析配置中心
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/8 14:12
 */
require(['../config'], function () {
    require([
            'webuploader'
            , 'giscloud/core/MyToastr'
            , 'common'
            , 'jquery.validate-zh'
            , 'datatables.net-bs'
            , 'css!stylesheets/table.css'
            , 'domReady!']
        , function (WebUploader, MyToastr) {
            $.fn.serializeObject = function () {
                var o = {};
                var a = this.serializeArray();
                $.each(a, function () {
                    if (o[this.name] !== undefined) {
                        if (!o[this.name].push) {
                            o[this.name] = [o[this.name]];
                        }
                        o[this.name].push(this.value || null);
                    } else {
                        o[this.name] = this.value || null;
                    }
                });
                return o;
            }
            $.fn.loadFormData = function (obj) {
                var key, value, tagName, type, arr;
                for (var x in obj) {
                    key = x;
                    value = obj[x];
                    $("[name='" + key + "'],[name='" + key + "[]']").each(function () {
                        tagName = $(this)[0].tagName;
                        type = $(this).attr('type');
                        if (tagName == 'INPUT') {
                            if (type == 'radio') {
                                $(this).attr('checked', $(this).val() == value);
                            } else if (type == 'checkbox') {
                                arr = value.split(',');
                                for (var i = 0; i < arr.length; i++) {
                                    if ($(this).val() == arr[i]) {
                                        $(this).attr('checked', true);
                                        break;
                                    }
                                }
                            } else {
                                $(this).val(value);
                            }
                        } else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
                            $(this).val(value);
                        }

                    });
                }
            }

            var uploader = WebUploader.create({
                pick: {
                    id: '#chooseFileBtn'
                    , multiple: false
                }
                , accept: {
                    extensions: 'jar'
                }
                // swf文件路径
                , swf: 'static/javascripts/libs/webuploader-0.1.5/Uploader.swf'
                // 文件接收服务端。
                , server: 'jarmanager/check.json'
                , chunked: false
                , auto: false
            });

            var $form = $('#jarForm');
            var tableId = "#configTable";
            var table = $(tableId).DataTable({
                bStateSave: true
                , bLengthChange: false
                , iDisplayLength: 10
                , stripeClasses: ["odd", "even"]//为奇偶行加上样式，兼容不支持CSS伪类的场合
                , serverSide: false   //启用服务器端分页
                , searching: true
                , order: []
                , "oLanguage": {
                    "sUrl": 'static/javascripts/libs/data-tables/i18n/Chinese.json'
                }
                , ajax: 'jarmanager/list.json'
                , columns: [
                    {title: '表达式', defaultContent: '', width: '40%'}
                    , {title: '说明', data: 'des', defaultContent: ''}
                    , {title: '文件', orderable: false, defaultContent: '', width: '30px'}
                    , {title: '操作', orderable: false, className: 'ceterCell', defaultContent: '', width: '40px'}
                ]
                , createdRow: function (row, data) {
                    var array = patternToArray(data.parsePattern);
                    var p = $(array).map(function (index, a) {
                        return a + '<br/>'
                    }).get();
                    $('td', row).eq(0).html(p.join(''));

                    var fsId = data.fsId;
                    if (fsId) {
                        $('td', row).eq(2).html('<i class="fa fa-2x fa-file-zip-o"></i>');
                    }
                    var $editBtn = $('<i>').addClass('fa fa-pencil').attr('title', '编辑').css({
                        'cursor': 'pointer'
                    });
                    $editBtn.click(function () {
                        if (fsId) {
                            showOrHideImageFlag(true);
                        }
                        $form.loadFormData(data);
                    });
                    $('td', row).eq(3).append($editBtn);
                    var $delBtn = $('<i>').addClass('fa  fa-trash-o')
                        .attr('title', '删除').css({
                            'cursor': 'pointer'
                            , 'marginLeft': '10px'
                        });
                    $delBtn.click(function () {
                        var formValues = JSON.stringify({pid: data.pid});
                        saveJarModel(formValues, 1);
                    })
                    $('td', row).eq(3).append($delBtn);
                }
            });
            table.on('draw.dt', function () {
                resizeWrapperLayout();
            });


            uploader.on('uploadSuccess', function (file, response) {
                if (response) {
                    if (response.success) {
                        reloadTable();
                    } else {
                        if (response.error) {
                            MyToastr.show(response.error, '错误', MyToastr.ERROR);
                        }
                    }
                }
            });

            uploader.on('fileQueued', function () {
                showOrHideImageFlag(true);
            });
            uploader.on('beforeFileQueued', function () {
                resetForm();
            });


            uploader.on('uploadError', function () {
                MyToastr.show('文件上传错误!', '错误', MyToastr.ERROR);
                resetForm();
            });
            uploader.on('error', function (type) {
                if (type == 'Q_TYPE_DENIED') {
                    MyToastr.show('请选择正确的文件类型!', '文件类型错误', MyToastr.ERROR);
                }
            });

            $form.validate({
                'errorPlacement': function (error, element) {
                    $(error).addClass('control-label').insertAfter($(element));
                    $(error).parents('.form-group').addClass('has-error');
                }
                , 'errorElement': 'label'
                , debug: true
            });
            //点击提交
            $('#submitBtn').click(function () {
                if ($form.valid()) {
                    //现有表格中的解析类型
                    var data = table.data(), length = data.length;

                    var formJsonValue = $form.serializeObject();
                    //现有解析项目大于0时,需要查询添加的解析是否已有解析类型
                    if ((length > 1) || (length == 1 && !(formJsonValue.pid))) {
                        var patternstr = $(data).map(function (index, item) {
                            return item.parsePattern;
                        }).get().join(',');
                        var newPatterns = patternToArray(formJsonValue.parsePattern);
                        for (var i = 0, newLength = newPatterns.length; i < newLength; i++) {
                            var newPattern = newPatterns[i];
                            if (patternstr.indexOf(newPattern) > -1) {
                                MyToastr.show('解析格式(' + newPattern + ')有重复!', '错误', MyToastr.ERROR);
                                return;
                            }
                        }
                    }
                    //组装表单数据
                    var formValues = JSON.stringify(formJsonValue);
                    var files = uploader.getFiles();
                    if (files.length > 0) {
                        uploader.option('formData', {jpm: formValues});
                        uploader.upload();
                    } else {
                        saveJarModel(formValues, 0)
                    }
                }
            });
            //点击提交
            $('#resetBtn').click(function () {
                resetForm(true);
            });

            function patternToArray(pattern) {
                return $(pattern.split('\n')).map(function (index, item) {
                    return item.trim();
                }).get();
            }


            var $imageFlag = $('#chooseFlag');
            $imageFlag.click(function () {
                resetForm();
            });
            /**
             * 移除上传容器中的文件
             */
            function resetForm(flag) {
                uploader.reset();
                $('#fsInput').val('');
                showOrHideImageFlag(false);
                if (flag) {
                    $form[0].reset();
                }
            }

            function reloadTable() {
                resetForm(true);
                table.ajax.reload();
            }


            function saveJarModel(formValues, type) {
                type = type || 0;
                $.ajax({
                    type: 'POST'
                    , url: 'jarmanager/save.json'
                    , data: {
                        jpm: formValues
                        , type: type //0为新增,1是删除
                    }
                    , cache: false
                    , success: function (res) {
                        if (res && res.success) {
                            reloadTable();
                        }
                    }
                    , error: function () {
                        MyToastr.show('请求失败!', '错误', MyToastr.ERROR);
                    }
                })
            }

            /**
             * 显示或隐藏上传的文件标识
             * @param flag
             */
            function showOrHideImageFlag(flag) {
                if (flag) {
                    $imageFlag.show();
                } else {
                    $imageFlag.hide();
                }
            }
        });
});
