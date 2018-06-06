package com.ss.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ss.mapper.IUiDao;
import com.ss.mapper.IUserDao;
import com.ss.mapper.OrderApplyItemMapper;
import com.ss.mapper.OrderApplyMapper;
import com.ss.pojo.*;
import com.ss.pojo.vo.OrderItemVo;
import com.ss.service.OrderApplyService;
import com.ss.utils.MailSendUtils;
import com.ss.utils.PageSize;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author lory
 * @description 订单申请模块业务层
 * @Package com.ss.service.impl
 * @date 2018年1月11日
 */
@Service
@Slf4j
public class OrderApplyServiceImpl implements OrderApplyService {


	private static final Integer pageSize = PageSize.APPLY_SIZE;

	@Autowired
	private OrderApplyMapper orderApplyMapper;
	
	@Autowired
	private IUiDao iUiDao;
	
	@Resource
	private IUserDao userDao;
	
	@Autowired
	private OrderApplyItemMapper orderApplyItemMapper;

	/*
	 * @Override
	 * 
	 * @Transactional public void applyOrderList(List<OrderItemVo> orderApplyList) {
	 * orderApplyMapper.applyOrderList(orderApplyList); }
	 */

	// 订单申请具体业务逻辑
	@Override
	public ModelResult transApplyOrder(List<OrderItemVo> orderApplies, User currUser) {
		String contentFinance = "";
		String contentUser = "";
		if (orderApplies != null && orderApplies.size() > 0) {
			// 遍历传递来的orderApplies设置相应的属性值
			for (OrderItemVo item : orderApplies) {
				item.setUserId(currUser.getId());
				item.setApplyTime(new Date());
				item.setStatus("审核中");
				contentFinance += "<p>您收到一笔新的付款申请：<br />订单号：" + item.getItemNumber() + "&nbsp;&nbsp;&nbsp;金额："
						+ item.getTotal() + "&nbsp;&nbsp;&nbsp;用途:" + item.getCategory() + "</p><br />";
				contentUser += "<p>您的付款申请已成功提交:<br />订单号：" + item.getItemNumber() + "&nbsp;&nbsp;&nbsp;金额："
						+ item.getTotal() + "&nbsp;&nbsp;&nbsp;用途:" + item.getCategory() + "</p><br />";
			}
		}
		contentFinance += "来自于：" + currUser.getNickname();
		try {
			// 将对应的付款申请插入订单表
			orderApplyMapper.applyOrderList(orderApplies);
			// 将order_item表当中的订单状态改为审核中
			orderApplyMapper.changeOderItemsStatusTojudy(orderApplies);
			// 邮件发送
			MailSendUtils.sendMail("xiaoxuan.chen@qinker.com", contentFinance, "璇姐");
			MailSendUtils.sendMail("jill.li@qinker.com", contentFinance, "小超");
			if (currUser.getEmail() != null) {
				MailSendUtils.sendMail(currUser.getEmail(), contentUser, currUser.getNickname());
			}
			return ModelResult.buildOk("申请成功");
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();     
			log.error("申请多笔订单失败:" + e.getStackTrace());
			return ModelResult.buildError(1, "申请失败");
		}

	}

	// 获取财务未审核订单列表
	@Override
	public ModelResult getNonAuditingOrderForFinance(User auditorUser, Integer page) {
		PageHelper.startPage(page, pageSize);
		// 如果用户权限不足，返回空对象集合
		if (!auditorUser.getRole().trim().equals("admin") && !auditorUser.getRole().trim().equals("inspector")) {
			PageInfo<OrderItemVo> pageInfo = new PageInfo<OrderItemVo>(new ArrayList<OrderItemVo>());
			return ModelResult.buildOk(pageInfo);
		}
		List<OrderItemVo> orderApplies = orderApplyMapper.getNonAuditingOrderForFinance();
		PageInfo<OrderItemVo> pageInfo = new PageInfo<OrderItemVo>(orderApplies);
		return ModelResult.buildOk(pageInfo);
	}

	// 根据财务的ID来获取财务已经审核的订单
	@Override
	public ModelResult getAuditingOrderForFinance(User auditorUser, Integer page) {
		PageHelper.startPage(page, pageSize);
		if (!auditorUser.getRole().trim().equals("admin")) {
			return ModelResult.buildOk(new PageInfo<>());
		}
		List<OrderItemVo> orders = orderApplyMapper.getAuditingOrderForFinance(auditorUser.getId());
		PageInfo<OrderItemVo> info = new PageInfo<>(orders);
		return ModelResult.buildOk(info);
	}

	@Override
	public ModelResult getNonAuditingOrderForUser(User auditorUser, Integer page) {
		PageHelper.startPage(page, pageSize);
		List<OrderItemVo> orders = orderApplyMapper.getNonAuditingOrderForUser(auditorUser.getId());
		PageInfo<OrderItemVo> info = new PageInfo<>(orders);
		return ModelResult.buildOk(info);
	}

	@Override
	public ModelResult getAuditingOrderForUser(User auditorUser, Integer page) {
		PageHelper.startPage(page, pageSize);
		List<OrderItemVo> vos = orderApplyMapper.getAuditingOrderForUser(auditorUser.getId());
		PageInfo<OrderItemVo> info = new PageInfo<>(vos);
		return ModelResult.buildOk(info);
	}

	@Override
	public int getNonAuditingOrderNumberForFinance() {
		return orderApplyMapper.getNonAuditingOrderNumberForFinance();
	}

	@Override
	public ModelResult transUpdateOrderStatusByOrderId(OrderItemVo itemVo,User userSession,Integer url) {
		if(userSession == null || !userSession.getRole().trim().equals("admin")) {
			log.error("updateOrderStatusByOrderId method 用户未登录或无权限尝试修改订单状态,系统有毛病");
			return ModelResult.buildError(103, "未登录或您没有权限");
		}
		itemVo.setAuditorId(userSession.getId());
		itemVo.setPayTime(new Date());
		//
		String content = "<p>您收到一笔新的付款结果：<br />订单号："+itemVo.getItemNumber()+"&nbsp;&nbsp;&nbsp;金额："+itemVo.getTotal()+"付款人：："+userSession.getNickname()+"</p>";
		try{
				if(itemVo.getPayWay() == 9) {
					Provider pro = iUiDao.getProviderByName(itemVo.getName().trim());
					if(pro == null) {
						return ModelResult.buildError(1, "供应商名称错误");
					}
					itemVo.setProvider_id(pro.getId());
					
					if(pro.getCategory().trim().equals("库存类")) {
						if(!StringUtils.isNumeric(itemVo.getOrderNo())) {
							return ModelResult.buildError(1,"该订单非数字，不能扣除库存,请更改为数字或重新申请");
						}
						if(pro.getBalance() < Float.valueOf(itemVo.getOrderNo())) {
							return ModelResult.buildError(1, "余额不足");
						}
						pro.setBalance(Float.valueOf(itemVo.getOrderNo()));
					}else {
						if(pro.getBalance() < itemVo.getTotal()) {
							return ModelResult.buildError(1, "余额不足");
						}
						pro.setBalance(itemVo.getTotal());
					}
					//更新供应商库存
					iUiDao.updateProviderBalance(pro);
					
				}
				if(url == 1) {
					orderApplyMapper.updateOrderStatus(itemVo);
					orderApplyMapper.updateOrderItemStatus(itemVo.getOrderItemId());
				}
				if(url == 2) {
					List<OrderApplyItem> items = orderApplyItemMapper.getItemsByMergeId(itemVo.getId());
					orderApplyItemMapper.judyOrderMerge(itemVo);
					orderApplyItemMapper.updateOrderItemById(items);
				}
				
				MailSendUtils.sendMail("xiaoxuan.chen@qinker.com", content, "旋姐");
				MailSendUtils.sendMail("jill.li@qinker.com", content, "小超");
				Integer[] uids = orderApplyMapper.selectMergeOrderId(itemVo.getId());
				for(int i=0;i<uids.length;i++) {
					User applyUser = userDao.selectByPrimaryKey(uids[i]);
					if(applyUser !=null && applyUser.getEmail() !=null) {
						MailSendUtils.sendMail(applyUser.getEmail(), content, applyUser.getNickname());
					}
				}
				return ModelResult.buildOk("申请成功");
		}catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error(e.getMessage());
			return ModelResult.buildError(1, "付款失败");
		}
	}

	@Override
	public ModelResult getAllOrders(User userSession, Integer page) {
		PageHelper.startPage(page, pageSize);
		if (userSession.getRole().trim().equals("manager")) {
			List<Order> orders = orderApplyMapper.getAllOrdersByManager();
			PageInfo<Order> pageInfo = new PageInfo<>(orders);
			return ModelResult.buildOk(pageInfo);
		}
		List<Order> orderList = orderApplyMapper.getAllOrders(userSession.getId());
		PageInfo<Order> pageInfo = new PageInfo<>(orderList);
		return ModelResult.buildOk(pageInfo);
	}
	
	@Override
	public ModelResult deleteOrderItem(Integer doS,Integer id) {
		OrderItemVo vo = orderApplyMapper.getOrderItemById(id);
		if(vo == null) {
			return ModelResult.buildError(1, "没有该订单");
		}
		if(vo.getStatus().trim().equals("已付款")) {
			return ModelResult.buildError(1, "该订单已付款,不能修改");
		}
		OrderItemVo itemVo = orderApplyMapper.getOrderItemById(id);
		try {
			if(doS == 1){
				orderApplyMapper.updateOrderItemByIdToCancle(id);
				orderApplyMapper.updateOrderTByIdToCancle(itemVo.getOrderItemId());
				return ModelResult.buildOk("删除成功");
			}
			if(doS == 2) {
				orderApplyMapper.updateOrderItemByIdToRefuse(id);
				orderApplyMapper.updateOrderTByIdToRefuse(itemVo.getOrderItemId());
				return ModelResult.buildOk("拒绝成功");
			}
		}catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("系统错误："+e.getStackTrace());
			return ModelResult.buildError(1, "系统错误");
		}
		return null;
	}

	@Override
	public List<OrderItemVo> getOrderItems(String itemNumber) {
		return orderApplyMapper.getOrderItems(itemNumber);
	}

	@Override
	public void changeOderItemStatusTojudy(OrderItemVo item) {
		orderApplyMapper.changeOderItemStatusTojudy(item.getOrderItemId());
	}

	/*
	 * @Override public void changeOderItemsStatusTojudy(List<OrderItemVo> orders) {
	 * orderApplyMapper.changeOderItemsStatusTojudy(orders); }
	 */

	@Override
	public List<Order> getAllOrdersWithNoPage(Integer userId) {
		return orderApplyMapper.getAllOrdersWithNoPage(userId);
	}

	@Override
	public void updateOrderItemStatus(Integer id) {
		orderApplyMapper.updateOrderItemStatus(id);
	}

	public void updateOrderBill(OrderItemVo itemVo) {
		orderApplyMapper.updateOrderBill(itemVo);
	}

	@Override
	public OrderItemVo getItemById(int id) {
		return orderApplyMapper.getOrderItemById(id);
	}

	@Override
	public void updateOrderItemByIdToRefuse(int id) {
		orderApplyMapper.updateOrderItemByIdToRefuse(id);
	}

	@Override
	public void updateOrderItemByIdToCancle(int id) {
		orderApplyMapper.updateOrderItemByIdToCancle(id);
	}

	@Override
	public ModelResult selectByParameters(Integer pageNumber, Order order, User user) {
		PageHelper.startPage(pageNumber, pageSize);
		if (user.getRole().trim().equals("CUSTO")) {
			order.setManNameId(user.getId());
		}
		if (order.getItemNumber() != null && !"".equals(order.getItemNumber())) {
			order.setItemNumber("%" + order.getItemNumber() + "%");
		}
		if (order.getCustomerName() != null && !"".equals(order.getCustomerName())) {
			order.setCustomerName("%" + order.getCustomerName() + "%");
		}
		List<Order> orders = orderApplyMapper.selectByParameters(order);
		PageInfo<Order> info = new PageInfo<>(orders);
		return ModelResult.buildOk(info);
	}

	@Override
	public void updateOrderTByIdToCancle(int orderItemId) {
		orderApplyMapper.updateOrderTByIdToCancle(orderItemId);
	}

	@Override
	public void updateOrderTByIdToRefuse(int orderItemId) {
		orderApplyMapper.updateOrderTByIdToRefuse(orderItemId);
	}

	@Override
	public List<OrderItemVo> selectOrderApplys(Integer[] ids) {
		return orderApplyMapper.selectOrderApplys(ids);
	}

	@Override
	public void deleteOrderApplys(Integer[] ids) {
		orderApplyMapper.deleteOrderApplys(ids);
	}

	@Override
	public void insertIntoOrderApplyFromMerge(List<OrderItemVo> items) {
		orderApplyMapper.insertIntoOrderApplyFromMerge(items);
	}

	@Override
	public List<Order> getAllOrdersByManager() {
		return orderApplyMapper.getAllOrdersByManager();
	}

	@Override
	public Integer[] selectMergeOrderId(int id) {
		return orderApplyMapper.selectMergeOrderId(id);
	}
}
