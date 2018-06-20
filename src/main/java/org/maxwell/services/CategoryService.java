package org.maxwell.services;

import java.util.List;

import org.maxwell.api.v1.model.CategoryDTO;

public interface CategoryService {
	
	List<CategoryDTO> getAllCategories();
	CategoryDTO getCategoryByName(String name);

}
