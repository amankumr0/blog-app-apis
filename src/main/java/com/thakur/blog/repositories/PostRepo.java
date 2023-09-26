package com.thakur.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thakur.blog.entities.Category;
import com.thakur.blog.entities.Post;
import com.thakur.blog.entities.User;


public interface PostRepo extends JpaRepository<Post, Integer> {

 	List<Post> findByUser(User user);
 	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String keyword);
}
