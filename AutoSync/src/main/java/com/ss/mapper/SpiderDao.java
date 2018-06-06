package com.ss.mapper;

import com.ss.pojo.app.AppWechat;
import com.ss.pojo.spider.AlipaySettle;
import com.ss.pojo.spider.SpiderDetail;
import com.ss.pojo.spider.SpiderModel;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface SpiderDao {
	
	List<SpiderDetail> getAllSpiderDetail(Date date);
	List<SpiderDetail> getSpiderDetailByDate(@Param("start") Date start, @Param("end") Date end);
	SpiderDetail getSpiderOrderByItemNumber(String itemNumber);
	SpiderDetail getOrderByItemNumber(String itemNumber);
	List<AppWechat> getAllAppWechat();
	List<SpiderModel> getOrderItems();
	void updateAppWechatStatus();

    List<SpiderModel> getOrderItemByCostAndDate(@Param("itemNumber") String itemNumber, @Param("time") String date);

    List<SpiderModel> getOrderItemByCostAndCost(@Param("itemNumber") String itemNumber, @Param("time") String date, @Param("cost") BigDecimal cost);
    
    List<AlipaySettle> getSettleFormApply(String payNumber);
    
    List<SpiderDetail> getNewDetailsByItemNumber(Integer orderId);
    List<SpiderDetail> getOldDetailsByItemNumber(@Param("cost") BigDecimal cost, @Param("itemNumber") String itemNumber);
}
