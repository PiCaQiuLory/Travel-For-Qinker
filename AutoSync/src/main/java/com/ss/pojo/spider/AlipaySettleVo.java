package com.ss.pojo.spider;

import java.io.Serializable;
import java.util.List;

public class AlipaySettleVo implements Serializable{

	private List<AlipaySettle> alipaySettles;

	public List<AlipaySettle> getAlipaySettles() {
		return alipaySettles;
	}

	public void setAlipaySettles(List<AlipaySettle> alipaySettles) {
		this.alipaySettles = alipaySettles;
	}
	
	
}
