package com.aerors.dms.service;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.service
 * @描述: 空间查询服务
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/1 15:45
 */
public interface IGeoService {
    void  queryByPolygon(JSONObject polygonGeoJson) throws IOException;
}
