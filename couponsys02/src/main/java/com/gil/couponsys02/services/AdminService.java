package com.gil.couponsys02.services;

import java.util.List;

import com.gil.couponsys02.beans.Company;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.beans.Customer;
import com.gil.couponsys02.exceptions.AlreadyInUseException;
import com.gil.couponsys02.exceptions.DataNotFoundException;
import com.gil.couponsys02.exceptions.InvalidDataException;

public interface AdminService {
	void addCompany(Company company) throws AlreadyInUseException;
	void updateCompany(Company company) throws AlreadyInUseException, InvalidDataException;
	void deleteCompany(int companyId) throws DataNotFoundException;
	List<Company> getAllCompanies();
	Company getOneCompany(int companyId) throws DataNotFoundException;
	
	void addCustomer(Customer customer) throws AlreadyInUseException;
	void updateCustomer(Customer customer) throws AlreadyInUseException;
	void deleteCustomer(int customerId) throws DataNotFoundException;
	List<Customer> getAllCustomers();
	Customer getOneCustomer(int customerId) throws DataNotFoundException;
	
	List<Coupon> getAllCoupons();

}
