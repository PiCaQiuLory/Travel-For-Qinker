package com.ss.mapper;

import java.util.List;

import com.ss.pojo.*;
import com.ss.pojo.vo.SearchOrderVo;

public interface IUiDao {
	List<SourceProductResponse> querySourceProductResponse(SourceProductResponse spr);
	
	List<TargetProductResponse> queryTargetProductResponse(TargetProductResponse tpr);
	
	List<ProductResponse> queryProductResponse(ProductResponse pr);
	
	void insertSourceProduct(SourceProductResponse spr);
	
	void insertTargetProduct(List<TargetProductResponse> list);
	
	void updateSourceProduct(SourceProductResponse spr);
	
	void updateTargetProduct(TargetProductResponse tpr);
	
	void deleteSourceProduct(long id);
	
	void deleteTargetProduct(long id);
	
	List<VendorResponse> queryVendorResponse(VendorResponse vr);
	
	void insertVendorResponse(VendorResponse vr);
	
	void deleteVendorResponse(long id);
	
	void updateVendorResponse(VendorResponse vr);
	
	List<SourceProductResponse> queryAdtSourceProductResponse(SourceProductResponse spr);
	
	List<TargetProductResponse> queryAdtTargetProductResponse(TargetProductResponse tpr);

	List<RemoteQuery> queryRemoteQueryResponse(RemoteQuery vr);

	void insertRemoteQueryResponse(RemoteQuery vr);

	void deleteRemoteQueryResponse(long id);

	void updateRemoteQueryResponse(RemoteQuery vr);

	List<SyncCount> querySyncCount(SyncCount syncCount);

    void insertDynamicProduct(List<DynamicProduct> list);

    List<DynamicProduct> queryDynamicProduct(DynamicProduct dynamicProduct);
    void deleteDynamicProducts(DynamicProduct dynamicProduct);

	void insertOrder(Order order);

	List<Order> queryOrder(SearchOrderVo searchOrderVo);

	Integer countAllOrder(SearchOrderVo searchOrderVo);

	void updateOrder(Order order);

	void deleteOrder(long id);

	void deleteOrderLog(long id);
	void deleteOrderLogById(long id);

	List<OrderLog> queryOrderLog(OrderLog log);

	void addOrderLog(OrderLog log);

	List<Provider> queryProvider(Provider vr);

	void insertProvider(Provider vr);

	void deleteProvider(long id);

	void updateProvider(Provider vr);
	
	List<Provider> getProviderAndBalance();

	List<OrderItem> queryOrderItem(OrderItem orderItem);
	List<OrderCostItem> queryOrderCostItem(OrderCostItem orderItem);

	void deleteOrderItem(long orderId);
	void deleteOrderItemByIdList(List<Long> idList);
	void insertOrderItem(List<OrderItem> items);
    void updateOrderItem(List<OrderItem> items);

	void deleteOrderCostItemByIdList(List<Long> idList);
	void insertOrderCostItem(List<OrderCostItem> items);
    void updateOrderCostItem(List<OrderCostItem> items);
	List<Provider> queryDestination(Provider provider);
	
	List<SearchOrderVo> judyIsThereOrder(String itemNumber);
	
	Provider getProviderByName(String name);
	
	void updateProviderBalance(Provider provider);
	
	void addProviderBalance(Provider provider);
}
