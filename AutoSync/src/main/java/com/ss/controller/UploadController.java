package com.ss.controller;

import com.ss.pojo.User;
import com.ss.pojo.trip.TravelResult;
import com.ss.pojo.trip.Trip;
import com.ss.pojo.trip.TripItem;
import com.ss.pojo.trip.TripItemDescription;
import com.ss.service.TripService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Controller
@RequestMapping("/upload")
@Transactional
public class UploadController {

	@Autowired
	private TripService tripService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public TravelResult uploadFile(Trip trip, MultipartFile file,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			return TravelResult.buildError(2, "请先登录");
		}
		trip.setCreate_date(new Date());
		trip.setUser_id(user.getId());
		trip.setDepartment_id(user.getDepartment_id());
		Workbook workbook = null;
		try {
			if (file == null || trip == null) {
				return TravelResult.buildError(1, "非法上传");
			} else if (file.getOriginalFilename().endsWith(".xls")) {
				workbook = new HSSFWorkbook(file.getInputStream());
			} else if (file.getOriginalFilename().endsWith(".xlsx")) {
				workbook = new XSSFWorkbook(file.getInputStream());
			} else {
				return TravelResult.buildError(1, "文件格式错误");
			}
			tripService.saveTrip(trip);
			Sheet sheet = workbook.getSheetAt(0);
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				for (int j = 0; j <= row.getLastCellNum(); j++) {
					if (row.getCell(j) == null) {
						continue;
					}
					String dateStr = this.getCellFormatValue(row.getCell(j));
					if (dateStr.startsWith("D")) {
						TripItem item = new TripItem();
						int day = Integer.valueOf(dateStr.substring(dateStr.indexOf("D") + 1));
						item.setDay(day);
						item.setTrip_id(trip.getId());
						if (this.getCellFormatValue(row.getCell(j + 2)).trim().equals("【预定项目】")) {
							String title = this.getCellFormatValue(sheet.getRow(i + 1).getCell(j + 2));
							item.setTitle(title);
							tripService.saveTripItems(item);
							TripItemDescription desc = new TripItemDescription();
							desc.setSort_id(0);
							desc.setTrip_item_id(item.getId());
							desc.setDescription(this.getCellFormatValue(sheet.getRow(i + 3).getCell(j + 2))+"\r\n"+
									this.getCellFormatValue(sheet.getRow(i + 4).getCell(j + 2))+"    "+
									this.getCellFormatValue(sheet.getRow(i + 4).getCell(j + 3))+"\r\n"+
									this.getCellFormatValue(sheet.getRow(i + 5).getCell(j + 2))+"    "+
									this.getCellFormatValue(sheet.getRow(i + 5).getCell(j + 3))+"\r\n"+
									this.getCellFormatValue(sheet.getRow(i + 6).getCell(j + 2))+"    "+
									this.getCellFormatValue(sheet.getRow(i + 6).getCell(j + 3)));
							List<TripItemDescription> descList = new ArrayList<>();
							descList.add(desc);
							tripService.saveTripItemDescription(descList);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return TravelResult.buildOk("上传成功");

	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(Cell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case Cell.CELL_TYPE_NUMERIC:
			case Cell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (DateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case Cell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}
}
