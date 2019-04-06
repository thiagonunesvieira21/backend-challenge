package com.invillia.acme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Super Classe dos controllers das APIs REST 
 * @author thiago.nvieira
 *
 */
public class BaseController {
	
	@Autowired
	private Environment env;
	
	public static final String AUTH_HEADER_NAME = "Authorization";
	
	public String getMenssage(String key) {
		return env.getProperty(key);
	}
}
