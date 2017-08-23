package com.salesmanager.business.scheduler.services;

import java.util.List;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.scheduler.model.Scheduler;

public interface SchedulerService extends SalesManagerEntityService<Long, Scheduler>{
	List<Scheduler> findByMerchant(Integer storeId);
}
