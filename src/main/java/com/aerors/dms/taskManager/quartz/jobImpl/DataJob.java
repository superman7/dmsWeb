package com.aerors.dms.taskManager.quartz.jobImpl;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aerors.dms.model.JobModel;

public class DataJob implements InterruptableJob{
	
	private static final Logger logger = LoggerFactory.getLogger(DataJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Starting DataJob ......");
		JobModel job = (JobModel) context.getMergedJobDataMap().get("jobInstance");
		System.out.println(job.getJobName());
	}

	public void interrupt() throws UnableToInterruptJobException {
		
	}

}
