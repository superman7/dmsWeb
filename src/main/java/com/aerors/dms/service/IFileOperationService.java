package com.aerors.dms.service;

import com.aerors.dms.model.WholeFileMeta;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.service
 * @描述: 文件操作服务接口
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/19 14:43
 */
public interface IFileOperationService {

    /**
     * 是否存在相应md5值的分块文件
     *
     * @param {Integer} chunk 文件块位置
     * @param {Long}    chunksize 文件分块大小
     * @param {Stirng}  md5 md5字符串
     * @param {String}  fid 对应文件的标识
     * @param {Long}    size 文件总大小
     * @return {boolean}
     */
    boolean checkChunkMd5(Integer chunk, long chunksize, String md5, String fid, long size) throws Exception;

    /**
     * 文件上传
     *
     * @param {MultipartFile} uFile 上传的spring文件
     * @param {String}        id 文件标识
     * @param {Integer}       chunks 总块数
     * @param {Integer}       chunk  分块序号
     * @param {cmd5}          cmd5  分块md5值
     * @param {long}          size  文件大小
     * @throws Exception
     */
    void uploadFile(MultipartFile uFile, String id, Integer chunks, Integer chunk, String cmd5, long size) throws Exception;

    /**
     * 通过路径文件Id,获取子文件集合
     *
     * @param {String} pathFileId 路径文件id
     * @return {List} list 集合
     */
    List<WholeFileMeta> getAllWholeFileByDir(String pathFileId);


    /**
     * 根据文件路径和名称获取文件流
     *
     * @param {String} path 文件路径
     * @param {String} name 文件名称
     * @return {InputStream} 文件流
     */
    InputStream getFileByPathAndName(String path, String name);

    /**
     * 改变文件完成状态
     *
     * @param {String} id 文件标识
     * @param {long}   size 文件大小
     */
    void complete(String id, long size);

    /**
     * 创建新文件或者编辑文件
     *
     * @param {String} path 文件路径
     * @param {String} name文件名称
     * @return {WholeFileMeta} wholeFileMeta 返回新的文件对象
     * @Param {String} fid 文件id
     */
    WholeFileMeta createOrEditFile(String path, String name, String fid);

    /**
     * 通过文件的的id删除文件对象
     *
     * @param {String} fid
     */
    void delFileByFid(String fid);

    /**
     * 通过文件名搜索相关的文件集合
     *
     * @param {String} searchtext
     * @return {List} 文件信息集合
     */
    List<WholeFileMeta> searchByFileName(String searchtext);

    /**
     * 获取指定路径下的所有文件夹集合
     *
     * @param {String} path 传入的父文件夹路径
     * @return {List} 文件夹集合
     */
    List<WholeFileMeta> getDirectoryByPath(String path);

    /**
     * 获取某路径下子文件夹的个数
     *
     * @param {String} path
     * @return {long} 个数
     */
    long getDirectoryCountByPath(String path);

    /**
     * 通过文件的的id更改文件存储路径
     *
     * @param {String} fid 文件id
     * @param {String} toPath 文件要移动的路径
     */
    void moveFileToPath(String fid, String toPath);

    /**
     * 检查文件是否存在
     *
     * @param {String} path 文件路径
     * @param {String} name 文件名称
     * @param {long}   size 文件大小
     * @param {String} type 文件类型
     * @param {String} md5 md5值
     * @return {ModelMap} {success:boolean,name:name} 当true的时候,返回当时的文件名称
     */
    ModelMap checkFileIsExist(String path, String name, long size, String type, String md5) throws Exception;


}
