package com.gil.couponsys02.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gil.couponsys02.beans.Category;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.clr.primary.CustomerDummy;
import com.gil.couponsys02.exceptions.AlreadyInUseException;
import com.gil.couponsys02.exceptions.DataNotFoundException;
import com.gil.couponsys02.exceptions.InvalidDataException;
import com.gil.couponsys02.exceptions.UnauthorizedAccessException;
import com.gil.couponsys02.login.ClientType;
import com.gil.couponsys02.login.LoginManager;
import com.gil.couponsys02.services.CompanyService;
import com.gil.couponsys02.services.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
	
	@PostMapping("/coupons")
	public ResponseEntity<?> purchaseCoupon(@RequestBody Coupon coupon, @RequestAttribute(name = "service") CustomerService customerService) throws AlreadyInUseException, DataNotFoundException, InvalidDataException {
			customerService.purchaseCoupon(coupon);
			return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/coupons")
	public ResponseEntity<?> getCustomerCoupons(@RequestAttribute(name = "service") CustomerService customerService) throws DataNotFoundException {
			return new ResponseEntity<>(customerService.getCustomerCoupons(), HttpStatus.OK);	
	}
	
	@GetMapping("/coupons/category")
	public ResponseEntity<?> getCustomerCoupons(@RequestParam Category category, @RequestAttribute(name = "service") CustomerService customerService) throws DataNotFoundException {
			return new ResponseEntity<>(customerService.getCustomerCoupons(category), HttpStatus.OK);	
	}
	
	
	@GetMapping("/coupons/maxprice")
	public ResponseEntity<?> getCustomerCoupons(@RequestParam double maxPrice, @RequestAttribute(name = "service") CustomerService customerService) throws DataNotFoundException {
			return new ResponseEntity<>(customerService.getCustomerCoupons(maxPrice), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getCompanyDetails(@RequestAttribute(name = "service") CustomerService customerService) throws DataNotFoundException {
			return new ResponseEntity<>(customerService.getCustomerDetails(), HttpStatus.OK);	
	}
}
