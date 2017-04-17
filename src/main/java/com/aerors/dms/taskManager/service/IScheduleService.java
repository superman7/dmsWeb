package com.aerors.dms.taskManager.service;

import java.util.List;

import com.aerors.dms.model.JobModel;

public interface IScheduleService {

    /**
     * 开始任务
     * 备  注：
     * 时  间： 
     * 作  者： 
     * 单  位： c503
     * 
     * @param jobId
     * @return 请求是否响应成功
     * @throws Exception
     */
    boolean startJob(String jobId) throws Exception;

    /**
     * 暂停任务
     * 备  注：
     * 时  间： 
     * 作  者： 
     * 单  位： c503
     * 
     * @param jobId
     * @return 请求是否响应成功
     * @throws Exception
     */
    boolean pauseJob(String jobId) throws Exception;

    /**
     * 继续暂停任务
     * 备  注：
     * 时  间： 
     * 作  者： 
     * 单  位： c503
     * 
     * @param jobId
     * @return 请求是否响应成功
     * @throws Exception
     */
    boolean resumeJob(String jobId) throws Exception;

    /**
     * 重新开始任务
     * 备  注：
     * 时  间： 
     * 作  者： 
     * 单  位： c503
     * 
     * @param jobId
     * @return 请求是否响应成功
     * @throws Exception
     */
    boolean restartJob(String jobId) throws Exception;

    /**
     * 取消任务，取消任务将删除该任务的所有信息
     * 备  注：
     * 时  间： 
     * 作  者： 
     * 单  位： c503
     * 
     * @param jobId
     * @return 请求是否响应成功
     * @throws Exception
     */
    boolean cancelJob(String jobId) throws Exception;

    /**
     * 停止任务
     * 备  注：
     * 时  间： 
     * 作  者： 
     * 单  位： c503
     * 
     * @param jobId
     * @return 请求是否响应成功
     * @throws Exception
     */
    boolean stopJob(String jobId) throws Exception;

    /**
     * 获取任务状态，列表中只包括当前正在运行的任务
     * 备  注：
     * 时  间： 
     * 作  者： 
     * 单  位： c503
     * 
     * @param jobIds
     * @return
     * @throws Exception
     */
    List<JobModel> getJobCurrentStatus(String[] jobIds) throws Exception;

    /**
     * 获取当前正在运行，等待运行的所有任务列表
     * 备  注：
     * 时  间： 
     * 作  者： 
     * 单  位： c503
     * 
     * @return 结果列表中包括正在运行running，等待运行waiting，就绪ready 的所有任务
     * @throws Exception
     */
    List<JobModel> getCurrentRunningReadyJobs() throws Exception;

    /**
     * 返回正处于RUNNING状态的任务
     * 备  注：
     * 时  间： 
     * 作  者： 
     * 单  位： c503
     * 
     * @return
     * @throws Exception
     */
    List<JobModel> getCurrentRunningJobs() throws Exception;

}
