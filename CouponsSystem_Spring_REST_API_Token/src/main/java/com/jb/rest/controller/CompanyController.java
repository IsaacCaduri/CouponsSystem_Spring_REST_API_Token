package com.jb.rest.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jb.dao.CompanyRepository;
import com.jb.dao.CouponRepository;
import com.jb.entity.Company;
import com.jb.entity.Coupon;
import com.jb.rest.ClientSession;
import com.jb.rest.InvalidIDException;
import com.jb.rest.InvalidLoginException;
import com.jb.rest.InvalidSessionException;
import com.jb.service.CompanyService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CompanyController {

	private Map<String, ClientSession> tokensMap;
	private CouponRepository couponRepo;

	@Autowired
	public CompanyController(@Qualifier("tokens") Map<String, ClientSession> tokensMap, CompanyRepository companyRepo, CouponRepository couponRepo) {
		this.tokensMap = tokensMap;
		this.couponRepo = couponRepo;
		
	}


	/**[v]
	 * Update Company
	 * ToDo - change this function for only the given company can change its details   
	 * @param Company
	 * @throws InvalidLoginException 
	 * @return
	 */
	@PutMapping("/companies/{token}")
	public Company updateCompany(@RequestBody Company company, @PathVariable String token)
			throws InvalidLoginException {
		
		ClientSession session = getSession(token);
		if (session!=null) {
			CompanyService service = session.getCompanyService();
			session.accessed();
			service.update(company);
			return company;
		}
		throw new InvalidLoginException("Session expired!");
	}
	
	private ClientSession getSession(String token) {
		return tokensMap.get(token);
	}

	/**[v]
	 * Delete Company
	 * @param companyId
	 * @throws InvalidSessionException 
	 */
	@DeleteMapping("/companies/{id}/{token}")
	public void deleteCompany(@PathVariable long id, @PathVariable String token)
	throws InvalidSessionException{
		
		ClientSession session = getSession(token);
		
		if (session==null) {
			throw new InvalidSessionException("Bad Session:-(");
		}
		CompanyService service = session.getCompanyService();
		session.accessed();
		service.deleteById(id);
	}

	/**[v]
	 * Add Coupon
	 * @param coupon
	 * @return new coupon
	 * @throws InvalidSessionException 
	 */
	@PostMapping("/coupons/{token}")
	public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon, @PathVariable String token)
			throws InvalidSessionException{
		
		ClientSession session = getSession(token);
		if (session!=null) {
			if (coupon==null) {
				ResponseEntity.noContent().build();
				}
		coupon.setId(0);
		couponRepo.save(coupon);
		return ResponseEntity.ok(coupon);
		}
		throw new InvalidSessionException("Session expired!");
	}

	/**[v]
	 * Delete Coupon
	 * @param CouponId
	 * @throws InvalidSessionException 
	 * @throws InvalidIDException 
	 */
	@DeleteMapping("/coupons/{id}/{token}")
	public ResponseEntity <Coupon> deleteCoupon(@PathVariable long id, @PathVariable String token)
			throws InvalidSessionException, InvalidIDException{
		
		ClientSession session = getSession(token);
	
		if (session!=null) {
			Optional<Coupon> coupon = couponRepo.findById(id);
			CompanyService service = session.getCompanyService();
			session.accessed();
			if (!coupon.isPresent()) {
				throw new InvalidIDException("Wrong Coupon ID :-(");
			}
			service.DeleteCoupon(id);
			return ResponseEntity.noContent().build();
		}
		throw new InvalidSessionException("Bad Session:-(");
	}
	
	/**[v]
	 * Update Coupon
	 * @param Coupon
	 * @return coupon
	 * @throws InvalidSessionException 
	 */
	@PutMapping("/coupons/{token}")
	public Coupon updateCoupon(@RequestBody Coupon coupon, @PathVariable String token) 
			throws InvalidSessionException{
		
		ClientSession session = getSession(token);
		
		if (session!=null) {
			CompanyService service = session.getCompanyService();
			session.accessed();
			service.updateCoupon(coupon);
			return coupon;
		}
		throw new InvalidSessionException("Session expired!");
	}
	
	

	
}