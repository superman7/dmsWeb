package com.aerors.dms.taskManager.model;

import java.sql.Timestamp;
import java.util.ArrayList;



/**
 * @工程: dmsWeb
 * @包名: ccom.aerors.th.taskManager.model
 * @描述: 任务流描述类
 * @作者: yd
 * @版本: V1.0
 * @时间: 2017/3/18 1
 */
public class Flow {


	/**
	 * 功能模块标识分隔符
	 */
	public static final String MODULE_ID_SPLIT = "-";

	protected int id;
	protected String flowName;
	protected int userId;
	protected int jobType;
	protected String steps;
	protected Timestamp createTime;
	protected String description;

	// 冗余：任务类型名称
	protected String jobTypeName;

	// 冗余：功能模块列表
	protected ArrayList<Module> moduleList;

	// 冗余：功能模块名称
	protected String moduleNameStr;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobTypeName() {
		return jobTypeName;
	}

	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
	}

	public ArrayList<Module> getModuleList() {
		return moduleList;
	}

	public void setModuleList(ArrayList<Module> moduleList) {
		this.moduleList = moduleList;

		if (moduleList != null) {
			if (moduleList.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (Module module : moduleList) {
					sb.append(module.getModuleName());
					sb.append(",");
				}
				String nameStr = sb.toString();
				nameStr = nameStr.substring(0, nameStr.length() - 1);
				this.setModuleNameStr(nameStr);
			}
		}
	}

	public String getModuleNameStr() {
		if (this.getModuleList() != null) {
			StringBuilder sb = new StringBuilder();
			for (Module module : moduleList) {
				sb.append(module.getModuleName());
				sb.append(",");
			}
			String nameStr = sb.toString();
			nameStr = nameStr.substring(0, nameStr.length() - 1);
			moduleNameStr = nameStr;
		}
		return moduleNameStr;
	}

	public void setModuleNameStr(String moduleNameStr) {
		this.moduleNameStr = moduleNameStr;
	}
}
