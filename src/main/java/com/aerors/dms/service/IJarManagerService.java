package com.aerors.dms.service;

import com.aerors.dms.model.JarParseModel;
import com.alibaba.fastjson.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.service
 * @描述: jar管理服务接口
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/29 13:28
 */
public interface IJarManagerService {
    /**
     * 检查jar文件是否合法
     *
     * @param {File} f jar文件
     * @return {JSONObject} 信息
     */
    JSONObject check(File f);

    /**
     * 保存解析jar
     *
     * @param {JarParseModel} jpm 解析模型
     * @param {Integer} type 类型0-编辑,1-删除
     */
    void save(JarParseModel jpm, Integer type);

    /**
     * 获取上传的文件解析列表
     *
     * @return {List}
     */
    List<JarParseModel> list();

    /**
     * 保存jar文件
     * @param {MultipartFile} 传入的jar文件
     * @param {JarParseModel} jpm 解析模型
     */
    void saveJar(MultipartFile uFile,JarParseModel jpm) throws IOException;
}
