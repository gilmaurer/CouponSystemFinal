package com.gil.couponsys02.clr.services;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gil.couponsys02.beans.Category;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.beans.Customer;
import com.gil.couponsys02.clr.primary.CustomerDummy;
import com.gil.couponsys02.exceptions.ExceptionHandler;
import com.gil.couponsys02.login.ClientType;
import com.gil.couponsys02.login.LoginManager;
import com.gil.couponsys02.services.AdminService;
import com.gil.couponsys02.services.CustomerService;
import com.gil.couponsys02.services.CustomerServiceImpl;
import com.gil.couponsys02.utils.ArtUtils;
import com.gil.couponsys02.utils.PrintUtils;

import lombok.RequiredArgsConstructor;

@Component
@Order(4)
@RequiredArgsConstructor
public class CustomerServiceTest implements CommandLineRunner {

	private final LoginManager loginManager;
	private final AdminService adminService;
	private final ExceptionHandler exceptionHandler;
	private final PrintUtils print;

	private CustomerService customerService;
	private int customerId;

	private static int testNumber = 0;

	@Override
	public void run(String... args) throws Exception {
		print.printArt(ArtUtils.CUSTOMER_TEST);
		loginTest();
		purchaseCouponTest();
		getCustomerCouponsTest();
		getCustomerCouponsByCategoryTest();
		getCustomerCouponsByPriceTest();
		getCustomerDetails();

	}

	private void loginTest() {
		try {
			print.testTitle("Login Test", true, ++testNumber);
			CustomerDummy customer = CustomerDummy.GIL;
			customerService = (CustomerService) loginManager.login(customer.getEmail(), customer.getPassword(),
					ClientType.CUSTOMER);
			customerId = ((CustomerServiceImpl) customerService).getCustomerId();
			print.result("Success to Login as " + customer.name() + "[id: " + customerId + "]");

			print.testTitle("Login Test", false, ++testNumber);
			loginManager.login("gilWrongEmail@maurer.com", customer.getPassword(), ClientType.CUSTOMER);

		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void purchaseCouponTest() {
		// TODO Auto-generated method stub
		try {
			print.testTitle("Purchase A Coupon By Logged In Customer", true, ++testNumber);
			print.result("Logged In Customer [id: " + customerId + "]  will purchase Coupon id 5. \n"
			+ "following the purchase, the coupon will be added to the customer's coupon list and the coupon amount will decrease by 1");
			Coupon couponToPurchase = adminService.getOneCompany(3).getCoupons().get(0);
			customerService.purchaseCoupon(couponToPurchase);
			print.printTable(Arrays.asList(customerService.getCustomerDetails()), Customer.class);
			print.printTable(customerService.getCustomerCoupons(couponToPurchase.getCategory()), Coupon.class);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
		
		couponAlreadyPurchasedException();
		noCouponLeftException();
		couponExpiredException();

	}

	private void couponAlreadyPurchasedException() {
		try {
			print.testTitle("Purchase A Coupon By Logged In Customer - coupon Already Purchased", false, ++testNumber);
			print.result("Logged In Customer [id: " + customerId + "]  will try purchase Coupon id 1. \n"
							+ "The purchase will fail because the customer already purchased this coupon");
			Coupon couponToPurchase = customerService.getCustomerCoupons().get(0);
			customerService.purchaseCoupon(couponToPurchase);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
		
	}

	private void noCouponLeftException() {
		try {
			print.testTitle("Purchase A Coupon By Logged In Customer - No Coupon Left", false, ++testNumber);
			print.result("Logged In Customer [id: " + customerId + "]  will try purchase Coupon id 8. \n"
							+ "The purchase will fail because there's no coupons left (coupon's amount = 0)");
			Coupon couponToPurchase = adminService.getOneCompany(4).getCoupons().get(1);
			customerService.purchaseCoupon(couponToPurchase);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
		
		
		
		
	}

	private void couponExpiredException() {
		try {
			print.testTitle("Purchase A Coupon By Logged In Customer - Coupon Expired", false, ++testNumber);
			print.result(
					"Logged In Customer [id: " + customerId + "]  will try purchase Coupon id 4. \n"
							+ "The purchase will fail because the coupon's end date has expired");
			Coupon couponToPurchase = adminService.getOneCompany(2).getCoupons().get(1);
			customerService.purchaseCoupon(couponToPurchase);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
		
		
	}

	private void getCustomerCouponsTest() {
		print.testTitle("Get All Customer's Coupons", true, ++testNumber);
		try {
			print.result("Get only coupons that purchased by logged in customer [id: " + customerId + "]");
			print.printTable(customerService.getCustomerCoupons(), Coupon.class);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void getCustomerCouponsByCategoryTest() {
		try {
			print.testTitle("Get All Customer's Coupons By Category", true, ++testNumber);
			print.result("Get only coupons that purchased by logged in customer [id: "
					+ customerId + "] with category " + Category.FOOD);
			print.printTable(customerService.getCustomerCoupons(Category.FOOD), Coupon.class);
			
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
	}

	private void getCustomerCouponsByPriceTest() {
		// TODO Auto-generated method stub
		try {
			print.testTitle("Get All Customer's Coupons Up to Price", true, ++testNumber);
			double maxPrice = 50;
			print.result("Get only coupons that purchased by logged in customer [id: "
					+ customerId + "] up to price of " + maxPrice);
			print.printTable(customerService.getCustomerCoupons(maxPrice), Coupon.class);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void getCustomerDetails() {
		// TODO Auto-generated method stub
		try {
			print.testTitle("Get Customer Details", true, ++testNumber);
			print.result("Get Details of logged in customer [" + customerId + "]");
			print.printTable(Arrays.asList(customerService.getCustomerDetails()), Customer.class);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

}
