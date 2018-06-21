package org.maxwell.services;

import java.util.List;

import org.maxwell.api.v1.model.CustomerDTO;

public interface CustomerService {

	List<CustomerDTO> getAllCustomers();

	CustomerDTO getCustomerById(Long  Id);

	CustomerDTO createNewCustomer(CustomerDTO customerDTO);
	
	 CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);
	 
	 CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
	 
	 void deleteCustomerById(Long id);
}
