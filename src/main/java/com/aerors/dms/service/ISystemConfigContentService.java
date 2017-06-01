/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */
package com.aerors.dms.service;

import java.util.List;

import com.aerors.dms.model.SystemConfigContentModel;

/** 
* @ClassName: ISystemConfigContentService 
* @Description: 系统参数类的Service层接口
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年5月2日 上午10:44:06 
* 
*/
public interface ISystemConfigContentService extends IBaseService<SystemConfigContentModel>{
	 /**
     * 获取系统参数列表
     *
     * @return {List}
     */
   public List<SystemConfigContentModel> list();
}
