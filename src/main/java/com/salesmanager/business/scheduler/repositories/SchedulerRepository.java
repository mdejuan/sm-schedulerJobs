package com.salesmanager.business.scheduler.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.salesmanager.scheduler.model.Scheduler;

@Repository("schedulerRepository")
public interface SchedulerRepository extends JpaRepository<Scheduler, Long>{
	@Query("select distinct o from Scheduler as o  left join fetch o.merchant om where om.id = ?1")
	List<Scheduler> findByMerchant(Integer storeId);
}
