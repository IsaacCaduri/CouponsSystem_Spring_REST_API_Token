package com.jb.rest.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jb.dao.CompanyRepository;
import com.jb.dao.CouponRepository;
import com.jb.dao.CustomerRepository;
import com.jb.entity.Company;
import com.jb.entity.Customer;
import com.jb.rest.ClientSession;
import com.jb.rest.InvalidIDException;
import com.jb.rest.InvalidSessionException;
import com.jb.service.AdminService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AdminController {
	
	private Map<String, ClientSession> tokensMap;
	private AdminService adminService;
	private CompanyRepository companyRepo;
	private CustomerRepository customerRepo;
	
	@Autowired
	public AdminController(@Qualifier("tokens")Map<String, ClientSession> tokensMap, AdminService adminService,
			CompanyRepository companyRepo, CustomerRepository customerRepo, CouponRepository couponRepo) {
		this.tokensMap = tokensMap;
		this.adminService = adminService;
		this.companyRepo = companyRepo;
		this.customerRepo = customerRepo;
	}


	/**
	 * Find All Companies
	 * 
	 * @return All Companies
	 * @throws InvalidLoginException 
	 */
	/**
	 * @param token
	 * @return
	 * @throws InvalidSessionException
	 */
	@GetMapping("/companies")
	public Collection<Company> getAllCompany(){
	 return	adminService.findAllCompanies();
	}
	
	/**[  ]
	 * Find Company By Id
	 * 
	 * @param id
	 * @return Company
	 * @throws InvalidSessionException 
	 */
	@GetMapping("/companies/{id}/{token}")
	public ResponseEntity<Company> getCompany(@PathVariable long id, @PathVariable String token) throws InvalidSessionException {
	
		ClientSession session = getSession(token);
		
		if (session!=null) {
		
			Optional<Company> company = companyRepo.findById(id);

		if (!company.isPresent()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(company.get());
	}
		throw new InvalidSessionException("Session expired!");
	}

	/**[  ]
	 * Add Company
	 * @param company
	 * @return new Company
	 * @throws InvalidSessionException 
	 */
	@PostMapping("/companies/{token}")
	public ResponseEntity<Company> addCompany(@RequestBody Company company, @PathVariable String token)
throws InvalidSessionException{
		
	ClientSession session = getSession(token);
	
	if (session!=null) {
		if(company== null) {
			ResponseEntity.noContent().build();
		}
		company.setId(0);
		companyRepo.save(company);
		return ResponseEntity.ok(company);
	}
	throw new InvalidSessionException("Session expired!");
	}
	
	/**
	 * Find All Customers
	 * 
	 * @return All Customers
	 */
	@GetMapping("/customers")
	public Collection<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

	/**[v]
	 * Find customer by id
	 * @param id
	 * @return customer
	 * @throws InvalidIDException 
	 * @throws InvalidSessionException 
	 */
	@GetMapping("/customers/{id}/{token}")
	public ResponseEntity<Customer> getCustomer(@PathVariable long id, @PathVariable String token) throws InvalidIDException, InvalidSessionException
		{

		ClientSession session = getSession(token);
		if (session!=null) {
			
		Optional<Customer> customer = customerRepo.findById(id);

		if (!customer.isPresent()) {
			throw new InvalidIDException("Wrong ID :-(");
		}
		return ResponseEntity.ok(customer.get());
	}
		throw new InvalidSessionException("Bad Session:-(");
	}

	/**[ ]
	 * Add Customer
	 * @param customer
	 * @return new customer
	 */
	@PostMapping("/customers/{token}")
	public ResponseEntity<Customer>  addCustomer(@RequestBody Customer customer, @PathVariable String token)
			throws InvalidSessionException{
	
		ClientSession session = getSession(token);
		
		if (session!=null) {
			if (customer==null) {
			ResponseEntity.noContent().build();
				}
		customer.setId(0);
		customerRepo.save(customer);
		return ResponseEntity.ok(customer);
	}
		throw new InvalidSessionException("Session expired!");
	}


	private ClientSession getSession(String token) {
		return tokensMap.get(token);
	}
	
	
	

}
