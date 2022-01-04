package com.jb.rest;

@SuppressWarnings("serial")
public class InvalidIDException extends Exception {
	public InvalidIDException(String message) {
		super(message);
	}
}
