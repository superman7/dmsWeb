/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */
package com.aerors.dms.dataAttr.service;

import java.util.List;

import com.aerors.dms.dataAttr.model.DataAttrModel;
import com.aerors.dms.service.IBaseService;

/** 
* @ClassName: IDataAttrService 
* @Description: DataAttrModel的Service层接口
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年5月18日 上午11:10:10 
* 
*/
public interface IDataAttrService extends IBaseService<DataAttrModel>{
	public List<DataAttrModel> list();
	
	public DataAttrModel stringToDataAttrModel(String param) throws Exception;
}
