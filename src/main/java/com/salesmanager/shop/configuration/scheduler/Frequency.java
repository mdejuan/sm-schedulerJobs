package com.salesmanager.shop.configuration.scheduler;

public enum Frequency {
	DAY("Every Day"),
	WEEK("Every week"),
	MONTH("Every month"),
	ONCE("Only once");
	
	private String value;
	 
	private Frequency(String value) {
		this.value = value;
	}
}
