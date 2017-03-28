package com.aerors.dms.service.impl;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.service.IGeoService;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;

import org.geotools.geojson.GeoJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.json.simple.JSONObject;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import java.io.IOException;
import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.service.impl
 * @描述: 空间查询服务实现
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/1 15:46
 */
public class GeoServiceImpl implements IGeoService{
    /**
     * 由于mongodb在空间查询时,如果多边形的经度范围大于180时,会查不到到结果
     * 所以在经度范围大于180时,要把多边形分成多个多边形来进行合并查询
     * @param {JSONObject} polygonGeoJson 通用geojson的json表达格式
     * @throws IOException
     */
    public void queryByPolygon(JSONObject polygonGeoJson) throws IOException {
        GeometryJSON gjson = new GeometryJSON();
        Polygon polygon = gjson.readPolygon(polygonGeoJson.toJSONString());


    }
}
