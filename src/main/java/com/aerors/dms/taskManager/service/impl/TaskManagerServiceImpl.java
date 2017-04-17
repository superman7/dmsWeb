package com.aerors.dms.taskManager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.aerors.dms.taskManager.dao.ModuleDao;
import com.aerors.dms.taskManager.model.Module;
import com.aerors.dms.taskManager.service.IModuleInterface;
import com.aerors.dms.taskManager.service.ITaskManagerService;
/**
 * @工程: dmsWeb
 * @包名: com.aerors.dms.taskManager.service.impl
 * @描述: 任务管理服务接口实现
 * @作者: yd(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2017/03/19
 */
@Service
public class TaskManagerServiceImpl implements ITaskManagerService {
	
	@Autowired
	private ModuleDao moduleDao;

	public int addModule(Module module) throws Exception {
		return 0;
	}

	public void removeModules(int[] moduleIds) throws Exception {
		
	}

	public void modifyModule(Module module) throws Exception {
		
	}

	public IModuleInterface getModuleImplOjbect(int moduleId) throws Exception {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.aerors.dms.taskManager.service.ITaskManagerService#searchModules()
	 */
	public List<Module> searchModules() {
		Query query=new Query();
		List<Module> list=moduleDao.searchModules(query);
		return list;
	}

}
