package com.negd.umang.coir.dao;

import java.net.URISyntaxException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import com.negd.umang.coir.request.pojo.McyReqPojo;
import com.negd.umang.coir.resources.ResourceLoader;
import com.negd.umang.coir.utility.CoirUtility;


@Repository
public class CoirDao {
	

	 
    @Value("${com.negd.umang.departmenturl.mcy}")
    private String mcyUrl;
	 
	
	@Autowired
	private JSONParser parser;
 
   
   
 
   
	public JSONObject mcy(McyReqPojo mcyReqPojo) throws  URISyntaxException, ParseException {

		mcyUrl=ResourceLoader.getProperties("com.negd.umang.departmenturl.mcy")+"?ApplicationNumber="+mcyReqPojo.getApplicationNumber()+
				"&schemeCode="+mcyReqPojo.getSchemeCode()+"&Beneficiary_ID="+mcyReqPojo.getBeneficiaryId();
		
		String response= CoirUtility.getResponse(mcyReqPojo.toString(), mcyReqPojo,
				mcyUrl, Object.class,
				HttpMethod.GET);
		return (JSONObject) parser. parse(response);
	}
	
	
	@Bean
	private JSONParser getJsonParser() {
		return new JSONParser(); 
	}

}
