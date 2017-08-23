package com.salesmanager.business.scheduler.services;


import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import com.salesmanager.business.scheduler.repositories.JobResultRepository;
import com.salesmanager.business.scheduler.repositories.SchedulerRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.scheduler.model.JobResult;
import com.salesmanager.scheduler.model.Scheduler;

@Service("jobResultService")
public class JobResultServiceImpl extends SalesManagerEntityServiceImpl<Long, JobResult> implements JobResultService{

	JobResultRepository jobResultRepository;
	@PersistenceContext
    private EntityManager em;
	@Inject
	public JobResultServiceImpl(JobResultRepository jobResultRepository) {
		super(jobResultRepository);
		this.jobResultRepository = jobResultRepository;
	}
	@Override
	public List<JobResult> findByMerchant(Integer storeId) {
		
		return jobResultRepository.findByMerchant(storeId);
	}

}
