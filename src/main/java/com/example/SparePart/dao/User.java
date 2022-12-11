package com.example.SparePart.dao;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Document("User")
public class User {

	@Id
	private ObjectId id;
	private String name;
	private String role;
}
