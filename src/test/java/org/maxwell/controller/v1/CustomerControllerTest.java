package org.maxwell.controller.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.maxwell.api.v1.model.CustomerDTO;
import org.maxwell.controller.RestResponseEntityExceptionHandler;
import org.maxwell.services.CustomerService;
import org.maxwell.services.ResourceNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CustomerControllerTest extends AbstractRestControllerTest {
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
		 mockMvc = MockMvcBuilders.standaloneSetup(customerController)
	                .setControllerAdvice(new RestResponseEntityExceptionHandler())
	                .build();
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

		mockMvc.perform(get("/api/v1/customers/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
	}

	@Test
	public void testGetByIdCustomers() throws Exception {
		CustomerDTO customer = new CustomerDTO();
		customer.setId(1l);
		customer.setName(NAME);
		customer.setAddress(ADDRESS);

		when(customerService.getCustomerById(anyLong())).thenReturn(customer);

		mockMvc.perform(get("/api/v1/customers/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME)));
	}

	@Test
	public void createNewCustomer() throws Exception {
		// given
		CustomerDTO customer = new CustomerDTO();
		customer.setName("Fred");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setName(customer.getName());
		returnDTO.setCustomerUrl("/api/v1/customers/1");

		when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

		// when/then
		mockMvc.perform(
				post("/api/v1/customers/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(customer)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", equalTo("Fred")))
				.andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
	}

	@Test
	public void testUpdateCustomer() throws Exception {
		// given
		CustomerDTO customer = new CustomerDTO();
		customer.setName("Fred");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setName(customer.getName());
		returnDTO.setCustomerUrl("/api/v1/customers/1");

		when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		// when/then
		mockMvc.perform(
				put("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(customer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo("Fred")))
				.andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
	}
	
	@Test
    public void testPatchCustomer() throws Exception {

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setName("Fred");
        customer.setAddress("Hollywood");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setName(customer.getName());
        returnDTO.setAddress(customer.getAddress());
        returnDTO.setCustomerUrl("/api/v1/customers/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(patch("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Fred")))
                .andExpect(jsonPath("$.address", equalTo("Hollywood")))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }
	
	@Test
	public void testDeleteCustomer() throws Exception {

		mockMvc.perform(delete("/api/v1/customers/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(customerService).deleteCustomerById(anyLong());
	}
	
	@Test
    public void testNotFoundException() throws Exception {

        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
	 
}
