package com.aerors.dms.service;

import java.util.Date;

import com.aerors.dms.model.JobModel;

public interface IJobService {
	void save(JobModel job);

	Date scheduleStart(JobModel job) throws Exception;
}
