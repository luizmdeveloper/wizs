package br.com.luizmariodev.wizs.api.model;

import java.time.LocalDate;
import java.time.Period;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerModel {
	
	private Long id;
	private String name;
	private String documentNumber;
	private LocalDate birthDate;
	private Integer age;
	private String documentNumberFormatter;
	
	public void settAge() {
		this.age = Period.between(birthDate, LocalDate.now()).getYears();
	}
	
	public void setDoumentNumberFormatter() {
		this.documentNumberFormatter = this.documentNumber.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
	}
}
