package com.example.SparePart.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SparePart.dao.SparePart;
import com.example.SparePart.dao.User;
import com.example.SparePart.exception.PartNotFound;
import com.example.SparePart.exception.UserAccessException;
import com.example.SparePart.pojo.SparePartDetails;
import com.example.SparePart.repository.SparePartRepository;
import com.example.SparePart.repository.UserRepository;
import com.example.SparePart.service.AdminService;
import com.example.SparePart.util.Constant;

@Service
public class AdminServiceimpl implements AdminService{

	@Autowired
	SparePartRepository partRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void deletePart(String partname,String userName) throws PartNotFound, UserAccessException {
		User user = userRepository.findByName(userName);
		if(user!=null) {
			if(!(user.getRole().equals(Constant.ADMIN_ROLE))) {
				throw new UserAccessException("No have api access");
			}
		}else {
			throw new UserAccessException("No user present with the provided name");
		}
		SparePart sparePart = partRepository.findItemByName(partname);
		if(sparePart==null) {
			throw new PartNotFound("Part is Not present "+partname);
		}
		partRepository.delete(sparePart);
		
	}

	@Override
	public SparePartDetails addPart(SparePartDetails details,String userName) throws UserAccessException {
		User user = userRepository.findByName(userName);
		if(user!=null) {
			if(!(user.getRole().equals(Constant.ADMIN_ROLE))) {
				throw new UserAccessException("No have api access");
			}
		}else {
			throw new UserAccessException("No user present with the provided name");
		}
		SparePart sparePart = partRepository.findItemByName(details.getName());
		if(sparePart==null) {
			SparePart part=new SparePart();
			part.setDiscription(details.getDetail());
			part.setId(ObjectId.get());
			part.setName(details.getName());
			part.setQuantity(details.getQuantity());
			SparePart save = partRepository.save(part);
		}else {
			details.setDetail("Part already Present");
		}
		
		return details;
	}

	@Override
	public SparePartDetails updatePart(SparePartDetails details,String userName) throws PartNotFound, UserAccessException  {
		User user = userRepository.findByName(userName);
		if(user!=null) {
			if(!(user.getRole().equals(Constant.ADMIN_ROLE))) {
				throw new UserAccessException("No have api access");
			}
		}else {
			throw new UserAccessException("No user present with the provided name");
		}
		SparePart sparePart = partRepository.findItemByName(details.getName());
		if(sparePart==null) {
			throw new PartNotFound("Part is Not present "+details.getName());
		}
		
		if(details.getDetail()!=null) {
			sparePart.setDiscription(details.getDetail());
		}
		if(details.getName()!=null) {
			sparePart.setName(details.getName());
		}
		
		if(details.getQuantity()!=null) {
			sparePart.setQuantity(details.getQuantity());
		}
		partRepository.save(sparePart);
		return details;
	}
 
}
