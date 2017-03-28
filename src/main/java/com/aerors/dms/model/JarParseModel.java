package com.aerors.dms.model;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.model
 * @描述: jar模型
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/28 14:06
 */
public class JarParseModel implements Serializable {
    @Id
    private String pid;

    private String parsePattern;

    private String des;

    private String className;

    private String fsId;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getParsePattern() {
        return parsePattern;
    }

    public void setParsePattern(String parsePattern) {
        this.parsePattern = parsePattern;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFsId() {
        return fsId;
    }

    public void setFsId(String fsId) {
        this.fsId = fsId;
    }
}
