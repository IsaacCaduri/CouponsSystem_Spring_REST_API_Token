package com.jb.rest;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jb.service.AdminService;
import com.jb.service.CompanyService;
import com.jb.service.CustomerService;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClientSession {
	
	private AdminService adminService;
	private CompanyService companyService;
	private CustomerService customerService;
	

	private long lastAccessedMillis;

	
	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public long getLastAccessedMillis() {
		return lastAccessedMillis;
	}

	public void accessed() {
		this.lastAccessedMillis = System.currentTimeMillis();
	}

}