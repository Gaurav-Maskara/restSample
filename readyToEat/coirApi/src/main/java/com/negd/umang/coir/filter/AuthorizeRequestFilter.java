package com.negd.umang.coir.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.negd.umang.coir.bi.CommonUtility;
import com.negd.umang.coir.response.pojo.CoirMasterResPojo;
import com.negd.umang.coir.response.pojo.ValidateSessionResPojo;
import com.sdl.umang.httpclient.ApacheHttpClient;



//@Component
public class AuthorizeRequestFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthorizeRequestFilter.class);
    
    @Value("${com.sdl.umang.mode}")
	private String mode;

	@Value("${com.sdl.umang.token.validation}")
	private String umangTokenValidation;

	@Value("${com.sdl.umang.deptid.validation}")
	private String umangDepartmentIDValidation;

	@Value("${com.sdl.umang.deptid}")
	private String umangDeptID;

	@Value("${com.sdl.umang.srvid}")
	private String umangServiceID;

	@Value("${com.sdl.umang.subsid}")
	private String umangSubServiceID;

	@Value("${com.sdl.umang.subsid2}")
	private String umangSubServiceID2;
	
	@Value("${com.sdl.umang.formtrkr}")
	private String umangFormtrkr;

	@Value("${com.sdl.umang.validate.token.bearer}")
	private String umangBearerToken;

	@Value("${com.sdl.umang.validate.session}")
	private String umangValidateSession;
	
	@Autowired
    private Environment environment;
    
    
	@Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain chain)
	    throws IOException, ServletException {
	
        	Gson gson =new Gson();
        	ApacheHttpClient objApacheHttpClient = new ApacheHttpClient();
	
	try {
	
	 HttpServletRequest httpRequest = (HttpServletRequest) request;
	 Enumeration<String> headerNames = httpRequest.getHeaderNames();
         Map<String, String> headerContent=new HashMap<String, String>();
	    
	    if (headerNames != null) {
	            while (headerNames.hasMoreElements()) {
	        	String key = (String) headerNames.nextElement();
	                String value = httpRequest.getHeader(key);
	                headerContent.put(key, value);    
	            }
	    }
	    
	 request = new RequestWrapper(httpRequest);
	       
	 String bodyContentObj=(String) request.getAttribute("fullRequestBody");
	 JSONParser parser = new JSONParser();
	 JSONObject bodyContentJson=new JSONObject();
         bodyContentJson = (JSONObject) parser.parse(bodyContentObj);
        	
        	Map<String, String> queryMap = new HashMap<>();
        	Map<String, String> headerMap = new HashMap<>();
        	ValidateSessionResPojo objSessionResPojo = new ValidateSessionResPojo();
        	CommonUtility objUtility = new CommonUtility();
	
	
		logger.debug("{} Header String deptid {} srvid {} {} {} {}", request.getAttribute("TRACKER"),
			headerContent.get("deptid"), headerContent.get("srvid"),
			headerContent.get("subsid"), headerContent.get("subsid2"),
			headerContent.get("formtrkr"));
	      
		logger.info("{},API Url,{},token,{},lat,{},long,{},location,{},userAgent,{}",
			        request.getAttribute("TRACKER"), httpRequest.getRequestURI(),
			        bodyContentJson.get("tkn"), bodyContentJson.get("lat"), bodyContentJson.get("lon"), bodyContentJson.get("lac"), bodyContentJson.get("usag"));

		
		if ("Y".equalsIgnoreCase(umangDepartmentIDValidation)) {

			if (!objUtility.isNotNullOrEmpty(bodyContentJson.get("mode") + "")
					|| !objUtility.isNotNullOrEmpty(headerContent.get("deptid") + "")
					|| !objUtility.isNotNullOrEmpty(headerContent.get("srvid") + "")
					|| !mode.contains(bodyContentJson.get("mode") + "")
					|| !Arrays.asList(umangDeptID.split(",")).contains(headerContent.get("deptid"))
					|| !Arrays.asList(umangServiceID.split(",")).contains(headerContent.get("srvid"))
					|| !Arrays.asList(umangSubServiceID.split(",")).contains(headerContent.get("subsid"))
					|| !Arrays.asList(umangSubServiceID2.split(",")).contains(headerContent.get("subsid2"))) {
				
				

			    	logger.debug("{} Request reject on basis of invalid MODE.", bodyContentJson.get("trkr"));
				
			    	CoirMasterResPojo coirMasterResPojo=new CoirMasterResPojo(); 
			    	
			    	coirMasterResPojo.setPd(null);
			    	coirMasterResPojo.setRs("BAD_REQUEST");
			    	coirMasterResPojo.setRc("400");

				    
			    	coirMasterResPojo.setRd("Please enter correct deptid | srvid |subsid | subsid2 | formtkr ");
				    
				    ObjectMapper obj=new ObjectMapper();
					String message=obj.writeValueAsString(coirMasterResPojo);
					
				    PrintWriter out = servletResponse.getWriter();
				    out.println(message);
				    
				    ((HttpServletResponse) servletResponse).setStatus(HttpStatus.BAD_REQUEST.value());
				    ((HttpServletResponse) servletResponse).setContentType(MediaType.APPLICATION_JSON_VALUE);
					return;
			}
		}
		if ("Y".equalsIgnoreCase(umangTokenValidation)) {
		        queryMap.put("tkn", bodyContentJson.get("tkn") + "");
			headerMap.put("Content-Type", "application/json");
			headerMap.put("Authorization", umangBearerToken);

			String response = objApacheHttpClient.postRequest(umangValidateSession, gson.toJson(queryMap), null,
	                                  headerMap, bodyContentJson.get("trkr") + "");

			objSessionResPojo = gson.fromJson(response, ValidateSessionResPojo.class);
			if ("F".equalsIgnoreCase(objSessionResPojo.getRs())) {
				
				    logger.debug("{} Request reject on basis of invalid tkn.", bodyContentJson.get("trkr"));
				
				    CoirMasterResPojo ioclMasterResPojo=new CoirMasterResPojo(); 
				    ioclMasterResPojo.setPd(null);
				    ioclMasterResPojo.setRs("FORBIDDEN");
				    ioclMasterResPojo.setRc("403");
				    ioclMasterResPojo.setRd("Kindly enter correct tkn paramater");
				
				    ObjectMapper obj=new ObjectMapper();
					String message=obj.writeValueAsString(ioclMasterResPojo);
					
					PrintWriter out = servletResponse.getWriter();
				    out.println(message);
				    
				    
				    ((HttpServletResponse) servletResponse).setStatus(HttpStatus.FORBIDDEN.value());
				    ((HttpServletResponse) servletResponse).setContentType(MediaType.APPLICATION_JSON_VALUE);
				     return;
				
			}else {
			    chain.doFilter(request, servletResponse);
			}
		}
		else {
		    chain.doFilter(request, servletResponse);
		}

	} catch (Exception e) {
	    logger.error("{} {} ", request.getAttribute("TRACKER") + " ", e);
		
		CoirMasterResPojo ioclMasterResPojo=new CoirMasterResPojo(); 
		ioclMasterResPojo.setPd(null);
		ioclMasterResPojo.setRs("UNAUTHORIZED");
		ioclMasterResPojo.setRc("401");
		
		ioclMasterResPojo.setRd("Kindly enter correct tkn paramater");
		
		ObjectMapper obj=new ObjectMapper();
		String message=obj.writeValueAsString(ioclMasterResPojo);
		
		PrintWriter out = servletResponse.getWriter();
	    out.println(message); 
		
		((HttpServletResponse) servletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
		((HttpServletResponse) servletResponse).setContentType(MediaType.APPLICATION_JSON_VALUE);
	    return;
	   
	 }
   }


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}


	@Override
	public void destroy() {
	}

}  
