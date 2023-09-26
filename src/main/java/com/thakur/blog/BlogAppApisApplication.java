package com.thakur.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.thakur.blog.config.AppConstant;
import com.thakur.blog.entities.Role;
import com.thakur.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role role = new Role();
			role.setId(AppConstant.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role2 = new Role();
			role.setId(AppConstant.NORMAL_USER);
			role2.setName("ROLE_NORMAL");

			this.roleRepo.saveAll(List.of(role, role2));
		} catch (Exception e) {
		}
	}

}
