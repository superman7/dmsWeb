package com.aerors.dms.service.impl;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.dao.IFileOperationDao;
import com.aerors.dms.dao.IJarmanagerDao;
import com.aerors.dms.model.JarParseModel;
import com.aerors.dms.service.IJarManagerService;
import com.aerors.dms.utils.JarUtil;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.service.impl
 * @描述: jar管理的实现类
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/29 13:29
 */
@Service
public class JarManagerServiceImpl implements IJarManagerService {
    @Autowired
    private IJarmanagerDao jarmanagerDaoImpl;
    @Autowired
    private IFileOperationDao fileOperationDaoImpl;


    @PostConstruct
    private void init() {
        List<JarParseModel> list = this.jarmanagerDaoImpl.queryList(null);
        for (JarParseModel jpm : list) {
            this.addOrRemovePattern(jpm, true);
        }
    }

    /**
     * 检查jar文件是否合法
     *
     * @param {File} f jar文件
     * @return {JSONObject} 报错信息
     */
    public JSONObject check(File f) {
        return JarUtil.checkJar(f);
    }

    /**
     * 保存解析jar
     *
     * @param {JarParseModel} jpm 解析模型
     * @param {Integer}       type 类型0-编辑,1-删除
     */
    public void save(JarParseModel jpm, Integer type) {
        String className = this.deleteJar(jpm);
        if (type != null && type == 1) {
            this.jarmanagerDaoImpl.del(jpm);
        } else {
            if (StringUtils.isEmpty(jpm.getClassName())) {
                jpm.setClassName(className);
            }
            this.jarmanagerDaoImpl.save(jpm);
        }
        this.addOrRemovePattern(jpm, type == 1 ? false : true);
    }

    /**
     * 添加或者移除解析
     *
     * @param {JarParseModel} jpm 解析模型
     * @param {booloean}      isAdd 操作类型,true-添加,false-移除
     */
    public void addOrRemovePattern(JarParseModel jpm, boolean isAdd) {
        String pattrenStr = jpm.getParsePattern();
        if (!StringUtils.isEmpty(pattrenStr)) {
            String[] patterns = pattrenStr.split("\n");
            for (String pattern : patterns) {
                Pattern p = Pattern.compile(pattern.trim(), Pattern.CASE_INSENSITIVE);
                if (isAdd) {
                    JarUtil.jarMap.put(p, jpm);
                } else {
                    JarUtil.jarMap.remove(p);
                }

            }
        }
    }

    /**
     * 获取上传的文件解析列表
     *
     * @return {List}
     */
    public List<JarParseModel> list() {
        return this.jarmanagerDaoImpl.queryList(null);
    }

    /**
     * 根据解析模型 删除jar
     *
     * @param {JarParseModel} jpm 继续模型
     * @return String 原模型的类名
     */
    private String deleteJar(JarParseModel jpm) {
        String result = null;
        String pid = jpm.getPid();
        //如果传入了新文件,就查看是否有原文件,有了需要删除旧文件
        if (pid != null) {
            JarParseModel jarParseModel = this.jarmanagerDaoImpl.queryById(pid);
            result = jarParseModel.getClassName();
            if (jarParseModel != null) {
                String fsid = jarParseModel.getFsId();
                if (fsid != null && (!fsid.equals(jpm.getFsId()))) {
                    this.fileOperationDaoImpl.deleteById(fsid);
                }
            }
        }
        return result;
    }

    /**
     * 保存jar文件
     *
     * @param {MultipartFile} 传入的jar文件
     * @param {JarParseModel} jpm 解析模型
     */
    public void saveJar(MultipartFile ufile, JarParseModel jpm) throws IOException {
        this.deleteJar(jpm);
        Object id = this.fileOperationDaoImpl.saveInputStream(ufile.getInputStream(), ufile.getOriginalFilename());
        if (id != null) {
            jpm.setFsId(id.toString());
            this.jarmanagerDaoImpl.save(jpm);
        }

    }
}
