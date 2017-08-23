package com.salesmanager.scheduler.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;

@Entity
@Table(name = "JOB_RESULT", schema=SchemaConstant.SALESMANAGER_SCHEMA)
public class JobResult extends SalesManagerEntity<Long, JobResult>{
	
	private static final long serialVersionUID = 2115708145601246238L;
	@Id
	@Column(name = "JOB_RESULT_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "JOB_RESULT_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@ManyToOne(targetEntity = Scheduler.class)
	@JoinColumn(name="SCHEDULER_ID")
	private Scheduler scheduler;
	
	@ManyToOne(targetEntity = MerchantStore.class)
	@JoinColumn(name="MERCHANT_ID")
	private MerchantStore merchant;
	
	@NotEmpty
	@Column(name = "RESULT", nullable=false)
	private String result;
	

	@Column(name = "DESCRIPTION")
	@Type(type = "org.hibernate.type.StringClobType")
	private String description;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "JOB_DATE")
	private Date jobDate;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Scheduler getScheduler() {
		return scheduler;
	}


	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getJobDate() {
		return jobDate;
	}


	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}


	public MerchantStore getMerchant() {
		return merchant;
	}


	public void setMerchant(MerchantStore merchant) {
		this.merchant = merchant;
	}

	
	
}
