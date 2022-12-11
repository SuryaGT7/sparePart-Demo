package com.example.SparePart.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SparePartBookResponse {

	private List<String> partsBooked;
	private List<String> partsNotBooked;
	private String status;
}
