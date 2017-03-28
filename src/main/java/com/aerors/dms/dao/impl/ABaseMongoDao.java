package com.aerors.dms.dao.impl;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.dao.IBaseMongoDao;
import com.aerors.dms.model.WholeFileMeta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.dao.impl
 * @描述: mongodb 数据库操作的抽象类
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/17 13:57
 */
public abstract class ABaseMongoDao<T extends Serializable> implements IBaseMongoDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(ABaseMongoDao.class);
    @Autowired
    protected MongoTemplate mongoTemplate;

    protected abstract Class<T> getEntityClass();

    public T queryOne(Query query) {
        logger.info("[Mongo Dao ]queryOne:" + query);
        return this.mongoTemplate.findOne(query, this.getEntityClass());
    }

    public void save(T t) {
        logger.info("[Mongo Dao ]save:" + t);
        this.mongoTemplate.save(t);
    }

    public void insert(T t) {
        logger.info("[Mongo Dao ]insert:" + t);
        this.mongoTemplate.insert(t);
    }

    public List<T> queryList(Query query) {
        logger.info("[Mongo Dao ]queryList:" + query);
        return this.mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 符合某条件的数据个数
     *
     * @param {Query} query 查询条件
     * @return long 实体对象个数
     */
    public Long queryCount(Query query) {
        return this.mongoTemplate.count(query, this.getEntityClass());
    }

    /**
     * 通过对象id 查询对象
     *
     * @param {Object} id 对象id
     * @return T 实体对象
     */
    public T queryById(Object id) {
        logger.info("[Mongo Dao ]queryById:" + id);

        return this.mongoTemplate.findById(id, this.getEntityClass());
    }
    
    /**
     * 查找集合中所有
     *
     * @param {Object} id 对象id
     * @return T 实体对象
     */
    public List<T> findAll(){
    	logger.info("[Mongo  Dao] findAll");
    	
    	return this.mongoTemplate.findAll(this.getEntityClass());
    }

    /**
     * 删除一个对象
     *
     * @param {T} t 实体对象;
     */
    public void del(T t) {
        this.mongoTemplate.remove(t);
    }

    /**
     * 删除一个对象
     *
     * @param {Object} id 实体对象的id;
     */
    public void delById(Object id) {
        logger.info("[Mongo Dao ]delById:" + id);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        this.mongoTemplate.remove(query, this.getEntityClass());
    }

    /**
     * 分页查询数据
     *
     * @param {int} startIndex 开始位置
     * @param {int} pageSize 数据长度
     * @return {List} 数据集合
     */
    public List<T> queryPageList(int startIndex, int pageSize) {
        return this.queryList(this.toPageQuery(startIndex, pageSize));
    }

    public Query toPageQuery(int startIndex, int pageSize) {
        return this.toPageQuery(startIndex, pageSize, null);
    }

    public Query toPageQuery(int startIndex, int pageSize, Query query) {
        if (query == null) {
            query = new Query();
        }
        query.skip(startIndex);
        query.limit(pageSize);
        return query;
    }

    public ConversionService getGenericConversionService() {
        return this.mongoTemplate.getConverter().getConversionService();
    }
}
