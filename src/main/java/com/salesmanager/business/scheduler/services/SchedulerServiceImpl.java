package com.salesmanager.business.scheduler.services;


import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import com.salesmanager.business.scheduler.repositories.SchedulerRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.scheduler.model.Scheduler;

@Service("schedulerService")
public class SchedulerServiceImpl extends SalesManagerEntityServiceImpl<Long, Scheduler> implements SchedulerService{

	SchedulerRepository schedulerRepository;
	@PersistenceContext
    private EntityManager em;
	@Inject
	public SchedulerServiceImpl(SchedulerRepository schedulerRepository) {
		super(schedulerRepository);
		this.schedulerRepository = schedulerRepository;
	}
	@Override
	public List<Scheduler> findByMerchant(Integer storeId) {
		
		return schedulerRepository.findByMerchant(storeId);
	}

}
