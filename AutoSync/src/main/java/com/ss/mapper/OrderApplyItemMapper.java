package com.ss.mapper;

import java.util.List;

import com.ss.pojo.OrderApplyItem;
import com.ss.pojo.OrderApplyMerge;
import com.ss.pojo.vo.OrderItemVo;

public interface OrderApplyItemMapper {

	void insertOrderItems(List<OrderApplyItem> applyItem);
	
	void insertOrderMerge(OrderApplyMerge merge);
	
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
