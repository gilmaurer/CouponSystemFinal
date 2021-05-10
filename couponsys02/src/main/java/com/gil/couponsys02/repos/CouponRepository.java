package com.gil.couponsys02.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gil.couponsys02.beans.Category;
import com.gil.couponsys02.beans.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer>  {
	boolean existsByCompanyIdAndTitle(int companyId, String Title);
	boolean existsByIdAndCompanyId(int id, int companyId);
	boolean existsByCompanyIdAndTitleAndIdIsNot(int companyId, String title, int id);
	List<Coupon> findByCompanyId(int companyId);
	List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);
	List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyId, double maxPrice);
	
	@Transactional
	@Modifying
	@Query(value ="DELETE FROM customers_coupons WHERE coupons_id IN (SELECT id FROM coupons WHERE company_id = :id)", nativeQuery = true)
	void deleteCouponsFromCustomersCouponsByCompanyId(@Param(value = "id") int companyId);
	
	@Transactional
	@Modifying
	@Query(value ="DELETE FROM customers_coupons WHERE coupons_id = :id", nativeQuery = true)
	void deleteCouponsFromCustomersCouponsByCouponId(@Param(value = "id") int couponId);
	//
	
	@Query(value ="SELECT * FROM coupons WHERE end_date < now();", nativeQuery = true)
	List<Coupon> getExpiredCoupon();

}
