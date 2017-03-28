/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 用来设置参数的默认值
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/4/28 16:48
 */
define(function () {
    /**
     * 如果第一个参数不是未定义返回第一个参数,否则返回第二个参数
     * @param {*} a
     * @param {*} b
     * @returns {*}
     */
    function defaultValue(a, b) {
        if (a !== undefined) {
            return a;
        }
        return b;
    }

    return defaultValue;
});