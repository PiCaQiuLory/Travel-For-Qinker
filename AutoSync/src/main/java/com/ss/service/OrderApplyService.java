package com.ss.service;

import java.util.List;

import com.ss.pojo.ModelResult;
import com.ss.pojo.Order;
import com.ss.pojo.User;
import com.ss.pojo.vo.OrderItemVo;

public interface OrderApplyService {

	//付款申请
	ModelResult transApplyOrder(List<OrderItemVo> orderItemVos, User user);
	
	//财务所有未审核订单查询
	ModelResult getNonAuditingOrderForFinance(User user, Integer page);
	
	//财务所有已审核订单
	ModelResult getAuditingOrderForFinance(User auditorUser, Integer page);
	
	//定制师所有未审核订单
	ModelResult getNonAuditingOrderForUser(User auditorUser, Integer page);
	
	public ModelResult deleteOrderItem(Integer doS, Integer id);
	
	//定制师所有已审核订单
	ModelResult getAuditingOrderForUser(User auditorUser, Integer page);
	
	//查询未审核订单条目
	int getNonAuditingOrderNumberForFinance();
	
	//更新订单审核状态
	ModelResult transUpdateOrderStatusByOrderId(OrderItemVo itemVo, User userSession, Integer url);
	
	//获取定制师对应的订单列表
	ModelResult getAllOrders(User userSession, Integer page);
	
	List<Order> getAllOrdersByManager();
	
	List<OrderItemVo> getOrderItems(String itemNumber);
	
	void changeOderItemStatusTojudy(OrderItemVo item);
	
	//void changeOderItemsStatusTojudy(List<OrderItemVo> orders);
	
	List<Order> getAllOrdersWithNoPage(Integer userId);
	
	void updateOrderItemStatus(Integer id);
	
	void updateOrderBill(OrderItemVo itemVo);

	void updateOrderItemByIdToRefuse(int id);
	
	void updateOrderItemByIdToCancle(int id);
	
	OrderItemVo getItemById(int id);
	
	ModelResult selectByParameters(Integer pageNumber, Order order, User user);
	
	void updateOrderTByIdToCancle(int orderItemId);
	
	void updateOrderTByIdToRefuse(int orderItemId);
	
	List<OrderItemVo> selectOrderApplys(Integer[] ids);
	
	void deleteOrderApplys(Integer[] ids);
	
	void insertIntoOrderApplyFromMerge(List<OrderItemVo> items);
	
	Integer[] selectMergeOrderId(int id);
}
