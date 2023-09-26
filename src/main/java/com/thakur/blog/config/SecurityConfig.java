package com.thakur.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.thakur.blog.security.JwtAuthenticationEntryPoint;
import com.thakur.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	public static final String[] PUBLIC_URLS = { "/api/v1/auth/**", 
																			  "/v3/api-docs", 
																			  "/v2/api-docs",
																			  "/swagger-resource/**", 
																			  "/swagger-ui/**", 
																			  "/webjars/**" };

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						authz -> authz.requestMatchers(PUBLIC_URLS).permitAll()
						.requestMatchers(HttpMethod.GET).permitAll()
						.anyRequest().authenticated())
				.exceptionHandling(e -> e.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

//	@Bean
//	public InMemoryUserDetailsManager createUserDetailsManager() {
//		UserDetails userDetails1 = createNewUser("admin1", "secret1");
//
//		return new InMemoryUserDetailsManager(userDetails1);
//	}
//
//	private UserDetails createNewUser(String username, String password) {
//		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
//
//		return User.builder().passwordEncoder(passwordEncoder).username(username).password(password)
//				.roles("USER", "ADMIN").build();
//	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
