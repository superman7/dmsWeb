package com.aerors.dms.controller;

import java.util.ArrayList;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aerors.dms.enumerate.Constant;
import com.aerors.dms.enumerate.Constant.JOB_TYPE;
import com.aerors.dms.model.JobModel;
import com.aerors.dms.service.IJobService;
import com.alibaba.fastjson.JSONObject;
import com.test.QuartzJobFactoryImpl;

@RestController
@RequestMapping("/job")
public class JobController {
	@Autowired
	private IJobService jobServiceImpl;
	
	@RequestMapping(value = "/archiveJob", method = RequestMethod.GET)
	public ModelMap saveArchiveJob(@RequestParam(name = "jobInfo") String jobInfo){
		ModelMap result = new ModelMap();
        boolean success = false;
        try {
        	JobModel job = JSONObject.parseObject(jobInfo, JobModel.class);
        	
        	//ArchiveTaskJob 设置ArchiveJob相关属性
        	job.setJobType(JOB_TYPE.DATA_IN.getEnumValue().toString());
        	job.setJobFlow(Constant.DEFAULT_DATA_IN_FLOW);
            this.jobServiceImpl.save(job);
            success = true;
            this.jobServiceImpl.scheduleStart(job);
        } catch (Exception e) {
        	e.printStackTrace(	);
        }
        result.put("success", success);

        return result;
	}
	
}
