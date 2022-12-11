package com.example.SparePart.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.SparePart.dao.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
	
	@Query("{name:'?0'}")
	User findByName(String name);
	
	public long count();
}
