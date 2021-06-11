package com.negd.umang.coir.bi;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.sdl.umang.httpclient.ApacheHttpClient;


@Component
@Scope(value = "prototype")

public class TransLogsIntegration {
	public ApacheHttpClient objApacheHttpClient = new ApacheHttpClient();
	public Map<String, String> headerMap = new HashMap<>();
	
    @Autowired
	private Environment environment;
	
	String response = null;
	String result = null;
	Gson objGson = new Gson();
	private static final Logger logger = LoggerFactory.getLogger(TransLogsIntegration.class);
	
	public void UserServiceTransLog(UserServiceTransLogsRequestPojo objUserServiceTransLogsRequestPojo, String tracker,
			String alertId) {
		try {
			result = objGson.toJson(objUserServiceTransLogsRequestPojo);
			
			logger.debug("{} {} {} {}", "REQ TO UserServiceTransLog", tracker,
					tracker, result);
			
			headerMap.put("Content-Type", "application/json");
			headerMap.put("Authorization", environment.getProperty("com.sdl.umang.dday.service.logs.bearer"));
			
			response = objApacheHttpClient.postRequest(environment.getProperty("com.sdl.umang.dday.service.logs.url"), result, null, headerMap,
					tracker);
		
			logger.debug("{} {} {} {}", "RES FROM UserServiceTransLog", tracker,
					tracker, response);
		} catch (Exception e) {
			logger.debug("{} {} {} {}", "RES FROM UserServiceTransLog", tracker,
					tracker, response);

		}

	}

	public void UserServiceTransLogDetails(UserServiceTransLogsDetailsReqPojo objUserServiceTransLogsDetailsReqPojo,
			String tracker, String alertId) {
		try {
			result = objGson.toJson(objUserServiceTransLogsDetailsReqPojo);
			
			logger.debug("com.sdl.umang.dday.service.logs.details {}",environment.getProperty("com.sdl.umang.dday.service.logs.bearer"));
			logger.debug("{} {} {} {}", "REQ TO UserServiceTransLogDetails", tracker, tracker, result);
			
			headerMap.put("Content-Type", "application/json");
			headerMap.put("Authorization", environment.getProperty("com.sdl.umang.dday.service.logs.bearer"));
			
			
			logger.info("{} {}", "Trying to ENTER transaction for", objUserServiceTransLogsDetailsReqPojo);
			response = objApacheHttpClient.postRequest(environment.getProperty("com.sdl.umang.dday.service.logs.details"), result, null,headerMap, tracker);
			logger.info("{} {}", "Successfully ENTERED transaction for", objUserServiceTransLogsDetailsReqPojo);
			
			
			logger.debug("{} {} {} {}", "RES FROM UserServiceTransLogDetails", tracker, tracker, response);
		} catch (Exception e) {
			logger.error("{} {} {} {}", "RES FROM UserServiceTransLogDetails", tracker, tracker, response);

		}

	}

}