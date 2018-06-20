package org.maxwell.services;

import java.util.List;

import org.maxwell.api.v1.model.CustomerDTO;

public interface CustomerService {

	List<CustomerDTO> getAllCustomers();

	CustomerDTO getCustomerByName(String name);
}
