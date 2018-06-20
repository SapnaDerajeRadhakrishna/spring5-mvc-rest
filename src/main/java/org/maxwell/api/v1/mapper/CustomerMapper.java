package org.maxwell.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.maxwell.api.v1.model.CustomerDTO;
import org.maxwell.domain.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	CustomerDTO customerToCustomerDTO(Customer customer);

}
