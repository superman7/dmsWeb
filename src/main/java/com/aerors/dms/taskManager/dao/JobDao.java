
package com.aerors.dms.taskManager.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.aerors.dms.taskManager.model.Flow;
import com.aerors.dms.taskManager.model.Job;

/** 
 * @author yd
 * @version 创建时间：2017年3月19日 上午10:23:38 
 * 任务操作类
 */

public class JobDao {
	
	/**
	 * 添加Job方法
	 * @param job
	 * @return ture为添加成功，flase为添加失败
	 */
	public boolean addJob(Job job){
		return false;
	}
	/**
	 * 根据jobId删除Job
	 * @param jobId
	 * @return
	 */
	public boolean deleteJobById(int jobId){
		return false;
	}
	/**
	 * 根据jobId批量删除Job
	 * @param jobIds
	 * @return
	 */
	public boolean deleteJobsByIds(int[] jobIds){
		return false;
	}
	/**
	 * 根据查询条件，进行Job查询，也可通过查询结果获取Job信息
	 * @param query
	 * @return
	 */
	public List<Job> searchJobs(Query query){
		List<Job> list=null;
		return list;
	}
	/**
	 * 根据JobId查询相对应的flow
	 * @param jobId
	 * @return
	 */
	public Flow searchFlowByJobId(int jobId){
		Flow flow=null;
		return flow;
	}

}
