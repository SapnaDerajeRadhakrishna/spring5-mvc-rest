package org.maxwell.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
	public static final String ADDRESS = "Orange County";

	@Mock
	CustomerRepository customerRepository;

	CustomerMapper customerMapper = CustomerMapper.INSTANCE;

	CustomerService customerService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		customerService = new CustomerServiceImpl(customerMapper, customerRepository);
	}

	@Test
	public void getAllCustomers() throws Exception {

		// given
		List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

		when(customerRepository.findAll()).thenReturn(customers);

		// when
		List<CustomerDTO> customerDTO = customerService.getAllCustomers();

		// then
		assertEquals(3, customerDTO.size());

	}

	@Test
	public void getCustomerByID() throws Exception {

		// given
		Customer customer = new Customer();
		customer.setId(ID);
		customer.setName(NAME);

		when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer));

		// when
		CustomerDTO customerDTO = customerService.getCustomerById(ID);

		// then
		assertEquals(ID, customerDTO.getId());
		assertEquals(NAME, customerDTO.getName());

	}

	@Test
	public void createNewCustomer() throws Exception {

		// given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setName(NAME);
		customerDTO.setAddress(ADDRESS);

		Customer savedCustomer = new Customer();
		savedCustomer.setName(customerDTO.getName());
		savedCustomer.setAddress(customerDTO.getAddress());
		savedCustomer.setId(1l);

		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

		// when
		CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

		// then
		assertEquals(customerDTO.getName(), savedDto.getName());
		assertEquals("/api/v1/customer/1", savedDto.getCustomerUrl());
	}
	
	@Test
    public void saveCustomerByDTO() throws Exception {

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setName(customerDTO.getName());
        savedCustomer.setId(1l);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);

        //then
        assertEquals(customerDTO.getName(), savedDto.getName());
        assertEquals("/api/v1/customer/1", savedDto.getCustomerUrl());
    }
	
	@Test
    public void deleteCustomerById() throws Exception {

        Long id = 1L;

        customerService.deleteCustomerById(id);

        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}
