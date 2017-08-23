package com.salesmanager.business.scheduler.services;

import java.util.List;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.scheduler.model.JobResult;


public interface JobResultService extends SalesManagerEntityService<Long, JobResult>{
	List<JobResult> findByMerchant(Integer storeId);
}
