package com.salesmanager.shop.configuration.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import com.salesmanager.business.marketing.services.EmailMarketingService;
import com.salesmanager.business.scheduler.services.JobResultService;
import com.salesmanager.business.scheduler.services.SchedulerService;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.marketing.model.EmailMarketing;
import com.salesmanager.scheduler.model.JobResult;
import com.salesmanager.scheduler.model.Scheduler;

@Configuration
public class ShedulerCronJob {
	@Inject
	private SchedulerService schedulerService;
	@Inject
	private JobResultService jobResultService;
	@Inject
	private EmailMarketingService emailMarketingService;
	// @Scheduled(cron = "0 0 8 * * *")

	@Scheduled(cron = "*/180 * * * * *")
	public void scheduleTask() throws ServiceException {
		JobResult result = new JobResult();
		List<Scheduler> schedulers = schedulerService.list();
		for (Scheduler scheduler : schedulers) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String currentDateFormatted = dateFormat.format(date) + " 00:00:00.0";
			if (scheduler.isActive() && scheduler.getStartDate().toString().equals(currentDateFormatted)) {
				if (scheduler.getType().equals(SchedulerType.EMAIL_MARKETING.toString())) {

					EmailMarketing emailMarketing = emailMarketingService
							.getByIdLoaded(Long.valueOf(scheduler.getJob()));
					if (emailMarketing.isActive()) {
						try {
							result.setJobDate(new Date());
							result.setScheduler(scheduler);
							result.setMerchant(emailMarketing.getMerchant());
							if (emailMarketing.getDate().toString().equals(currentDateFormatted)) {
								emailMarketingService.sendMarketingEmail(emailMarketing);
								result.setResult("ok");
								jobResultService.save(result);

							}
						} catch (Exception e) {
							result.setResult("ko");
							result.setDescription(e.getCause().getMessage().toString());
							jobResultService.save(result);
						}
					}

				}
			}
		}

	}
}
