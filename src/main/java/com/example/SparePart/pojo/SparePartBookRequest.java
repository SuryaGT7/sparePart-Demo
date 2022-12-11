package com.example.SparePart.pojo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class SparePartBookRequest {

	private List<SparePartDetails> orderRequest;
	private UserDetail user;
}
