/**
 * 
 */
package com.aerors.dms.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aerors.dms.taskManager.model.Module;
import com.aerors.dms.taskManager.service.ITaskManagerService;
import com.aerors.dms.taskManager.service.impl.TaskManagerServiceImpl;

/** 
 * @author 作者 E-mail: 
 * @version 创建时间：2017年3月19日 下午2:24:26 
 * 任务组件控制器类
 * 类说明 
 */

@RestController
@RequestMapping("/searchModule")
public class ModuleController {
	@Autowired
	private ITaskManagerService taskManagerManager;
	 @RequestMapping(value = "/searchModule")
	    public ModelAndView searchModule() {
		 System.out.println("!!!!!!!!!!!!!!");
	        return new ModelAndView("searchModule");
	    }
	 @RequestMapping(value = "/addMoudle")
	 public ModelAndView addMoudle(){
		 System.out.println("!!!!!!!!!!!!!!");
	        return new ModelAndView();
	 }
	 @RequestMapping(value="/moduleList")
	 public ModelMap moduleList(){
		    ModelMap result = new ModelMap();
	        List<Module> list = this.taskManagerManager.searchModules();
	        result.put("data", list);
	        return result;
	 }

}
