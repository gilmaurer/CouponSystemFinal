package com.gil.couponsys02.services;

import java.util.List;

import com.gil.couponsys02.beans.Category;
import com.gil.couponsys02.beans.Company;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.exceptions.AlreadyInUseException;
import com.gil.couponsys02.exceptions.DataNotFoundException;
import com.gil.couponsys02.exceptions.InvalidDataException;

public interface CompanyService {
	void addCoupon(Coupon coupon) throws AlreadyInUseException;
	void updateCoupon(Coupon coupon) throws InvalidDataException, AlreadyInUseException;
	void deleteCoupon(int couponId) throws DataNotFoundException;
	List<Coupon> getCompanyCoupons();
	List<Coupon> getCompanyCoupons(Category category);
	List<Coupon> getCompanyCoupons(double maxPrice);
	Company getCompanyDetails() throws DataNotFoundException ;
	
}
