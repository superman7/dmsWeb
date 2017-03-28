package com.aerors.dms.service;

import com.aerors.dms.model.WholeFileMeta;

import org.json.simple.JSONObject;
import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.core.geo.GeoJson;

import java.io.IOException;
import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.service
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/11 15:25
 */
public interface IParseService {
    /**
     * 如果文件为xml,将进行自动解析
     *
     * @param wholeFileMeta
     */
    void parse(WholeFileMeta wholeFileMeta) throws Exception;

    /**
     * 分页查询数据
     *
     * @param {int}    startIndex 开始位置
     * @param {int}    pageSize 数据长度
     * @param {String} orderColumn 排序字段
     * @param {String} asc 正序/倒序
     * @param {String} geometryJson 图形表达,geojson字符串
     * @return {JSONObject} {list:list,total:size}
     */
    JSONObject getListByPage(int startIndex, int pageSize, String orderColumn, String asc, String geometryJson);

    /**
     * 通过 wholeFileMeta对象的旧ID查看,来查看是否需要复制对象
     *
     * @param {String} oldWFMId 原来的xml
     * @param {String} wholeFileMeta 新的文件对象
     */
    void copy(String oldWFMId, WholeFileMeta wholeFileMeta) throws Exception;
}
