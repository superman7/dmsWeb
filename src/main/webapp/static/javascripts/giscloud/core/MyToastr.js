/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 消息提示类
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/20 16:41
 */
define(['toastr'], function (Toastr) {
    'use strict';
    var option = {
        positionClass: 'toast-top-center'
        , progressBar: true
        , closeButton: true
    }


    return {
        /**
         * 消息提示类
         * @param {String} message 消息体
         * @param {String} title 消息头
         * @param {String} type 消息类型
         */
        show: function (message, title, type) {
            type = type || this.INFO;
            Toastr[type](message, title, option);
        }
        , INFO: 'info'
        , WARRING: 'warning'
        , ERROR: 'error'
        , SUCCESS: 'success'
    };
});