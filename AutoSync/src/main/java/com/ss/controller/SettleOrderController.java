package com.ss.controller;

import com.ss.pojo.ModelResult;
import com.ss.pojo.SettleOrder;
import com.ss.pojo.SettleOrderVo;
import com.ss.pojo.User;
import com.ss.service.SettleOrderService;
import com.ss.utils.Page;
import net.sf.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 对账模块控制层
 * 
 * @author lory
 * @description TODO
 * @Package com.ss.controller
 * @date 2018年2月24日
 */
@Controller
public class SettleOrderController {

	@Autowired
	private SettleOrderService orderService;

	static Map<String, List<SettleOrder>> localMap = new HashMap<>();

	static Map<String, List<SettleOrder>> localMap2 = new HashMap<>();

	/**
	 * 根据条件筛选异常订单
	 * 
	 * @author lory
	 * @Description TODO
	 * @Params @param conditionPojo
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/querySettleOrder/{pageNum}")
	@ResponseBody
	public ModelResult settleOrder(@PathVariable Integer pageNum, HttpServletRequest request) {
		Page page = new Page();
		if (pageNum == null) {
			page.setCurrentPage(1);
		} else {
			page.setCurrentPage(pageNum);
		}
		page.setStar((page.getCurrentPage() - 1) * page.getPageSize());
		List<SettleOrderVo> orderVos;
		try {
			// orderVos =
			// this.getListFromJsonFile("/root/develop/resourceFile/finishOrder.json");
			orderVos = this.getListFromJsonFile("D:\\finishOrder.json");
		} catch (IOException e) {
			return ModelResult.buildError(1, "json文件错误");
		}
		if (orderVos == null) {
			return ModelResult.buildError(1, "json文件错误");
		}
		List<SettleOrder> settleOrders = orderService.getAllSettleOrder();
		HashMap<String, SettleOrder> map1 = new HashMap<String, SettleOrder>(settleOrders.size());
		for (SettleOrder order : settleOrders) {
			map1.put(order.getItemNumber().trim(), order);
		}
		List<SettleOrder> result = new ArrayList<>();
		for (int i = 0; i < orderVos.size(); i++) {
			if (orderVos.get(i) == null || orderVos.get(i).getItemNumber() == null) {
				continue;
			}
			if (map1.get(orderVos.get(i).getItemNumber().trim()) == null) {
				SettleOrder order = new SettleOrder();
				order.setItemNumber(orderVos.get(i).getItemNumber());
				order.setSubtotal(orderVos.get(i).getSub_total());
				order.setSubtotalGUI(new Float(0));
				order.setLength(orderVos.get(i).getLength());
				order.setLengthGUI(0);
				order.setReason("直连未有此订单");
				order.setManFlat(orderVos.get(i).getPlatform());
				result.add(order);
			} else {
				if (orderVos.get(i).getSub_total() == null
						|| map1.get(orderVos.get(i).getItemNumber()).getSubtotal() == null) {
					SettleOrder order = new SettleOrder();
					order.setItemNumber(orderVos.get(i).getItemNumber());
					order.setSubtotal(
							orderVos.get(i).getSub_total() == null ? new Float(0) : orderVos.get(i).getSub_total());
					order.setSubtotalGUI(map1.get(orderVos.get(i).getItemNumber()).getSubtotal() == null ? new Float(0)
							: map1.get(orderVos.get(i).getItemNumber()).getSubtotal());
					order.setReason("直连总金额为空");
					order.setManFlat(orderVos.get(i).getPlatform());
					order.setNickname(map1.get(orderVos.get(i).getItemNumber()).getNickname());
					result.add(order);
				} else if (!map1.get(orderVos.get(i).getItemNumber()).getSubtotal().toString().trim()
						.equals(orderVos.get(i).getSub_total().toString().trim())) {
					SettleOrder order = new SettleOrder();
					order.setItemNumber(orderVos.get(i).getItemNumber());
					order.setSubtotal(orderVos.get(i).getSub_total());
					order.setSubtotalGUI(map1.get(orderVos.get(i).getItemNumber()).getSubtotal());
					order.setReason("订单总金额不匹配");
					order.setManFlat(orderVos.get(i).getPlatform());
					order.setNickname(map1.get(orderVos.get(i).getItemNumber()).getNickname());
					result.add(order);
				}
			}
		}
		User user = (User) request.getSession().getAttribute("user");
		if (localMap.get(user.getId()) != null) {
			localMap = new HashMap<>();
		}
		localMap.put("finish", result);
		int count = result.size();
		page.setNum(count);
		page.setTotalPage(count % 20 == 0 ? count / 20 : count / 20 + 1);
		page.setDataList(result.subList(page.getStar(),
				count - page.getStar() > page.getPageSize() ? page.getStar() + page.getPageSize() : count));
		return ModelResult.buildOk(page);
	}

	@RequestMapping("/queryItemNumberSettleOrder/{pageNum}")
	@ResponseBody
	public ModelResult queryItemNumberOrderSettle(@PathVariable Integer pageNum, HttpServletRequest request,
			@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
		if (startDate == null) {
			startDate = "2018-02-14";
		}
		if (endDate == null) {
			endDate = new Date().toString();
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start = new Date();
		Date end = new Date();
		try {
			start = format.parse(startDate);
			long now = format.parse(endDate).getTime() + 86400000;
			end = new Date(now);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		Page page = new Page();
		if (pageNum == null || pageNum == 1) {
			page.setCurrentPage(1);
		} else {
			page.setCurrentPage(pageNum);
		}
		page.setStar((page.getCurrentPage() - 1) * page.getPageSize());
		List<SettleOrderVo> orderVos;
		try {
			// orderVos =
			// this.getListFromJsonFile("/root/develop/resourceFile/allOrder.json");
			orderVos = this.getListFromJsonFile("D:\\allOrder.json");
		} catch (IOException e) {
			return ModelResult.buildError(1, "json文件错误");
		}
		if (orderVos == null) {
			return ModelResult.buildError(1, "json文件错误");
		}
		List<SettleOrder> settleOrders = orderService.getAllSettleOrderWithEnd(start, end);
		HashMap<String, SettleOrder> map1 = new HashMap<String, SettleOrder>(settleOrders.size());
		for (SettleOrder order : settleOrders) {
			map1.put(order.getItemNumber().trim(), order);
		}
		List<SettleOrder> result = new ArrayList<>();
		for (int i = 0; i < orderVos.size(); i++) {
			if (orderVos.get(i) == null || orderVos.get(i).getItemNumber() == null) {
				continue;
			}
			
			if(orderVos.get(i).getItemNumber().trim().equals("4570236259")) {
			
				if (map1.get(orderVos.get(i).getItemNumber().trim()) == null) {
					SettleOrder order = new SettleOrder();
					order.setItemNumber(orderVos.get(i).getItemNumber());
					order.setReason("直连未有此订单");
					order.setManFlat(orderVos.get(i).getPlatform());
					order.setUser(orderVos.get(i).getUser());
					order.setStartDate(orderVos.get(i).getDateFormat());
					result.add(order);
				}
				
			}

		}
		// User user = (User) request.getSession().getAttribute("user");
		if (result.size() > 0) {

			if (localMap2.get("allOrder") != null) {
				localMap2 = new HashMap<>();
			}
			localMap2.put("allOrder", result);

			int count = result.size();
			page.setNum(count);
			page.setTotalPage(count % 20 == 0 ? count / 20 : count / 20 + 1);
			page.setDataList(result.subList(page.getStar(),
					count - page.getStar() > page.getPageSize() ? page.getStar() + page.getPageSize() : count));
			return ModelResult.buildOk(page);
		} else {
			return ModelResult.buildError(1, "未有异常订单");
		}
	}

	private List<SettleOrderVo> getListFromJsonFile(String path) throws IOException {
		// File file = new File("/root/develop/resourceFile/data.json");
		File file = new File(path);
		String content = FileUtils.readFileToString(file);
		List<SettleOrderVo> datas = new ArrayList<>();
		JSONArray array = JSONArray.fromObject(content);
		datas = JSONArray.toList(array, SettleOrderVo.class);
		return datas;
	}

	@RequestMapping("/getExceptionOrder")
	public void getSettleOrderExcel(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			List<SettleOrder> orderList = localMap.get(user.getId());
			response.setHeader("Content-disposition",
					"attachment; filename=" + new String("异常订单".getBytes("gb2312"), "ISO-8859-1") + ".xls");
			response.setContentType("application/msexcel");// 定义输出类型
			HSSFWorkbook wb = new HSSFWorkbook();
			// 设置字体
			Sheet sheet = wb.createSheet("异常订单");
			Row row0 = sheet.createRow(0);
			Row row1 = sheet.createRow(1);
			for (int i = 0; i <= 11; i++) {
				row1.createCell(i);
			}
			for (Cell cell : row1) {
				this.setHeadBorder(wb, cell);
			}
			Cell cell0 = row0.createCell(0);
			cell0.setCellValue("平台与直连异常订单");
			row0.createCell(1);
			row0.createCell(2);
			row0.createCell(3);
			row0.createCell(4);
			row0.createCell(5);
			row0.createCell(6);
			row0.createCell(7);
			row0.createCell(8);
			row0.createCell(9);
			row0.createCell(10);
			row0.createCell(11);
			for (Cell cell : row0) {
				this.setHeadBorder(wb, cell);
			}
			CellRangeAddress rangeAddress = new CellRangeAddress(0, 1, 0, 11);
			sheet.addMergedRegion(rangeAddress);
			if (orderList.size() > 0) {
				Row row3 = sheet.createRow(2);
				row3.createCell(0).setCellValue("日期：");
				row3.createCell(1);
				row3.createCell(2);
				row3.createCell(3).setCellValue(this.dateFormat(new Date()));
				row3.createCell(4);
				row3.createCell(5);
				row3.createCell(6);
				row0.createCell(7);
				row0.createCell(8);
				row0.createCell(9);
				row0.createCell(10);
				row0.createCell(11);
				for (Cell cell : row3) {
					this.setBorder(wb, cell);
				}
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 11));
				Row row5 = sheet.createRow(3);
				row5.createCell(0).setCellValue("订单号");
				row5.createCell(1);
				row5.createCell(2).setCellValue("平台");
				row5.createCell(3);
				row5.createCell(4).setCellValue("平台总金额");
				row5.createCell(5);
				row5.createCell(6).setCellValue("直连系统总金额");
				row5.createCell(7);
				row5.createCell(8).setCellValue("原因");
				row5.createCell(9);
				row5.createCell(10).setCellValue("定制师");
				row5.createCell(11);
				for (Cell cell : row5) {
					this.initColumnCenterstyle(wb, cell);
				}
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 5));
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 6, 7));
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 8, 9));
				sheet.addMergedRegion(new CellRangeAddress(3, 3, 10, 11));
				for (int i = 0; i < orderList.size(); i++) {
					Row row = sheet.createRow(i + 4);
					row.createCell(0).setCellValue(orderList.get(i).getItemNumber());
					row.createCell(1);
					row.createCell(2).setCellValue(orderList.get(i).getManFlat());
					row.createCell(3);
					row.createCell(4).setCellValue(this.numberFormat(orderList.get(i).getSubtotal()));
					row.createCell(5);
					row.createCell(6).setCellValue(this.numberFormat(orderList.get(i).getSubtotalGUI()));
					;
					row.createCell(7);
					row.createCell(8).setCellValue(orderList.get(i).getReason());
					row.createCell(9);
					row.createCell(10).setCellValue(orderList.get(i).getNickname());
					row.createCell(11);
					for (Cell cell : row) {
						this.setBorder(wb, cell);
					}
					sheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 1));
					sheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 2, 3));
					sheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 4, 5));
					sheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 6, 7));
					sheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 8, 9));
					sheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 10, 11));
				}
			}
			try {
				wb.write(response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("/getItemNumberException")
	public void getItemNumberException(HttpServletResponse response, HttpServletRequest request)
			throws UnsupportedEncodingException {
		// User user = (User) request.getSession().getAttribute("user");
		// if (user != null) {
		List<SettleOrder> orderList = localMap2.get("allOrder");
		response.setHeader("Content-disposition",
				"attachment; filename=" + new String("异常订单".getBytes("gb2312"), "ISO-8859-1") + ".xls");
		response.setContentType("application/msexcel");// 定义输出类型
		HSSFWorkbook wb = new HSSFWorkbook();
		// 设置字体
		Sheet sheet = wb.createSheet("异常订单");
		Row row0 = sheet.createRow(0);
		Row row1 = sheet.createRow(1);
		for (int i = 0; i <= 8; i++) {
			row1.createCell(i);
		}
		for (Cell cell : row1) {
			this.setHeadBorder(wb, cell);
		}
		Cell cell0 = row0.createCell(0);
		cell0.setCellValue("平台与直连异常订单");
		row0.createCell(1);
		row0.createCell(2);
		row0.createCell(3);
		row0.createCell(4);
		row0.createCell(5);
		row0.createCell(6);
		row0.createCell(7);
		row0.createCell(8);
		for (Cell cell : row0) {
			this.setHeadBorder(wb, cell);
		}
		CellRangeAddress rangeAddress = new CellRangeAddress(0, 1, 0, 8);
		sheet.addMergedRegion(rangeAddress);
		if (orderList.size() > 0) {
			Row row3 = sheet.createRow(2);
			row3.createCell(0).setCellValue("日期：");
			row3.createCell(1);
			row3.createCell(2);
			row3.createCell(3).setCellValue(this.dateFormat(new Date()));
			row3.createCell(4);
			row3.createCell(5);
			row3.createCell(6);
			row3.createCell(7);
			row3.createCell(8);
			for (Cell cell : row3) {
				this.setBorder(wb, cell);
			}
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
			sheet.addMergedRegion(new CellRangeAddress(2, 2, 3, 8));
			Row row5 = sheet.createRow(3);
			row5.createCell(0).setCellValue("订单号");
			row5.createCell(1);
			row5.createCell(2).setCellValue("平台");
			row5.createCell(3);
			row5.createCell(4).setCellValue("帐户");
			row5.createCell(5);
			row5.createCell(6);
			row5.createCell(7);
			row5.createCell(8);
			for (Cell cell : row5) {
				this.initColumnCenterstyle(wb, cell);
			}
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 3));
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 4, 8));
			for (int i = 0; i < orderList.size(); i++) {
				Row row = sheet.createRow(i + 4);
				row.createCell(0).setCellValue(orderList.get(i).getItemNumber());
				row.createCell(1);
				row.createCell(2).setCellValue(orderList.get(i).getManFlat());
				row.createCell(3);
				row.createCell(4).setCellValue(orderList.get(i).getUser());
				row.createCell(5);
				row.createCell(6);
				row.createCell(7);
				row.createCell(8);
				for (Cell cell : row) {
					this.setBorder(wb, cell);
				}
				sheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 0, 1));
				sheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 2, 3));
				sheet.addMergedRegion(new CellRangeAddress(i + 4, i + 4, 4, 8));
			}
		}
		try {
			wb.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// }
	}

	private void setBorder(Workbook wb, Cell cell) {
		CellStyle style = wb.createCellStyle();
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		cell.setCellStyle(style);

	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>单元格的默认样式<br>
	 * <b>作者：</b>yixq<br>
	 * <b>@param wb <b>@return</b>
	 */
	public void initColumnCenterstyle(Workbook wb, Cell cell) {
		CellStyle style = wb.createCellStyle();
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER_SELECTION);// 左右居中
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);
		style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．
		cell.setCellStyle(style);
	}

	private void setHeadBorder(Workbook wb, Cell cell) {
		CellStyle style = wb.createCellStyle();
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		Font headFont = wb.createFont();
		headFont.setFontName("宋体");
		headFont.setFontHeightInPoints((short) 14);
		headFont.setBold(true);
		style.setFont(headFont);
		style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		cell.setCellStyle(style);
	}

	private String dateFormat(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String dateStr = format.format(date);
		return dateStr;
	}

	private String numberFormat(Float num) {
		return NumberFormat.getCurrencyInstance().format(num);
	}
}
