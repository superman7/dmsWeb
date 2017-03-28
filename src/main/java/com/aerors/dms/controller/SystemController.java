package com.aerors.dms.controller;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.service.ISystemConfigServer;
import com.aerors.dms.utils.ParseUtil;

import net.sf.json.JSON;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.controller
 * @描述: 配置中心控制器
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/20 10:47
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private ISystemConfigServer systemConfigServerImpl;

    /**
     * 字段控制--添加字段
     *
     * @param {String} fcid 字段配置模型id
     * @param {String} fieldName 字段名称
     * @return {ModelMap} {success:boolean} 成功/失败
     */
    @RequestMapping(value = "/editField/{fieldName}.json", method = RequestMethod.POST)
    public ModelMap editField(@RequestParam String fcid, @RequestParam int type, @PathVariable String fieldName) {
        ModelMap result = new ModelMap();
        result.put("success", this.systemConfigServerImpl.editField(fcid, type, fieldName));
        return result;
    }

    /**
     * 分页获取 配置信息
     *
     * @param startIndex
     * @param pageSize
     * @param draw
     * @return
     */
    @RequestMapping(value = "list.json", method = RequestMethod.GET)
    public ModelMap list(@RequestParam(name = "start") int startIndex
            , @RequestParam(name = "length") int pageSize
            , @RequestParam(name = "draw") int draw) {
        ModelMap result = new ModelMap();
        long total = this.systemConfigServerImpl.getCount();
        result.put("recordsTotal", total);
        result.put("recordsFiltered", total);
        result.put("draw", draw);
        result.put("data", this.systemConfigServerImpl.getListByPage(startIndex, pageSize));

        return result;
    }

    @RequestMapping(value = "/editTitle.json", method = RequestMethod.POST)
    public ModelMap editTitle(@RequestParam String fcid, @RequestParam String title) {
        ModelMap result = new ModelMap();
        result.put("success", this.systemConfigServerImpl.editTitle(fcid, title));
        return result;
    }

    @RequestMapping(value = "/parseFile.json", method = RequestMethod.POST)
    public ModelMap parseFile(@RequestParam("file") MultipartFile uFile) {
        ModelMap result = new ModelMap();
        try {
            String praseJsonStr = ParseUtil.parseNameFile(uFile.getOriginalFilename(), uFile.getInputStream());
            if (praseJsonStr != null) {

                JSONObject s = JSONObject.fromObject(praseJsonStr);

                TreeSet<String> set = new TreeSet<String>(ParseUtil.getAllKeys(s, null, null).keySet());
                set.comparator();
                result.put("keys", set);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
