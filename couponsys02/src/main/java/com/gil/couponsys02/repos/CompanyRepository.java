package com.gil.couponsys02.repos;

import java.util.Optional;

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
	Company findByEmail(String email);

}	
