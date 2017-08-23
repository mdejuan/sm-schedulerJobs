package com.salesmanager.business.scheduler.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salesmanager.business.scheduler.services.JobResultService;
import com.salesmanager.core.business.utils.ajax.AjaxResponse;
import com.salesmanager.scheduler.model.JobResult;
import com.salesmanager.shop.admin.model.web.Menu;
import com.salesmanager.shop.configuration.scheduler.ConstantsScheduler;
import com.salesmanager.shop.utils.DateUtil;

/**
 * Custom job result controller for admin
 * 
 * @author Marcos de Juan
 *
 */
@Controller
@RequestMapping("/admin/jobResult")
public class JobResultController {
	private static final Logger LOGGER = LoggerFactory.getLogger(JobResultController.class);
	
	@Inject
	private JobResultService jobResultService;
	
	
	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value="/list.html", method=RequestMethod.GET)
	public String displayJobResult(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		setMenu(model,request);
		return ConstantsScheduler.Tiles.JobResult.jobResultList;	

	}
	
	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value="/edit.html", method=RequestMethod.GET)
	public String displaySchedulerEdit(@RequestParam("id") long id, HttpServletRequest request, HttpServletResponse response, Model model, Locale locale) throws Exception {
		return displayJobResult(id,request,response,model,locale);
	}
	
	
	@Transactional
	private String displayJobResult(Long jobResultId, HttpServletRequest request, HttpServletResponse response,Model model,Locale locale) throws Exception {
		
		this.setMenu(model, request);
		
		JobResult jobResult = new JobResult();
		
		if(jobResultId!=null && jobResultId!=0) {//edit mode
			
			
			jobResult = jobResultService.getById(jobResultId);	
			
			if(jobResult==null) {
				return "redirect:/list.html";
			}
			

		} else {
			
		}
		model.addAttribute("jobResult", jobResult);
		return ConstantsScheduler.Tiles.JobResult.jobResultDetails;
		
		
	}
		
	
	
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasRole('STORE_ADMIN')")
	@RequestMapping(value="/paging.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> pageJobResult(HttpServletRequest request, HttpServletResponse response) {

		AjaxResponse resp = new AjaxResponse();

		
		try {
			
			
			List<JobResult> jobResult = null;
						
			jobResult = jobResultService.list();
				
			for(JobResult result : jobResult) {
				
				@SuppressWarnings("rawtypes")
				Map entry = new HashMap();
				entry.put("id", result.getId());
				entry.put("scheduler.id", result.getScheduler().getId());
				entry.put("scheduler.job", result.getScheduler().getJob());
				entry.put("result", result.getResult());
				entry.put("merchant", result.getMerchant().getStorename());
				entry.put("date", DateUtil.formatDate(result.getJobDate()));
				
				resp.addDataEntry(entry);
				
				
			}
			
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);
			

		
		} catch (Exception e) {
			LOGGER.error("Error while paging job results", e);
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
	public @ResponseBody ResponseEntity<String> deleteJobResult(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String sid = request.getParameter("id");

		AjaxResponse resp = new AjaxResponse();

		
		try {
			
			Long id = Long.parseLong(sid);
			
			JobResult entity = jobResultService.getById(id);

			if(entity==null) {

				resp.setStatusMessage("Error finding job result to delete");
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);			
				
			} else {
				
				jobResultService.delete(entity);
				resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);
				
			}
		
		
		} catch (Exception e) {
			LOGGER.error("Error while deleting job result", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
	
}
