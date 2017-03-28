package com.aerors.dms.dao;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.springframework.data.mongodb.core.query.Query;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.dao
 * @描述: mongodbGridFs 的数据存储接口
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/19 14:36
 */
public interface IFileOperationDao {

    /**
     * 来保存文件
     *
     * @param {java.io.File} file 保存的文件
     */
    Object saveFile(File file);

    /**
     * 来保存文件 输入流
     *
     * @param {java.io.InputStream} fis 保存的文件输入流
     * @param {String}              fileName 保存的文件名
     */
    Object saveInputStream(InputStream fis, String fileName);

    /**
     * 通过Md5查询文件
     *
     * @param {Query} query 查询文件
     * @return {GridFSDBFile}
     */
    GridFSDBFile queryByMd5(String md5);

    /**
     * 通过id 删除文件
     *
     * @param {String} id
     */
    void deleteById(String id);

    /**
     * 根据id获取文件
     *
     * @param {String} id
     * @return {InputStream} 文件流
     */
    InputStream querybyById(String fsId);
}
