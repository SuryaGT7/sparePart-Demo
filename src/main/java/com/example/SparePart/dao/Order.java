package com.example.SparePart.dao;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.SparePart.pojo.SparePartDetails;
import com.example.SparePart.pojo.UserDetail;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Document("order")
public class Order {

	@Id
	private ObjectId id;
	private UserDetail userDetails;
	private Date date;
	private SparePartDetails sparePartDetails;
	private String orderStatus;
	
}
