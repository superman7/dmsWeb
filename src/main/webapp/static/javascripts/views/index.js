/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 主页的入口文件
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/4/27 17:17
 */
require(['../config'], function () {
    require([
            'giscloud/App'
            , 'common'
            , 'css!stylesheets/index.css'
            , 'domReady!']
        , function (App) {
            new App($('.main-content')[0]);
        });
});

