/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */
package com.aerors.dms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aerors.dms.dao.ISystemConfigContentDao;
import com.aerors.dms.model.SystemConfigContentModel;

/** 
* @ClassName: SystemConfigContentDaoImpl 
* @Description: systemConfigContentModel的Dao层实现类
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年4月28日 上午8:58:01 
* 
*/
@Repository
public class SystemConfigContentDaoImpl extends ABaseMongoDao<SystemConfigContentModel> implements ISystemConfigContentDao {

	@Override
	protected Class<SystemConfigContentModel> getEntityClass() {
		return SystemConfigContentModel.class;
	}

}
