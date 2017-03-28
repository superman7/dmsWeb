/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 地球画图工具类
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/25 9:04
 */
define([
    '../core/DeveloperError'
    , 'jquery'
    , 'Cesium'
], function (DeveloperError
    , $) {
    'use strict';
    /**
     * 绘图工具构造方法
     * @param earth
     * @param options {drawEnd:funciton} 选项
     */
    var draw = function (earth, options) {
        if ($.isEmptyObject(earth)) {
            throw new DeveloperError('必须指定地球');
        }
        this.init = function () {
            //绘图句柄
            this.screenSpaceEventHandler = null;
            if (this.pointCollection != null) {
                this.pointCollection.removeAll();
            }
            this.pointCollection = null;
            if (this.polylineEntity != null) {
                this.removeEntity(this.polylineEntity);
            }
            this.polylineEntity = null;
            this.movePoint = null;
            if (this.geometryEntity != null) {
                this.removeEntity(this.geometryEntity);
            }
            this.geometryEntity = null;
            this.drawType = null;
            this.drawing = false;
            if (this.tip != null) {
                this.removeEntity(this.tip);
            }
            this.tip = null;
        }

        /**
         * 激活一个绘图类型
         * @param {String} geometryType  绘图类型,默认为多边形
         */
        this.activate = function (geometryType) {
            this.init();
            this.drawing = true;
            this.drawType = geometryType || this.POLYGON;
            if (this.screenSpaceEventHandler == null) {
                this.screenSpaceEventHandler = new Cesium.ScreenSpaceEventHandler(earth.scene.canvas);
            }
            var that = this;
            this.screenSpaceEventHandler.setInputAction(function (mouseClick) {
                var cartesian = earth.camera.pickEllipsoid(mouseClick.position);
                if (cartesian) {
                    if (!that.containPoint(cartesian)) {
                        that.getPointCollection().add({
                            position: cartesian
                            , color: Cesium.Color.YELLOW
                        });
                    }
                }
            }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
            this._addMousemoveEvent();
            this._addMouseRightEvent();
            this._addDBClickEvent();
        };
        this._addMousemoveEvent = function () {
            var that = this;
            this.screenSpaceEventHandler.setInputAction(function (movement) {
                var cartesian = earth.camera.pickEllipsoid(movement.endPosition);
                if (cartesian) {
                    that.movePoint = cartesian;
                    if (that.tip == null) {
                        that.tip = that.addEntity({
                            label: {
                                text: '单击确认,右键取消,双击结束!'
                                , font: '10px sans-serif'
                                , verticalOrigin: Cesium.VerticalOrigin.BOTTOM
                                , horizontalOrigin: Cesium.HorizontalOrigin.LEFT
                            }
                        });
                    }
                    that.tip.position = cartesian;
                    that._draw();
                }
            }, Cesium.ScreenSpaceEventType.MOUSE_MOVE);
        };
        this._addMouseRightEvent = function () {
            var that = this;
            this.screenSpaceEventHandler.setInputAction(function () {
                var points = that.getPointCollection();
                if (points.length > 0) {
                    points.remove(points.get(points.length - 1));
                    that._draw();
                } else {
                    that.deactivate();
                }

            }, Cesium.ScreenSpaceEventType.RIGHT_CLICK);
        };
        this._addDBClickEvent = function () {
            var that = this;
            this.screenSpaceEventHandler.setInputAction(function () {
                that.deactivate();
            }, Cesium.ScreenSpaceEventType.LEFT_DOUBLE_CLICK);
        };
        /**
         * 解除绘图指令
         */
        this.deactivate = function () {
            if (this.screenSpaceEventHandler != null && !this.screenSpaceEventHandler.isDestroyed()) {
                this.screenSpaceEventHandler.destroy();
            }
            if (this.drawing) {
                this.finishDrawing();
            }
        };
        /**
         * 结束画图动作
         */
        this.finishDrawing = function () {
            this.drawing = false;
            this._draw();
            this.removeEntity(this.tip);
            if ($.isFunction(options.drawEnd)) {
                options.drawEnd(this.getWGS84Points());
            }
        };
        /**
         *
         * @param obj
         * @returns {Entity}
         */
        this.addEntity = function (obj) {
            return earth.entities.add(obj);
        }
        this.removeEntity = function (entity) {
            earth.entities.remove(entity);
            entity = null;
        }
        /**
         * 绘制画图过程
         * @private
         */
        this._draw = function () {
            //绘制图形外边界轮廓
            var points = this.getPoints(), number = points.length;
            if (this.drawing) {
                if (number > 0) {
                    points.push(this.movePoint);
                    points.push(points[0]);
                    if (this.polylineEntity == null) {
                        this.polylineEntity = this.addEntity({
                            polyline: {
                                positions: points
                                , width: 2
                                , material: Cesium.Color.WHITE
                            }
                        });
                    } else {
                        this.polylineEntity.polyline.positions = points;
                    }
                } else {
                    this.removeEntity(this.polylineEntity);
                }
                switch (this.drawType) {
                    case this.POLYGON:
                        if (number > 1) {
                            if (this.geometryEntity == null) {
                                this.geometryEntity = this.addEntity({
                                    polygon: {
                                        hierarchy: points
                                        , material: Cesium.Color.BLUE.withAlpha(0.5)
                                    }
                                });

                            } else {
                                this.geometryEntity.polygon.hierarchy = points;
                            }
                        } else {
                            this.removeEntity(this.geometryEntity);
                        }
                        break;
                }
            } else {
                if (number < 3) {
                    this.getPointCollection().removeAll();
                    this.removeEntity(this.polylineEntity);
                    this.removeEntity(this.geometryEntity);
                }
            }

        };
        /**
         * 获取点集合
         * @returns {PointPrimitiveCollection}
         */
        this.getPointCollection = function () {
            if (this.pointCollection == null) {
                this.pointCollection = new Cesium.PointPrimitiveCollection();
                earth.scene.primitives.add(this.pointCollection);
            }
            return this.pointCollection;
        };

        this.containPoint = function (point) {
            var isContain = false, len = this.getPointCollection().length;
            for (var i = 0; i < len; ++i) {
                var p = this.getPointCollection().get(i);
                var position = p.position;
                if (point.x === position.x && point.y === position.y) {
                    isContain = true;
                    break;
                }
            }
            return isContain;
        }
        /**
         * 获取所有左键选中的点
         * @returns {Array}
         */
        this.getPoints = function () {
            var points = [], len = this.getPointCollection().length;
            for (var i = 0; i < len; ++i) {
                var p = this.getPointCollection().get(i);
                points.push(p.position);
            }
            return points;
        };
        this.getWGS84Points = function () {
            var result = [], points = this.getPoints();
            points = Cesium.Ellipsoid.WGS84.cartesianArrayToCartographicArray(points);
            for (var i = 0, len = points.length; i < len; i++) {
                var point = points[i];
                result.push([Cesium.Math.toDegrees(point.longitude), Cesium.Math.toDegrees(point.latitude)])
            }
            return result;
        }
    }
    draw.prototype.POLYGON = 'polygon';//画一个多边形
    return draw;
});
