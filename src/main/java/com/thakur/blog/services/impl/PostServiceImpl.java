package com.thakur.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.thakur.blog.entities.Category;
import com.thakur.blog.entities.Post;
import com.thakur.blog.entities.User;
import com.thakur.blog.exceptions.ResourceNotFoundException;
import com.thakur.blog.payloads.PageResponse;
import com.thakur.blog.payloads.PostDto;
import com.thakur.blog.repositories.CategoryRepo;
import com.thakur.blog.repositories.PostRepo;
import com.thakur.blog.repositories.UserRepo;
import com.thakur.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepo postRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public PageResponse getPosts(Integer pageSize, Integer pageNo, String sortBy, String order) {
		Sort sort= (order.equals("asc"))
							?Sort.by(sortBy).ascending()
									:Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> page = this.postRepo.findAll(pageable);
		
		List<Post> posts = page.getContent();
		
		List<PostDto> postsList = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PageResponse pageResponse = new PageResponse();
		pageResponse.setContent(postsList);
		pageResponse.setPageNumber(page.getNumber());
		pageResponse.setPageSize(page.getSize());
		pageResponse.setTotalElements(page.getTotalElements());
		pageResponse.setTotalPages(page.getTotalPages());
		pageResponse.setLast(page.isLast());
		return pageResponse;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
		return this.postRepo.findByUser(user).stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		return this.postRepo.findByCategory(category).stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public PostDto getPostById(Integer postId) {
		return this.modelMapper.map(this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId)), PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId, Integer categoryId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		post.setCategory(category);
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		return this.modelMapper.map(this.postRepo.save(post), PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		Post post = new Post();
		post.setImageName("dafault.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		return this.modelMapper.map(this.postRepo.save(post), PostDto.class);
	}

	
	@Override
	public List<PostDto> serachPost(String Keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(Keyword);
		return posts.stream().map(post ->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}
	
	
	
}
