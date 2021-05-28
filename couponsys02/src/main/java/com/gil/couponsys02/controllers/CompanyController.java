package com.gil.couponsys02.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.gil.couponsys02.beans.Category;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.exceptions.AlreadyInUseException;
import com.gil.couponsys02.exceptions.DataNotFoundException;
import com.gil.couponsys02.exceptions.InvalidDataException;
import com.gil.couponsys02.exceptions.UnauthorizedAccessException;
import com.gil.couponsys02.login.ClientType;
import com.gil.couponsys02.login.LoginManager;
import com.gil.couponsys02.services.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
	
	
	@PostMapping("/coupons")
	public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon, @RequestAttribute(name = "service") CompanyService companyService) throws AlreadyInUseException, InvalidDataException {
			companyService.addCoupon(coupon);
			return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/coupons")
	public ResponseEntity<?> getCompanyCoupons(@RequestAttribute(name = "service") CompanyService companyService) {
			return new ResponseEntity<>(companyService.companyCoupons(), HttpStatus.OK);
	}
	
	@PutMapping("/coupons")
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon, @RequestAttribute(name = "service") CompanyService companyService) throws InvalidDataException, AlreadyInUseException {
			companyService.updateCoupon(coupon);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/coupons/{couponId}")
	public ResponseEntity<?> deleteCoupon(@PathVariable int couponId, @RequestAttribute(name = "service") CompanyService companyService) throws DataNotFoundException {
			companyService.deleteCoupon(couponId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
	}
	
	@GetMapping("/coupons/category")
	public ResponseEntity<?> getCompanyCoupons(@RequestParam Category category, @RequestAttribute(name = "service") CompanyService companyService) {
			return new ResponseEntity<>(companyService.getCompanyCoupons(category), HttpStatus.OK);
	}
	
	
	@GetMapping("/coupons/maxprice")
	public ResponseEntity<?> getCompanyCoupons(@RequestParam double maxPrice, @RequestAttribute(name = "service") CompanyService companyService) {
		return new ResponseEntity<>(companyService.getCompanyCoupons(maxPrice), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<?> getCompanyDetails(@RequestAttribute(name = "service") CompanyService companyService) throws DataNotFoundException {
			return new ResponseEntity<>(companyService.companyDetails(), HttpStatus.OK);
	}
	
	

}
