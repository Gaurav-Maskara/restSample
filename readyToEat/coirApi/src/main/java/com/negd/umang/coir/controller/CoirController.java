package com.negd.umang.coir.controller;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.negd.umang.coir.bi.TransLogsIntegration;
import com.negd.umang.coir.bi.UserServiceTransLogsRequestPojo;
import com.negd.umang.coir.request.pojo.CoirMasterReqPojo;
import com.negd.umang.coir.request.pojo.McyReqPojo;
import com.negd.umang.coir.response.pojo.CoirMasterResPojo;
import com.negd.umang.coir.serviceImpl.CoirService;
import com.negd.umang.coir.utility.CoirUtility;




@RestController
@RequestMapping("/ws1")
public class CoirController {
	
	private static final Logger logger = LoggerFactory.getLogger(CoirController.class);
	
	@Autowired
	private CoirService coirService; 
	
	@Autowired
	private CoirMasterResPojo coirMasterResPojo; 
	
	@Autowired
	private Environment environment;
	
	@Autowired
    private UserServiceTransLogsRequestPojo objUserServiceTransLogsRequestPojo;
	
	@Autowired
    private TransLogsIntegration objTransLogsIntegration;
	

	
	
	@PostMapping("/mcy")
	public ResponseEntity<?> mcy(@Valid @RequestBody McyReqPojo mcyReqPojo) throws Exception {
		long before =System.currentTimeMillis();
		long after=0;
		JSONObject response;
		String biKey=environment.getProperty("com.sdl.umang.coir.bi.mcy");
		
		try {
			
			logger.info("{} {} {}", "REQ", mcyReqPojo.getTrkr(), mcyReqPojo.getApitrkr(), mcyReqPojo);
			CoirUtility.doBasicValidation(mcyReqPojo);
			response =coirService.mcy(mcyReqPojo);
			
			
			after=System.currentTimeMillis();
			setUmangResponse(mcyReqPojo, response, coirMasterResPojo);
			
	    	logger.debug("{} {} {} {} {}", "RES FROM COIR", mcyReqPojo.getTrkr(),mcyReqPojo.getApitrkr(), mcyReqPojo, before - after);
	    	
	    	//addTransaction(response.get("Key").toString(), sepiDataReqPojo);

			
		  }  catch (Exception e) {
			  logger.error("{} {} {}","Exception Occurred",mcyReqPojo.getApitrkr(), e);
	    	   
			  coirMasterResPojo.setRc("503");
	    	  CoirUtility.pushDataToBI(mcyReqPojo, before, biKey, e.getMessage().replaceAll(",", ""), coirMasterResPojo, "exception");
	    	  throw e;
		  }
	     	finally { CoirUtility.pushDataToBI(mcyReqPojo, before, biKey, "S", coirMasterResPojo, "finally"); }
		return new ResponseEntity<>(coirMasterResPojo, HttpStatus.OK);
	}
	
	
	
	
	
	private void setUmangResponse(CoirMasterReqPojo coirMasterReqPojo, Object apiResponse, CoirMasterResPojo ddayMasterResPojo) {
		  if (apiResponse != null ) {
			  coirMasterResPojo.setPd(apiResponse);;
			  coirMasterResPojo.setRs("S");
			  coirMasterResPojo.setRc("200");
			  coirMasterResPojo.setRd("OK");
		    }else {
		    	coirMasterResPojo.setRs("F");
		    	coirMasterResPojo.setRc("200");
		    	coirMasterResPojo.setRd("No record found");
		    	coirMasterResPojo.setPd(apiResponse);
		    }
	}
	
   /*
	 public boolean addTransaction(String appno,  SepiDataReqPojo sepiDataReqPojo) {
	    	
	    	try {
				
	    		objUserServiceTransLogsRequestPojo.setDeptid(sepiDataReqPojo.getSrvid());
				objUserServiceTransLogsRequestPojo.setDeptresp("Application submitted successfully");
				objUserServiceTransLogsRequestPojo.setSdltid(appno);
				objUserServiceTransLogsRequestPojo.setEvent("Application submitted successfully");
				objUserServiceTransLogsRequestPojo.setStatus("S");

				objUserServiceTransLogsRequestPojo.setSource("web");
 
				objUserServiceTransLogsRequestPojo.setSid(sepiDataReqPojo.getDeptid());
				objUserServiceTransLogsRequestPojo.setTkn(sepiDataReqPojo.getTkn());
				objTransLogsIntegration.UserServiceTransLog(objUserServiceTransLogsRequestPojo, sepiDataReqPojo.getApitrkr(), "200");

				UserServiceTransLogsDetailsReqPojo objUserServiceTransLogsDetailsReqPojo = new UserServiceTransLogsDetailsReqPojo();

				// For Application Number
				objUserServiceTransLogsDetailsReqPojo.setKey("Tracking ID");
				objUserServiceTransLogsDetailsReqPojo.setValue(appno);
				objTransLogsIntegration.UserServiceTransLogDetails(objUserServiceTransLogsDetailsReqPojo,sepiDataReqPojo.getApitrkr(), "200");
				
				
				logger.info("{} {} {} {}", "Successfully Added transaction for", sepiDataReqPojo.getTrkr(), sepiDataReqPojo.getApitrkr(),
						sepiDataReqPojo);
				
				return true;

			} catch (Exception e) {
				logger.error("Exception in Transactional Logs {},{},{}", sepiDataReqPojo.getApitrkr(),e, sepiDataReqPojo);
				return false;
			}
	    	
	    } 
	 
	 */

	
	
}
