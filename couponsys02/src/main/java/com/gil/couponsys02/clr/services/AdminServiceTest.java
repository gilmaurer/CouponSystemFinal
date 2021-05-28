package com.gil.couponsys02.clr.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gil.couponsys02.beans.Company;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.beans.Customer;
import com.gil.couponsys02.clr.primary.CompanyDummy;
import com.gil.couponsys02.clr.primary.CustomerDummy;
import com.gil.couponsys02.exceptions.ExceptionHandler;
import com.gil.couponsys02.login.ClientType;
import com.gil.couponsys02.login.LoginManager;
import com.gil.couponsys02.services.AdminService;
import com.gil.couponsys02.utils.ArtUtils;
import com.gil.couponsys02.utils.PrintUtils;

import lombok.RequiredArgsConstructor;

//@Component
@Order(2)
@RequiredArgsConstructor
public class AdminServiceTest implements CommandLineRunner {

	private final LoginManager loginManager;
	private final ExceptionHandler exceptionHandler;
	private final PrintUtils print;

	private AdminService adminService;

	private static final String ADMIN_EMAIL = "admin@admin.com";
	private static final String ADMIN_PASSWORD = "admin";

	private static int testNumber = 0;

	@Override
	public void run(String... args) throws Exception {

		print.printArt(ArtUtils.ADMIN_TEST);
		loginTest();
		addComponyTest();
		updateComponyTest();
		deleteComponyTest();
		getAllComponiesTest();
		getOneCompanyTest();
		addCustomerTest();
		updateCustomerTest();
		deleteCustomerTest();
		getAllCustomersTest();
		getOneCustomerTest();

	}

	private void loginTest() {
		try {
			print.testTitle("Login Test", true, ++testNumber);
			adminService = (AdminService) loginManager.login(ADMIN_EMAIL, ADMIN_PASSWORD, ClientType.ADMINISTRATOR);
			print.result("Success to Login as " + ClientType.ADMINISTRATOR.name());

			print.testTitle("Login Test", false, ++testNumber);
			loginManager.login("WrongEmail@admin.com", ADMIN_PASSWORD, ClientType.ADMINISTRATOR);

		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
	}

	private void addComponyTest() {
		try {
			print.testTitle("Add Company", true, ++testNumber);
			print.result("Company " + CompanyDummy.KFC.name() + " will be added to Companies table");
			adminService.addCompany(Company.builder().name(CompanyDummy.KFC.name()).email(CompanyDummy.KFC.getEmail())
					.password(CompanyDummy.KFC.getPassword()).build());
			print.printTable(adminService.getAllCompanies(), Company.class);

			companyNameAlreadyInUseException();
			companyEmailAlreadyInUseExcpetion();

		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void companyNameAlreadyInUseException() {
		try {
			print.testTitle("Add Company", false, ++testNumber);
			print.result("Add Company with already in use name " + CompanyDummy.APPLE.name() + " will fail");
			adminService.addCompany(Company.builder().name(CompanyDummy.APPLE.name()).email(CompanyDummy.EMI.getEmail())
					.password(CompanyDummy.EMI.getPassword()).build());
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void companyEmailAlreadyInUseExcpetion() {
		try {
			print.testTitle("Add Company", false, ++testNumber);
			print.result("Add Company with already in use email " + CompanyDummy.APPLE.getEmail() + " will fail");
			adminService.addCompany(Company.builder().name(CompanyDummy.EMI.name()).email(CompanyDummy.APPLE.getEmail())
					.password(CompanyDummy.EMI.getPassword()).build());
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void updateComponyTest() {

		try {
			print.testTitle("Update Company", true, ++testNumber);
			print.result(CompanyDummy.NIKE.name() + "'s email and password will be updated successfully");
			Company toUpdate = adminService.getOneCompany(2);
			toUpdate.setEmail("NEWNikeemail@nike.com");
			toUpdate.setPassword("newnikepass");
			adminService.updateCompany(toUpdate);
			print.printTable(adminService.getAllCompanies(), Company.class);

			comapnyNameNotAllowedToChangeException();
			companyEmailAlreadyInUseException();

		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void comapnyNameNotAllowedToChangeException() {

		try {
			print.testTitle("Update Company", false, ++testNumber);
			print.result("Update the name  of " + CompanyDummy.NIKE.name() + "to new name will fail");
			Company toUpdate = adminService.getOneCompany(2);
			toUpdate.setName("nikeNewName");
			adminService.updateCompany(toUpdate);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
	}

	void companyEmailAlreadyInUseException() {
		try {
			print.testTitle("Update Company", false, ++testNumber);
			print.result("Update the email  of " + CompanyDummy.NIKE.name() + "to " + CompanyDummy.ALIEXPRESS.getEmail()
					+ " will fail");
			Company toUpdate = adminService.getOneCompany(2);
			toUpdate.setEmail(CompanyDummy.ALIEXPRESS.getEmail());
			adminService.updateCompany(toUpdate);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void deleteComponyTest() {

		try {
			print.testTitle("Delete Company", true, ++testNumber);
			print.result(CompanyDummy.ALIEXPRESS.name()
					+ " (id 5) will be deleted from Companies Table, as well as it's Coupons (id 9, 10)");
			adminService.deleteCompany(5);
			print.printTable(adminService.getAllCompanies(), Company.class);
			print.printTable(adminService.getAllCustomers(), Customer.class);
			print.printTable(adminService.getAllCoupons(), Coupon.class);

			print.testTitle("Delete Company", false, ++testNumber);
			print.result("Attempt to delete company with id = 22 will fail");
			adminService.deleteCompany(22);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}
	}

	private void getAllComponiesTest() {
		try {
			print.testTitle("Get All Companies", true, ++testNumber);
			print.printTable(adminService.getAllCompanies(), Company.class);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void getOneCompanyTest() {
		try {
			print.testTitle("Get One Company By ID", true, ++testNumber);
			print.result(CompanyDummy.FACEBOOK.name() + " will retrived by it's ID (3)");
			print.printTable(Arrays.asList(adminService.getOneCompany(3)), Company.class);

			print.testTitle("Get One Company By ID", false, ++testNumber);
			print.result("Attempt to get company with id = 13 will fail");
			adminService.getOneCompany(13);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void addCustomerTest() {
		try {
			print.testTitle("Add Customer", true, ++testNumber);
			print.result("Customer " + CustomerDummy.IDAN_S + " will be added to Customers table");
			adminService.addCustomer(Customer.builder().firstName(CustomerDummy.IDAN_S.getFirsName()).lastName(CustomerDummy.IDAN_S.getLastName())
					.email(CustomerDummy.IDAN_S.getEmail()).password(CustomerDummy.IDAN_S.getPassword()).build());
			print.printTable(adminService.getAllCustomers(), Customer.class);
			
			
			print.testTitle("Add Customer", false, ++testNumber);
			print.result("Add Customer with already in use email " + CustomerDummy.MICHAELA.getEmail() + " will fail");
			adminService.addCustomer(Customer.builder().firstName(CustomerDummy.MICHAELA.getFirsName()).lastName(CustomerDummy.MICHAELA.getLastName())
					.email(CustomerDummy.MICHAELA.getEmail()).password(CustomerDummy.MICHAELA.getPassword()).build());
			
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void updateCustomerTest() {
		try {
			Customer toUpdate = adminService.getOneCustomer(5);
			print.testTitle("Update Customer", true, ++testNumber);
			print.result(CustomerDummy.BRANDA.getFirsName() + " " + CustomerDummy.BRANDA.getLastName() + "(id: "
					+ toUpdate.getId() + ") last name, email and password will be updated");
			toUpdate.setLastName("NewLastName");
			toUpdate.setEmail("newBrandaMail@new.com");
			toUpdate.setPassword("newbrandapass");
			adminService.updateCustomer(toUpdate);
			print.printTable(Arrays.asList(adminService.getOneCustomer(5)), Customer.class);
			
			print.testTitle("Update Customer", false, ++testNumber);
			print.result("Update email of Customer " + toUpdate.getId()  + " with already in use email will fail");
			toUpdate.setEmail(CustomerDummy.EITAN.getEmail());
			adminService.updateCustomer(toUpdate);
			
		} catch (Exception e) {
			exceptionHandler.handle(e);
			
		}
	}

	private void deleteCustomerTest() {
		try {
			print.testTitle("Delete Customer", true, ++testNumber);
			print.result(CustomerDummy.SVETLANA.name() + " (id 6) will be deleted from Cutomers Table");
			adminService.deleteCustomer(6);
			print.printTable(adminService.getAllCustomers(), Customer.class);
			
			print.testTitle("Delete Customer", false, ++testNumber);
			print.result("Attempt to delete cutomer with id = 43 will fail");
			adminService.deleteCustomer(43);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void getAllCustomersTest() {
		try {
			print.testTitle("Get All Customers", true, ++testNumber);
			print.printTable(adminService.getAllCustomers(), Customer.class);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

	private void getOneCustomerTest() {
		try {
			print.testTitle("Get One Customer By ID", true, ++testNumber);
			print.result(CustomerDummy.EITAN.getFirsName() + " " + CustomerDummy.EITAN.getLastName() + " will retrive by his ID (3)");
			Customer customerFromDb = adminService.getOneCustomer(3);
			print.printTable(Arrays.asList(customerFromDb), Customer.class);
			
			print.testTitle("Get One Customer By ID", false, ++testNumber);
			print.result("Attempt to get customer with id = 61 will failed");
			adminService.getOneCustomer(61);
		} catch (Exception e) {
			exceptionHandler.handle(e);
		}

	}

}
