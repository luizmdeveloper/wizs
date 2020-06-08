package br.com.luizmariodev.wizs.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3775073881258048303L;
	
	public EntityNotFoundException(Long entityId) {
		super(String.format("No entity with id %s was found", entityId));
	}

}
