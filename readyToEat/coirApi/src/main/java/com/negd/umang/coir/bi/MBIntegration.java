package com.negd.umang.coir.bi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdl.umang.mb.producer.MBProducer;


public class MBIntegration {
static MBProducer objMbProducer = null;
private Logger logger = LoggerFactory.getLogger(this.getClass());
	public void reload(){
		objMbProducer = null;
	}
	public MBProducer startProducer(){
		try{
			if(objMbProducer == null){
				objMbProducer = new MBProducer();
				objMbProducer.initialize("/home/application/conf/messageBroker/application.properties");
				//objMbProducer.initialize("F:/confg/UmangDepartment/messageBroker/application.properties");
			}
		}catch(Exception e){
			e.printStackTrace();
			try{
				objMbProducer.close();
			}catch(Exception ex){
				ex.printStackTrace();
				objMbProducer = null;
			}
			objMbProducer = null;
		}
		return objMbProducer;
	}
/*	public boolean toMessageBroker(String topic, String data, String tracker) {
		try{
			startProducer();
			objMbProducer.produce(topic, data);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("{} {}",tracker,e);
			try{
				objMbProducer.close();
				return false;
			}catch(Exception ex){
				ex.printStackTrace();
				objMbProducer = null;
				return false;
			}
		}
	}*/
	public boolean toMessageBroker(String topic, String key, String data, String tracker) {
		try{
			logger.debug("{}, bi logging data  {}",tracker,data);
			startProducer();
			objMbProducer.produce(topic, data);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("{} {}",tracker,e);
			try{
				objMbProducer.close();
				objMbProducer = null;
				return false;
			}catch(Exception ex){
				ex.printStackTrace();
				objMbProducer = null;
				return false;
			}
		}
	}

}