package org.maxwell.services;

import java.util.List;
import java.util.stream.Collectors;

import org.maxwell.api.v1.mapper.CustomerMapper;
import org.maxwell.api.v1.model.CustomerDTO;
import org.maxwell.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerMapper customerMapper;
	private CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
		this.customerMapper = customerMapper;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository
				.findAll()
				.stream().map(customerMapper::customerToCustomerDTO)
				.collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerByName(String name) {
		return customerMapper.customerToCustomerDTO(customerRepository.findByName(name));
	}

}
