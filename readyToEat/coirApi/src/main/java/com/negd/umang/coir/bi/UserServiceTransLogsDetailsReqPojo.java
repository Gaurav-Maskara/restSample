package com.negd.umang.coir.bi;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(value = "prototype")
public class UserServiceTransLogsDetailsReqPojo {

	private String sdltid;//appointment id
	private String key;
	private String value;

	private String tkn;
	 private String did;
	 

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getSdltid() {
		return sdltid;
	}

	public void setSdltid(String sdltid) {
		this.sdltid = sdltid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTkn() {
		return tkn;
	}

	public void setTkn(String tkn) {
		this.tkn = tkn;
	}

	@Override
	public String toString() {
		return "UserServiceTransLogsDetailsReqPojo [sdltid=" + sdltid + ", key=" + key + ", value=" + value + ", tkn="
				+ tkn + ", did=" + did + "]";
	}


}
