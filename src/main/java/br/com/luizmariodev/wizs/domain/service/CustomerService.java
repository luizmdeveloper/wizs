package br.com.luizmariodev.wizs.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.luizmariodev.wizs.domain.exception.BusinessException;
import br.com.luizmariodev.wizs.domain.exception.EntityNotFoundException;
import br.com.luizmariodev.wizs.domain.model.Customer;
import br.com.luizmariodev.wizs.domain.repository.CustomerRepository;

@Service
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;		
	}
	
	@Transactional
	public Customer save(Customer customer) { 
		existsCustomerByDocumentNumber(customer.getDocumentNumber());				
		return customerRepository.save(customer);
	}
	
	@Transactional
	public Customer update(Long customerId, Customer customer) {
		var customerSave = findById(customerId);
		existsCustomerByDocumentNumber(customerId, customer.getDocumentNumber());		
		BeanUtils.copyProperties(customer,customerSave, "id");		
		return customerSave;
	}
	
	@Transactional
	public void delete(Long customerId) {
		try {
			customerRepository.deleteById(customerId);
			customerRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(customerId);
		}		
	}	
	
	public Customer findById(Long customerId) {
		Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
		
		if (!optionalCustomer.isPresent())
			throw new EntityNotFoundException(customerId);
		
		return optionalCustomer.get();
	}
	
	private void existsCustomerByDocumentNumber(Long customerId, String documentNumber) {
		Optional<Customer> optionalCustomer = customerRepository.findCutomerByDocumentNumber(customerId, documentNumber);
		
		if (optionalCustomer.isPresent())
			throw new BusinessException("Document number already registered");
	}

	private void existsCustomerByDocumentNumber(String documentNumber) {
		Optional<Customer> optionalCustomer = customerRepository.findCutomerByDocumentNumber(documentNumber);
		
		if (optionalCustomer.isPresent())
			throw new BusinessException("Document number already registered");
	}
	
}
