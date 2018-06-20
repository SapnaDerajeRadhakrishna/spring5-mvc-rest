package org.maxwell.controller.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.maxwell.api.v1.model.CustomerDTO;
import org.maxwell.services.CustomerService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CustomerControllerTest {
	public static final String NAME = "Jim";
	public static final String ADDRESS = "Orange County";

	@Mock
	CustomerService customerService;

	@InjectMocks
	CustomerController customerController;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	public void testListCustomers() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setId(1l);
		customer1.setName(NAME);
		customer1.setAddress(ADDRESS);

		CustomerDTO customer2 = new CustomerDTO();
		customer2.setId(2l);
		customer2.setName("Bob");
		customer2.setAddress("Cerritos");

		List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

		when(customerService.getAllCustomers()).thenReturn(customers);

		mockMvc.perform(get("/api/v1/customers/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
	}

	@Test
	public void testGetByNameCategories() throws Exception {
		CustomerDTO customer = new CustomerDTO();
		customer.setId(1l);
		customer.setName(NAME);
		customer.setAddress(ADDRESS);

		when(customerService.getCustomerByName(anyString())).thenReturn(customer);

		mockMvc.perform(get("/api/v1/customers/Jim").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME)));
	}
}
