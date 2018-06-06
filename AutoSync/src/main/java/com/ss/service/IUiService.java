package com.ss.service;

import java.util.List;

import com.ss.pojo.*;
import com.ss.pojo.vo.SearchOrderVo;

public interface IUiService {
	public List<SourceProductResponse> querySourceProductResponse(SourceProductResponse spr);
	
	public List<TargetProductResponse> queryTargetProductResponse(TargetProductResponse tpr);
	
	public List<ProductResponse> queryProductResponse(ProductResponse response);
	
	public CommonResponse addSourceTargetProductResponse(SourceProductResponse spr);
	
	public CommonResponse updateSourceProduct(SourceProductResponse spr);
	
	public CommonResponse updateTargetProduct(TargetProductResponse tpr);

	public CommonResponse deleteSourceProduct(long id);
	
	public CommonResponse deleteTargetProduct(long id);
	
	public List<VendorResponse> queryVendorResponse(VendorResponse vr);
	
	public CommonResponse insertVendorResponse(VendorResponse vr);
	
	public CommonResponse deleteVendorResponse(long id);
	
	public CommonResponse updateVendorResponse(VendorResponse vr);
	
	public List<SourceProductResponse> queryAdtSourceProductResponse(SourceProductResponse spr);
	
	public List<TargetProductResponse> queryAdtTargetProductResponse(TargetProductResponse tpr);

	public List<RemoteQuery> queryRemoteQuery(RemoteQuery remoteQuery);
	public CommonResponse insertRemoteQuery(RemoteQuery vr);

	public CommonResponse deleteRemoteQuery(long id);

	public CommonResponse updateRemoteQuery(RemoteQuery vr);

	public List<SyncCount> querySyncCount(SyncCount syncCount);

	public void updateProduct(String sourceId);

	public CommonResponse insertOrder(Order vr);

	CommonResponse updateOrder(Order vr);

	List<Order> queryOrders(SearchOrderVo vo);

	Integer queryOrderCount(SearchOrderVo vo);

	void deleteOrder(long id);

	List<OrderLog> queryOrderLogs(long id);

	List<OrderLog> queryAllOrderLogs();

	void addOrderLog(OrderLog log);

    void deleteOrderLogById(long id);

	public List<Provider> queryProvider(Provider vr);

	public CommonResponse insertProvider(Provider vr);

	public CommonResponse deleteProvider(long id);

	public CommonResponse updateProvider(Provider vr);
	
	public List<Provider> getProviderAndBalance();

	List<OrderItem> queryOrderItem(OrderItem item);
	void deleteOrderItem(long orderId);
	void deleteOrderItem(List<Long> items);
	void updateOrderItem(List<OrderItem> items);
	void insertOrderItem(List<OrderItem> items);
	List<OrderCostItem> queryOrderCostItem(OrderCostItem item);
	void deleteOrderCostItem(List<Long> items);
	void updateOrderCostItem(List<OrderCostItem> items);
	void insertOrderCostItem(List<OrderCostItem> items);
	List<Provider> queryDestination(Provider provider);
	
	List<SearchOrderVo> judyIsThereOrder(String itemNumber);
	
	Provider getProviderByName(String name);
	
	void updateProviderBalance(Provider provider);
	
	void addProviderBalance(Provider provider);
}
