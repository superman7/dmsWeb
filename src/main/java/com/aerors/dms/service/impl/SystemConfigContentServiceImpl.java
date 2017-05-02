/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */
package com.aerors.dms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.aerors.dms.dao.ISystemConfigContentDao;
import com.aerors.dms.model.SystemConfigContentModel;
import com.aerors.dms.service.ISystemConfigContentService;

/** 
* @ClassName: SystemConfigContentServiceImpl 
* @Description: 系统参数类的Service层实现类
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年5月2日 上午10:49:23 
* 
*/
public class SystemConfigContentServiceImpl implements ISystemConfigContentService{
	@Autowired
    private ISystemConfigContentDao systemConfigContentDaoImpl;
    
	public void save(SystemConfigContentModel systemConfigContent) {
		this.systemConfigContentDaoImpl.save(systemConfigContent);
	}

}
