package com.example.SparePart.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.SparePart.dao.Order;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {
 
}
