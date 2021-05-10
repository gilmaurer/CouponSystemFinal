package com.gil.couponsys02.repos;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gil.couponsys02.beans.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	boolean existsByEmail(String email);
	boolean existsByEmailAndIdIsNot(String email, int id);
	Customer findByEmailAndPassword(String email, String password);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM customers_coupons WHERE coupons_id = :id", nativeQuery = true)
	void deleteCouponsFromCustomersCouponsByCouponId(@Param(value = "id") int couponId);

}
