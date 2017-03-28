package com.aerors.dms.service.impl;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.dao.IFileOperationDao;
import com.aerors.dms.dao.IWholeFileMetaDao;
import com.aerors.dms.model.WholeFileMeta;
import com.aerors.dms.service.IFileOperationService;
import com.aerors.dms.service.IParseService;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.service.impl
 * @描述: 文件操作的具体实现
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/19 14:43
 */
@Service
public class FileOperationServiceImpl implements IFileOperationService {

    @Autowired
    private IFileOperationDao mongoDbGridFsDaoImpl;

    @Autowired
    private IWholeFileMetaDao wholeFileMetaDaoImpl;


    @Autowired
    private IParseService parseServiceImpl;

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
    public boolean checkChunkMd5(Integer chunk, long chunksize, String md5, String fid, long size) throws Exception {
        boolean result = false;
        GridFSFile fileMeta = mongoDbGridFsDaoImpl.queryByMd5(md5);
        if (fileMeta != null && fileMeta.getLength() == chunksize) {
            result = true;
        }
        if (result) {
            WholeFileMeta wfm = this.wholeFileMetaDaoImpl.queryById(fid);
            this.addMd5ToBlocks(chunk, md5, wfm, chunksize, size);
        }

        return result;
    }

    private void addMd5ToBlocks(int chunk, String md5, WholeFileMeta wfm, long chunsize, long size) throws Exception {
        LinkedList<String> blocks = wfm.getBlocks();
        if (!blocks.contains(md5)) {
            while (true) {
                if (chunk >= blocks.size()) {
                    blocks.add("");
                } else {
                    break;
                }
            }
            blocks.set(chunk, md5);
            wfm.setBlocks(blocks);
            wfm.setSize(wfm.getSize() + chunsize);
            if (wfm.getSize() == size) {
                wfm.setIsComplete(1);
            }
            if (wfm.getIsComplete() == 1) {
                this.parseServiceImpl.parse(wfm);
            }
            this.wholeFileMetaDaoImpl.save(wfm);
        }


    }

    /**
     * 文件上传
     *
     * @param {MultipartFile} uFile 上传的spring文件
     * @param {String}        id 文件标识
     * @param {Integer}       chunks 总块数
     * @param {Integer}       chunk  分块序号
     * @param {cmd5}          cmd5  分块md5值
     * @param {long}          size  文件总大小
     * @throws Exception
     */
    public void uploadFile(MultipartFile uFile, String id, Integer chunk, Integer chunks, String cmd5, long size) throws Exception {
        WholeFileMeta wholeFileMeta = this.wholeFileMetaDaoImpl.queryById(id);
        //如果有分块信息,并且总块数大于1,证明文件为分块传输
        String tempName = wholeFileMeta.getName();
        if (chunks != null && chunks > 1) {
            tempName = tempName + "_" + chunks + "_" + chunk;
        }

        wholeFileMeta.setMtime(System.currentTimeMillis());
        mongoDbGridFsDaoImpl.saveInputStream(uFile.getInputStream(), tempName);
        this.addMd5ToBlocks(chunk, cmd5, wholeFileMeta, uFile.getSize(), size);

    }

    /**
     * 通过路径文件Id,获取子文件集合
     *
     * @param {String} pathFileId 路径文件id
     * @return {List} list 集合
     */
    public List<WholeFileMeta> getAllWholeFileByDir(String pathFileId) {

        return this.wholeFileMetaDaoImpl.getAllWholeFileByParenOrderByTime(this.getParent(pathFileId));
    }

    private WholeFileMeta getParent(String pathFileId) {
        WholeFileMeta parent = null;
        if (pathFileId != null && !pathFileId.isEmpty()) {
            parent = new WholeFileMeta();
            parent.setId(pathFileId);
        }
        return parent;
    }

    /**
     * 根据文件路径和名称获取文件流
     *
     * @param {String} pathFileId 路径文件id
     * @param {String} name 文件名称
     * @return {InputStream} 文件流
     */
    public InputStream getFileByPathAndName(String pathFileId, String name) {
        SequenceInputStream sis = null;
        WholeFileMeta wfm = this.wholeFileMetaDaoImpl.queryByParenAndName(this.getParent(pathFileId), name);
        if (wfm != null) {
            List<String> blocks = wfm.getBlocks();
            Vector<InputStream> v = new Vector<InputStream>(blocks.size());
            for (String blockMd5 : blocks) {
                GridFSDBFile block = this.mongoDbGridFsDaoImpl.queryByMd5(blockMd5);
                v.add(block.getInputStream());
            }
            sis = new SequenceInputStream(v.elements());
        }

        return sis;
    }

    /**
     * 改变文件完成状态
     *
     * @param {String} id 文件标识
     * @param {long}   size 文件大小
     */
    public void complete(String id, long size) {
        WholeFileMeta wfm = this.wholeFileMetaDaoImpl.queryById(id);
        if (wfm.getSize() == size) {
            wfm.setIsComplete(1);
        }
        this.wholeFileMetaDaoImpl.save(wfm);
    }

    /**
     * 创建新文件或者编辑文件
     *
     * @param {String} path 文件路径
     * @param {String} name文件名称
     * @return {WholeFileMeta} wholeFileMeta 返回新的文件对象
     * @Param {String} fid 文件id
     */
    public WholeFileMeta createOrEditFile(String path, String name, String fid) {
        WholeFileMeta wholeFileMeta = null;
        if (fid == null || fid.trim().equals("")) {
            wholeFileMeta = new WholeFileMeta();
            wholeFileMeta.setParent(this.getWFMByPath(path));
            wholeFileMeta.setDir(1);
            wholeFileMeta.setName("");
            wholeFileMeta.setMtime(System.currentTimeMillis());
        } else {
            wholeFileMeta = this.wholeFileMetaDaoImpl.queryById(fid);
        }
        if (!wholeFileMeta.getName().equals(name)) {
            wholeFileMeta.setRelname(name);
            name = renameFileForRepace(this.getWFMByPath(path), name);
            wholeFileMeta.setName(name);
            this.wholeFileMetaDaoImpl.save(wholeFileMeta);
        }
        return wholeFileMeta;
    }

    /**
     * 如果某路径下含有相同的文件名,则重新命名
     *
     * @param {WholeFileMeta} parent 父路径文件
     * @param {String}        name 名称
     * @return {String} 返回文件
     */
    private String renameFileForRepace(WholeFileMeta parent, String name) {
        int index = 1;
        String prefixName = "", suffixName = null;
        int pointIndex = name.lastIndexOf(".");

        if (pointIndex == -1) {//未找到扩展名
            suffixName = "";
            prefixName = name;
        } else {
            prefixName = name.substring(0, pointIndex);
            suffixName = name.substring(pointIndex);
        }
        while (true) {
            WholeFileMeta wholeFileMeta = this.wholeFileMetaDaoImpl.queryByParenAndName(parent, name);
            //证明有重复信息文件,需要重命名
            if (wholeFileMeta != null) {
                name = prefixName + "(" + index + ")" + suffixName;
                index += 1;
            } else {
                break;
            }
        }
        return name;
    }

    /**
     * 通过文件的的id删除文件对象
     *
     * @param {String} fid
     */
    public void delFileByFid(String fid) {
        this.wholeFileMetaDaoImpl.delById(fid);
    }

    /**
     * 通过文件名搜索相关的文件集合
     *
     * @param {String} searchtext
     * @return {List} 文件信息集合
     */
    public List<WholeFileMeta> searchByFileName(String searchtext) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(searchtext));
        return this.wholeFileMetaDaoImpl.queryList(query);
    }

    /**
     * 获取指定路径下的所有文件夹集合
     *
     * @param {String} path 传入的父文件夹路径
     * @return {List} 文件夹集合
     */
    public List<WholeFileMeta> getDirectoryByPath(String path) {
        return this.wholeFileMetaDaoImpl.getDirectoryByParentOrderByTime(this.getParent(path));
    }

    /**
     * 获取某路径下子文件夹的个数
     *
     * @param {String} path
     * @return {long} 个数
     */
    public long getDirectoryCountByPath(String path) {
        return this.wholeFileMetaDaoImpl.getDirectoryCountByParent(this.getParent(path));
    }

    /**
     * 通过文件的的id更改文件存储路径
     *
     * @param {String} fid 文件id
     * @param {String} toPath 文件要移动的路径
     */
    public void moveFileToPath(String fid, String toPath) {
        WholeFileMeta wfm = this.wholeFileMetaDaoImpl.queryById(fid);
        wfm.setParent(this.getParent(toPath));
        this.wholeFileMetaDaoImpl.save(wfm);
    }

    /**
     * 检查文件是否存在
     *
     * @param {String} path 文件路径
     * @param {String} name 文件名称
     * @param {long}   size 文件大小
     * @param {String} type 文件类型
     * @param {String} md5 md5值
     * @return {boolean} 是否存在
     */
    public ModelMap checkFileIsExist(String path, String name, long size, String type, String md5) throws Exception {
        //如果存在相同md5值的文件,证明文件可能已上传过,下面要进行详细判断
        ModelMap mm = new ModelMap();
        WholeFileMeta wholeFileMeta = wholeFileMetaDaoImpl.queryByMd5(md5);
        boolean isExist = false;
        if (wholeFileMeta != null && wholeFileMeta.getIsComplete() == 1) {
            if (wholeFileMeta.getType().equals(type) && wholeFileMeta.getSize() == size) {
                isExist = true;
            }
        }
        //如果不存在,创建新文件
        if (wholeFileMeta == null) {
            wholeFileMeta = new WholeFileMeta();
            wholeFileMeta.setMd5(md5);
            wholeFileMeta.setDir(0);
            wholeFileMeta.setType(type);
            wholeFileMeta.setIsComplete(0);
            wholeFileMeta.setBlocks(new LinkedList<String>());
        }

        wholeFileMeta.setParent(this.getWFMByPath(path));
        wholeFileMeta.setMtime(System.currentTimeMillis());
        String oldId = wholeFileMeta.getId();
        if (isExist) {
            wholeFileMeta.setId(null);
            wholeFileMeta.setName(this.renameFileForRepace(wholeFileMeta.getParent(), name));
        } else {
            wholeFileMeta.setName(name);
        }
        wholeFileMeta.setRelname(name);

        this.wholeFileMetaDaoImpl.save(wholeFileMeta);
        this.parseServiceImpl.copy(oldId, wholeFileMeta);
        mm.put("success", isExist);
        mm.put("fid", wholeFileMeta.getId());
        return mm;
    }

    private WholeFileMeta getWFMByPath(String path) {
        //检查路径是否为复杂路径,即是否包含/,如果是检查父文件夹是否创建
        String[] paths = path.split("\\/");
        WholeFileMeta parent = null;
        for (String tempName : paths) {
            if (!tempName.isEmpty()) {
                //同名文件
                WholeFileMeta wfm = this.wholeFileMetaDaoImpl.queryByParenAndName(parent, tempName);
                if (wfm == null) {
                    wfm = new WholeFileMeta();
                    wfm.setName(tempName);
                    wfm.setDir(1);
                    wfm.setParent(parent);
                    this.wholeFileMetaDaoImpl.insert(wfm);
                }
                parent = wfm;
            }
        }
        return parent;
    }
}
