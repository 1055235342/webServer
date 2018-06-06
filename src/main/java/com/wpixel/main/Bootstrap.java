package com.wpixel.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.wpixel.server.Server;

import jdk.internal.org.xml.sax.InputSource;

public class Bootstrap {
	
	private static Logger logger = Logger.getLogger(Bootstrap.class);
	
	protected static Bootstrap admin = null;
	protected String configFile = "conf/server.ini";
	protected static Properties prop = null;
	
	@SuppressWarnings("resource")
	protected void init(){
		File file = null;
		InputStream fis = null;
		prop = new Properties();
		try {
			file = new File(configFile);
			fis = new FileInputStream(file);
			prop.load(fis);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		if(admin == null){
			Bootstrap b = new Bootstrap();
			b.init();
			String object = prop.get("port").toString();
			int port = Integer.valueOf(object);
			new Server().run(port);
		}
	}
}
