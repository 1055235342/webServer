package com.wpixel.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.wpixel.pojo.IniPropertices;
import com.wpixel.server.Server;

/**
 * 项目的根装载器
 * @author RWBY
 *
 */
public class Bootstrap {
	
	private static Logger logger = Logger.getLogger(Bootstrap.class);
	
	protected static Bootstrap admin = null;
	protected String configFile = "/server.ini";
	protected static Properties prop = null;
	
	/**
	 * 初始化配置文件
	 */
	protected void init(){
		File file = null;
		InputStream fis = null;
		prop = new Properties();
		try {
//			file = new File(configFile);
//			fis = new FileInputStream(file);
//			prop.load(fis);

			InputStream in = this.getClass().getResourceAsStream(configFile);
			prop.load(in);

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
			IniPropertices.port = prop.get("port").toString();
			IniPropertices.appBase = prop.get("appBase").toString();
			int port = Integer.valueOf(IniPropertices.port);
			new Server().run(port);
		}
	}
}
