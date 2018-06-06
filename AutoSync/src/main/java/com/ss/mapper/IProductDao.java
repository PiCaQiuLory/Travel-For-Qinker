package com.ss.mapper;

import java.util.List;
import java.util.Map;

import com.ss.pojo.OutputTemlate;
import com.ss.pojo.Product;
import com.ss.pojo.SourceProduct;
import com.ss.pojo.TargetProduct;

public interface IProductDao {
	List<Product> getUpdatePro(String date);
	
	int updateProduct(Product product);
	
	List<String> getSourceId(String sourceId);
	
	List<Integer> getOffset(String sourceId);
	
	int updateProductPackage(Product product);
	
	int insertProduct(List<Product> productList);
	
	List<OutputTemlate> getOutputList();
	
	List<OutputTemlate> getOutputListForUpdate(String sourceId);
	
	int updateProductStatus(Product product);
	
	int updateTargetProductStatus(TargetProduct tp);
	
	int updateSourceProductStatus(SourceProduct sp);
	
	int updateLastSyncDatetime(TargetProduct tp);
	
	SourceProduct getPercentage(String sourceId);
	
	int updateCount(Map<String, Object> map);
	
	int insertCount(Map<String, Object> map);
	
	
}
