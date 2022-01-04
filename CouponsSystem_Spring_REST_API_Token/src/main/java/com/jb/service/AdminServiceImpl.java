package com.jb.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jb.dao.CompanyRepository;
import com.jb.dao.CustomerRepository;
import com.jb.entity.Company;
import com.jb.entity.Customer;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AdminServiceImpl implements AdminService {
	
	
	private CompanyRepository  companyRepository;
	private CustomerRepository customerRepository;
	private long adminId;

	
	@Autowired
	public AdminServiceImpl(CompanyRepository companyRepository,
			CustomerRepository customerRepository) {
		this.companyRepository = companyRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public Collection<Company> findAllCompanies() {
		return companyRepository.findAll();
	}

	@Override
	public Company findCompanyById(long id) {
		return companyRepository.findById(id).orElse(null);
	}

	@Override
	public Company craeteCompany(Company company) {
		if (company != null) {
			company.setId(0);
			return companyRepository.save(company);
		}
		return null;
	}

	@Override
	public void deleteCompanyById(long id) {
		companyRepository.deleteById(id);
	}

	@Override
	public Customer craeteCustomer(Customer customer) {
		if (customer != null) {
			customer.setId(0);
			return customerRepository.save(customer);
		}
		return null;
	}

	@Override
	public Customer findCustomerById(long id) {
		return customerRepository.findById(id).orElse(null);
	}

	public void setAdminId(long adminId) {
	this.adminId = adminId;
	}


}
