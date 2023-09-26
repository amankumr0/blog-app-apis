package com.thakur.blog.services;

import java.util.List;
import com.thakur.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerUser(UserDto userDto);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUser();
	
	void deleteUser(Integer userId);
}
