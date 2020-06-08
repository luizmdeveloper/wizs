package br.com.luizmariodev.wizs.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.luizmariodev.wizs.api.model.CustomerInputModel;
import br.com.luizmariodev.wizs.domain.model.Customer;

@Component
public class CustomerDissambler {
	
	private final ModelMapper modelMapper;

	public CustomerDissambler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public Customer toDomainModel(CustomerInputModel customerInputModel) {
		return modelMapper.map(customerInputModel, Customer.class);
	}
}
