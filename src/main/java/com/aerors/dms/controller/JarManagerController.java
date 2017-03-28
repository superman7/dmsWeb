package com.aerors.dms.controller;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.model.JarParseModel;
import com.aerors.dms.service.IJarManagerService;
import com.aerors.dms.utils.JarUtil;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.List;


/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.controller
 * @描述: jar管理控制器
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/29 13:25
 */
@RestController
@RequestMapping("/jarmanager")
public class JarManagerController {
    @Autowired
    private IJarManagerService jarManagerServiceImpl;

    @RequestMapping(value = "/check.json", method = RequestMethod.POST)
    public ModelMap parseFile(@RequestParam("file") MultipartFile uFile, @RequestParam(value = "jpm") String jpmStr) {
        ModelMap result = new ModelMap();
        boolean success = false;
        String error = null;
        JSONObject checkResult = null;
        try {
            String name = uFile.getOriginalFilename();
            if (name != null && name.toLowerCase().endsWith(JarUtil.TYPE)) {
                File f = File.createTempFile(uFile.getOriginalFilename(), JarUtil.TYPE);
                uFile.transferTo(f);
                checkResult = this.jarManagerServiceImpl.check(f);

                JarUtil.removeFile(f);

                if (checkResult.getBoolean("success")) {
                    JarParseModel jpm = JSONObject.parseObject(jpmStr, JarParseModel.class);
                    jpm.setClassName(checkResult.getString("className"));
                    this.jarManagerServiceImpl.saveJar(uFile, jpm);
                    success = true;
                }else{
                    error = checkResult.getString("error");
                }
            } else {
                error = "不是有效的jar文件";
            }
        } catch (Exception e) {
            error = "操作异常!";
        }
        result.put("success", success);
        result.put("error", error);
        return result;
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
    public ModelMap list() {
        ModelMap result = new ModelMap();
        List<JarParseModel> list = this.jarManagerServiceImpl.list();
        result.put("data", list);
        return result;
    }

    @RequestMapping(value = "/save.json", method = RequestMethod.POST)
    public ModelMap saveParseJarModel(@RequestParam(value = "jpm") String jpmStr, @RequestParam(defaultValue = "0") Integer type) {
        ModelMap result = new ModelMap();
        boolean success = false;
        try {
            JarParseModel jpm = JSONObject.parseObject(jpmStr, JarParseModel.class);
            this.jarManagerServiceImpl.save(jpm, type);
            success = true;
        } catch (Exception e) {

        }
        result.put("success", success);

        return result;
    }
}
