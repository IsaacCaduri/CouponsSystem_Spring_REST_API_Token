package com.jb.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jb.dao.CouponRepository;
import com.jb.dao.CustomerRepository;
import com.jb.entity.Coupon;
import com.jb.entity.Customer;

@Service
@Scope("prototype")
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepo;
	private CouponRepository couponRepo;
	private long CustomerId;

	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepo, CouponRepository couponRepo) {
		this.customerRepo = customerRepo;
		this.couponRepo = couponRepo;
	}

	@Override
	public Collection<Customer> findAll() {
		return customerRepo.findAll();
	}

	@Override
	public Customer findCustomerById(long id) {
		return customerRepo.findById(id).orElse(null);
	}

	@Override
	public Customer craete(Customer customer) {
		if (customer != null) {
			customer.setId(0);
			return customerRepo.save(customer);
		}
		return null;
	}

	@Override
	public Customer update(Customer customer) {
		return customerRepo.save(customer);
	}

	@Override
	public void deleteById(long id) {
		customerRepo.deleteById(id);
	}

	@Override
	public Collection<Coupon> findAllCoupons() {
		return couponRepo.findAll();
	}

	@Override
	public Collection<Coupon> findAllCouponsByCategory(long id, int categoryId) {
		return couponRepo.findAllByCompanyIdAndCategory(id, categoryId);
	}

	@Override
	public Collection<Coupon> findCustomerCoupon(long id) {
		return findCustomerCoupon(CustomerId);
	}


	public void setCustomerId(long customerId) {
		this.CustomerId = customerId;
	}

	@Override
	public Coupon findCouponById(long id) {
		return couponRepo.findById(id).orElse(null);
	}

}
