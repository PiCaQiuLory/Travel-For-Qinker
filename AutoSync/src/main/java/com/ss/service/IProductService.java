package com.ss.service;

import java.util.List;

import com.ss.pojo.Product;

public interface IProductService {
	public void saveOrUpdateProduct(List<Product> list);
	
	public void syncProduct();
	
	public void syncProduct(String sourceId);
}
