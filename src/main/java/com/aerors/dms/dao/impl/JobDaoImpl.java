package com.aerors.dms.dao.impl;

import org.springframework.stereotype.Repository;

import com.aerors.dms.dao.IJobDao;
import com.aerors.dms.model.JobModel;

@Repository
public class JobDaoImpl extends ABaseMongoDao<JobModel> implements IJobDao {

	@Override
	protected Class<JobModel> getEntityClass() {
		return JobModel.class;
	}

}
