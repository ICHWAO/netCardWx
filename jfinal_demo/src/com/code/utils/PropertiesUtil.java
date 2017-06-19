package com.code.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import com.jfinal.kit.PathKit;

public class PropertiesUtil {
	
	//获取properties文件值
	public static String getValue(String key) {
		Properties prop = new Properties();
		try {
			//装载配置文件
	        String path = PathKit.class.getClassLoader().getResource("").toURI().getPath()+"//quartz_job.properties";

			prop.load(new FileInputStream(new File(path)));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//返回获取的值
		return prop.getProperty(key);
	}
}
