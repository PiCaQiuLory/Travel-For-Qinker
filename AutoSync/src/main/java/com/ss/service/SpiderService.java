package com.ss.service;

import com.ss.pojo.spider.AlipaySettle;
import com.ss.pojo.spider.SpiderDetail;

import java.util.Date;
import java.util.List;
public interface SpiderService {

	List<SpiderDetail> getAllSpiderDetail(Date date) throws Exception;
	List<SpiderDetail> getSpiderDetailByDate(Date start, Date end) throws Exception;
	SpiderDetail getSpiderOrderByItemNumber(String itemNumber);
	List<SpiderDetail> getErrorOrder(Date start, Date end);
	List<AlipaySettle> getSettleFormApply(String payNumber);
}
