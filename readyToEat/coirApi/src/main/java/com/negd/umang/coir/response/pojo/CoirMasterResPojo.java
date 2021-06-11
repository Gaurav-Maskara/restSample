package com.negd.umang.coir.response.pojo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
@Component
@Scope(value = "prototype")
public class CoirMasterResPojo {
	
	private String rs;
	private String rc;
	private String rd;
	private Object pd;

}
