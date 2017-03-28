package com.aerors.dms.dao;

import com.aerors.dms.model.ParseModel;
import com.aerors.dms.model.WholeFileMeta;
import com.mongodb.BasicDBObject;

import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.query.Query;

import sun.nio.cs.ext.IBM037;

import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.dao
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/13 15:06
 */
public interface IParseDao extends IBaseMongoDao<ParseModel> {
    /**
     * 分页查询数据
     *
     * @param {int}    startIndex 开始位置
     * @param {int}    pageSize 数据长度
     * @param {String} orderColumn 排序字段
     * @param {String} orderDir 排序样式
     * @param {Query   }    查询条件
     * @return {List} 数据集合
     */
    public List<ParseModel> queryPageList(int startIndex, int pageSize, String orderColumn, String orderDir, Query query);

    /**
     * 分页查询数据
     *
     * @param {int}           startIndex 开始位置
     * @param {int}           pageSize 数据长度
     * @param {String}        orderColumn 排序字段
     * @param {String}        orderDir 排序样式
     * @param {List<GeoJson>} glist 图形集合
     * @return {List} 数据集合
     */
    List<ParseModel> queryPageList(int startIndex, int pageSize, String orderColumn, String orderDir, List<GeoJson> glist);

    /**
     * 通过WholeFileMeta的id 获取解析对象
     *
     * @param {Stirng} oldWFMId WholeFileMeta的id
     * @return {ParseModel} 解析对象
     */
    ParseModel queryByWFMId(String oldWFMId);

    /**
     * 格式化查询条件
     *
     * @param {List<GeoJson>} slist 图形集合
     * @return {Query}
     */
    Query formatterGeoQuery(List<GeoJson> slist);

    /**
     * 格式化查询条件
     *
     * @param {List<GeoJson>} slist 图形集合
     * @param {Query}         query 查询条件
     * @return {Query}
     */
    Query formatterGeoQuery(List<GeoJson> slist, Query query);
}
