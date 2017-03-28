package com.aerors.dms.taskManager.service;

import org.quartz.JobExecutionContext;

import com.aerors.dms.dao.IBaseMongoDao;
import com.aerors.dms.taskManager.model.ModuleResult;

public interface IModuleInterface {
	/**
	 * 功能模块执行
	 * 
	 * @param inDir
	 *            数据输入为值
	 * @param outDir
	 *            数据输出位置
	 * @param categoryId
	 *            数据分类标识
	 * @param categoryStencilFilePath
	 *            数据分类模版文件路径
	 * @param jobId
	 *            任务标识
	 * @param log
	 *            日志对象
	 * @param db
	 *            数据库对象
	 * @param osInfo
	 *            操作系统对象信息
	 * @return moduleResult 功能模块运行结果对象
	 */
	ModuleResult executeModule(String inDir, String outDir, Integer categoryId,
			String categoryStencilFilePath, Integer jobId, IBaseMongoDao db, String osInfo, JobExecutionContext context);

}
