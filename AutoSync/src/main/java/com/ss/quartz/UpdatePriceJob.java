package com.ss.quartz;

import com.ss.service.IProductService;

import javax.annotation.Resource;

public class UpdatePriceJob {

	@Resource
	private IProductService productService;
	
	public void updatePrice(){
		/*
		 * 查询product表和tareget_product表
		 * select * from targetProduct tp left join product p on 
		 * tp.sourcePid = p.id and syncTime(1|2|3|4|5|6) in (current_time +-10 mins)
		 */
		productService.syncProduct();
	}
}
