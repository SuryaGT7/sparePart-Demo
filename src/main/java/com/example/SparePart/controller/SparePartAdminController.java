package com.example.SparePart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SparePart.exception.PartNotFound;
import com.example.SparePart.exception.UserAccessException;
import com.example.SparePart.pojo.SparePartDetails;
import com.example.SparePart.service.AdminService;

@Controller
@RequestMapping("admin")
public class SparePartAdminController {

	@Autowired
	AdminService adminService;
	
	@PostMapping("add/part")
	public ResponseEntity addPart(@RequestBody(required = true) SparePartDetails details,
			@RequestParam(required = true) String userName) {
		SparePartDetails part;
		try {
			part = adminService.addPart(details,userName);
		} catch (UserAccessException exp) {
			return ResponseEntity.status(401).body(exp.getMessage());
		}
		if(part.getDetail().equals("Part already Present")) {
			return ResponseEntity.status(400).body("Part already Present Please update");
		}
		return ResponseEntity.status(202).body(part);
	}
	
	@DeleteMapping("remove/part")
	public ResponseEntity deletePart(@RequestParam(required = true) String partname,
			@RequestParam(required = true) String userName) throws PartNotFound {
		try {
			adminService.deletePart(partname,userName);
		} catch (PartNotFound exp) {
			
			return ResponseEntity.status(404).body(exp.getMessage());
		} catch (UserAccessException exp) {
			
			return ResponseEntity.status(401).body(exp.getMessage());
		}
		return ResponseEntity.status(202).body("Part Deleted");
	}
	
	@PostMapping("updatePartDetail")
	public ResponseEntity updatePart(@RequestBody(required = true) SparePartDetails details,
			@RequestParam(required = true) String userName) throws PartNotFound{
		SparePartDetails part;
		try {
			part = adminService.updatePart(details,userName);
		} catch (PartNotFound exp) {
			
			return ResponseEntity.status(404).body(exp.getMessage());
		} catch (UserAccessException exp) {
			
			return ResponseEntity.status(401).body(exp.getMessage());
		}
		return ResponseEntity.status(202).body(part);
	}
}
