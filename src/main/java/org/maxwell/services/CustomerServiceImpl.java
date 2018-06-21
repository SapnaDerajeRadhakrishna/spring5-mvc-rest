package org.maxwell.services;

import java.util.List;
import java.util.stream.Collectors;

import org.maxwell.api.v1.mapper.CustomerMapper;
import org.maxwell.api.v1.model.CustomerDTO;
import org.maxwell.domain.Customer;
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
				.stream()
				.map(customer -> {
						CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
						customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
						return customerDTO;
				})
				.collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .map(customerDTO -> {
                    //set API URL
                    customerDTO.setCustomerUrl("/api/v1/customer/" + id);
                    return customerDTO;
                })
                .orElseThrow(RuntimeException::new);
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
	}

	private CustomerDTO saveAndReturnDTO(Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);
		CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);
		returnDto.setCustomerUrl("/api/v1/customer/" + savedCustomer.getId());
		return returnDto;
	}

	@Override
	public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
		customer.setId(id);
		return saveAndReturnDTO(customer);
	}

	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		return customerRepository.findById(id).map(customer -> {

			if (customerDTO.getName() != null) {
				customer.setName(customerDTO.getName());
			}

			if (customerDTO.getAddress() != null) {
				customer.setAddress(customerDTO.getAddress());
			}

			CustomerDTO returnDto = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
			returnDto.setCustomerUrl("/api/v1/customer/" + customer.getId());
			return returnDto;
			
		}).orElseThrow(RuntimeException::new); // todo implement better exception handling;
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}
}
