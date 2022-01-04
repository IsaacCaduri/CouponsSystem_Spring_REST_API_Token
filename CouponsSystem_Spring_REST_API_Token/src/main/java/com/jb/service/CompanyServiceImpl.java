package com.jb.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jb.dao.CompanyRepository;
import com.jb.dao.CouponRepository;
import com.jb.entity.Company;
import com.jb.entity.Coupon;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompanyServiceImpl implements CompanyService {
	
	private CompanyRepository companyRepository;
	private CouponRepository couponRepository;
	private long CompenyId;
	
	
	
	@Autowired
	public CompanyServiceImpl(CompanyRepository companyRepository, CouponRepository couponRepository) {
		this.companyRepository = companyRepository;
		this.couponRepository = couponRepository;
	}

	@Override
	public Collection<Company> findAll() {
		return companyRepository.findAll();
	}

	@Override
	public Company findCompanyById(long id) {
		return companyRepository.findById(id).orElse(null);
	}

	@Override
	public Company craete(Company company) {
		if (company != null) {
			company.setId(0);
			return companyRepository.save(company);
		}
		return null;
	}

	@Override
	public Company update(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public void deleteById(long id) {
		companyRepository.deleteById(id);
	}

	@Override
	public Coupon craeteCoupon(Coupon coupon) {
		if (coupon != null) {
			coupon.setId(0);
			return couponRepository.save(coupon);
		}
		return null;
	}

	@Override
	public void  DeleteCoupon(long id) {
	couponRepository.deleteById(id);
	}

	@Override
	public Coupon updateCoupon(Coupon coupon) {
		return couponRepository.save(coupon);
	}

	@Override
	public Collection<Coupon> findAllCoupons() {
		return couponRepository.findAll();
	}

	@Override
	public Collection<Coupon> findCompanyCouponsByCategory(int category) {
		return findCompanyCouponsByCategory(category);
	}

	public void setComanyId(long id) {
	this.CompenyId = id;
	}


}
