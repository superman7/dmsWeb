package com.aerors.dms.taskManager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aerors.dms.taskManager.model.Module;

/**
 * @工程: dmsWeb
 * @包名: com.aerors.dms.taskManager.service
 * @描述: 任务管理服务接口
 * @作者: yd(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2017/03/19
 */

public interface ITaskManagerService {
	
	/**
	 * 添加功能模块
	 * 备  注：
	 * 时  间： 
	 * 作  者： 
	 * 单  位： c503
	 * 
	 * @param module
	 * @throws Exception
	 */
	
	int addModule(Module module) throws Exception;

	/**
	 * 批量删除功能模块
	 * 备  注：
	 * 时  间： 
	 * 作  者： 
	 * 单  位： c503
	 * 
	 * @param moduleIds
	 * @throws Exception
	 */
	void removeModules(int[] moduleIds) throws Exception;

	/**
	 * 修改功能模块
	 * 备  注：
	 * 时  间： 
	 * 作  者： 
	 * 单  位： c503
	 * 
	 * @param module
	 * @throws Exception
	 */
	void modifyModule(Module module) throws Exception;
/**
 * 查询所有功能模块
 * @return
 */
	List<Module> searchModules();

	/**
	 * 获取功能模块实现对象
	 * 备  注：
	 * 时  间： 
	 * 作  者： 
	 * 单  位： c503
	 * 
	 * @param moduleId
	 * @return
	 * @throws Exception
	 */
	IModuleInterface getModuleImplOjbect(int moduleId) throws Exception;

}
