package org.maxwell.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.maxwell.api.v1.mapper.CustomerMapper;
import org.maxwell.api.v1.model.CustomerDTO;
import org.maxwell.domain.Customer;
import org.maxwell.repositories.CustomerRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CustomerServiceTest {
	public static final Long ID = 2L;
	public static final String NAME = "Jimmy";
	CustomerService customerService;

	@Mock
	CustomerRepository customerRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
	}

	@Test
	public void getAllCategories() throws Exception {

		// given
		List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

		when(customerRepository.findAll()).thenReturn(customers);

		// when
		List<CustomerDTO> customerDTO = customerService.getAllCustomers();

		// then
		assertEquals(3, customerDTO.size());

	}

	@Test
	public void getCategoryByName() throws Exception {

		// given
		Customer customer = new Customer();
		customer.setId(ID);
		customer.setName(NAME);

		when(customerRepository.findByName(anyString())).thenReturn(customer);

		// when
		CustomerDTO customerDTO = customerService.getCustomerByName(NAME);

		// then
		assertEquals(ID, customerDTO.getId());
		assertEquals(NAME, customerDTO.getName());

	}
}
