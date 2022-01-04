package com.jb.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.jb.dao.AdminRepository;
import com.jb.dao.CompanyRepository;
import com.jb.dao.CouponRepository;
import com.jb.dao.CustomerRepository;
import com.jb.entity.Admin;
import com.jb.entity.Company;
import com.jb.entity.Customer;
import com.jb.service.AdminServiceImpl;
import com.jb.service.CompanyServiceImpl;
import com.jb.service.CustomerServiceImpl;

@Service
public class CouponSystem {

	private ApplicationContext context;
	private CompanyRepository companyRepository;
	private CustomerRepository customerRepository;
	private AdminRepository adminRepository;

	
	
	@Autowired
	public CouponSystem(ApplicationContext context, CompanyRepository companyRepository,
			CustomerRepository customerRepository, CouponRepository couponRepository, AdminRepository adminRepository) {
		this.context = context;
		this.companyRepository = companyRepository;
		this.customerRepository = customerRepository;
		this.adminRepository = adminRepository;
	}


	
	public ClientSession login(String email, String password) throws InvalidLoginException {
	
		/*Login = customer*/
		Customer customer = customerRepository.findByEmailAndPassword(email, password);
			if (customer == null) {
		
		/*Login = company*/
			Company company = companyRepository.findByEmailAndPassword(email, password);
			if (company==null) {
			
				/*Login = company*/
				Admin admin = adminRepository.findByEmailAndPassword(email, password);
				if (admin==null) {
					throw new InvalidLoginException("Invalid email or password");
			}
				/* Creates the service. */
				AdminServiceImpl service = context.getBean(AdminServiceImpl.class);
				service.setAdminId(admin.getId());
				/* Build the session. */
				ClientSession session = context.getBean(ClientSession.class);
				/* Set the last access time. */
				session.setAdminService(service);
				session.accessed();
				/* return the session. */
				return session;	
			}
			/* Creates the service. */
			CompanyServiceImpl service = context.getBean(CompanyServiceImpl.class);
			service.setComanyId(company.getId());
			/* Build the session. */
			ClientSession session = context.getBean(ClientSession.class);
			/* Set the last access time. */
			session.setCompanyService(service);
			session.accessed();
			/* return the session. */
			return session;	
		}
		/* Creates the service. */
		CustomerServiceImpl service = context.getBean(CustomerServiceImpl.class);
		service.setCustomerId(customer.getId());
		/* Build the session. */
		ClientSession session = context.getBean(ClientSession.class);
		/* Set the last access time. */
		session.setCustomerService(service);
		session.accessed();
		/* return the session. */
		return session;
	}
}
