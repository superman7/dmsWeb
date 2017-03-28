package com.aerors.dms.model;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.mongodb.BasicDBObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.model
 * @描述: 解析模型类
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/11 15:09
 */
public class ParseModel implements Serializable {

    @Id
    private String id;

    private GeoJsonPolygon geometry;

    @DBRef(lazy = true)
    private WholeFileMeta wholeFile;

    private BasicDBObject parseValue;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GeoJsonPolygon getGeometry() {
        return geometry;
    }

    public void setGeometry(GeoJsonPolygon geometry) {
        this.geometry = geometry;
    }

    public BasicDBObject getParseValue() {
        return parseValue;
    }

    public void setParseValue(BasicDBObject parseValue) {
        this.parseValue = parseValue;
    }

    public WholeFileMeta getWholeFile() {
        return wholeFile;
    }

    public void setWholeFile(WholeFileMeta wholeFile) {
        this.wholeFile = wholeFile;
    }
}
