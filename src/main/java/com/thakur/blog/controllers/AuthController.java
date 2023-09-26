package com.thakur.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thakur.blog.exceptions.ApiException;
import com.thakur.blog.payloads.JwtAuthRequest;
import com.thakur.blog.payloads.JwtAuthResponse;
import com.thakur.blog.payloads.UserDto;
import com.thakur.blog.security.JwtTokenHelper;
import com.thakur.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private UserService userService;
	

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> authenticationController(@RequestBody JwtAuthRequest request)
			throws BadCredentialsException {
		String username = request.getUsername();
		String password = request.getPassword();
		this.authenticate(username, password);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws BadCredentialsException {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			throw new ApiException("Invalid username password");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto ){
		UserDto daDto = this.userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(daDto, HttpStatus.OK);
	}
}
