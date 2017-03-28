/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 异常处理类
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/4/28 17:07
 */
define([
    './define'
], function (define) {
    'use strict';
    /**
     * 开发异常 构造方法
     * @param {String} message 异常字符串
     * @constructor
     */
    function DeveloperError(message) {
        /**
         * 'DeveloperError' 标明是由开发者抛出的错误异常
         * @type {string}
         * @readyonly
         */
        this.name = 'DeveloperError';
        /**
         * 异常消息内容
         * @type {String}
         * @readyonly
         */
        this.message = message;

        //IE浏览器没有一个堆栈属性,除非你抛出这个异常
        var stack;
        try {
            throw new Error();
        } catch (e) {
            stack = e.stack;
        }
        /**
         * 如果可用,显示该异常的堆栈消息
         * @type {String}
         * @readyonly
         */
        this.stack = stack;
    }

    DeveloperError.prototype.toString = function () {
        var str = this.name + ' : ' + this.message;
        if (define(this.stack)) {
            str += ' \n ' + this.stack.toString();
        }
        return str;
    }

    return DeveloperError;
});