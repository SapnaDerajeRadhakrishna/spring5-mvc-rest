package org.maxwell.services;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.maxwell.api.v1.mapper.CustomerMapper;
import org.maxwell.api.v1.model.CustomerDTO;
import org.maxwell.bootstrap.Bootstrap;
import org.maxwell.domain.Customer;
import org.maxwell.repositories.CategoryRepository;
import org.maxwell.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run(); //load data

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void patchCustomerUpdateName() throws Exception {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        String originalName = originalCustomer.getName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getName());
        assertThat(originalName, not(equalTo(updatedCustomer.getName())));
    }

    @Test
    public void patchCustomerUpdateAddress() throws Exception {
        String updatedAddress = "UpdatedAddress";
        long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        //save original address
        String originalAddress = originalCustomer.getAddress();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setAddress(updatedAddress);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedAddress, updatedCustomer.getAddress());
        assertThat(originalAddress, not(equalTo(updatedCustomer.getAddress())));
    }

    private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();

        System.out.println("Customers Found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }
}
