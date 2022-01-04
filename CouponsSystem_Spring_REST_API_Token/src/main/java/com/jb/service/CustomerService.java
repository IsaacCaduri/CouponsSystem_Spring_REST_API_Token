package com.jb.service;

import java.util.Collection;

import com.jb.entity.Coupon;
import com.jb.entity.Customer;

public interface CustomerService {

	Collection<Customer> findAll();
	
	Customer craete(Customer customer);
	
	Customer update(Customer customer);
	
	void deleteById(long id);

	Customer findCustomerById(long id);
	
	/*Function of Coupon's form the Customer side - findAllCoupons() */

	Collection<Coupon> findAllCoupons();
	
	Collection<Coupon> findAllCouponsByCategory(long id,int category);
	
	Collection<Coupon>findCustomerCoupon(long id);
	
	Coupon findCouponById(long id);
	
	
	
}

