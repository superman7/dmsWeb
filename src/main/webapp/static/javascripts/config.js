/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @包名:
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/4/27 17:01
 */
require.config({
    baseUrl: 'static'
    , paths: {
        'jquery': 'javascripts/libs/jquery-1.12.3.min'
        , 'ko': 'javascripts/libs/knockout/knockout'
        , 'css': 'javascripts/libs/css.min'
        , 'bootstrap': 'javascripts/libs/bootstrap-3.3.5/js/bootstrap.min'
        , 'giscloud': 'javascripts/giscloud'
        , 'domReady': 'javascripts/libs/requirejs-2.1.9/domReady'
        , 'jquery.nicescroll': 'javascripts/libs/jquery.nicescroll.min'
        , 'common': 'javascripts/common'
        , 'html5shiv': 'javascripts/libs/html5shiv/html5shiv.min'
        , 'respond': 'javascripts/libs/respond/respond.min'
        , 'webuploader': 'javascripts/libs/webuploader-0.1.5/webuploader.min'
        , 'toastr': 'javascripts/libs/toastr/toastr.min'
        , 'draggabilly': 'javascripts/libs/draggabilly/draggabilly.pkgd.min'
        , 'zTree': 'javascripts/libs/zTree/js/jquery.ztree.all.min'
        , 'contextjs': 'javascripts/libs/contextjs'
        , 'viewer': 'javascripts/libs/viewer/viewer.min'
        , 'datatables.net': 'javascripts/libs/data-tables/js/jquery.dataTables.min'
        , 'datatables.net-bs': 'javascripts/libs/data-tables/js/dataTables.bootstrap.min'
        , 'Cesium': 'javascripts/libs/cesium/1.23/Cesium/Cesium'
        , '../jquery.validate.min': 'javascripts/libs/validate/jquery.validate'
        , 'jquery.validate-zh': 'javascripts/libs/validate/localization/messages_zh.min'
        , 'jquery.select-multiple': 'javascripts/libs/jquery.select-multiple/js/jquery.select-multiple'
        , 'jquery.quicksearch': 'javascripts/libs/jquery.select-multiple/js/jquery.quicksearch'
    }
    , shim: {
        'bootstrap': ['jquery', 'css!bootstrap/../../css/bootstrap.min.css', 'css!stylesheets/style.css', 'css!stylesheets/style-responsive.css']
        , 'jquery.nicescroll': ['jquery']
        , 'common': ['bootstrap', 'jquery.nicescroll']
        , 'webuploader': ['bootstrap', 'css!webuploader/../webuploader.css']
        , 'toastr': ['css!toastr/../toastr.min.css']
        , 'draggabilly': ['jquery']
        , 'zTree': ['jquery', 'css!zTree/../../css/metroStyle/metroStyle.css']
        , 'contextjs': ['css!contextjs/../contextjs.css']
        , 'viewer': ['css!viewer/../viewer.min.css']
        , 'datatables.net-bs': ['css!datatables.net-bs/../../css/dataTables.bootstrap.min.css']
        , 'Cesium': ['css!Cesium/../Widgets/widgets.css']
        , 'jquery.select-multiple': ['jquery.quicksearch', 'css!jquery.select-multiple/../../css/select-multiple.css']
        , 'jquery.quicksearch': ['jquery']
    }
});
