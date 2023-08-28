package com.api.restuniversity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String NOT_FOUND = "Not found ";

	public NotFoundException(Class<?> clazz, String field, String identifier) {
		super(NOT_FOUND + clazz.getSimpleName() + " with " + field + " = " + identifier);
	}

	public NotFoundException(Class<?> clazz, String field, Long identifier) {
		super(NOT_FOUND + clazz.getSimpleName() + " with " + field + " = " + identifier);
	}

	public NotFoundException(Class<?> clazz, String identifier) {
		super(NOT_FOUND + clazz.getSimpleName() + " with value = " + identifier);
	}
}
