package com.jb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jb.entity.Admin;

/**
 * AdminRepository
 *  Is Hibernate framework for to auto build query for the SQL DataBase
 *  Create Read Update Delete
 *  
 * @author isaac290
 *
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>  {

	// FREE CreateReadUpdateDelete

	/**
	 * Find By Email And Password
	 * Verifying by checking email & password, Admins login
	 * <br>
	 * Email - is unique by default,  
	 * @param email
	 * @param password
	 * @return
	 */
	Admin findByEmailAndPassword(String email, String password);
}
