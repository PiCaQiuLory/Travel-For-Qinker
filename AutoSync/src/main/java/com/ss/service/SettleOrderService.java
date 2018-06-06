package com.ss.service;

import java.util.Date;
import java.util.List;

import com.ss.pojo.SettleOrder;
/**
 * @author lory
 * @description 对账模块service层接口
 * @Package com.ss.service
 * @date 2018年2月24日
 */
public interface SettleOrderService {

	/**
	 * 根据条件查询所有订单
	 * @author lory
	 * @Description TODO
	 * @Params @param conditionPojo
	 * @Params @return
	 * @return List<SettleOrder>
	 */
	public List<SettleOrder> getAllSettleOrder();
	
	public Integer getLengthByItemNumber(Integer orderId);
	
	public List<SettleOrder> getAllSettleOrderWithEnd(Date startDate, Date endDate);
	
}
