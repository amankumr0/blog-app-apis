package com.thakur.blog.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Role {
		@Id
		private Integer Id;
		private String name;
		
}
