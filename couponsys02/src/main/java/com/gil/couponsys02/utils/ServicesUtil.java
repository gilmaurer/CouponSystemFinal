package com.gil.couponsys02.utils;

import com.gil.couponsys02.services.ClientService;
import com.gil.couponsys02.services.CompanyService;
import com.gil.couponsys02.services.CompanyServiceImpl;
import com.gil.couponsys02.services.CustomerService;
import com.gil.couponsys02.services.CustomerServiceImpl;

public class ServicesUtil {

	public static int getServiceId(ClientService clientSerivce) {
		int serviceId = 0;
		if (clientSerivce instanceof CompanyService) {
			serviceId = ((CompanyServiceImpl) clientSerivce).getComapnyId();
		} else if (clientSerivce instanceof CustomerService) {
			serviceId = ((CustomerServiceImpl) clientSerivce).getCustomerId();
		}
		
		return serviceId;
	}

}
