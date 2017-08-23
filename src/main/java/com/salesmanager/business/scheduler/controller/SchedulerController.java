package com.salesmanager.business.scheduler.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salesmanager.business.scheduler.services.SchedulerService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.business.utils.ajax.AjaxResponse;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.scheduler.model.Scheduler;
import com.salesmanager.shop.admin.model.web.Menu;
import com.salesmanager.shop.configuration.scheduler.ConstantsScheduler;
import com.salesmanager.shop.configuration.scheduler.Frequency;
import com.salesmanager.shop.configuration.scheduler.SchedulerType;
import com.salesmanager.shop.utils.DateUtil;

/**
 * Custom scheduler controller for admin
 * 
 * @author Marcos de Juan
 *
 */
@Controller
@RequestMapping("/admin/scheduler")
public class SchedulerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerController.class);
	@Inject
	private MerchantStoreService merchantService;

	@Inject
	private SchedulerService schedulerService;
	
	
	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value="/list.html", method=RequestMethod.GET)
	public String displayScheduler(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		setMenu(model,request);
		return ConstantsScheduler.Tiles.Scheduler.schedulerList;	

	}
	
	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value="/edit.html", method=RequestMethod.GET)
	public String displaySchedulerEdit(@RequestParam("id") long id, HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws Exception {
		return displayScheduler(id,request,response,model,locale);
	}
	
	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value="/create.html", method=RequestMethod.GET)
	public String displaySchedulerCreate(HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws Exception {
		return displayScheduler(null,request,response,model,locale);
	}
	@Transactional
	private String displayScheduler(Long schedulerId, HttpServletRequest request, HttpServletResponse response,Model model,Locale locale) throws Exception {
		
		this.setMenu(model, request);
		List<MerchantStore> stores = merchantService.list();
		
		
		Scheduler scheduler = new Scheduler();
		
		if(schedulerId!=null && schedulerId!=0) {//edit mode
			
			
			scheduler = schedulerService.getById(schedulerId);
			
			
			if(scheduler==null) {
				return "redirect:/list.html";
			}
			

		} else {
			
		}
		model.addAttribute("schedulerTypes", SchedulerType.class);
		model.addAttribute("frequency", Frequency.class);
		model.addAttribute("merchants", stores);
		model.addAttribute("scheduler", scheduler);
		
		return ConstantsScheduler.Tiles.Scheduler.schedulerDetails;
		
		
	}
		
	
	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value="/save.html", method=RequestMethod.POST)
	public String saveScheduler(@Valid @ModelAttribute("scheduler") Scheduler scheduler, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		MerchantStore store = null;
	
		List<MerchantStore> stores = merchantService.list();
		
		//display menu
		setMenu(model,request);
		store = merchantService.getById(scheduler.getMerchant().getId());
		Scheduler dbEntity =	null;	
		
		if(scheduler.getId() != null && scheduler.getId() >0) { //edit entry
			
			//get from DB
			dbEntity = schedulerService.getById(scheduler.getId());
			
			if(dbEntity==null) {
				return "redirect:/admin/scheduler/scheduler.html";
			}
		}
		scheduler.setMerchant(store);
		
		
		if (result.hasErrors()) {
			return ConstantsScheduler.Tiles.Scheduler.schedulerDetails;
		}
		
		schedulerService.save(scheduler);
		model.addAttribute("frequency", Frequency.class);	
		model.addAttribute("scheduler", scheduler);
		model.addAttribute("merchants", stores);
		model.addAttribute("success","success");
		return displayScheduler(model,request,response);
		
	}

	
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value="/paging.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> pageScheduler(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();

		
		try {
			
			
			List<Scheduler> schedulers = null;
						
			schedulers = schedulerService.list();
				
			for(Scheduler scheduler : schedulers) {
				
				@SuppressWarnings("rawtypes")
				Map entry = new HashMap();
				entry.put("id", scheduler.getId());
				entry.put("name", scheduler.getName());
				entry.put("merchant", scheduler.getMerchant().getStorename());
				entry.put("date", DateUtil.formatDate(scheduler.getStartDate()));
				
				resp.addDataEntry(entry);
				
				
			}
			
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);
			

		
		} catch (Exception e) {
			LOGGER.error("Error while paging schedulers", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}
		
		String returnString = resp.toJSONString();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
		
		
	}
	
	

	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {
		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		
		activeMenus.put("configuration", "scheduler");
		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("configuration");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
		//
		
	}
	
	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value="/remove.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> deleteScheduler(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String sid = request.getParameter("id");

		AjaxResponse resp = new AjaxResponse();

		
		try {
			
			Long id = Long.parseLong(sid);
			
			Scheduler entity = schedulerService.getById(id);

			if(entity==null) {

				resp.setStatusMessage("Error finding scheduler to delete");
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);			
				
			} else {
				
				schedulerService.delete(entity);
				resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);
				
			}
		
		
		} catch (Exception e) {
			LOGGER.error("Error while deleting scheduler", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
	
}
