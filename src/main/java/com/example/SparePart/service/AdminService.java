package com.example.SparePart.service;

import org.springframework.stereotype.Service;

import com.example.SparePart.exception.PartNotFound;
import com.example.SparePart.exception.UserAccessException;
import com.example.SparePart.pojo.SparePartDetails;
@Service
public interface AdminService {

	public void deletePart(String partname,String userName) throws PartNotFound,UserAccessException ;

	public SparePartDetails addPart(SparePartDetails details,String userName)throws UserAccessException ;

	public SparePartDetails updatePart(SparePartDetails details,String userName) throws PartNotFound,UserAccessException ;

}
