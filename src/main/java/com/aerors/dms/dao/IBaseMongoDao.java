package com.aerors.dms.dao;
/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */

import org.springframework.core.convert.ConversionService;
import org.springframework.data.mongodb.core.query.Query;

import java.io.Serializable;
import java.util.List;

/**
 * @工程: dmsWeb
 * @包名: com.aerors.dms.dao
 * @描述: mongodb数据库操作的基础接口
 */
public interface IBaseMongoDao<T extends  Serializable> {
    /**
     * 获取满足条件的数据集合
     * @param {Query} query 查询条件
     * @return {List} T 实体对象集合
     */
    List<T> queryList(Query query);
    
    /**
     * 查询一个对象
     * @param {Query} query 查询条件
     * @return T 实体对象
     */
    T queryOne(Query query);

    /**
     * 保存或更新一个对象
     * @param {T} t 实体对象;
     */
    void save(T t);

    /**
     * 插入一个对象
     * @param {T} t 实体对象;
     */
    void insert(T t);

    /**
     * 删除一个对象
     * @param {T} t 实体对象;
     */
    void del(T t);
    
    /**
     * 删除一个对象
     * @param {Object} id 实体对象的id;
     */
    void delById(Object id);
    
    /**
     * 符合某条件的数据个数
     * @param {Query} query 查询条件
     * @return long 实体对象个数
     */
    Long queryCount(Query query);
    
    /**
     * 通过对象id 查询对象
     * @param {Object} id 对象id
     * @return T 实体对象
     */
    T queryById(Object id);

    /**
     * 分页查询数据
     * @param {int} startIndex 开始位置
     * @param {int} pageSize 数据长度
     * @return {List} 数据集合
     */
    List<T> queryPageList(int startIndex, int pageSize);
    
    ConversionService getGenericConversionService();
}