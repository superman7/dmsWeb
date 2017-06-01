/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */
package com.aerors.dms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aerors.dms.model.JarParseModel;
import com.aerors.dms.model.SystemConfigContentModel;
import com.aerors.dms.service.ISystemConfigContentService;
import com.aerors.dms.taskManager.model.Module;
import com.alibaba.fastjson.JSONObject;

/** 
* @ClassName: SystemConfigContentController 
* @Description: 系统配置参数控制类
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年4月28日 上午9:21:12 
* 
*/
@RestController
@RequestMapping("/systemConfig")
public class SystemConfigContentController {
	@Autowired
	private ISystemConfigContentService systemConfigContentServiceImpl;
	
	@RequestMapping(value="/new", method = RequestMethod.GET)
	public ModelMap addSystemConfigContent(@RequestParam(value = "systemConfig") String systemConfig){
		ModelMap result = new ModelMap();
        boolean success = false;
        try {
        	SystemConfigContentModel sccm = JSONObject.parseObject(systemConfig, SystemConfigContentModel.class);
            this.systemConfigContentServiceImpl.save(sccm);
            success = true;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        result.put("success", success);

        return result;
	}
	@RequestMapping(value="/systemConfigList")
	public ModelMap systemConfigList(){
		ModelMap result = new ModelMap();
		List<SystemConfigContentModel> list = this.systemConfigContentServiceImpl.list();
		for(SystemConfigContentModel sccm : list){
			if(sccm.getModifyFlag().trim().equals("1")){
		    	sccm.setModifyFlag("可以修改");	
			}else{
				sccm.setModifyFlag("不可修改");
			}
		}
		result.put("data", list);
		return result;
	 }
	
}
