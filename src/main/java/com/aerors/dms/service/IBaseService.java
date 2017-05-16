/**
 * Copyright © 2017 西安航天天绘数据技术有限公司制图与地理信息室所有
 */
package com.aerors.dms.service;

import java.util.List;

/** 
* @ClassName: IBaseService 
* @Description: 资源类service基本服务接口
*  
* @author pudge.fan(pudge_fan@163.com) 
* @date 2017年5月11日 下午3:36:04 
* 
*/
public interface IBaseService<T> {
	public void save(T t);
	public void remove(T t);
	public Long count();
	public List<T> findAllWithPage(int startIndex, int pageSize);
	public T queryById(Object id);
	public void removeById(Object id);
}
