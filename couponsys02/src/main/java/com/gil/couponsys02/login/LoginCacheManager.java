package com.gil.couponsys02.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gil.couponsys02.services.ClientService;

@Component
public class LoginCacheManager {

	private Map<String, ClientService> services = new HashMap<>();
	
	public void insertService(String key, ClientService service) {
		this.services.put(key, service);
	}
	
	public ClientService getService(String key) {
		return this.services.get(key);
	}
	
	public void removeService(String key) {
		this.services.remove(key);
	}
}
