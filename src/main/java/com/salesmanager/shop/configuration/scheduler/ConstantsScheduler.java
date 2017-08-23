package com.salesmanager.shop.configuration.scheduler;

import com.salesmanager.shop.admin.controller.ControllerConstants;

public interface ConstantsScheduler extends ControllerConstants {
	interface Tiles {
		interface Scheduler {
			final String schedulerList = "admin-scheduler-list";
			final String schedulerDetails = "admin-scheduler-details";
		}
		
		interface JobResult {
			final String jobResultList = "admin-jobResult-list";
			final String jobResultDetails = "admin-jobResult-details";
		}
	}
}
