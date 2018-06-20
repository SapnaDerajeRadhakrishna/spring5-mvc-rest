package org.maxwell.api.vi.mapper;

import org.junit.Test;
import org.maxwell.api.v1.mapper.CategoryMapper;
import org.maxwell.api.v1.model.CategoryDTO;
import org.maxwell.domain.Category;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {
	public static final String NAME = "Joe";
	public static final long ID = 1L;

	CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

	@Test
	public void categoryToCategoryDTO() throws Exception {

		// given
		Category category = new Category();
		category.setName(NAME);
		category.setId(ID);

		// when
		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

		// then
		assertEquals(Long.valueOf(ID), categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}
}
