
package com.thakur.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thakur.blog.entities.User;

public interface UserRepo extends JpaRepository <User,Integer>{
	Optional< User> findByEmail(String email);
}
