package com.api.restuniversity.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConflictException(String msg) {
		super(msg);
	}

	public ConflictException(Class<?> clazz, String field, String id) {
		super("Conflict in " + clazz.getSimpleName() + " with " + field + " = " + id);
	}

}
