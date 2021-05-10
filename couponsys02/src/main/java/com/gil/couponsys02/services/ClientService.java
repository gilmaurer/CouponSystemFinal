package com.gil.couponsys02.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gil.couponsys02.exceptions.UnauthorizedAccessException;
import com.gil.couponsys02.repos.CompanyRepository;
import com.gil.couponsys02.repos.CouponRepository;
import com.gil.couponsys02.repos.CustomerRepository;

@Service
public abstract class ClientService {
	
	@Autowired
	protected  CompanyRepository companyRepository;
	
	@Autowired
	protected  CustomerRepository customerRepository;
	
	@Autowired
	protected  CouponRepository couponRepository;
	
	
	
	public abstract ClientService login(String email, String password) throws UnauthorizedAccessException;




	
}
