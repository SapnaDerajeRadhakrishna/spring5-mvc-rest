package org.maxwell.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.maxwell.api.v1.model.CategoryDTO;
import org.maxwell.domain.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	CategoryDTO categoryToCategoryDTO(Category category);
}
