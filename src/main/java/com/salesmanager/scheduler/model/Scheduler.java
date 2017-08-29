package com.salesmanager.scheduler.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;


@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SCHEDULER", schema=SchemaConstant.SALESMANAGER_SCHEMA)
public class Scheduler extends SalesManagerEntity<Long, Scheduler> implements Auditable{

	private static final long serialVersionUID = 2115708545601246238L;
	@Id
	@Column(name = "SCHEDULER_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "SCHEDULER_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@NotEmpty
	@Column(name = "NAME", nullable=false)
	private String name;
	
	@NotEmpty
	@Column(name = "DESCRIPTION", nullable=false)
	private String description;
	
	@NotEmpty
	@Column(name = "SCHEDULER_TYPE", nullable=false)
	private String type;
	
	@NotEmpty
	@Column(name = "ID_JOB", nullable=false)
	private String job;
	
	@Column(name = "SCHEDULER_REPEAT", nullable=true)
	private String repeat;
	
	@Column(name = "ACTIVE", nullable=true)
	private boolean active;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	private Date startDate; 
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXEC_DATE")
	private Date lastExcecDate;
	
	@ManyToOne(targetEntity = MerchantStore.class)
	@JoinColumn(name="MERCHANT_ID")
	private MerchantStore merchant;
	
	@OneToMany(mappedBy = "scheduler", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<JobResult> jobResult = new ArrayList<JobResult>();
	
	
	@Embedded
	private AuditSection auditSection = new AuditSection();


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	

	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public MerchantStore getMerchant() {
		return merchant;
	}


	public void setMerchant(MerchantStore merchant) {
		this.merchant = merchant;
	}


	public AuditSection getAuditSection() {
		return auditSection;
	}


	public void setAuditSection(AuditSection auditSection) {
		this.auditSection = auditSection;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRepeat() {
		return repeat;
	}


	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Date getLastExcecDate() {
		return lastExcecDate;
	}


	public void setLastExcecDate(Date lastExcecDate) {
		this.lastExcecDate = lastExcecDate;
	}


	public List<JobResult> getJobResult() {
		return jobResult;
	}


	public void setJobResult(List<JobResult> jobResult) {
		this.jobResult = jobResult;
	}
	
	
}
