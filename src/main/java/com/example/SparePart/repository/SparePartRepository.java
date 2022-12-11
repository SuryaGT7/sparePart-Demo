package com.example.SparePart.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.SparePart.dao.SparePart;

public interface SparePartRepository extends MongoRepository<SparePart, ObjectId> {
	
	@Query("{Name:'?0'}")
	SparePart findItemByName(String name);
	
	public long count();

}
