package com.gil.couponsys02.services;

import java.util.List;

import com.gil.couponsys02.beans.Category;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.beans.Customer;
import com.gil.couponsys02.exceptions.AlreadyInUseException;
import com.gil.couponsys02.exceptions.DataNotFoundException;
import com.gil.couponsys02.exceptions.InvalidDataException;

public interface CustomerService {
	void purchaseCoupon(Coupon coupon) throws AlreadyInUseException, DataNotFoundException, InvalidDataException;
	List<Coupon> getCustomerCoupons() throws DataNotFoundException;
	List<Coupon> getCustomerCoupons(Category category) throws DataNotFoundException;
	List<Coupon> getCustomerCoupons(double maxPrice) throws DataNotFoundException;
	Customer getCustomerDetails() throws DataNotFoundException;
}
