package com.example.SparePart.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SparePart.dao.Order;
import com.example.SparePart.dao.SparePart;
import com.example.SparePart.dao.User;
import com.example.SparePart.exception.PartNotFound;
import com.example.SparePart.exception.UserAccessException;
import com.example.SparePart.pojo.SparePartBookRequest;
import com.example.SparePart.pojo.SparePartBookResponse;
import com.example.SparePart.pojo.SparePartDetails;
import com.example.SparePart.repository.OrderRepository;
import com.example.SparePart.repository.SparePartRepository;
import com.example.SparePart.repository.UserRepository;
import com.example.SparePart.service.SparePartService;
import com.example.SparePart.util.Constant;
@Service
public class SparePartServiceImpl implements SparePartService {

	@Autowired
	SparePartRepository partRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public SparePartDetails partDetails(String partName,String username) throws PartNotFound, UserAccessException {
		User user = userRepository.findByName(username);
		if(user!=null) {
			if(!(user.getRole().equals(Constant.ADMIN_ROLE)|| user.getRole().equals(Constant.USER_ROLE))) {
				throw new UserAccessException("No have api access");
			}
		}else {
			throw new UserAccessException("No user present with the provided name");
		}
		
		SparePartDetails details=new SparePartDetails();
		
		SparePart sparePart = partRepository.findItemByName(partName);
		if(sparePart == null) {
			throw new PartNotFound("Part is Not present "+partName);
		}
		details.setName(sparePart.getName());
		details.setDetail(sparePart.getDiscription());
		details.setQuantity(sparePart.getQuantity());
		
		
		return details;
	}

	@Override
	public SparePartBookResponse bookPart(SparePartBookRequest bookRequest) throws UserAccessException {
		User user = userRepository.findByName(bookRequest.getUser().getName());
		if(user!=null) {
			if(!( user.getRole().equals(Constant.USER_ROLE))) {
				throw new UserAccessException("No have api access");
			}
		}else {
			throw new UserAccessException("No user present with the provided name");
		}
		SparePartBookResponse bookResponse=new SparePartBookResponse();
		List<String> partBooked=new ArrayList<>();
		List<String> partNotBooked=new ArrayList<>();
		bookRequest.getOrderRequest().stream().forEach((sparePart)->{
			SparePart currentItems = partRepository.findItemByName(sparePart.getName());
			if(currentItems.getQuantity()>=sparePart.getQuantity()) {
				currentItems.setQuantity(currentItems.getQuantity()-sparePart.getQuantity());
				updateOrder(Constant.COMPLETED_STATUS,bookRequest,sparePart);
				partRepository.save(currentItems);
				partBooked.add(sparePart.getName());
				
			}else {
				updateOrder(Constant.NOT_COMPLETED_STATUS,bookRequest,sparePart);	
				partNotBooked.add(sparePart.getName());
			}
		});
		
		bookResponse.setPartsBooked(partBooked);
		bookResponse.setPartsNotBooked(partNotBooked);
		if(partBooked.size()==0) {
			bookResponse.setStatus(Constant.NOT_COMPLETED_STATUS);
		}else if(partNotBooked.size()>0){
			bookResponse.setStatus(Constant.COMPLETED_PARTIALLY_STATUS);
		}else{
			bookResponse.setStatus(Constant.COMPLETED_STATUS);
			
		}
		
		return bookResponse;
	}
	
	
	private void updateOrder(String status ,SparePartBookRequest bookRequest,SparePartDetails sparePart) {
		Order newOrder=new Order();
		newOrder.setDate(new Date());
		newOrder.setId(ObjectId.get());
		newOrder.setOrderStatus(status);
		newOrder.setSparePartDetails(sparePart);
		newOrder.setUserDetails(bookRequest.getUser());
		orderRepository.save(newOrder);
	}

}
