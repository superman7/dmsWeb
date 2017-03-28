/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */
/**
 * @工程: gisplatform
 * @描述: 根据文件名获取图标的css名
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/13 16:19
 */
define(function () {
    var typePattern = {
        'exe': /\.exe$/i,
        'word': /\.(doc|docx)$/i,
        'excel': /\.(xls|xlsx)$/i,
        'pic': /\.(jpg|jpeg|gif|bmp|png|jpe|cur|svg|svgz|tif|tiff|ico)$/i,
        'pdf': /\.pdf$/i,
        'txt': /\.txt$/i,
        'zip': /\.zip$/i,
        'rar': /\.rar$/i,
        'ppt': /\.(ppt|pptx)$/i,
        'mp3': /\.(wma|wav|mp3|aac|ra|ram|mp2|ogg|aif|mpega|amr|mid|midi|m4a)$/i,
        'dws': /\.(dws|dwf|dwt|dwg)$/i,
        'mmap': /\.mmap$/i,
        'xmind': /\.xmind$/i,
        'mm': /\.mm$/i,
        'video': /\.(wmv|rmvb|mpeg4|mpeg2|flv|avi|3gp|mpga|qt|rm|wmz|wmd|wvx|wmx|wm|swf|mpg|mp4|mkv|mpeg|mov|asf|m4v)$/i
    };
    function getCssNameByFileName(name){
        var cssName = 'default';
        for (var key in typePattern) {
            if (typePattern[key].test(name)) {
                cssName = key;
                break;
            }
        }
        return cssName;
    }
    return getCssNameByFileName;
});