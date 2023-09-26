package com.thakur.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thakur.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
