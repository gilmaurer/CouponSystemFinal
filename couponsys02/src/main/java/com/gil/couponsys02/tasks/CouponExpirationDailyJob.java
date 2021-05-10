package com.gil.couponsys02.tasks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gil.couponsys02.beans.Coupon;
import com.gil.couponsys02.repos.CouponRepository;
import com.gil.couponsys02.utils.PrintUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponExpirationDailyJob {
	
	private final CouponRepository couponRepository;
	private final PrintUtils print;
	private List<Coupon> expiredCoupons;
	

	
	@Scheduled(initialDelay=20000, fixedRate=60000)
	public void deleteExpiredCoupons() throws IllegalArgumentException, IllegalAccessException {
		print.printArt("*** START DAILY JOB ***");
		expiredCoupons = couponRepository.getExpiredCoupon();
		print.result("Expired");
		print.printTable(expiredCoupons, Coupon.class);
		print.result("Coupon Deleted.........");
		for(Coupon coupon : expiredCoupons) {
			if(LocalDate.now().isAfter(coupon.getEndDate().toLocalDate())) {
				couponRepository.deleteCouponsFromCustomersCouponsByCouponId(coupon.getId());
				couponRepository.deleteById(coupon.getId());
			}
		}
		print.printArt("*** END DAILY JOB ***");
		
		print.result("After Deletion:");
		print.printTable(couponRepository.findAll(), Coupon.class);
		expiredCoupons.clear();
		
		
		
		
	}
	
	
	
}
