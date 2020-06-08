package br.com.luizmariodev.wizs.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.luizmariodev.wizs.api.model.CustomerModel;
import br.com.luizmariodev.wizs.domain.model.Customer;

@Component
public class CustomerAssembler {
	
	private final ModelMapper modelMapper;
	
	public CustomerAssembler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public CustomerModel toModel(Customer customer) {
		var customerModel = modelMapper.map(customer, CustomerModel.class);
		customerModel.settAge();
		customerModel.setDoumentNumberFormatter();
		return customerModel;
	}

	public List<CustomerModel> toCollectionModel(List<Customer> customers) {
		return customers.stream()
				.map(customer -> toModel(customer))
				.collect(Collectors.toList());
	}	
}
