package com.example.SparePart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SparePart.exception.PartNotFound;
import com.example.SparePart.exception.UserAccessException;
import com.example.SparePart.pojo.SparePartBookRequest;
import com.example.SparePart.pojo.SparePartBookResponse;
import com.example.SparePart.pojo.SparePartDetails;
import com.example.SparePart.service.SparePartService;

@Controller
public class SparePartController {
	
	@Autowired
	SparePartService sparePartServiceImpl;
	
	@GetMapping("partDetails")
	public ResponseEntity getPartDetails(@RequestParam(required = true) String partName,
			@RequestParam(required = true) String userName) {
		SparePartDetails details;
		try {
			details = sparePartServiceImpl.partDetails(partName,userName);
		} catch (PartNotFound exp) {
			
			return ResponseEntity.status(404).body(exp.getMessage());
		} catch (UserAccessException exp) {
			
			return ResponseEntity.status(401).body(exp.getMessage());
		}
		return ResponseEntity.ok(details);
		
	}
	
	@PostMapping("bookSpareParts")
	public ResponseEntity bookSpareParts(@RequestBody SparePartBookRequest bookRequest){
		
		SparePartBookResponse response;
		try {
			response = sparePartServiceImpl.bookPart(bookRequest);
		} catch (UserAccessException e) {
			
			return ResponseEntity.status(401).body(e.getMessage());
		}
		return ResponseEntity.status(201).body(response);
	}
	
	
	
	

}
