
package com.aerors.dms.taskManager.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.aerors.dms.taskManager.model.Flow;
import com.aerors.dms.taskManager.model.Module;

/** 
 * 任务流操作类
 * @author yd 
 * @version 创建时间：2017年3月19日 上午9:33:24 
 * 类说明 
 */

public class FlowDao {
	
	/**
	 * 任务流添加方法
	 * @param flow
	 * @return
	 */
	public boolean addFlow(Flow flow){
		return false;
	}
	/**
	 * 根据flowId删除任务流
	 * @param flowId
	 * @return
	 */
	public boolean deleteFlowById(int flowId){
		return false;
	}
	/**
	 * 根据任务流Id批量删除任务流
	 * @param flowIds
	 * @return
	 */
	public boolean deleteFlowByIds(int[] flowIds){
		return false;
	}
	
	/**
	 * 更新任务流，默认flowId不可更改
	 * @param flow
	 * @return
	 */
	public boolean updateFlow(Flow flow){
		return false;
	}
	/**
	 * 查询任务流
	 * @param query
	 * @return
	 */
	public List<Flow> searchFlows(Query query){
		List<Flow> list=null;
		return list;
	}
	/**
	 * 根据flowId查询相关联的任务组件
	 * @param flowId
	 * @return
	 */
	public List<Module> searchModulesByFlowId(int flowId){
		List<Module> list=null;
		return list;
	}

}
