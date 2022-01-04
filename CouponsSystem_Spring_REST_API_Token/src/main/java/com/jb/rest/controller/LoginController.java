package com.jb.rest.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jb.rest.ClientSession;
import com.jb.rest.CouponSystem;
import com.jb.rest.InvalidLoginException;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class LoginController {
	private static final int LENGTH_TOKEN = 15;
	private CouponSystem couponSystem;
	private Map<String, ClientSession> tokensMap;

	@Autowired
	public LoginController(CouponSystem couponSystem, @Qualifier("tokens") Map<String, ClientSession> tokensMap) {
		this.couponSystem = couponSystem;
		this.tokensMap = tokensMap;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) throws InvalidLoginException {

		ClientSession session = couponSystem.login(email, password);
		String token = generateToken();

		tokensMap.put(token, session);

		return ResponseEntity.ok(token);
	}

	private String generateToken() {
		return UUID.randomUUID()
				.toString()
				.replaceAll("-", "")
				.substring(0, LENGTH_TOKEN);
	}

}
