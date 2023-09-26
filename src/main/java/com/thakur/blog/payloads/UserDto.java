package com.thakur.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	
	private int id;
	@NotEmpty(message = "Name should not be empty !!")
	private String name;
	@Email(message = "Invalid email !!")
	private String email ;
	@Size(min = 8, message = "Password must contains 8 characters !!")
	private String password;
	private String about;
}
