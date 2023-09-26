package com.thakur.blog.services;

import java.util.List;

import com.thakur.blog.payloads.PageResponse;
import com.thakur.blog.payloads.PostDto;

public interface PostService {
	PageResponse getPosts(Integer pageSize, Integer pageNo, String sortBy, String order);
	 List<PostDto> getPostByUser(Integer userId);
	 List<PostDto> getPostByCategory(Integer categoryId);
	 PostDto getPostById(Integer postId);
	 PostDto updatePost(PostDto postDto, Integer postId,Integer categoryId);
	 void deletePost(Integer postId);
	 PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	 List<PostDto> serachPost(String Keyword);
}
