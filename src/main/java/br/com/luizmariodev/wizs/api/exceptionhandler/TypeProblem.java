package br.com.luizmariodev.wizs.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum TypeProblem {
	
	ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
	ERROR_BUSINESS("/error-business", "Business rule violation"), 
	PARAMETER_INVALID("/parameter-invalid", "Parameter invalid"),
	ERROR_SYSTEM("/error-de-system", "Error in system"),
	DATA_INVALID("/error-data-invalid", "Data invalid");
	
	private String title;
	private String path;
	
	private TypeProblem(String path, String title) {
		this.path = "https://api.wizs-customer.luizmario.com.br" + path;
		this.title = title;
	}
}
