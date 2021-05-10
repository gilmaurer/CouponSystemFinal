package com.gil.couponsys02.login;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.gil.couponsys02.exceptions.UnauthorizedAccessException;
import com.gil.couponsys02.services.AdminService;
import com.gil.couponsys02.services.ClientService;
import com.gil.couponsys02.services.CompanyService;
import com.gil.couponsys02.services.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginManager {
	
	private final ApplicationContext ctx;
	private ClientService cleintService;
	
	public ClientService login(String email, String password, ClientType clientType) throws UnauthorizedAccessException {
		 
		switch(clientType) {
		case ADMINISTRATOR:
			cleintService = (ClientService) ctx.getBean(AdminService.class);
			break;
		case COMPANY:
			cleintService = (ClientService) ctx.getBean(CompanyService.class);
			break;
		case CUSTOMER:
			cleintService = (ClientService) ctx.getBean(CustomerService.class);
			break;
		default:
			break;
		
		}
		return cleintService.login(email, password);
	}
}
