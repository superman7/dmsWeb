/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 检查变量是否定义
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/4/28 16:58
 */
define(function () {
    'use strict';
    /**
     * 变量如果被定义返回true,否则返回false
     * @param {Object} value
     * @returns {boolean}
     */
    function define(value) {
        return value !== undefined && value != null;
    }

    return define;
});