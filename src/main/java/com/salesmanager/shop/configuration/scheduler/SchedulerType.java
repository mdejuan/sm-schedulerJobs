package com.salesmanager.shop.configuration.scheduler;

public enum SchedulerType {
	EMAIL_MARKETING("Marketing Email"),
	PROMOTIONS("Promotions");
	
	private String value;
	 
	private SchedulerType(String value) {
		this.value = value;
	}
}
