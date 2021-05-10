package com.gil.couponsys02.clr.primary;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.gil.couponsys02.beans.Category;
import com.gil.couponsys02.beans.Company;
import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.beans.Customer;
import com.gil.couponsys02.repos.CompanyRepository;
import com.gil.couponsys02.repos.CouponRepository;
import com.gil.couponsys02.repos.CustomerRepository;
import com.gil.couponsys02.utils.ArtUtils;
import com.gil.couponsys02.utils.PrintUtils;

import lombok.RequiredArgsConstructor;

@Component
@Order(1)
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

	private final CompanyRepository companyRepository;
	private final CustomerRepository customerRepository;
	private final CouponRepository couponRepository;
	private final PrintUtils print;

	private static final int NUM_COMPANIES = 5;
	private static final int NUM_CUSTOMERS = 10;

	
	private List<Company> companiesToDb = new ArrayList<>();
	private List<Customer> customerToDb = new ArrayList<>();

	@Override
	public void run(String... args) throws Exception {

		print.printArt(ArtUtils.BOOTSTRAP);

		initCompanies();
		initCustomers();
		printTables();
		
	}

	private void printTables() throws IllegalArgumentException, IllegalAccessException {
		print.printTable(companyRepository.findAll(), Company.class);
		print.printTable(couponRepository.findAll(), Coupon.class);
		print.printTable(customerRepository.findAll(), Customer.class);
	}

	private void initCustomers() {
		List<Coupon> couponsFromDb = couponRepository.findAll();
		for (int i = 0; i < NUM_CUSTOMERS; i++) {
			CustomerDummy dummy = CustomerDummy.values()[i];
			Customer customer = Customer.builder()
					.firstName(dummy.getFirsName())
					.lastName(dummy.getLastName())
					.email(dummy.getEmail())
					.password(dummy.getPassword())
					.coupon(couponsFromDb.get(i))
					.build();

			customerToDb.add(customer);
		}
		customerRepository.saveAll(customerToDb);

	}

	private void initCompanies() {
		for (int i = 0; i < NUM_COMPANIES; i++) {

			CompanyDummy dummy = CompanyDummy.values()[i];
			Company company = Company.builder().name(dummy.name()).email(dummy.getEmail()).password(dummy.getPassword())
					.build();

			Coupon coupon1 = getCoupon(company, i * 2);
			Coupon coupon2 = getCoupon(company, (i * 2) + 1);

			company.setCoupons(Arrays.asList(coupon1, coupon2));

			companiesToDb.add(company);
		}
		companyRepository.saveAll(companiesToDb);

	}

	private Coupon getCoupon(Company company, int index) {
		CouponDummy dummy = CouponDummy.values()[index];
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
