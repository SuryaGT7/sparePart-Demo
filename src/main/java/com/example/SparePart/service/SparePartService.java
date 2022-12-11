package com.example.SparePart.service;

import org.springframework.stereotype.Service;

import com.example.SparePart.exception.PartNotFound;
import com.example.SparePart.exception.UserAccessException;
import com.example.SparePart.pojo.SparePartBookRequest;
import com.example.SparePart.pojo.SparePartBookResponse;
import com.example.SparePart.pojo.SparePartDetails;
@Service
public interface SparePartService {

	public SparePartDetails partDetails(String partName,String userName) throws PartNotFound,UserAccessException;

	public SparePartBookResponse bookPart(SparePartBookRequest bookRequest) throws UserAccessException;

	
	
}
