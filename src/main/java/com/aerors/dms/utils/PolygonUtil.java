package com.aerors.dms.utils;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.vividsolutions.jts.geom.*;
import org.json.simple.JSONObject;
import org.opengis.feature.type.GeometryType;

import java.util.ArrayList;
import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.utils
 * @描述: 多边形工具
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/4 9:23
 */
public class PolygonUtil {
    private static GeometryFactory gf = new GeometryFactory();

    /**
     * 对多边形就行自动切割,以保证每个多边形的经度差不大于180
     *
     * @param {Polygon} polygon 目标多边形
     * @return {List} 多边形数组
     */
    public static List<Geometry> autoCut(Geometry polygon) {
        List<Geometry> results = new ArrayList<Geometry>();
        //获取多边形的外接矩形
        Envelope envelope = polygon.getEnvelopeInternal();
        if (envelope.getMaxY() > 90 || envelope.getMinY() < -90 || envelope.getMinX() < -180 || envelope.getMaxX() > 180) {
            throw new IllegalArgumentException("传入图形的坐标范围有误!");
        }

        //范围大于180时,去中间区域进行分割
        if (envelope.getWidth() > 180) {
            double centerX = (envelope.getMaxX() + envelope.getMinX()) / 2;
            Envelope left = new Envelope(envelope.getMinX(), centerX, envelope.getMinY(), envelope.getMaxY());
            Envelope right = new Envelope(centerX, envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY());
            Geometry leftCut = polygon.intersection(gf.toGeometry(left));
            results.addAll(autoCut(leftCut));
            Geometry rightCut = polygon.intersection(gf.toGeometry(right));
            results.addAll(autoCut(rightCut));
        } else {
            results.add(polygon);
        }
        return results;
    }

    public void CommonGeoJsonToSpringGeoJson(JSONObject commGeoJSON){
        
    }
}
