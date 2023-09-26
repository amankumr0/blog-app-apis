package com.thakur.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.thakur.blog.config.AppConstant;
import com.thakur.blog.payloads.ApiResponse;
import com.thakur.blog.payloads.PageResponse;
import com.thakur.blog.payloads.PostDto;
import com.thakur.blog.services.impl.FileServiceImpl;
import com.thakur.blog.services.impl.PostServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostServiceImpl postService;
	
	@Autowired
	private FileServiceImpl fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/posts/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@Valid
																				@RequestBody PostDto postDto, 
																				@PathVariable Integer userId, 
																				@PathVariable Integer categoryId){
		PostDto dto= this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(dto,HttpStatus.CREATED);
	}
	
	@PutMapping("/posts/{postId}/category/{categoryId}")
	public ResponseEntity<PostDto> updatePost(@Valid
																				@RequestBody PostDto postDto, 
																				@PathVariable Integer postId,
																				@PathVariable Integer categoryId){
		PostDto dto= this.postService.updatePost(postDto,postId, categoryId);
		return new ResponseEntity<PostDto>(dto,HttpStatus.CREATED);
	}
	
	
	
	@GetMapping("/posts/user/{userId}")
	public ResponseEntity<List<PostDto>> getByUser(@PathVariable Integer userId){
		return new ResponseEntity<List<PostDto>>(this.postService.getPostByUser(userId), HttpStatus.OK);
	}
	
	@GetMapping("/posts/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getByCategory(@PathVariable Integer categoryId){
		return new ResponseEntity<List<PostDto>>(this.postService.getPostByCategory(categoryId), HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PageResponse> getPosts(@RequestParam(value =  "pageNo" , defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNo,
																					   @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
																					   @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
																					   @RequestParam(value = "order", defaultValue = AppConstant.SORT_DIR, required = false) String order
																						){
		return new ResponseEntity<PageResponse>(this.postService.getPosts(pageSize,pageNo,sortBy,order), HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getById(@PathVariable Integer postId){
		return new ResponseEntity<PostDto>(this.postService.getPostById(postId), HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> getById(@PathVariable String keyword){
		return new ResponseEntity<List<PostDto>>(this.postService.serachPost(keyword), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Post is successufully deleted !!",true));
	}
	
	@PostMapping("/posts/upload/image/{userId}")
	public ResponseEntity<PostDto> uploadImage(@PathVariable Integer userId, @RequestParam MultipartFile file, @RequestParam("catId") Integer catId) throws IOException{
		PostDto post = this.postService.getPostById(userId);
		String imageName =  this.fileService.uploadImage(path, file);
		post.setImageName(imageName);
		return new ResponseEntity<PostDto>(this.postService.updatePost(post, userId, catId),HttpStatus.OK); 
	}
	
	@GetMapping(value =  "/posts/image/{imageName}"
			, produces = MediaType.IMAGE_JPEG_VALUE
			)
	public ResponseEntity<Void> servImage(@PathVariable String imageName
			, HttpServletResponse response
			) throws IOException{
		InputStream iStream  = this.fileService.getResource(path,imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(iStream, response.getOutputStream());
//		String URI = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(imageName).toUriString();
		return new ResponseEntity<Void> (HttpStatus.OK);
	}
	
}
