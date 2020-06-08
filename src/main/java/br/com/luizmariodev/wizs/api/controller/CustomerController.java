package br.com.luizmariodev.wizs.api.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizmariodev.wizs.api.assembler.CustomerAssembler;
import br.com.luizmariodev.wizs.api.assembler.CustomerDissambler;
import br.com.luizmariodev.wizs.api.model.CustomerInputModel;
import br.com.luizmariodev.wizs.api.model.CustomerModel;
import br.com.luizmariodev.wizs.api.model.filter.CustomerFilter;
import br.com.luizmariodev.wizs.domain.infrastructure.specifactions.CustomerSpecification;
import br.com.luizmariodev.wizs.domain.model.Customer;
import br.com.luizmariodev.wizs.domain.repository.CustomerRepository;
import br.com.luizmariodev.wizs.domain.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	private final CustomerRepository customerRepository;
	private final CustomerService customerService;
	private final CustomerAssembler customerAssembler;
	private final CustomerDissambler customerDissambler;
		
	public CustomerController(CustomerRepository customerRepository, CustomerService customerService, CustomerAssembler customerAssembler, CustomerDissambler customerDissambler) {
		this.customerRepository = customerRepository;
		this.customerService = customerService;
		this.customerAssembler = customerAssembler;
		this.customerDissambler = customerDissambler;
	}	

	@GetMapping
	public Page<CustomerModel> findAll(CustomerFilter filter, @PageableDefault(size = 10) Pageable pageable) {
		var customers = customerRepository.findAll(CustomerSpecification.find(filter), pageable);
		var customersModel = customerAssembler.toCollectionModel(customers.getContent());
		var customerPageable = new PageImpl<>(customersModel, pageable, customers.getTotalElements());
		return customerPageable;
	}
	
	@GetMapping("/{customerId}")
	public Customer findById(@PathVariable Long customerId) {
		return customerService.findById(customerId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerModel save(@RequestBody @Valid CustomerInputModel customerInputModel) {
		var customer = customerService.save(customerDissambler.toDomainModel(customerInputModel));
		return customerAssembler.toModel(customer);
	}
	
	@PutMapping("{customerId}")
	public CustomerModel update(@PathVariable Long customerId, @RequestBody @Valid CustomerInputModel customerInputModel) {
		var customer = customerService.update(customerId, customerDissambler.toDomainModel(customerInputModel));
		return customerAssembler.toModel(customer);
	}
	
	@PatchMapping("{customerId}")
	public CustomerModel updatePatch(@PathVariable Long customerId, @RequestBody @Valid CustomerInputModel customerInputModel) {
		var customer = customerService.update(customerId, customerDissambler.toDomainModel(customerInputModel));
		return customerAssembler.toModel(customer);
	}	
	
	@DeleteMapping("/{customerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long customerId) {
		customerService.delete(customerId);
	}
}
