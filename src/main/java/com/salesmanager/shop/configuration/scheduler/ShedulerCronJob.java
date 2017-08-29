package com.salesmanager.shop.configuration.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import com.salesmanager.business.marketing.services.EmailMarketingService;
import com.salesmanager.business.marketing.services.PromotionService;
import com.salesmanager.business.scheduler.services.JobResultService;
import com.salesmanager.business.scheduler.services.SchedulerService;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.marketing.model.EmailMarketing;
import com.salesmanager.marketing.model.Promotion;
import com.salesmanager.scheduler.model.JobResult;
import com.salesmanager.scheduler.model.Scheduler;
import com.salesmanager.shop.configuration.marketing.PromotionType;

@Configuration
public class ShedulerCronJob {
	@Inject
	private SchedulerService schedulerService;
	@Inject
	private JobResultService jobResultService;
	@Inject
	private EmailMarketingService emailMarketingService;

	@Inject
	private PromotionService promotionService;
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
					if (emailMarketing.isActive() && emailMarketing.getDate().toString().equals(currentDateFormatted)) {
						try {
							result.setJobDate(new Date());
							result.setScheduler(scheduler);
							result.setMerchant(emailMarketing.getMerchant());
							emailMarketingService.sendMarketingEmail(emailMarketing);
							result.setResult("<font color='green'>ok</font>");
							jobResultService.save(result);

						} catch (Exception e) {
							result.setResult("<font color='red'>ko</font>");
							result.setDescription(e.getCause().getMessage().toString());
							jobResultService.save(result);
						}
					}

				} else if (scheduler.getType().equals(SchedulerType.PRODUCT_PROMOTIONS.toString())) {
					List<Promotion> promotionsStart = new ArrayList<Promotion>();
					List<Promotion> promotionsStop = new ArrayList<Promotion>();
					List<Promotion> promotions = promotionService.list();
					for (Promotion promotion : promotions) {
						if ((promotion.getPromotionType().equals(PromotionType.Category_Discount.toString())
								|| promotion.getPromotionType().equals(PromotionType.Product_Discount.toString())
								|| promotion.getPromotionType().equals(PromotionType.Manufacturer_Discount.toString()))
								&& promotion.isActive()) {
							if (promotion.getStartDate().toString().equals(currentDateFormatted)) {
								promotionsStart.add(promotion);

							} else if (promotion.getEndDate().toString().equals(currentDateFormatted)) {
								promotionsStop.add(promotion);
							}

						}
						if (!promotionsStart.isEmpty()) {
							promotionService.activatePromotions(promotionsStart);
						}
						if (!promotionsStop.isEmpty()) {
							promotionService.disablePromotions(promotionsStop);
						}
					}
				} else if (scheduler.getType().equals(SchedulerType.ORDER_PROMOTIONS.toString())) {
					
				}
			}
		}
	}

}
