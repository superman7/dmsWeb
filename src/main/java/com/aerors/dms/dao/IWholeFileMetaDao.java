package com.aerors.dms.dao;

import com.aerors.dms.model.WholeFileMeta;

import java.util.List;


/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.dao
 * @描述: 整文件信息的数据库操作接口
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/19 15:41
 */
public interface IWholeFileMetaDao extends IBaseMongoDao<WholeFileMeta> {

    /**
     * 根据文件md5获取文件
     *
     * @param {String} md5 MD5值
     * @return {WholeFileMeta} wholeFileMeta 文件信息
     */
    WholeFileMeta queryByMd5(String md5);

    /**
     * 按修改时间排序获取某文件的子文件集合
     *
     * @param {WholeFileMeta} parent 父文件对象
     * @return {List<WholeFileMeta>} 文件信息集合
     */
    List<WholeFileMeta> getAllWholeFileByParenOrderByTime(WholeFileMeta parent);

    /**
     * 获取指定路径下的所有文件夹集合,并按时间排序
     *
     * @param {WholeFileMeta} parent 父文件对象
     * @return {List} 文件夹集合
     */
    List<WholeFileMeta> getDirectoryByParentOrderByTime(WholeFileMeta parent);

    /**
     * 获取某路径下子文件夹的个数
     *
     * @param {WholeFileMeta} parent 父文件对象
     * @return {long} 个数
     */
    long getDirectoryCountByParent(WholeFileMeta parent);
    /**
     * 根据文件md5获取已上传完成文件
     *
     * @param {String} md5 MD5值
     * @return {WholeFileMeta} wholeFileMeta 文件信息
     */
    WholeFileMeta queryCompleteByMd5(String md5);
    /**
     * 通过父文件和自身名称获取 路径下文件
     * 同路径下同名文件的个数最大为1
     *
     * @param {WholeFileMeta} parent 父文件对象
     * @param {String} name 文件名称
     * @return {WholeFileMeta} 路径下文件
     */
    WholeFileMeta queryByParenAndName(WholeFileMeta parent, String name);
}
