package com.aerors.dms.controller;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.service.IParseService;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.controller
 * @描述: gis数据的操作入口
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/13 10:38
 */
@RestController
@RequestMapping("/gisData")
public class GisDataController {
    @Autowired
    private IParseService parseServiceImpl;

    /**
     * 分页查询
     *
     * @param {int} startIndex 数据起始位置
     * @param {int} pageSize 数据长度  orderColum: "path", orderDIr: "asc"
     * @return
     */
    @RequestMapping(value = "/pagesearch.json", method = RequestMethod.POST)
    public ModelMap pagesearch(
            @RequestParam(name = "startIndex") int startIndex
            , @RequestParam(name = "pageSize") int pageSize
            , @RequestParam(name = "draw") int draw
            , @RequestParam(name = "orderColumn", required = false) String orderColumn
            , @RequestParam(name = "orderDir", required = false) String orderDir
            , @RequestParam(name = "geometry", required = false) String geometryStr
    ) {
        ModelMap result = new ModelMap();
        JSONObject resultJson = this.parseServiceImpl.getListByPage(startIndex, pageSize, orderColumn, orderDir, geometryStr);

        result.put("recordsTotal", resultJson.get("total"));
        result.put("recordsFiltered", resultJson.get("total"));
        result.put("draw", draw);
        result.put("data", resultJson.get("list"));
        return result;
    }
    
   
}
