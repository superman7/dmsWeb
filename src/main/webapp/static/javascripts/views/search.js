/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: cesium的地球
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/12 14:08
 */
require(['../config'], function () {
    require([
            'giscloud/tools/Draw'
            , 'Cesium'
            , 'common'
            , 'datatables.net-bs'
            , 'css!stylesheets/table.css'
            , 'domReady!']
        , function (DrawTool) {
            'use strict';
            Cesium.Camera.DEFAULT_VIEW_RECTANGLE = Cesium.Rectangle.fromDegrees(73, 3, 136, 54);
            var viewer = new Cesium.Viewer('cesiumContainer', {
                imageryProvider: false
                , animation: false
                , baseLayerPicker: false
                , timeline: false
                , infoBox: false
                , geocoder: false
                , sceneModePicker: false
                , selectionIndicator: false
                , navigationHelpButton: false
                , fullscreenElement: 'cesiumContainer'
                , shadows: true
            });
            //viewer.scene.globe.enableLighting = true;
            //去除默认logo
            var creditDisplay = viewer.scene.frameState.creditDisplay;
            var defaultLogo = creditDisplay._defaultImageCredits[0];
            creditDisplay.removeDefaultCredit(defaultLogo);
            addStaticImage('static/images/map/World.jpg');
            var drawTool = null;

            /**
             * 添加影像数据
             *
             * @param {String} url 路径
             * @param {Cesium.Rectangle} rectangle 影像付完
             */
            function addStaticImage(url, rectangle) {
                var imageProvider = new Cesium.SingleTileImageryProvider({
                    url: url
                    , rectangle: rectangle || Cesium.Rectangle.MAX_VALUE
                });
                return viewer.imageryLayers.addImageryProvider(imageProvider);
            }

            var addedImageryLayer = {};

            var tableId = '#resultTable';
            var table = $(tableId).DataTable({
                bStateSave: true
                , autoWidth: true
                , bLengthChange: false
                , iDisplayLength: 5
                , stripeClasses: ["odd", "even"]//为奇偶行加上样式，兼容不支持CSS伪类的场合
                , serverSide: true   //启用服务器端分页
                , searching: false
                , order: []
                , "oLanguage": {
                    "sUrl": 'static/javascripts/libs/data-tables/i18n/Chinese.json'
                }
                , ajax: function (data, callback, settings) {
                    $.post('gisData/pagesearch.json', formatterQueryCondition(data), function (result) {
                        callback(result);
                    });
                }
                , columns: [
                    {orderable: false, defaultContent: '', className: 'details-control'}
                    , {title: '缩略图', orderable: false, defaultContent: ''}
                    , {data: 'name', title: '名称', defaultContent: ''}
                    , {data: 'path.name', title: '路径', defaultContent: ''}
                    , {title: '查看', orderable: false, className: 'ceterCell', defaultContent: ''}
                ]
                , createdRow: function (row, data) {
                    //缩略图
                    if (data.path) {
                        var imagesrc = "pan/thumbnail?name=" + (data.name + '\\Sthumb.jpg') + "&path=" + data.path.id + "&size=80_80";
                        var image = $('<img>').addClass('thumbImage').attr('src', imagesrc);
                        $('td', row).eq(1).append(image);
                        //最后操作
                        var $addToMapBtn = $('<button>').addClass('btn btn-small btn-primary');
                        $('<i>').addClass('fa fa-2x fa-map-marker').appendTo($addToMapBtn);
                        $addToMapBtn.click(function () {
                            var envelope = data.envelope;
                            var rectangle = Cesium.Rectangle.fromDegrees(envelope.west, envelope.south, envelope.east, envelope.north);
                            if (!(data.id in addedImageryLayer)) {

                                if (envelope) {
                                    var url = "pan/thumbnail?name=" + (data.name + '.jpg') + "&path=" + data.path.id + "&size=0_0";
                                    addedImageryLayer[data.id] = addStaticImage(url, rectangle);
                                }
                            }
                            viewer.camera.flyTo({
                                destination: rectangle
                                , complete: function () {
                                    viewer.imageryLayers.raiseToTop(addedImageryLayer[data.id]);
                                }
                            });
                        })
                        $('td', row).eq(4).append($addToMapBtn);
                    }
                }
            });
            table.on('draw.dt', function () {
                resizeWrapperLayout();
            })
            $(tableId + ' tbody ').on('click', 'td.details-control', function () {
                var tr = $(this).closest('tr');
                var row = table.row(tr);
                if (row.child.isShown()) {
                    row.child.hide();
                    tr.removeClass('shown');
                }
                else {
                    row.child(detailsRow(row.data())).show();
                    tr.addClass('shown');
                }
                resizeWrapperLayout();
            });

            /**
             * 绘制按钮
             * @type {*|jQuery|HTMLElement}
             */

            var $drawBtn = $('#drawBtn');
            $drawBtn.click(function () {
                $drawBtn.removeClass('btn-warning');
                $drawBtn.addClass('btn-info');
                if (drawTool == null) {
                    drawTool = new DrawTool(viewer, {
                        drawEnd: function (points) {
                            if (points.length > 2) {
                                $drawBtn.removeClass('btn-info');
                                $drawBtn.addClass('btn-warning');
                            }
                            table.draw();
                        }
                    });
                } else {
                    drawTool.deactivate();
                }
                drawTool.activate(DrawTool.POLYGON);
            });


            function detailsRow(rowData) {
                var envelope = rowData.envelope;
                if (envelope) {
                    return "<div>" +
                        "<p>北纬:" + envelope.north + "</p>" +
                        "<p>南纬:" + envelope.south + "</p>" +
                        "<p>西经:" + envelope.west + "</p>" +
                        "<p>东经:" + envelope.east + "</p>" +
                        "</div>";
                } else {
                    return "";
                }
            }

            /**
             * 组装查询参数
             */
            function formatterQueryCondition(data) {
                var param = {
                    startIndex: data.start
                    , pageSize: data.length
                    , draw: data.draw
                };
                if (drawTool) {
                    var points = drawTool.getWGS84Points();
                    if (points.length >= 3) {
                        points.push(points[0]);
                        param.geometry = JSON.stringify({
                            type: 'Polygon'
                            , coordinates: points
                        })
                    }
                }
                //组装排序参数
                if (data.order && data.order.length > 0) {
                    var orderColumn = data.order[0]
                    param.orderColumn = data.columns[orderColumn.column].data;
                    switch (param.orderColumn) {
                        case 'path.name':
                            param.orderColumn = 'wholeFile.$id';
                            break;
                    }
                    param.orderDir = orderColumn.dir;
                }
                return param;
            }
        });
});
