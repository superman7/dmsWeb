package com.aerors.dms.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/** 
* @ClassName: SystemConfigContentModel 
* @Description: ArchiveTaskJob(这里用一句话描述这个类的作用)
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年4月25日 下午2:19:32 
* 
*/
public class SystemConfigContentModel implements Serializable{

	/** 
	* @Fields serialVersionUID : 序列化ID 
	*/
	private static final long serialVersionUID = 672229665059314844L;

	/** 系统参数id **/
	@Id
	private String systemConfigContentId;
	
	/** 系统参数Key **/
	private String systemConfigContentKey;
	
	/** 系统参数Value **/
	private String systemConfigContentValue;
	
	public String getSystemConfigContentId() {
		return systemConfigContentId;
	}
	public void setSystemConfigContentId(String systemConfigContentId) {
		this.systemConfigContentId = systemConfigContentId;
	}
	public String getSystemConfigContentKey() {
		return systemConfigContentKey;
	}
	public void setSystemConfigContentKey(String systemConfigContentKey) {
		this.systemConfigContentKey = systemConfigContentKey;
	}
	public String getSystemConfigContentValue() {
		return systemConfigContentValue;
	}
	public void setSystemConfigContentValue(String systemConfigContentValue) {
		this.systemConfigContentValue = systemConfigContentValue;
	}
}
