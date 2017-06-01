/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */
package com.aerors.dms.dataAttr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aerors.dms.dataAttr.model.DataAttrModel;
import com.aerors.dms.dataAttr.service.IDataAttrService;
import com.alibaba.fastjson.JSONObject;

/** 
* @ClassName: DataAttrController 
* @Description: DataAttrModel的操作入口
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年5月18日 下午1:15:03 
* 
*/
@RestController
@RequestMapping("/dataAttr")
public class DataAttrController {
	@Autowired
	private IDataAttrService dataAttrServiceImpl;
	
	@RequestMapping(value="/new", method = RequestMethod.GET)
	public ModelMap addSystemConfigContent(@RequestParam(value = "dataAttr") String dataAttr){
		ModelMap result = new ModelMap();
        boolean success = false;
        try {
        	DataAttrModel sccm = dataAttrServiceImpl.stringToDataAttrModel(dataAttr);
            this.dataAttrServiceImpl.save(sccm);
            success = true;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        result.put("success", success);

        return result;
	}
}
