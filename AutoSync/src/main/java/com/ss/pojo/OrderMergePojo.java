package com.ss.pojo;

import java.io.Serializable;

public class OrderMergePojo implements Serializable{

	private String name;
	
	private Integer[] arr;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer[] getArr() {
		return arr;
	}

	public void setArr(Integer[] arr) {
		this.arr = arr;
	}
	
	
}
