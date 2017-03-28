package com.aerors.dms.dao.impl;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.dao.ISystemConfigDao;
import com.aerors.dms.model.FieldConfig;

import org.springframework.stereotype.Repository;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.dao.impl
 * @描述:
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/7/20 11:04
 */
@Repository
public class SystemConfigDaoImpl extends ABaseMongoDao<FieldConfig> implements ISystemConfigDao{
    @Override
    protected Class<FieldConfig> getEntityClass() {
        return FieldConfig.class;
    }
}
