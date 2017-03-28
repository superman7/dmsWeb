package com.aerors.dms.taskManager.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @工程: dmsWeb
 * @包名: ccom.aerors.th.taskManager.model
 * @描述: 任务组件描述类
 * @作者: yd
 * @版本: V1.0
 * @时间: 2017/3/18 1
 */
@Document(collection = "module")
public class Module  implements Serializable{
	
	@Id
	protected String id;
	protected String moduleName;
	protected int userId;
	protected String path;
	protected String className;
	protected int jobType;
	protected Date createTime;
	protected String description;

	// 冗余：任务类型名称
	protected String jobTypeName;

	public String getId() {
		return id;
	}

	public void setId(int String) {
		this.id = id;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
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

}
