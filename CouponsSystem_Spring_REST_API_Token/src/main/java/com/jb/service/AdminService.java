package com.jb.service;

import java.util.Collection;

import com.jb.entity.Company;
import com.jb.entity.Customer;

public interface AdminService {
	
	Collection<Company> findAllCompanies();
	
	Company findCompanyById(long id);
	
	Company craeteCompany(Company company);
	
	void  deleteCompanyById(long id);
		
	Customer craeteCustomer(Customer customer);
	
	Customer findCustomerById(long id);
	
}
