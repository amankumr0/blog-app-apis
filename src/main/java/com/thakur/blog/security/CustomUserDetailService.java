package com.thakur.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.thakur.blog.entities.User;
import com.thakur.blog.exceptions.ResourceNotFoundException;
import com.thakur.blog.repositories.UserRepo;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user =  this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "username", username));
		return user;
	}

}
