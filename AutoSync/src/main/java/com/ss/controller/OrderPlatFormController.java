package com.ss.controller;

import com.ss.pojo.trip.OrderPlatform;
import com.ss.pojo.trip.TravelResult;
import com.ss.service.OrderPlatFormService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class OrderPlatFormController {

	@Autowired
	private OrderPlatFormService orderPlatFormService;

	@RequestMapping("/except/orderplat")
	@ResponseBody
	public TravelResult getOrderPlatForm() {
		List<OrderPlatform> orders = orderPlatFormService.getPlatFormOrder();
		return TravelResult.buildOk(orders);
	}

	@RequestMapping("/exceptOrder/excel")
	public void createExcel(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String("未上传订单".getBytes("gb2312"), "ISO-8859-1") + ".xls");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<OrderPlatform> orders = orderPlatFormService.getPlatFormOrder();
		response.setContentType("application/msexcel");// 定义输出类型
		HSSFWorkbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		for(int i=0;i<=orders.size()+1;i++) {
			Row row = sheet.createRow(i);
			for(int j=0;j<=4;j++) {
				row.createCell(j);
			}
			if(row.getRowNum()==0) {
				row.getCell(0).setCellValue("订单号");
				row.getCell(1).setCellValue("顾客姓名");
				row.getCell(2).setCellValue("接单时间");
				row.getCell(3).setCellValue("账户名");
				row.getCell(4).setCellValue("备注");
			}
		}
		for(int k=0;k<orders.size();k++) {
			sheet.getRow(k+1).getCell(0).setCellValue(orders.get(k).getItemNumber());
			sheet.getRow(k+1).getCell(1).setCellValue(orders.get(k).getCustomerName());
			sheet.getRow(k+1).getCell(2).setCellValue(orders.get(k).getGetOrderTimer());
			sheet.getRow(k+1).getCell(3).setCellValue(orders.get(k).getUser());
			sheet.getRow(k+1).getCell(4).setCellValue(orders.get(k).getRemark());
		}
		try {
			wb.write(response.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
