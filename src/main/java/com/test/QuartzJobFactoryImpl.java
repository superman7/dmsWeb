package com.test;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class QuartzJobFactoryImpl implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("任务成功运行");
		ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
		System.out.println("任务名称 = [" + scheduleJob.getJobName() + "]");
	}

}
