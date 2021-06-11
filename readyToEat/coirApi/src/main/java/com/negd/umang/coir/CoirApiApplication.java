package com.negd.umang.coir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
//@PropertySource("file:/home/application/conf/UmangDepartment/dday/application.properties")
//@PropertySource("file:C:\\Users\\gaurav.maskara\\Desktop\\application.properties")
public class CoirApiApplication {

	public static void main(String[] args) {
		//System.setProperty("spring.config.location", "C:\\Users\\gaurav.maskara\\Desktop\\application.properties");
		SpringApplication.run(CoirApiApplication.class, args);
	}

}
