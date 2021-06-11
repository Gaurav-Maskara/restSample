package com.negd.umang.coir.utility;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.negd.umang.coir.bi.MBIntegration;
import com.negd.umang.coir.exceptionhandler.BasicValidationException;
import com.negd.umang.coir.request.pojo.CoirMasterReqPojo;
import com.negd.umang.coir.response.pojo.CoirMasterResPojo;



@Component
public class CoirUtility {
	
    private static final Logger logger = LoggerFactory.getLogger(CoirUtility.class);

	
	private CoirUtility() {}
	
	static RestTemplate restTemplate;
	static HttpHeaders headers;
	
	static {
		  restTemplate = new RestTemplate();
		  headers = new HttpHeaders();
	}
	
	
	   private static String isBIAllowed;
	   private static String biErrorData;
	   private static String biPer;
	   
	   
	   private static String user;
	   private static String pass;

	   
	   @Value("${com.sdl.umang.coir.bi.allowed}")
	   public void setIsBIAllowed(String value) {
		   isBIAllowed = value;
	   }
	   
	   @Value("${com.sdl.umang.coir.bi.error.data}")
	   public void setBiErrorData(String value) {
		   biErrorData = value;
	   }
	   
	   @Value("${com.sdl.umang.coir.bi.per}")
	   public void setBiPer(String value) {
		   biPer = value;
	   }
	   
	  
	   
	public static String getResponse(String request, CoirMasterReqPojo requestPojo, String endpoint, Class responseType, HttpMethod httpMethod) throws RestClientException, URISyntaxException {
		   
		    headers.setContentType(MediaType.APPLICATION_JSON);
			  
			HttpEntity<String> entity = new HttpEntity<>(request, headers);
			ResponseEntity<String> response = restTemplate.exchange(new URI(endpoint), httpMethod, entity, String.class);
	      
			requestPojo.setStatusCode(response.getStatusCode().toString());
			return response.getBody();
	   }
	
	
	public static void doBasicValidation(CoirMasterReqPojo ioclMasterReqPojo ) throws BasicValidationException {
		   if (isNotNullOrEmpty(ioclMasterReqPojo.getTkn())
					&& isNotNullOrEmpty(ioclMasterReqPojo.getSrvid())
					&& isNotNullOrEmpty(ioclMasterReqPojo.getUsrid())
					&& isNotNullOrEmpty(ioclMasterReqPojo.getMode())
					&& isNotNullOrEmpty(ioclMasterReqPojo.getPltfrm())) {
		   } else {
			   throw new BasicValidationException("Either of tkn/srvid/usrid/mode/pltfrm is incorrect") ;
		   }
	   }
	
	 public static boolean isNotNullOrEmpty(String stringToTest) {
			if (stringToTest != null && !"".equalsIgnoreCase(stringToTest.trim())) {
				return true;
			}
			return false;
	   }
	  
	 
	 public static void pushDataToBI(CoirMasterReqPojo coirMasterReqPojo, long before, String biKey, String message, CoirMasterResPojo coirMasterResPojo, String biTypeExceptionOrFinally) {
		 
		   logger.info("{} {} {} {} {}", "Trying to Push Data to BI", coirMasterReqPojo.getTrkr(), coirMasterReqPojo.getApitrkr(),
				   coirMasterReqPojo, System.currentTimeMillis() - before);
		  
		      String biTopic="";
			  if("exception".equalsIgnoreCase(biTypeExceptionOrFinally)) {
				    biTopic=biErrorData;
			  }else {
				    biTopic=biPer;
				    message = "S";
					if("503".equalsIgnoreCase(coirMasterResPojo.getRc())){
						message="F";
					}
			  }
		  
		    if ("Y".equalsIgnoreCase(isBIAllowed)) {
			long after = System.currentTimeMillis();
			MBIntegration objIntegration = new MBIntegration();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			try {
				
				 String data = sdf.format(new Date()) + "," + coirMasterReqPojo.getDeptid() + ","
						+ coirMasterReqPojo.getUsrid() + "," + coirMasterReqPojo.getMode() + ","
						+ coirMasterReqPojo.getPltfrm() + "," + coirMasterReqPojo.getTkn() + "," + "COIR" + ","
						+ biKey+ ","
						+ (after - before) + "," + message + ","
						+ coirMasterReqPojo.getSrvid()  + "," + coirMasterReqPojo.getSubsid()
						+ "," + coirMasterReqPojo.getSubsid2() + "," + coirMasterReqPojo.getFormtrkr(); 

				objIntegration.toMessageBroker(biTopic, "", data, coirMasterReqPojo.getApitrkr());
				
				logger.debug("{} {} {} {} {}", "Successfully entered BI Data for",biTypeExceptionOrFinally, coirMasterReqPojo.getTrkr(),
						coirMasterReqPojo.getApitrkr(), message);
				
			} catch (Exception ex) {
				logger.error("Exception in Message Broker", coirMasterReqPojo.getApitrkr(), ex);
			}
		    }
		}

	
	
	
}
