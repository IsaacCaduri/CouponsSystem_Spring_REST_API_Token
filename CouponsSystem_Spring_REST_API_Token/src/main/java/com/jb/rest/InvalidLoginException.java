package com.jb.rest;

@SuppressWarnings("serial")
public class InvalidLoginException extends Exception {
	public InvalidLoginException(String message) {
		super(message);
	}
}
