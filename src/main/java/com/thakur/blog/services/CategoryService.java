package com.thakur.blog.services;

import java.util.List;


import com.thakur.blog.payloads.CategoryDto;

public interface CategoryService {
	CategoryDto getCategory(int id);
	List<CategoryDto> getCategories();
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,int id);
	void deleteCategory(int id);
}
