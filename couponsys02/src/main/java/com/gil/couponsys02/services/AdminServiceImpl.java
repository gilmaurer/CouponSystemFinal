package com.gil.couponsys02.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gil.couponsys02.beans.Company;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.beans.Customer;
import com.gil.couponsys02.exceptions.AlreadyInUseException;
import com.gil.couponsys02.exceptions.DataNotFoundException;
import com.gil.couponsys02.exceptions.ErrorsMessages;
import com.gil.couponsys02.exceptions.InvalidDataException;
import com.gil.couponsys02.exceptions.UnauthorizedAccessException;
import com.gil.couponsys02.login.ClientType;

@Service
public class AdminServiceImpl extends ClientService implements AdminService {

	private static final String ADMIN_EMAIL = "admin@admin.com";
	private static final String ADMIN_PASSWORD = "admin";

	@Override
	public ClientService login(String email, String password) throws UnauthorizedAccessException {
		if (!(email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD))) {
			throw new UnauthorizedAccessException(ErrorsMessages.LOGIN_FAILED, email, ClientType.ADMINISTRATOR.name());
		}
		return this;

	}

	@Override
	public void addCompany(Company company) throws AlreadyInUseException {
		if (this.companyRepository.existsByNameOrEmail(company.getName(), company.getEmail())) {
			throw new AlreadyInUseException(ErrorsMessages.NAME_OR_EMAIL_ALREADY_IN_USE, company.getName(),
					company.getEmail());
		}
		this.companyRepository.save(company);

	}

	@Override
	public void updateCompany(Company company) throws AlreadyInUseException, InvalidDataException {
		if (!this.companyRepository.existsByIdAndName(company.getId(), company.getName())) {
			throw new InvalidDataException(ErrorsMessages.NAME_REPLACED, company.getId());
		}
		if (this.companyRepository.existsByEmailAndIdIsNot(company.getEmail(), company.getId())) {
			throw new AlreadyInUseException(ErrorsMessages.EMAIL_ALREADY_IN_USE, company.getClass().getSimpleName(),
					company.getEmail());
		}
		this.companyRepository.saveAndFlush(company);

	}

	@Override
	public void deleteCompany(int companyId) throws DataNotFoundException {
		if (!this.companyRepository.existsById(companyId)) {
			throw new DataNotFoundException(ErrorsMessages.NOT_FOUND_BY_ID, Company.class.getSimpleName(), companyId);
		}
		this.couponRepository.deleteCouponsFromCustomersCouponsByCompanyId(companyId);
		this.companyRepository.deleteById(companyId);

	}

	@Override
	public List<Company> getAllCompanies() {
		return this.companyRepository.findAll();
	}

	@Override
	public Company getOneCompany(int companyId) throws DataNotFoundException {
		return this.companyRepository.findById(companyId)
				.orElseThrow(() -> new DataNotFoundException(ErrorsMessages.NOT_FOUND_BY_ID,
						Company.class.getSimpleName(), companyId));

	}

	@Override
	public void addCustomer(Customer customer) throws AlreadyInUseException {
		if (customerRepository.existsByEmail(customer.getEmail())) {
			throw new AlreadyInUseException(ErrorsMessages.EMAIL_ALREADY_IN_USE, customer.getClass().getSimpleName(),
					customer.getEmail());
		}
		this.customerRepository.save(customer);

	}

	@Override
	public void updateCustomer(Customer customer) throws AlreadyInUseException {
		if (this.customerRepository.existsByEmailAndIdIsNot(customer.getEmail(), customer.getId())) {
			throw new AlreadyInUseException(ErrorsMessages.EMAIL_ALREADY_IN_USE, customer.getClass().getSimpleName(),
					customer.getEmail());
		}
		this.customerRepository.saveAndFlush(customer);

	}

	@Override
	public void deleteCustomer(int customerId) throws DataNotFoundException {
		if (!this.customerRepository.existsById(customerId)) {
			throw new DataNotFoundException(ErrorsMessages.NOT_FOUND_BY_ID, Customer.class.getSimpleName(), customerId);
		}
		this.customerRepository.deleteById(customerId);

	}

	@Override
	public List<Customer> getAllCustomers() {
		return this.customerRepository.findAll();
	}

	@Override
	public Customer getOneCustomer(int customerId) throws DataNotFoundException {
		return this.customerRepository.findById(customerId)
				.orElseThrow(() -> new DataNotFoundException(ErrorsMessages.NOT_FOUND_BY_ID,
						Customer.class.getSimpleName(), customerId));
	}

	@Override
	public List<Coupon> getAllCoupons() {
		return this.couponRepository.findAll();
	}

}
