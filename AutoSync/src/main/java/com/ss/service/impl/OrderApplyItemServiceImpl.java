package com.ss.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ss.mapper.OrderApplyItemMapper;
import com.ss.mapper.OrderApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ss.pojo.ModelResult;
import com.ss.pojo.OrderApplyItem;
import com.ss.pojo.OrderApplyMerge;
import com.ss.pojo.OrderMergePojo;
import com.ss.pojo.User;
import com.ss.pojo.vo.OrderItemVo;
import com.ss.service.OrderApplyItemService;

@Service
public class OrderApplyItemServiceImpl implements OrderApplyItemService{

	@Autowired
	private OrderApplyItemMapper itemDao;
	
	@Autowired
	private OrderApplyMapper orderApplyMapper;
	
	@Override
	public void insertOrderItems(List<OrderApplyItem> itemTos) {
		itemDao.insertOrderItems(itemTos);
	}
	@Override
	public int insertOrderMerge(OrderApplyMerge merge) {
		itemDao.insertOrderMerge(merge);
		return merge.getId();
	}
	@Override
	public List<OrderApplyMerge> getAllOrderItems(OrderApplyMerge merge) {
		return itemDao.getAllOrderItems(merge);
	}
	@Override
	public List<OrderApplyItem> getItemsByMergeId(Integer id) {
		return itemDao.getItemsByMergeId(id);
	}
	@Override
	public void deleteItemsMerge(List<Integer> ids) {
		itemDao.deleteItemsMerge(ids);
	}
	@Override
	public int selectCountMergeItems(Integer id) {
		return itemDao.selectCountMergeItems(id);
	}
	@Override
	public void deleteMergeById(Integer id) {
		itemDao.deleteMergeById(id);
	}
	@Override
	public void robackMerge(Integer id) {
		itemDao.robackMerge(id);
	}
	@Override
	public OrderApplyMerge getMergeById(Integer id) {
		return itemDao.getMergeById(id);
	}
	@Override
	public void updateMergeTotal(OrderApplyItem item) {
		itemDao.updateMergeTotal(item);
	}
	@Override
	public void judyOrderMerge(OrderItemVo itemVo) {
		itemDao.judyOrderMerge(itemVo);
	}
	@Override
	public void updateOrderItemById(List<OrderApplyItem> items) {
		itemDao.updateOrderItemById(items);
	}
	@Override
	public List<OrderItemVo> selectFromOrderApplyByMany(OrderItemVo vo) {
		return itemDao.selectFromOrderApplyByMany(vo);
	}
	@Override
	public List<OrderApplyMerge> selectFromOrderApplyMergeByMany(OrderApplyMerge merge) {
		return itemDao.selectFromOrderApplyMergeByMany(merge);
	}
	@Override
	public void updateOrderMergeBill(OrderApplyMerge merge) {
		itemDao.updateOrderMergeBill(merge);
	}
	@Override
	public ModelResult transDeleteOrderMergeItems(OrderApplyItem applyItem) {
		try {
			OrderApplyMerge merge = itemDao.getMergeById(applyItem.getOrderMergeId());
			OrderItemVo itemVo = new OrderItemVo();
			itemVo.setApplyTime(applyItem.getApplyTime());
			itemVo.setCategory(applyItem.getCategory());
			itemVo.setItemNumber(applyItem.getItemNumber());
			itemVo.setOrderItemId(applyItem.getOrderItemId());
			itemVo.setName(merge.getName());
			itemVo.setOrderNo(applyItem.getOrderNo());
			itemVo.setTotal(applyItem.getTotal());
			itemVo.setType(applyItem.getType());
			itemVo.setUserId(applyItem.getUserId());
			itemVo.setUseDate(applyItem.getUseDate());
			itemVo.setStatus(merge.getStatus());
			orderApplyMapper.applyOneOrder(itemVo);
			List<Integer> ids = new ArrayList<>();
			ids.add(applyItem.getId());
			itemDao.deleteItemsMerge(ids);
			itemDao.updateMergeTotal(applyItem);
			int mergeNumber = itemDao.selectCountMergeItems(applyItem.getOrderMergeId());
			if(mergeNumber == 0) {
				itemDao.deleteMergeById(applyItem.getOrderMergeId());
			}
			return ModelResult.buildOk("回滚成功");
		}catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ModelResult.buildOk("回滚失败");
		}
		
	}
	@Override
	public ModelResult transRollBackOrder(OrderApplyMerge orderApplyMerge) {
		
		List<OrderApplyItem> applyItems = itemDao.getItemsByMergeId(orderApplyMerge.getId());
		if(applyItems == null || applyItems.size() == 0 ) {
			return ModelResult.buildError(1, "回滚失败,订单项为空");
		}
		List<OrderItemVo> itemVoList = new ArrayList<>();
		for(OrderApplyItem item : applyItems) {
			OrderItemVo itemVo = new OrderItemVo();
			itemVo.setApplyTime(item.getApplyTime());
			itemVo.setCategory(item.getCategory());
			itemVo.setItemNumber(item.getItemNumber());
			itemVo.setName(orderApplyMerge.getName());
			itemVo.setOrderItemId(item.getOrderItemId());
			itemVo.setOrderNo(item.getOrderNo());
			itemVo.setTotal(item.getTotal());
			itemVo.setType(item.getType());
			itemVo.setUserId(item.getUserId());
			itemVo.setUseDate(item.getUseDate());
			itemVo.setStatus(orderApplyMerge.getStatus());
			itemVoList.add(itemVo);
		}
		try {
			orderApplyMapper.insertIntoOrderApplyFromMerge(itemVoList);
			itemDao.robackMerge(orderApplyMerge.getId());
			itemDao.deleteMergeById(orderApplyMerge.getId());
			return ModelResult.buildOk("取消合并成功");
		}catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ModelResult.buildError(1, "取消合并失败");
		}
	}
	@Override
	public ModelResult transMergeOrderItems(OrderMergePojo mergePo,User user) {
		try{
			List<OrderItemVo> itemTo =  orderApplyMapper.selectOrderApplys(mergePo.getArr());
			if(itemTo == null || itemTo.size() <1) {
				return ModelResult.buildError(1, "操作失败");
			}
			List<OrderApplyItem> applyItems = new ArrayList<>();
			Float subTotal = 0f;
			for(OrderItemVo item : itemTo) {
				OrderApplyItem orderApplyItem = new OrderApplyItem();
				orderApplyItem.setApplyTime(item.getApplyTime());
				orderApplyItem.setCategory(item.getCategory());
				orderApplyItem.setItemNumber(item.getItemNumber());
				orderApplyItem.setTotal(item.getTotal());
				orderApplyItem.setOrderNo(item.getOrderNo());
				orderApplyItem.setOrderItemId(item.getOrderItemId());
				orderApplyItem.setUseDate(item.getUseDate());
				orderApplyItem.setType(item.getType());
				orderApplyItem.setUserId(item.getUserId());
				subTotal = subTotal + item.getTotal();
				applyItems.add(orderApplyItem);
			}
		
			OrderApplyMerge merge = new OrderApplyMerge();
			merge.setMergeNumber(mergePo.getName());
			merge.setMergeUserId(7);
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			merge.setMergeTime(cal.getTime());
			merge.setMergeUserId(user.getId());
			merge.setName(itemTo.get(0).getName());
			merge.setSubTotal(subTotal);
			itemDao.insertOrderMerge(merge);
			for(OrderApplyItem item : applyItems) {
				item.setOrderMergeId(merge.getId());
			}
			itemDao.insertOrderItems(applyItems);
			orderApplyMapper.deleteOrderApplys(mergePo.getArr());
			return ModelResult.buildOk("修改成功");
		}catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ModelResult.buildError(1, "操作失败");
		}
	}

}
