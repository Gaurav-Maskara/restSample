package com.negd.umang.coir.bi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class CommonUtility {
    
        private static final Logger logger = LoggerFactory.getLogger(CommonUtility.class);
    
    

	public boolean isNotNullOrEmpty(String stringToTest) {
		if (stringToTest != null && !"".equalsIgnoreCase(stringToTest.trim())) {
			return true;
		}
		return false;
	}
	
	public String serialize(String obj, String path) {
		String res = "fail";
		try {

			FileOutputStream fout = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fout);
			out.writeObject(obj);
			out.flush();
			out.close();
			res = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public String deSerialize(String path) {
		String obj = null;
		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			obj = (String) in.readObject();

			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	
}
