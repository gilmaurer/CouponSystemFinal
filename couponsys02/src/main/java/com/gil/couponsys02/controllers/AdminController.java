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
import org.springframework.web.bind.annotation.RestController;

import com.gil.couponsys02.beans.Company;
import com.gil.couponsys02.beans.Customer;
import com.gil.couponsys02.exceptions.AlreadyInUseException;
import com.gil.couponsys02.exceptions.DataNotFoundException;
import com.gil.couponsys02.exceptions.InvalidDataException;
import com.gil.couponsys02.services.AdminService;
import com.gil.couponsys02.services.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	
	@GetMapping("/coupons")
	public ResponseEntity<?> getAllCoupons(@RequestAttribute(name = "service") AdminService adminService) {
		return new ResponseEntity<>(adminService.getAllCoupons(), HttpStatus.OK);
	}
	
	@PostMapping("/companies")
	public ResponseEntity<?> addCompany(@RequestBody Company company, @RequestAttribute(name = "service") AdminService adminService) throws AlreadyInUseException {
			adminService.addCompany(company);
			return new ResponseEntity<>(HttpStatus.CREATED);

		
	}
	
	@PutMapping("/companies")
	public ResponseEntity<?> updateCompany(@RequestBody Company company, @RequestAttribute(name = "service") AdminService adminService) throws AlreadyInUseException, InvalidDataException {
			adminService.updateCompany(company);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@DeleteMapping("/companies/{companyId}")
	public ResponseEntity<?> deleteCompany(@PathVariable int companyId, @RequestAttribute(name = "service") AdminService adminService) throws DataNotFoundException {
			adminService.deleteCompany(companyId);
			return new ResponseEntity<>(HttpStatus.OK);	
	}
	
	@GetMapping("/companies")
	public ResponseEntity<?> getAllCompanies(@RequestAttribute(name = "service") AdminService adminService) {
		return new ResponseEntity<>(adminService.getAllCompanies(), HttpStatus.OK);
	}
	
	@GetMapping("/companies/{companyId}")
	public ResponseEntity<?> getOneCompany(@PathVariable int companyId, @RequestAttribute(name = "service") AdminService adminService) throws DataNotFoundException  {
			return new ResponseEntity<>(adminService.getOneCompany(companyId), HttpStatus.OK);	
	}
	
	@PostMapping("/customers")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer, @RequestAttribute(name = "service") AdminService adminService) throws AlreadyInUseException {
			adminService.addCustomer(customer);
			return new ResponseEntity<>(HttpStatus.CREATED);
		
		
	}
	
	@PutMapping("/customers")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @RequestAttribute(name = "service") AdminService adminService) throws AlreadyInUseException {
			adminService.updateCustomer(customer);
			return new ResponseEntity<>(HttpStatus.OK);
	
	}

	@DeleteMapping("/customers/{customerId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int customerId, @RequestAttribute(name = "service") AdminService adminService) throws DataNotFoundException {
			adminService.deleteCustomer(customerId);
			return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/customers")
	public ResponseEntity<?> getAllCustomers(@RequestAttribute(name = "service") AdminService adminService) {
		return new ResponseEntity<>(adminService.getAllCustomers(), HttpStatus.OK);
	}
	
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<?> getOneCustomer(@PathVariable int customerId, @RequestAttribute(name = "service") AdminService adminService) throws DataNotFoundException  {
			return new ResponseEntity<>(adminService.getOneCustomer(customerId), HttpStatus.OK);
	}
	
	
	

}
