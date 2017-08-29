package com.salesmanager.shop.configuration.scheduler;

public enum SchedulerType {
	EMAIL_MARKETING("Marketing Email"),
	PRODUCT_PROMOTIONS("Product Promotions"),
	ORDER_PROMOTIONS("Orde Promotions");
	
	private String value;
	 
	private SchedulerType(String value) {
		this.value = value;
	}
}
