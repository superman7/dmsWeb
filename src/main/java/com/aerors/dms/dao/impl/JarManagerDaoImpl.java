package com.aerors.dms.dao.impl;
/**
 * Copyright © 2000-2016 西安航天天绘数据技术有限公司地理信息与制图室所有
 */

import com.aerors.dms.dao.IJarmanagerDao;
import com.aerors.dms.model.JarParseModel;

import org.springframework.stereotype.Repository;

/**
 * @工程: gisplatform
 * @包名: com.aerors.th.gis.dao.impl
 * @描述: jar管理持久化实现
 * @作者: 巩志远(iamlgong@163.com)
 * @版本: V1.0
 * @时间: 2016/8/1 13:50
 */
@Repository
public class JarManagerDaoImpl extends ABaseMongoDao<JarParseModel> implements IJarmanagerDao {
    @Override
    protected Class<JarParseModel> getEntityClass() {
        return JarParseModel.class;
    }
}
