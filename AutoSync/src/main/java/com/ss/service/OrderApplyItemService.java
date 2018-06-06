package com.ss.service;

import java.util.List;

import com.ss.pojo.ModelResult;
import com.ss.pojo.OrderApplyItem;
import com.ss.pojo.OrderApplyMerge;
import com.ss.pojo.OrderMergePojo;
import com.ss.pojo.User;
import com.ss.pojo.vo.OrderItemVo;

public interface OrderApplyItemService {
	
	ModelResult transDeleteOrderMergeItems(OrderApplyItem applyItem);
	
	ModelResult transRollBackOrder(OrderApplyMerge orderApplyMerge);
	
	ModelResult transMergeOrderItems(OrderMergePojo mergePo, User user);
	
	void insertOrderItems(List<OrderApplyItem> itemTos);
	
	int insertOrderMerge(OrderApplyMerge merge);
	
	List<OrderApplyMerge> getAllOrderItems(OrderApplyMerge merge);
	
	List<OrderApplyItem> getItemsByMergeId(Integer id);
	
	void deleteItemsMerge(List<Integer> ids);
	
	int selectCountMergeItems(Integer id);
	
	void deleteMergeById(Integer id);
	
	void robackMerge(Integer id);
	
	OrderApplyMerge getMergeById(Integer id);
	
	void updateMergeTotal(OrderApplyItem item);
	
	void judyOrderMerge(OrderItemVo itemVo);
	
	void updateOrderItemById(List<OrderApplyItem> items);
	
	List<OrderItemVo> selectFromOrderApplyByMany(OrderItemVo vo);
	
	List<OrderApplyMerge> selectFromOrderApplyMergeByMany(OrderApplyMerge merge);
	
	void updateOrderMergeBill(OrderApplyMerge merge);

}
