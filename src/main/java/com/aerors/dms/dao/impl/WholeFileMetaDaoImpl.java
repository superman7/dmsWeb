package com.aerors.dms.dao.impl;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.dao.IWholeFileMetaDao;
import com.aerors.dms.model.WholeFileMeta;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.dao.impl
 * @描述: 整文件信息的数据库操作具体实现
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/19 15:42
 */
@Repository
class WholeFileMetaDaoImpl extends ABaseMongoDao<WholeFileMeta> implements IWholeFileMetaDao {
    @Override
    protected Class<WholeFileMeta> getEntityClass() {
        return WholeFileMeta.class;
    }

    /**
     * 根据文件md5获取文件
     *
     * @param md5
     * @return {WholeFileMeta} wholeFileMeta 文件信息
     */
    public WholeFileMeta queryByMd5(String md5) {
        Query query = new Query();
        query.addCriteria(Criteria.where("md5").is(md5));
        return this.queryOne(query);
    }

    /**
     * 按修改时间排序获取某文件的子文件集合
     *
     * @param {WholeFileMeta} parent 父文件对象
     * @return {List<WholeFileMeta>} 文件信息集合
     */
    public List<WholeFileMeta> getAllWholeFileByParenOrderByTime(WholeFileMeta parent) {
        Query query = this.getQueryFormParent(parent);
        query.addCriteria(Criteria.where("isComplete").is(1));
        query.with(new Sort(new Order(Direction.DESC, "mtime")));
        return this.queryList(query);
    }

    /**
     * 获取指定路径下的所有文件夹集合,并按时间排序
     *
     * @param {WholeFileMeta} parent 父文件对象
     * @return {List} 文件夹集合
     */
    public List<WholeFileMeta> getDirectoryByParentOrderByTime(WholeFileMeta parent) {
        Query query = this.getQueryFormParent(parent).addCriteria(Criteria.where("dir").is(1));
        query.with(new Sort(new Order(Direction.DESC, "mtime")));
        return this.queryList(query);
    }

    /**
     * 获取某路径下子文件夹的个数
     *
     * @param {WholeFileMeta} parent 父文件对象
     * @return {long} 个数
     */
    public long getDirectoryCountByParent(WholeFileMeta parent) {
        Query query = this.getQueryFormParent(parent).addCriteria(Criteria.where("dir").is(1));
        return this.queryCount(query);
    }

    /**
     * 根据文件md5获取已上传完成文件
     *
     * @param {String} md5 MD5值
     * @return {WholeFileMeta} wholeFileMeta 文件信息
     */
    public WholeFileMeta queryCompleteByMd5(String md5) {
        Query query = new Query();
        query.addCriteria(Criteria.where("md5").is(md5).andOperator(Criteria.where("isComplete").is(1)));
        return this.queryOne(query);
    }

    /**
     * 通过父文件和自身名称获取 路径下文件
     * 同路径下同名文件的个数最大为1
     *
     * @param {WholeFileMeta} parent 父文件对象
     * @param {String}        name 文件名称
     * @return {WholeFileMeta} 路径下文件
     */
    public WholeFileMeta queryByParenAndName(WholeFileMeta parent, String name) {
        Query query = this.getQueryFormParent(parent).addCriteria(Criteria.where("name").regex("^"+name+"$","gi"));
        return this.queryOne(query);
    }

    private Query getQueryFormParent(WholeFileMeta parent) {
        Query query = new Query();
        Criteria parentCriteria = Criteria.where("parent");
        if (parent == null) {
            parentCriteria.exists(false);
        } else {
            parentCriteria.is(parent);
        }
        query.addCriteria(parentCriteria);
        return query;
    }

}
