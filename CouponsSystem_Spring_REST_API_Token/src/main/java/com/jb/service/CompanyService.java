package com.jb.service;

import java.util.Collection;

import com.jb.entity.Company;
import com.jb.entity.Coupon;

public interface CompanyService {
	
	Collection<Company> findAll();
	
	Company findCompanyById(long id);
	
	Company craete(Company company);
	
	Company update(Company company);
	
	void deleteById(long id);
	
	/*Function of Coupon's form the Company side - craeteCoupon() 
	 * 																			DeleteCoupon() 
	 * 																			updateCoupon() 
	 * 																			findAllCoupons()*/
	
	Coupon craeteCoupon(Coupon coupon);
	
	void DeleteCoupon(long id);
	
	Coupon updateCoupon(Coupon coupon);
	
	Collection<Coupon> findAllCoupons();
	
	Collection<Coupon>findCompanyCouponsByCategory(int category);
	
	
}
