package org.maxwell.bootstrap;

import org.maxwell.domain.Category;
import org.maxwell.domain.Customer;
import org.maxwell.repositories.CategoryRepository;
import org.maxwell.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;

	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		Category fruits = new Category();
		fruits.setName("Fruits");

		Category dried = new Category();
		dried.setName("Dried");

		Category fresh = new Category();
		fresh.setName("Fresh");

		Category exotic = new Category();
		exotic.setName("Exotic");

		Category nuts = new Category();
		nuts.setName("Nuts");

		categoryRepository.save(fruits);
		categoryRepository.save(dried);
		categoryRepository.save(fresh);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);

		log.debug("Category data Loaded {}", categoryRepository.count());

		Customer cust1 = new Customer();
		cust1.setName("Maxwell");
		cust1.setAddress("Chicago");

		Customer cust2 = new Customer();
		cust2.setName("Sriansh");
		cust2.setAddress("Cerritos");

		customerRepository.save(cust1);
		customerRepository.save(cust2);

		log.debug("Customer data Loaded {}", customerRepository.count());

	}
}
