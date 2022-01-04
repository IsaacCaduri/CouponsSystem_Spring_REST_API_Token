package com.jb.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jb.entity.Coupon;


@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

	// FREE CreateReadUpdateDelete
	
	/**
	 * Get all coupons for a specified company.
	 * 
	 * @param id
	 * The id of the company.
	 * @return Coupons of the company.
	 */
	List<Coupon> findAllByCompanyId(long id);
	
	/**
	 * Find All Coupons By CategoryId
	 * @param categoryId
	 * @return Coupons By CategoryId
	 */
	List<Coupon>findAllCouponsByCategory(int category);
	

	/**
	 * Get all coupons in a given category for a specified company.
	 * 
	 * @param id
	 * The id of the company.
	 * @param category
	 * The category.
	 * @return Coupons of the company for a given category.
	 */
	List<Coupon> findAllByCompanyIdAndCategory(long id, long categoryId);

	/**
	 * Get all coupons below price, for a specified company.
	 * 
	 * @param id
	 * The id of the company.
	 * @param price
	 * The price below which all coupons will be returned.
	 * @return Coupons below price for the company.
	 */
	List<Coupon> findByCompanyIdAndPriceLessThan(long id, double price);

	/**
	 * Get all coupons before a given end date, for a specified company.
	 * 
	 * @param id
	 * The id of the company.
	 * @param date
	 * The date.
	 * @return Coupons before the given end date.
	 */
	List<Coupon> findAllByCompanyIdAndEndDateBefore(long id, Date date);

	/**
	 * Get all coupons for a specified customer.
	 * 
	 * @param id
	 * The id of the customer.
	 * @return All customer coupons.
	 */
	@Query("select coupons from Customer customer join customer.coupons coupons where customer.id=:id ")
	List<Coupon> findCustomerCoupons(long id);

	/**
	 * Get all customer coupons for a given category.
	 * @param id The id of the customer.
	 * @param category The category.
	 * @return Coupons for a given category.
	*/
	@Query("select coupons from Customer customer join customer.coupons coupons where customer.id=:id and coupons.category=:category ")
	List<Coupon> findCustomerCouponsByCategory(long id, long category);
	
	/**
	 * Get all coupons below price, for a specified customer.
	 * 
	 * @param id
	 * The id of the customer.
	 * @param price
	 * The price below which all coupons will be returned.
	 * @return Coupons below price for the customer.
	 */
	@Query("select coupons from Customer customer join customer.coupons coupons where customer.id=:id and coupons.price<:price ")
	List<Coupon> findCustomerCouponsLessThan(long id, double price);
	
	/**
	 * @return All expired coupons in the system.
	 */
	@Query("from Coupon as  coupons where coupons.endDate< NOW()")
	List<Coupon> findAllExpiredCoupons();
	
	
}
