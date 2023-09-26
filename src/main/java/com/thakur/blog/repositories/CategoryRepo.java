package com.thakur.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thakur.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
