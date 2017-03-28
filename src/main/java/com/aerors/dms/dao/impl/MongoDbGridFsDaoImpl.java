package com.aerors.dms.dao.impl;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.dao.IFileOperationDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;

import java.io.*;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.dao.impl
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/19 14:38
 */
@Repository
public class MongoDbGridFsDaoImpl implements IFileOperationDao {
    private static final Logger logger = LoggerFactory.getLogger(MongoDbGridFsDaoImpl.class);
    @Autowired
    private GridFsOperations operations;

    /**
     * 使用mongodb 的 gridfs 来保存文件
     *
     * @param {java.io.File} file 保存的文件
     * @return {Object} 保存文件的Id;
     */
    public Object saveFile(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this.saveInputStream(fis, file.getName());
    }

    /**
     * 使用mongodb 的 gridfs 来保存文件 输入流
     *
     * @param {java.io.InputStream} fis 保存的文件输入流
     * @param {String}              fileName 保存的文件名
     * @return {Object} 保存文件的Id;
     */
    public Object saveInputStream(InputStream fis, String fileName) {
        logger.info("[GridFsFile Dao ]save:" + fileName);
        Object id = null;
        try {
            //获取文件输入流
            GridFSFile gridFSFile = operations.store(fis, fileName);
            gridFSFile.save();
            fis.close();
            id = gridFSFile.getId();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 通过Md5查询文件
     *
     * @param {String} md5 md5值
     * @return {GridFSDBFile} 输出流
     */
    public GridFSDBFile queryByMd5(String md5) {
        logger.info("[GridFsFile Dao ]queryByMd5:" + md5);
        Query query = new Query();
        query.addCriteria(Criteria.where("md5").is(md5));
        GridFSDBFile file = operations.findOne(query);
        return file;
    }

    /**
     * 通过id 删除文件
     *
     * @param {String} id
     */
    public void deleteById(String id) {
        this.operations.delete(this.getIdQuery(id));
    }

    /**
     * 根据id获取文件
     *
     * @param {String} id
     * @return {InputStream} 文件流
     */
    public InputStream querybyById(String id) {
        return this.operations.findOne(this.getIdQuery(id)).getInputStream();
    }

    private Query getIdQuery(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return query;
    }
}
