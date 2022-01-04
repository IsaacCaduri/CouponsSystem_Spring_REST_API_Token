package com.jb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	// FREE CreateReadUpdateDelete
	
	Customer findByEmailAndPassword(String email, String password);

}