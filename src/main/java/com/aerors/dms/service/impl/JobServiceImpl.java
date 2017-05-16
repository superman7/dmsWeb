package com.aerors.dms.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aerors.dms.dao.IJobDao;
import com.aerors.dms.model.JobModel;
import com.aerors.dms.service.IJobService;
import com.aerors.dms.taskManager.quartz.jobImpl.DataJob;

@Service
public class JobServiceImpl implements IJobService{
	@Autowired
    private IJobDao jobDaoImpl;
	@Autowired
	private Scheduler scheduler;
	
	@PostConstruct
    private void init() throws SchedulerException {
		//schedulerFactoryBean 由spring创建注入	
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("mvc-dispatcher-servlet.xml");
//		scheduler = (Scheduler)ctx.getBean("schedulerFactoryBean");
		scheduler.start();
    }
	
	public void save(JobModel job) {
        this.jobDaoImpl.save(job);
	}

	public Date scheduleStart(JobModel job) throws Exception {
		JobDetail scheduleJob = JobBuilder.newJob(DataJob.class).withIdentity(job.getJobName(), job.getJobGroup()).build();
		Trigger scheduleTirgger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).startNow().build();
		if(job.getCronExpression() != null && !job.getCronExpression().equals("")){
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			scheduleTirgger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
		}
		
		JobDataMap contextMap = scheduleJob.getJobDataMap();
		contextMap.put("jobInstance", job);
		return scheduler.scheduleJob(scheduleJob, scheduleTirgger);
	}
}
	