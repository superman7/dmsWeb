package com.aerors.dms.model;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.BasicDBObject;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Set;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.model
 * @描述: 解析地段配置类
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/20 10:44
 */
public class FieldConfig implements Serializable {
    @Id
    private String id;
    private String title;
    private int flag; //0--不能删除,1--可删除
    private BasicDBObject fields = new BasicDBObject();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public BasicDBObject getFields() {
        return fields;
    }

    public void setFields(BasicDBObject fields) {
        this.fields = fields;
    }

}
