package com.thakur.blog.payloads;

import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	
	@NotEmpty
	private String title;
	
	@NotEmpty
	@Size(min = 50)
	private String content;
	private String imageName;
	private Date addedDate;
	private UserDto user;
	private CategoryDto category;
}
