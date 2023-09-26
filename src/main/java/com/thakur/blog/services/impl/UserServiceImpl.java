package com.thakur.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thakur.blog.config.AppConstant;
import com.thakur.blog.entities.Role;
import com.thakur.blog.entities.User;
import com.thakur.blog.payloads.UserDto;
import com.thakur.blog.repositories.RoleRepo;
import com.thakur.blog.repositories.UserRepo;
import com.thakur.blog.services.UserService;
import com.thakur.blog.exceptions.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		User savedUser = this.userRepo.save(user);
		return this.modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " User Id", userId));

		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());

		User updatedUser = this.userRepo.save(user);

		return this.modelMapper.map(updatedUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUser() {

		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(users, UserDto.class))
				.collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		Role role = this.roleRepo.findById(AppConstant.NORMAL_USER).get();
		user.getRoles().add(role);
		User regUser = this.userRepo.save(user);
		UserDto daDto = this.modelMapper.map(regUser, UserDto.class);
		return daDto;
	}

}
