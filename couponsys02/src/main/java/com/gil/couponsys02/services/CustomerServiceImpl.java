package com.gil.couponsys02.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.gil.couponsys02.beans.Category;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.beans.Customer;
import com.gil.couponsys02.exceptions.AlreadyInUseException;
import com.gil.couponsys02.exceptions.DataNotFoundException;
import com.gil.couponsys02.exceptions.ErrorsMessages;
import com.gil.couponsys02.exceptions.InvalidDataException;
import com.gil.couponsys02.exceptions.UnauthorizedAccessException;
import com.gil.couponsys02.login.ClientType;

@Service
@Scope("prototype")
public class CustomerServiceImpl extends ClientService implements CustomerService {

	private int customerId;
	
	@Override
	public ClientService login(String email, String password) throws UnauthorizedAccessException {
		Customer customer = this.customerRepository.findByEmailAndPassword(email, password);
		if (customer == null) {
			throw new UnauthorizedAccessException(ErrorsMessages.LOGIN_FAILED, email, ClientType.CUSTOMER.name());

		}
		setCustomerId(customer.getId());
		return this;
	}

	@Override
	//@Transactional
	public void purchaseCoupon(Coupon coupon) throws AlreadyInUseException, DataNotFoundException, InvalidDataException {
		Customer customer = getCustomerDetails();
		validatePurchase(coupon, customer);
		coupon.setAmount(coupon.getAmount()-1);
		customer.getCoupons().add(coupon);
		this.couponRepository.saveAndFlush(coupon);
		this.customerRepository.saveAndFlush(customer);

	}

	private void validatePurchase(Coupon coupon, Customer customer) throws AlreadyInUseException, InvalidDataException {
		if(customer.getCoupons().stream().anyMatch(customerCoupon -> customerCoupon.getId() == coupon.getId())) {
			throw new AlreadyInUseException(ErrorsMessages.COUPON_ALREADY_PURCHASED, customerId, coupon.getId());
		}
		if(coupon.getAmount() == 0) {
			throw new InvalidDataException(ErrorsMessages.NO_COUPONS_LEFT, coupon.getId());
		}
		if(LocalDate.now().isAfter(coupon.getEndDate().toLocalDate())) {
			throw new InvalidDataException(ErrorsMessages.COUPON_EXPIRED, coupon.getId());
		}
	}

	@Override
	public List<Coupon> getCustomerCoupons() throws DataNotFoundException {
		return getCustomerDetails().getCoupons();
	}

	@Override
	public List<Coupon> getCustomerCoupons(Category category) throws DataNotFoundException {
		return getCustomerDetails().getCoupons().stream().filter(coupon -> coupon.getCategory() == category)
				.collect(Collectors.toList());
	}

	@Override
	public List<Coupon> getCustomerCoupons(double maxPrice) throws DataNotFoundException {
		return getCustomerDetails().getCoupons().stream().filter(coupon -> coupon.getPrice() <= maxPrice)
				.collect(Collectors.toList());

	}

	@Override
	public Customer getCustomerDetails() throws DataNotFoundException {
		return this.customerRepository.findById(customerId).orElseThrow(() -> new DataNotFoundException(ErrorsMessages.NOT_FOUND_BY_ID,
				Customer.class.getSimpleName(), customerId));
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

}
