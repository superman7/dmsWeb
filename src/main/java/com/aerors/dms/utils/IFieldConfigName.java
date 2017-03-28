package com.aerors.dms.utils;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.utils
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/20 13:25
 */
public interface IFieldConfigName {
    //解析类型
    final String parsetObject = "xml";
    //左上角经纬度
    final String LeftTopLongitude = "LeftTopLongitude";
    final String LeftTopLatitude = "LeftTopLatitude";
    //左下角经纬度
    final String LeftBottomLongitude = "LeftBottomLongitude";
    final String LeftBottomLatitude = "LeftBottomLatitude";
    //右下角
    final String RightBottomLongitude = "RightBottomLongitude";
    final String RightBottomLatitude = "RightBottomLatitude";
    //右上角
    final String RightTopLongitude = "RightTopLongitude";
    final String RightTopLatitude = "RightTopLatitude";

    final String TOP ="top";
    final String LEFT ="left";
    final String BOTTOM ="bottom";
    final String RIGHT ="right";
}
