package com.negd.umang.coir.bi;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(value = "prototype")
public class UserServiceTransLogsRequestPojo {
	private String deptid;
	private String sdltid;// apponitment id
	private String event;// booking
	private String status;// success or f
	private String deptresp;// result
	private String source;// mode
	private String imsi;
	private String sid;// servid

	private String tkn;
	 private String did;
	 

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getSdltid() {
		return sdltid;
	}

	public void setSdltid(String sdltid) {
		this.sdltid = sdltid;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeptresp() {
		return deptresp;
	}

	public void setDeptresp(String deptresp) {
		this.deptresp = deptresp;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getTkn() {
		return tkn;
	}

	public void setTkn(String tkn) {
		this.tkn = tkn;
	}

	@Override
	public String toString() {
		return "UserServiceTransLogsRequestPojo [deptid=" + deptid + ", sdltid=" + sdltid + ", event=" + event
				+ ", status=" + status + ", deptresp=" + deptresp + ", source=" + source + ", imsi=" + imsi + ", sid="
				+ sid + ", tkn=" + tkn + ", did=" + did + "]";
	}

}

