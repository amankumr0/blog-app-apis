package com.thakur.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import com.thakur.blog.payloads.CategoryDto;
import com.thakur.blog.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
		return new ResponseEntity<>(this.categoryService.createCategory(categoryDto),HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable int categoryId){
		return ResponseEntity.ok(this.categoryService.updateCategory(categoryDto,categoryId));
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getById(@PathVariable int categoryId){
		return ResponseEntity.ok(this.categoryService.getCategory(categoryId));
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		return ResponseEntity.ok(this.categoryService.getCategories());
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId){
		this.categoryService.deleteCategory(categoryId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
	
	
