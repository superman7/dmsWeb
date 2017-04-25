package com.aerors.dms.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/** 
* @ClassName: SystemConfigContentModel 
* @Description: 该类为系统参数实体类
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
	
	/** 是否可修改标识：0：不可修改；1:可以修改 **/
	private String modifyFlag;
	
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
	public String getModifyFlag() {
		return modifyFlag;
	}
	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
}
