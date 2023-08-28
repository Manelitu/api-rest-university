package com.api.restuniversity.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadRequestException() {
		super("Invalid element.");
	}

	public BadRequestException(String msg) {
		super(msg);
	}

	public BadRequestException(Class<?> clazz) {
		super("Invalid Request: " + clazz.getSimpleName());
	}

	public BadRequestException(Class<?> clazz, String field) {
		super("Attribute " + field + " of class " + clazz.getSimpleName() + " is null.");
	}

	public BadRequestException(Class<?> ownerClass, Class<?> ownedClass) {
		super(ownedClass.getSimpleName() + " of " + ownerClass.getSimpleName() + " not found.");
	}

}
