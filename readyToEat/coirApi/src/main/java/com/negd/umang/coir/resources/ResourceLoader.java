package com.negd.umang.coir.resources;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdl.umang.propertyloader.OsType;


public class ResourceLoader {
	private static final Logger log = LoggerFactory.getLogger(ResourceLoader.class);
	private static Properties properties;
	private static String propertiesFilePath;

	private ResourceLoader() {
	}
	static String filePath = "C:\\Umang\\workspace\\EY_workspace\\coirApi\\src\\main\\resources\\application-stg.properties";

	
	public static String getProperties(String key) {
		try {
			if (OsType.isWindows()) {
				propertiesFilePath = filePath;
			} else {
				propertiesFilePath = "/home/application/conf/UmangDepartment/coir/application.properties";
			}
			if (properties == null) {
				log.info("****Properties file Loading****");
				properties = new Properties();
				log.info("Loading property file {}", propertiesFilePath);
				try (FileInputStream fileInputStream = new FileInputStream(propertiesFilePath);
						InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");) {
					properties.load(inputStreamReader);
				} catch (Exception e) {
					throw new Exception("Unable to load property file" + propertiesFilePath);
				}
				log.info("****Properties file Loaded****");
			}
		} catch (Exception e) {
			log.error("ResourceLoader", e);
		} finally {

		}

		return properties.getProperty(key);
	}

	public static void reloadProperties() {
		properties = null;
	}
}
