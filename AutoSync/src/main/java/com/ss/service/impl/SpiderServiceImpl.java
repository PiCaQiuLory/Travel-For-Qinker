package com.ss.service.impl;

import com.ss.mapper.SpiderDao;
import com.ss.pojo.spider.AlipaySettle;
import com.ss.pojo.spider.SpiderDetail;
import com.ss.service.SpiderService;
import com.ss.utils.DateConventer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Service
public class SpiderServiceImpl implements SpiderService{
	
	@Resource
	private SpiderDao spiderDao;

	@Override
	public List<SpiderDetail> getAllSpiderDetail(Date date) throws Exception {
		return spiderDao.getAllSpiderDetail(DateConventer.formatDate("yyyy-MM-dd", date));
	}

	@Override
	public List<SpiderDetail> getSpiderDetailByDate(Date start, Date end) throws Exception {
		return spiderDao.getSpiderDetailByDate(DateConventer.formatDate("yyyy-MM-dd", start),DateConventer.formatDate("yyyy-MM-dd", end));
	}

	@Override
	public SpiderDetail getSpiderOrderByItemNumber(String itemNumber) {
		return spiderDao.getSpiderOrderByItemNumber(itemNumber);
	}

	@Override
	public List<AlipaySettle> getSettleFormApply(String payNumber) {
		return spiderDao.getSettleFormApply(payNumber);
	}

	@Override
	public List<SpiderDetail> getErrorOrder(Date start, Date end) {
		List<SpiderDetail> newOrders = spiderDao.getSpiderDetailByDate(start, end);
		List<SpiderDetail> errorOrders = new ArrayList<>();
		Iterator<SpiderDetail> iterator = newOrders.iterator();
		while(iterator.hasNext()) {
			SpiderDetail newOrder = iterator.next();
			SpiderDetail oldOrder = spiderDao.getSpiderOrderByItemNumber(newOrder.getItemNumber().trim());
			if(oldOrder == null) {
				newOrder.setWrongReason("直连未有此订单");
				errorOrders.add(newOrder);
				//iterator.remove();
				continue;
			}else {
				if(newOrder.getPrice().toString().equals("0.00")) {
					continue;
				}
				if(!oldOrder.getPrice().toString().equals(newOrder.getPrice().toString())) {
					List<SpiderDetail> details = spiderDao.getNewDetailsByItemNumber(newOrder.getId());
					if(details.size() == 0) {
						continue;
					}else {
						for (SpiderDetail newDetail : details) {
							List<SpiderDetail> oldDetail = spiderDao.getOldDetailsByItemNumber(newDetail.getPrice(), oldOrder.getItemNumber());
							if(oldDetail == null || oldDetail.size()==0) {
								newOrder.setWrongReason("金额不对，错的金额：爬取："+newOrder.getPrice()+";直连："+oldOrder.getPrice());
								errorOrders.add(newOrder);
							}
						}
					}
				}
			}
			
		}
		return errorOrders;
	}

	
}
