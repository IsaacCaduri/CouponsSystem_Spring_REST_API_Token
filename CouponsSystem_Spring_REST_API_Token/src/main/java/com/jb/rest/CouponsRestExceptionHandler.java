package com.jb.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jb.rest.controller.CompanyController;
import com.jb.rest.controller.CustomerController;
import com.jb.rest.controller.LoginController;
import com.jb.rest.error_response.ErrorResponse;

@ControllerAdvice(assignableTypes = { LoginController.class, CompanyController.class,CustomerController.class })
public class CouponsRestExceptionHandler {

	@ExceptionHandler(InvalidLoginException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ErrorResponse handleUnauthorized(InvalidLoginException ex) {
		return ErrorResponse.now(HttpStatus.UNAUTHORIZED, String.format("UNAUTHORIZED: %s", ex.getMessage()));
	}
	
	@ExceptionHandler(InvalidSessionException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleUnauthorized(InvalidSessionException ex) {
		return ErrorResponse.now(HttpStatus.INTERNAL_SERVER_ERROR, String.format("INTERNAL SERVER ERROR: %s", ex.getMessage()));
	}
	
	@ExceptionHandler(InvalidIDException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorResponse handleUnauthorized(InvalidIDException ex) {
		return ErrorResponse.now(HttpStatus.INTERNAL_SERVER_ERROR, String.format("INTERNAL SERVER ERROR: %s", ex.getMessage()));
	}
	

		
}
