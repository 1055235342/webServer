package com.wpixel.pojo;

import java.util.HashMap;
import java.util.Map;

import io.netty.handler.codec.http.HttpResponseStatus;

public abstract class StatusCode {

	private final static String LOC = "302";
	private final static String NOT_FOND = "404";
	private final static String BAD_REQUEST = "400";
	private final static String INTERNAL_SERVER_ERROR = "500";
	
	public static Map<String, HttpResponseStatus> mapStatus = new HashMap<String, HttpResponseStatus>();
	
	static{
		mapStatus.put(LOC, HttpResponseStatus.FOUND);
		mapStatus.put(NOT_FOND, HttpResponseStatus.NOT_FOUND);
		mapStatus.put(BAD_REQUEST, HttpResponseStatus.BAD_REQUEST);
		mapStatus.put(INTERNAL_SERVER_ERROR, HttpResponseStatus.INTERNAL_SERVER_ERROR);
	}
	
}
