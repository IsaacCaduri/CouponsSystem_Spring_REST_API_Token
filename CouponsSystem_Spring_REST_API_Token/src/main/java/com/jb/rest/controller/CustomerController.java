package com.jb.rest.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jb.dao.CouponRepository;
import com.jb.dao.CustomerRepository;
import com.jb.entity.Coupon;
import com.jb.entity.Customer;
import com.jb.rest.ClientSession;
import com.jb.rest.InvalidIDException;
import com.jb.rest.InvalidSessionException;
import com.jb.service.CustomerService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {

	private Map<String, ClientSession> tokensMap;
	private CustomerRepository customerRepo;
	private CouponRepository couponRepo;

	@Autowired
	public CustomerController(@Qualifier("tokens") Map<String, ClientSession> tokensMap, CustomerRepository customerRepo, CouponRepository couponRepo) {
		this.tokensMap = tokensMap;
		this.customerRepo = customerRepo;
		this.couponRepo = couponRepo;
	}

	/**[v]
	 * Update Customer
	 * @param customer
	 * @return
	 * @throws InvalidSessionException
	 */
	@PutMapping("/customers/{token}")
	public Customer updateCustomer(@RequestBody Customer customer, @PathVariable String token)
			throws InvalidSessionException{
	
		ClientSession session = getSession(token);
		
		if (session!=null) {
			CustomerService service =session.getCustomerService();
			session.accessed();
			service.update(customer);
			return customer;
		}
		throw new InvalidSessionException("Session expired!");
	}

	
	/**[v]
	 * Delete Customer
	 * @param customerId
	 * @throws InvalidSessionException 
	 */
	@DeleteMapping("/customers/{id}/{token}")
	public ResponseEntity <Customer> deleteCustomer(@PathVariable long id, @PathVariable String token)
			throws InvalidSessionException,InvalidIDException {
		
		ClientSession session = getSession(token);
		if (session!=null) {
		Optional<Customer> customer = customerRepo.findById(id);

		if (customer.isPresent()) {
			customerRepo.deleteById(id);
			return ResponseEntity.noContent().build();
			}
		throw new InvalidIDException("Wrong ID :-(");
		}
		throw new InvalidSessionException("Bad Session:-(");
	}

	
	/**[v]
	 * Purchase A Coupon
	 * @param couponid
	 * @param customerId
	 * @return
	 * @throws InvalidIDException 
	 */
	@PutMapping("/customers/{couponid}/{customerId}/{token}")
	public ResponseEntity<Coupon> purchaseCoupon(@PathVariable long couponid, @PathVariable long customerId, @PathVariable String token) 
			throws InvalidSessionException, InvalidIDException{
		
		ClientSession session = getSession(token);
		
		if (session!=null) {
			Optional<Customer> customer = customerRepo.findById(customerId);
			Optional<Coupon> coupon = couponRepo.findById(couponid);
		if (coupon.isPresent() && customer.isPresent()) {
			Customer cus = customer.get();
			Coupon coup = coupon.get();
			coup.setAmount(coup.getAmount() - 1);
			cus.add(coup);
			customerRepo.save(cus);
			return ResponseEntity.ok(coup);
			}
		throw new InvalidIDException("Wrong ID :-(");
		}
		throw new InvalidSessionException("Bad Session:-(");
	}
	
	
	/**[v]
	 * <h1>Find All Coupons</h1>
	 * 
	 * ->NO TOKOKEN NEEDED<-
	 * @return AllCoupons
	 */
	@GetMapping("/coupons")
	public Collection<Coupon> getAllCoupons(){
		return couponRepo.findAll();
	}
	
	
	/**[v]
	 * <h1>Find Coupon By Id</h1>
	 * 
	 * ->NO TOKOKEN NEEDED<-
	 * @param id
	 * @throws InvalidIDException 
	 * @returnCoupon
	 */
	@GetMapping("/coupons/{id}")
	public ResponseEntity<Coupon> getCoupon(@PathVariable long id) throws InvalidIDException{

		Optional<Coupon> coupon = couponRepo.findById(id);

		if (!coupon.isPresent()) {
			throw new InvalidIDException("Wrong ID :-(");
		}
		return ResponseEntity.ok(coupon.get());
	}
	
	
	private ClientSession getSession(String token) {
		return tokensMap.get(token);
	}
}
