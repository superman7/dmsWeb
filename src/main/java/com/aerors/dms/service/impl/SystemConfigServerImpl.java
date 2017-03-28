package com.aerors.dms.service.impl;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.dao.ISystemConfigDao;
import com.aerors.dms.model.FieldConfig;
import com.aerors.dms.service.ISystemConfigServer;
import com.aerors.dms.utils.IFieldConfigName;
import com.aerors.dms.utils.ParseUtil;
import com.mongodb.BasicDBObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.List;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.service.impl
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/20 10:55
 */
@Service
public class SystemConfigServerImpl implements ISystemConfigServer {

    @Autowired
    private ISystemConfigDao systemConfigDaoImpl;


//    @PostConstruct
    private void init() {
        //添加默认的配置
        this.systemConfigDaoImpl.save(this.getInitFieldConfig(IFieldConfigName.parsetObject, "需要解析的文件类型", new String[]{"xml"}));

        this.systemConfigDaoImpl.save(this.getInitFieldConfig(IFieldConfigName.LeftTopLongitude, "左上角经度", new String[]{"ProductMetaData.TopLeftLongitude"}));
        this.systemConfigDaoImpl.save(this.getInitFieldConfig(IFieldConfigName.LeftTopLatitude, "左上角纬度", new String[]{"ProductMetaData.TopLeftLatitude"}));

        this.systemConfigDaoImpl.save(this.getInitFieldConfig(IFieldConfigName.LeftBottomLongitude, "左下角经度", new String[]{"ProductMetaData.BottomLeftLongitude"}));
        this.systemConfigDaoImpl.save(this.getInitFieldConfig(IFieldConfigName.LeftBottomLatitude, "左下角纬度", new String[]{"ProductMetaData.BottomLeftLatitude"}));

        this.systemConfigDaoImpl.save(this.getInitFieldConfig(IFieldConfigName.RightBottomLongitude, "右下角经度", new String[]{"ProductMetaData.BottomRightLongitude"}));
        this.systemConfigDaoImpl.save(this.getInitFieldConfig(IFieldConfigName.RightBottomLatitude, "右下角纬度", new String[]{"ProductMetaData.BottomRightLatitude"}));

        this.systemConfigDaoImpl.save(this.getInitFieldConfig(IFieldConfigName.RightTopLongitude, "右上角经度", new String[]{"ProductMetaData.TopRightLongitude"}));
        this.systemConfigDaoImpl.save(this.getInitFieldConfig(IFieldConfigName.RightTopLatitude, "右上角纬度", new String[]{"ProductMetaData.TopRightLatitude"}));
    }

    private FieldConfig getInitFieldConfig(String id, String title, String[] fields) {
        FieldConfig fc = this.getFieldConfig(id, 0);
        if (fields != null) {
            BasicDBObject fieldsDB = fc.getFields();
            for (String field : fields) {
                fieldsDB.put(ParseUtil.repalcePointToX(field), 0);
            }
            fc.setFields(fieldsDB);
        }
        if (title != null) {
            fc.setTitle(title);
        }
        return fc;
    }

    private FieldConfig getFieldConfig(String id, int iscanremove) {
        FieldConfig fc = this.systemConfigDaoImpl.queryById(id);
        if (fc == null) {
            fc = new FieldConfig();
            fc.setId(id);
            fc.setFlag(iscanremove);
        }

        return fc;
    }

    /**
     * 添加字段
     *
     * @param {String} fcid 字段配置模型id
     * @param {String} fieldName 字段名称
     * @param {Stirng} type 操纵类型 0--增加, 1--删除
     * @return {boolean} 成功/失败
     */
    public boolean editField(String fcid, int type, String fieldName) {
        boolean result = false;
        try {
            FieldConfig fc = this.getFieldConfig(fcid, 1);
            BasicDBObject fields = fc.getFields();
            //字段含有.的时候 mongodb 不能作为key 存储
            //为了以后的查询方便,这里把字段中的.使用*代替;
            fieldName = ParseUtil.repalcePointToX(fieldName);
            switch (type) {
                case 0:
                    fields.put(fieldName, 1);
                    break;
                case 1:
                    if (fields.keySet().contains(fieldName)) {
                        if (fields.getInt(fieldName) == 1) {
                            fields.remove(fieldName);
                        }
                    }
                    break;
            }
            fc.setFields(fields);
            this.systemConfigDaoImpl.save(fc);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public long getCount() {
        return this.systemConfigDaoImpl.queryCount(null);
    }

    public List<FieldConfig> getListByPage(int startIndex, int pageSize) {
        return this.systemConfigDaoImpl.queryPageList(startIndex, pageSize);
    }

    /**
     * 更改字段描述信息
     *
     * @param {String} fcid 字段配置模型id
     * @param {String} title 描述信息
     * @return {boolean} 成功/失败
     */
    public boolean editTitle(String fcid, String title) {
        boolean result = false;
        try {
            FieldConfig fc = this.systemConfigDaoImpl.queryById(fcid);
            fc.setTitle(title);
            this.systemConfigDaoImpl.save(fc);
            result = true;
        } catch (Exception e) {

        }
        return result;
    }
}
