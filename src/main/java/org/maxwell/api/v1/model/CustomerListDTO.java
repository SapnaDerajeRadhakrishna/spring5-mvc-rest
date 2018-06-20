package org.maxwell.api.v1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerListDTO {
	List<CustomerDTO> customers;
}
