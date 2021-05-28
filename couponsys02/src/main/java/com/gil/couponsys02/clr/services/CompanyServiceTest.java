package com.gil.couponsys02.clr.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gil.couponsys02.beans.Category;
import com.gil.couponsys02.beans.Company;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.beans.Customer;
import com.gil.couponsys02.clr.primary.CompanyDummy;
import com.gil.couponsys02.clr.primary.CouponDummy;
import com.gil.couponsys02.exceptions.DataNotFoundException;
import com.gil.couponsys02.exceptions.ExceptionHandler;
import com.gil.couponsys02.login.ClientType;
import com.gil.couponsys02.login.LoginManager;
import com.gil.couponsys02.services.AdminService;
import com.gil.couponsys02.services.CompanyService;
import com.gil.couponsys02.services.CompanyServiceImpl;
import com.gil.couponsys02.utils.ArtUtils;
import com.gil.couponsys02.utils.PrintUtils;

import lombok.RequiredArgsConstructor;

//@Component
@Order(3)
@RequiredArgsConstructor
public class CompanyServiceTest implements CommandLineRunner {
	
	private final LoginManager loginManager;
	private final AdminService adminService;
	private final ExceptionHandler exceptionHandler;
	private final PrintUtils print;

	private CompanyService companyService;
	private int companyId;
	
	private static int testNumber = 0;
	
	@Override
	public void run(String... args) throws Exception {
		print.printArt(ArtUtils.COMPANY_TEST);
		loginTest();
		addCouponTest();
		updateCouponTest();
		deleteCouponTest();
		getCompanyCouponsTest();
		getCompanyCouponsByCategoryTest();
		getCompanyCouponsByPriceTest();
		getComponyDetails();
	}

	private void loginTest() {
		try {
			print.testTitle("Login Test", true, ++testNumber);
			CompanyDummy company = CompanyDummy.APPLE;
			companyService = (CompanyService) loginManager.login(company.getEmail(), company.getPassword(), ClientType.COMPANY);
			companyId = ((CompanyServiceImpl)companyService).getComapnyId();
			print.result("Success to Login as " + company.name() + "[id: " + companyId + "]");

			print.testTitle("Login Test", false, ++testNumber);
			loginManager.login("AppleWrongEmail@admin.com", company.getPassword(), ClientType.COMPANY);

		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
	}

	private void addCouponTest() {
		try {
			print.testTitle("Add Coupon", true, ++testNumber);
			print.result("Added new Coupon to logged in company: " + CompanyDummy.APPLE.name());
			Coupon coupon = getCoupon(CouponDummy.COUPON_TEST);  
			companyService.addCoupon(coupon);
			print.printTable(Arrays.asList(companyService.companyDetails()), Company.class);
			print.printTable(adminService.getAllCoupons(), Coupon.class);
			
			print.testTitle("Add Coupon", false, ++testNumber);
			print.result("Added new Coupon to logged in company: " + CompanyDummy.APPLE.name() + " with the same title will fail");
			coupon = getCoupon(CouponDummy.COUPON_TEST);  
			companyService.addCoupon(coupon);
	
			
		} catch(Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void updateCouponTest() {
		try {
			print.testTitle("Update Coupon", true, ++testNumber);
			Coupon toUpdate = companyService.companyCoupons().get(0);
			print.result("The following values of coupon id " + toUpdate.getId() + " will be update: \n"
					+ "Amount: " + toUpdate.getAmount() + ", Price: " + toUpdate.getPrice() + ", Description: "
					+ toUpdate.getDescription());
			
			toUpdate.setAmount(13);
			toUpdate.setPrice(64.99);
			toUpdate.setDescription("coupon New description");
			companyService.updateCoupon(toUpdate);
			print.printTable(adminService.getAllCoupons(), toUpdate.getClass());
			
			updateCompanyIdException();
			titleReplacedException();
	
		} catch(Exception e) {
			exceptionHandler.handle(e);
		}
		
	}

	private void updateCompanyIdException() {
		try {
			print.testTitle("Update Coupon", false, ++testNumber);
			Coupon toUpdate = companyService.companyCoupons().get(0);
			print.result("Update company of coupon id " + toUpdate.getId() + " will fail");
			toUpdate.setCompany(adminService.getOneCompany(3));
			companyService.updateCoupon(toUpdate);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
		
	}

	private void titleReplacedException() {
		try {
			print.testTitle("Update Coupon", false, ++testNumber);
			Coupon toUpdate = companyService.companyCoupons().get(0);
			print.result("Update title of coupon id " + toUpdate.getId()
			+ " will fail");
			
			toUpdate.setTitle(companyService.companyCoupons().get(1).getTitle());
			companyService.updateCoupon(toUpdate);
			
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
		
	}

	private void deleteCouponTest() {
		try {
			print.testTitle("Delete Coupon", true, ++testNumber);	
			print.result("Coupon id 2 will be deleted from Coupons Table, as well from company coupon list and customer purchased history");
			companyService.deleteCoupon(2);
			print.printTable(adminService.getAllCoupons(), Coupon.class);
			print.printTable(adminService.getAllCompanies(), Company.class);
			print.printTable(adminService.getAllCustomers(), Customer.class);
			
			print.testTitle("Delete Coupon", false, ++testNumber);	
			print.result("Attempt to delete coupons with id = 22 will fail");
			companyService.deleteCoupon(22);
			
		} catch(Exception e) {
			exceptionHandler.handle(e);
		}
		
	}

	private void getCompanyCouponsTest() {
		try {
			print.testTitle("Get All Company's Coupons", true, ++testNumber);
			print.result("Get only coupons that belong to logged in company [id: " + companyId + "]");
			print.printTable(companyService.companyCoupons(), Coupon.class);
		} catch(Exception e) {
			exceptionHandler.handle(e);
		}
		
	}

	private void getCompanyCouponsByCategoryTest() {
		try {
			print.testTitle("Get All Company's Coupons By Category", true, ++testNumber);
			print.result("Get only coupons that belong to logged in company [id: "
					+ companyId + "] with category " + Category.FOOD);
			print.printTable(companyService.getCompanyCoupons(Category.FOOD), Coupon.class);
		} catch(Exception e) {
			exceptionHandler.handle(e);
		}
		
	}

	private void getCompanyCouponsByPriceTest() {
		try {
			print.testTitle("Get All Company's Coupons Up to Price", true, ++testNumber);
			double maxPrice = 50;
			print.result("Get only coupons that belong to logged in company [id: "
					+ companyId + "] up to price of " + maxPrice);
			print.printTable(companyService.getCompanyCoupons(maxPrice), Coupon.class);
		} catch(Exception e) {
			exceptionHandler.handle(e);
		}
		
	}

	private void getComponyDetails() {
		try {
			print.testTitle("Get Company Details", true, ++testNumber);
			print.result("Get Details of logged in company [" + companyId + "]");
			print.printTable(Arrays.asList(companyService.companyDetails()), Company.class);
		} catch(Exception e) {
			exceptionHandler.handle(e);
		}
		
	}
	
	private Coupon getCoupon(CouponDummy dummy) throws DataNotFoundException {
		Company company = this.companyService.companyDetails();
		Category category = Category.values()[dummy.getCategoryId()];
		String title = category.getTitle(dummy.getTitle());
		String description = company.getName() + " " + title;
		Date startDate = Date.valueOf(LocalDate.of(dummy.getYear(), dummy.getMonth(), dummy.getDay()));
		Date endDtate = Date.valueOf(startDate.toLocalDate().plusYears(1));
		int amount = dummy.getAmount();
		double price = dummy.getPrice();
		String image = description.replaceAll(" ", "") + ".jpg";

		return Coupon.builder().company(company).category(category).title(title).description(description)
				.startDate(startDate).endDate(endDtate).amount(amount).price(price).image(image).build();
	}

}
