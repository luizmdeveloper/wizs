package br.com.luizmariodev.wizs.api.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerInputModel {
	
	private Long id;
	
	@NotBlank
	private String name; 

	@NotBlank
	@Size(min = 11, max = 11)
	private String documentNumber;
	
	@NotNull
	private LocalDate birthDate;	
}
