package org.maxwell.controller.v1;

import org.maxwell.api.v1.model.CustomerDTO;
import org.maxwell.api.v1.model.CustomerListDTO;
import org.maxwell.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/customers/")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping
	public ResponseEntity<CustomerListDTO> getallCustomers() {

		return new ResponseEntity<CustomerListDTO>(new CustomerListDTO(customerService.getAllCustomers()),
				HttpStatus.OK);
	}

	@GetMapping("{name}")
	public ResponseEntity<CustomerDTO> getCustomerByName(@PathVariable String name) {
		return new ResponseEntity<CustomerDTO>(customerService.getCustomerByName(name), HttpStatus.OK);
	}

}
