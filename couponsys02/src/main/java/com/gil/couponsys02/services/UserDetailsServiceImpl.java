package com.gil.couponsys02.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gil.couponsys02.beans.Company;
import com.gil.couponsys02.beans.Customer;
import com.gil.couponsys02.repos.CompanyRepository;
import com.gil.couponsys02.repos.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CustomerRepository customerRepository;

	private static final String ADMIN_EMAIL = "admin@admin.com";
	private static final String ADMIN_PASSWORD = "admin";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = getUserFromDb(username);
		if (user == null) {
			// TODO - handle Exception
			throw new UsernameNotFoundException("Not Found: " + username);
		}

		return user;
	}

	private User getUserFromDb(String username) {

		if (username.equalsIgnoreCase(ADMIN_EMAIL)) {
			return new User(ADMIN_EMAIL, ADMIN_PASSWORD, new ArrayList<>());
		}
		
		Company company = this.companyRepository.findByEmail(username);
		if (company != null) {
			return new User(company.getEmail(), company.getPassword(), new ArrayList<>());
		}
		
		Customer customer = this.customerRepository.findByEmail(username);
		if (customer != null) {
			return new User(customer.getEmail(), customer.getPassword(), new ArrayList<>());
		}

		return null;
	}

}
