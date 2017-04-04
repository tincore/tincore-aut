package com.tincore.auth.server.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(Object id) {
		super("could not find entity '" + id + "'.");
	}

	public EntityNotFoundException(Object id, Throwable e) {
		super("could not find entity '" + id + "'.", e);
	}
}