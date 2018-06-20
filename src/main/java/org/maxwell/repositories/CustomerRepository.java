package org.maxwell.repositories;

import org.maxwell.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	Customer findByName(String name);
}
