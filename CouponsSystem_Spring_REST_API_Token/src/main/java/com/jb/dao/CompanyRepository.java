package com.jb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.entity.Company;

/**
 * 
 * @author isaac290
 *
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	// FREE CreateReadUpdateDelete

	Company findByEmailAndPassword(String email, String password);
}
