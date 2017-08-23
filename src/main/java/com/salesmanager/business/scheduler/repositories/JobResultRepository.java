package com.salesmanager.business.scheduler.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.salesmanager.scheduler.model.JobResult;

@Repository("jobResultRepository")
public interface JobResultRepository extends JpaRepository<JobResult, Long>{
	@Query("select distinct o from JobResult as o  left join fetch o.merchant om where om.id = ?1")
	List<JobResult> findByMerchant(Integer storeId);
}
