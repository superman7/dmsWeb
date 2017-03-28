package com.aerors.dms.dao.impl;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.dao.IParseDao;
import com.aerors.dms.model.ParseModel;
import com.aerors.dms.model.WholeFileMeta;
import com.mongodb.BasicDBObject;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Shape;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.dao.impl
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/11 15:22
 */
@Repository
public class ParseDaoImpl extends ABaseMongoDao<ParseModel> implements IParseDao {
    @Override
    protected Class<ParseModel> getEntityClass() {
        return ParseModel.class;
    }

    /**
     * 分页查询数据
     *
     * @param {int}        startIndex 开始位置
     * @param {int}        pageSize 数据长度
     * @param {String}     orderColumn 排序字段
     * @param {String}     orderDir 排序样式
     * @param {List<Shape> } glist 图形集合
     * @return {List} 数据集合
     */
    public List<ParseModel> queryPageList(int startIndex, int pageSize, String orderColumn, String orderDir, List<GeoJson> glist) {
        Query query = this.toPageQuery(startIndex, pageSize, this.formatterGeoQuery(glist));
        Sort.Direction direction = Sort.Direction.ASC;
        if (orderDir != null) {
            if (orderDir.toUpperCase().endsWith("DESC")) {
                direction = Sort.Direction.DESC;
            }
        }
        if (orderColumn == null) {
            orderColumn = "name";
        }
        query.with(new Sort(new Sort.Order(direction, orderColumn)));
        Criteria geoCriteria = new Criteria();
        if (glist != null && glist.size() > 0) {
            Criteria[] criterialist = new Criteria[glist.size()];
            for (int i = 0, len = glist.size(); i < len; i++) {
                criterialist[i] = Criteria.where(this.getGeoField()).intersects(glist.get(i));
            }
            geoCriteria.orOperator(criterialist);
            query.addCriteria(geoCriteria);
        }

        return this.queryList(query);
    }

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
    public List<ParseModel> queryPageList(int startIndex, int pageSize, String orderColumn, String orderDir, Query query) {
        query = this.toPageQuery(startIndex, pageSize, query);
        Sort.Direction direction = Sort.Direction.ASC;
        if (orderDir != null) {
            if (orderDir.toUpperCase().endsWith("DESC")) {
                direction = Sort.Direction.DESC;
            }
        }
        if (orderColumn == null) {
            orderColumn = "name";
        }
        query.with(new Sort(new Sort.Order(direction, orderColumn)));

        return this.queryList(query);
    }

    /**
     * 通过WholeFileMeta的id 获取解析对象
     *
     * @param {Stirng} oldWFMId WholeFileMeta的id
     * @return {ParseModel} 解析对象
     */
    public ParseModel queryByWFMId(String oldWFMId) {
        if (oldWFMId == null || oldWFMId.isEmpty()) {
            return null;
        } else {
            WholeFileMeta wfm = new WholeFileMeta();
            wfm.setId(oldWFMId);
            Query query = new Query();
            query.addCriteria(Criteria.where("wholeFile").is(wfm));
            return this.queryOne(query);
        }
    }

    /**
     * 格式化查询条件
     *
     * @param {List<GeoJson>} slist 图形集合
     * @return {Query}
     */
    public Query formatterGeoQuery(List<GeoJson> slist) {
        return this.formatterGeoQuery(slist, null);
    }

    /**
     * 格式化查询条件
     *
     * @param {List<GeoJson>} slist 图形集合
     * @param {Query}         query 查询条件
     * @return {Query}
     */
    public Query formatterGeoQuery(List<GeoJson> slist, Query query) {
        if (query == null) {
            query = new Query();
        }
        if (slist != null) {
            int size = slist.size();
            Criteria[] criterialist = new Criteria[size];
            for (int i = 0; i < size; i++) {
                criterialist[i] = Criteria.where(this.getGeoField()).intersects(slist.get(i));
            }
            query.addCriteria(new Criteria().orOperator(criterialist));
        }
        return query;
    }


    protected String getGeoField() {
        return "geometry";
    }
}