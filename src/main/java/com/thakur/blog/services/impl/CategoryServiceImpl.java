package com.thakur.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thakur.blog.entities.Category;
import com.thakur.blog.exceptions.ResourceNotFoundException;
import com.thakur.blog.payloads.CategoryDto;
import com.thakur.blog.repositories.CategoryRepo;
import com.thakur.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto getCategory(int id) {
		Category category  = this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("category", "categoryId", id));
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> list = this.categoryRepo.findAll();
		List<CategoryDto> listDao = list.stream().map(cat -> this.modelMapper.map(cat,CategoryDto.class )).collect(Collectors.toList());
		return listDao;
	}

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.categoryRepo.save(this.modelMapper.map(categoryDto, Category.class));
		return modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, int id) {
		Category category = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("category", "categoryIs", id));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDesc(categoryDto.getCategoryDesc());
		return modelMapper.map(this.categoryRepo.save(category),CategoryDto.class);
	}

	@Override
	public void deleteCategory(int id) {
		this.categoryRepo.delete(this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("category", "categoryId", id)));
	}

}
