/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */
package com.aerors.dms.dataAttr.dao.impl;

import org.springframework.stereotype.Repository;

import com.aerors.dms.dao.impl.ABaseMongoDao;
import com.aerors.dms.dataAttr.dao.IDataAttrDao;
import com.aerors.dms.dataAttr.model.DataAttrModel;

/** 
* @ClassName: DataAttrDaoImpl 
* @Description: DataAttrModel的Dao层实现类
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年5月18日 上午10:58:02 
* 
*/
@Repository
public class DataAttrDaoImpl extends ABaseMongoDao<DataAttrModel> implements IDataAttrDao{

	@Override
	protected Class<DataAttrModel> getEntityClass() {
		return DataAttrModel.class;
	}

}
