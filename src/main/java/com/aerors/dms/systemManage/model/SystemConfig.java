package com.aerors.dms.systemManage.model;

import org.springframework.data.annotation.Id;

public class SystemConfig {
	@Id
    private String id;

    private String systemConfigKey;

    private String systemConfigValue;

    private String modifyFlag;

    private String desc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSystemConfigKey() {
		return systemConfigKey;
	}

	public void setSystemConfigKey(String systemConfigKey) {
		this.systemConfigKey = systemConfigKey;
	}

	public String getSystemConfigValue() {
		return systemConfigValue;
	}

	public void setSystemConfigValue(String systemConfigValue) {
		this.systemConfigValue = systemConfigValue;
	}

	public String getModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
