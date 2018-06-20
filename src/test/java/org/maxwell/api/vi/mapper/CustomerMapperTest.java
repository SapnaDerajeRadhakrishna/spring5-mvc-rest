package org.maxwell.api.vi.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.maxwell.api.v1.mapper.CustomerMapper;
import org.maxwell.api.v1.model.CustomerDTO;
import org.maxwell.domain.Customer;

public class CustomerMapperTest {
	public static final String NAME = "Joe";
	public static final String ADDRESS = "Orange County";
	public static final long ID = 1L;

	CustomerMapper customerMapper = CustomerMapper.INSTANCE;

	@Test
	public void customerToCustomerDTO() throws Exception {

		// given
		Customer customer = new Customer();
		customer.setName(NAME);
		customer.setId(ID);
		customer.setAddress(ADDRESS);

		// when
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

		// then
		assertEquals(Long.valueOf(ID), customerDTO.getId());
		assertEquals(NAME, customerDTO.getName());
		assertEquals(ADDRESS, customerDTO.getAddress());
	}
}
