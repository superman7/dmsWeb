package com.aerors.dms.controller;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.enumerate.Pan;
import com.aerors.dms.model.WholeFileMeta;
import com.aerors.dms.service.IFileOperationService;
import com.aerors.dms.utils.ImageUtils;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.controller
 * @描述: 云盘的各种操作
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/5/3 10:17
 */
@RestController
@RequestMapping("/pan")
public class PanController {
    private static final Logger logger = LoggerFactory.getLogger(PanController.class);

    @Autowired
    private IFileOperationService fileOperationServiceImpl;

    /**
     * 获取某路径下的文件列表
     *
     * @param {String} path 传入的路径
     * @return {JSON} 获取路径结果,格式如{errno:'',list:[]}
     * errno:1--代表出错,0--代表正确
     */
    @RequestMapping(value = "/list.json", produces = "application/json", method = RequestMethod.GET)
    public ModelMap getList(@RequestParam(name = "path", required = false, defaultValue = "") String path) {
        ModelMap mm = new ModelMap();
        //定义错误表示字符;
        int error = Pan.NOERROR;

        List<ModelMap> list = null;
        try {
            List<WholeFileMeta> result = this.fileOperationServiceImpl.getAllWholeFileByDir(path);
            list = this.formatterList(result, false);
        } catch (Exception e) {
            e.printStackTrace();
            error = Pan.HASERROR;
        }
        mm.put("errno", error);
        if (error == Pan.NOERROR) {
            mm.put("list", list);
        }
        return mm;
    }

    private List<ModelMap> formatterList(List<WholeFileMeta> wfmList, boolean isSearch) {
        List<ModelMap> list = new ArrayList<ModelMap>();
        for (WholeFileMeta wfm : wfmList) {
            ModelMap temp = new ModelMap();
            temp.put("id", wfm.getId());
            temp.put("name", wfm.getName());
            JSONObject pathJson = this.formatterPath(wfm);
            if (isSearch) {
                pathJson.put("name", wfm.getParent().getToRootPath());
                pathJson.put("ids", wfm.getParent().getToRootIds());
            }
            temp.put("path", pathJson);
            temp.put("size", wfm.getSize());
            temp.put("dir", wfm.getDir());
            temp.put("mtime", wfm.getMtime());
            list.add(temp);
        }
        return list;
    }

    private JSONObject formatterPath(WholeFileMeta wfm) {
        JSONObject result = null;
        WholeFileMeta parent = wfm.getParent();

        if (parent != null) {
            result = new JSONObject();
            result.put("id", parent.getId());
            result.put("name", parent.getName());
            result = result;
        }
        return result;
    }

    /**
     * 获取某路径下的所有文件夹集合
     *
     * @param {String} path 传入的父文件夹路径
     * @return {List<JSON>} 获取文件夹集合结果
     */
    @RequestMapping(value = "/tree.json", produces = "application/json", method = RequestMethod.GET)
    public List<ModelMap> getTreeNode(@RequestParam(name = "id", required = false) String parendId) {
        List<ModelMap> result = new ArrayList<ModelMap>();


        List<WholeFileMeta> wfmList = this.fileOperationServiceImpl.getDirectoryByPath(parendId);
        for (WholeFileMeta wfm : wfmList) {
            ModelMap temp = new ModelMap();
            temp.put("name", wfm.getName());
            temp.put("id", wfm.getId());
            temp.put("isParent", this.fileOperationServiceImpl.getDirectoryCountByPath(wfm.getId()) > 0);
            result.add(temp);
        }

        return result;
    }

    /**
     * 移动文件路径
     *
     * @param {String[]} movefileids 文件id集合
     * @param {topath}   topath 移动到的路径
     * @return {JSON} 移动成功的文件集合
     */
    @RequestMapping(value = "/moveFiles.json", produces = "application/json", method = RequestMethod.POST)
    public ModelMap moveFiles(@RequestParam(value = "movefileids[]") String[] movefileids, @RequestParam(value = "topath", defaultValue = "") String topath) {
        ModelMap mm = new ModelMap();
        for (String fid : movefileids) {
            this.fileOperationServiceImpl.moveFileToPath(fid, topath);
        }
        mm.put("success", true);
        return mm;
    }

    /**
     * 获取图片的缩略图
     *
     * @param {String} path 图片路径
     * @param {String} size 图片尺寸,如190_240
     * @param {byte[]} response //输出流
     */
    @RequestMapping(value = "/thumbnail", method = RequestMethod.GET)
    public void thumbnail(@RequestParam(name = "path", defaultValue = "") String path
            , @RequestParam(name = "name") String name, @RequestParam(name = "size", required = false, defaultValue = "180_194") String size, HttpServletResponse response) {
        try {
            String[] width_height = size.split("_");
            int width = Integer.parseInt(width_height[0]);
            int height = Integer.parseInt(width_height[1]);
            InputStream is = this.fileOperationServiceImpl.getFileByPathAndName(path, name);
            if (is != null) {
                ImageIO.write(ImageUtils.thumbnailImage(is, width, height), "PNG", response.getOutputStream());
            } else {
                response.reset();
                response.getOutputStream().flush();
            }
        } catch (NumberFormatException e) {
            logger.warn("缩略图尺寸转换失败!");
        } catch (IOException e) {
            logger.warn("输出流获取失败!");
        }
    }

    /**
     * 文件上传
     *
     * @param {MultipartFile} uFile 上传的spring文件
     * @param {String}        id 文件标识
     * @param {long}          size  文件大小
     * @param {Integer}       chunks 总块数
     * @param {Integer}       chunk  分块序号
     * @param {cmd5}          cmd5  分块md5值
     * @throws Exception
     */
    @RequestMapping(value = "/upload.json", method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile uFile,
                       @RequestParam("id") String fid,
                       @RequestParam("size") long size,
                       @RequestParam(name = "chunks", required = false) Integer chunks,
                       @RequestParam(name = "chunk", required = false, defaultValue = "0") Integer chunk,
                       @RequestParam("cmd5") String cmd5) throws Exception {

        fileOperationServiceImpl.uploadFile(uFile, fid, chunk, chunks, cmd5, size);
    }

    /**
     * 改变文件完成状态
     *
     * @param {String} id 文件标识
     * @param {long}   size 文件大小
     * @throws Exception
     */
   /* @RequestMapping(value = "/complete.json", method = RequestMethod.POST)
    public void complete(@RequestParam(value = "fid") String id,
                         @RequestParam("size") long size) {
        this.fileOperationServiceImpl.complete(id, size);
    }
*/

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
    @RequestMapping(value = "/checkFileIsExist.json", method = RequestMethod.POST, produces = "application/json")
    public ModelMap checkFileIsExist(@RequestParam(value = "path", defaultValue = "") String path,
                                     @RequestParam("name") String name,
                                     @RequestParam("size") long size,
                                     @RequestParam("type") String type,
                                     @RequestParam("md5") String md5) throws Exception {
        return fileOperationServiceImpl.checkFileIsExist(path, name, size, type, md5);
    }

    /**
     * 检查分块文件的md5
     *
     * @param {String}  md5 md5值
     * @param {String}  fid 对应文件的标识
     * @param {long}    chunksize 文件分块大小
     * @param {Integer} chunk 对应块顺序
     * @param {long}    size 文件总大小
     * @return {success:boolean} 返回检查结果
     * @throws Exception
     */
    @RequestMapping(value = "/checkChunkMd5.json", method = RequestMethod.POST, produces = "application/json")
    public ModelMap checkMd5(@RequestParam("md5") String md5,
                             @RequestParam("fid") String id,
                             @RequestParam("chunksize") long chunksize,
                             @RequestParam(name = "chunk", required = false, defaultValue = "0") Integer chunk,
                             @RequestParam("size") long size
    ) throws Exception {
        ModelMap mm = new ModelMap();
        mm.put("success", fileOperationServiceImpl.checkChunkMd5(chunk, chunksize, md5, id, size));
        return mm;
    }

    /**
     * 检查文件的md5
     *
     * @param {String} path 文件夹所在路径
     * @param {String} name 文件夹名称
     * @return {Object} 返回处理结果及新的文件信息
     * @throws Exception
     * @Param {String} iscreate 是否创建 1--代表创建,0--代表更新
     */
    @RequestMapping(value = "/createOrEditFile.json", method = RequestMethod.POST, produces = "application/json")
    public ModelMap createOrEditFile(@RequestParam(value = "path", defaultValue = "") String path,
                                     @RequestParam("name") String name, @RequestParam(value = "fid", required = false) String fid) {
        ModelMap mm = new ModelMap();
        boolean success = false;
        try {
            WholeFileMeta wfm = this.fileOperationServiceImpl.createOrEditFile(path, name, fid);
            name = wfm.getName();
            fid = wfm.getId();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        mm.put("success", success);
        mm.put("name", name);
        if (fid != null && fid.trim().length() > 0) {
            mm.put("fid", fid);
        }
        return mm;
    }

    /**
     * 删除文件
     *
     * @param {String[]} fids 文件id集合
     * @return {JSON} 删除成功的文件集合
     */
    @RequestMapping(value = "/delFiles.json", method = RequestMethod.POST, produces = "application/json")
    public ModelMap delFiles(@RequestParam(value = "fids[]") String[] fids) {
        ModelMap mm = new ModelMap();
        List<String> list = new ArrayList<String>();
        for (String fid : fids) {
            try {
                this.fileOperationServiceImpl.delFileByFid(fid);
                list.add(fid);
            } catch (Exception e) {

            }
        }
        mm.put("list", list);
        return mm;
    }

    /**
     * 获取某路径下的文件列表
     *
     * @param {String} searchtext 传入的路径
     * @return {JSON} 获取路径结果,格式如{errno:'',list:[]}
     * errno:1--代表出错,0--代表正确
     */
    @RequestMapping(value = "/search.json", produces = "application/json", method = RequestMethod.GET)
    public ModelMap search(@RequestParam(name = "searchtext", required = false, defaultValue = "") String searchtext) {
        ModelMap mm = new ModelMap();
        //定义错误表示字符;
        int error = Pan.NOERROR;

        List<ModelMap> list = null;
        try {
            list = this.formatterList(this.fileOperationServiceImpl.searchByFileName(searchtext), true);
        } catch (Exception e) {
            error = Pan.HASERROR;
        }
        mm.put("errno", error);
        if (error == Pan.NOERROR) {
            mm.put("list", list);
        }
        return mm;
    }

}
