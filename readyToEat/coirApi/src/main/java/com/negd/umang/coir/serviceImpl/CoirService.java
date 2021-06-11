package com.negd.umang.coir.serviceImpl;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.negd.umang.coir.dao.CoirDao;
import com.negd.umang.coir.request.pojo.McyReqPojo;


@Service
public class CoirService {
	
	@Autowired
	private CoirDao coirDao; 
	
	
	
	public JSONObject mcy(McyReqPojo mcyReqPojo) throws Exception {
		return coirDao.mcy(mcyReqPojo);
	}
	


}
