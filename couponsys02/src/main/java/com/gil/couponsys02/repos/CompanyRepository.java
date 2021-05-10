package com.gil.couponsys02.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gil.couponsys02.beans.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	boolean existsByNameOrEmail(String name, String email);
	boolean existsByIdAndName(int id, String name);
	boolean existsByEmailAndIdIsNot(String email, int id);
	Company findByEmailAndPassword(String email, String password);
	Company getIdByEmailAndPassword(String email, String password);
	
	
//	@Query(value="SELECT EXISTS(SELECT * FROM couponsystemspring.companies WHERE email=:email and id<>:id );"
//			, nativeQuery = true)
//	int alreadyEmailExists(
//			@Param("email") String email,
//			@Param("id") Integer id);
}	
