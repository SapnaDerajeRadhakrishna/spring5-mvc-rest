package org.maxwell.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
	
	private Long id;
	
	@ApiModelProperty(required = true )
	private String name;
	
	@ApiModelProperty(required = true )
	private String address;

	@JsonProperty("customer_url")
    private String customerUrl;
}
