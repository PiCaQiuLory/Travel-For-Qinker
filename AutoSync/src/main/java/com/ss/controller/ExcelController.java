package com.ss.controller;

import com.ss.pojo.trip.*;
import com.ss.service.ReserveService;
import com.ss.service.TripService;
import com.ss.utils.SendEmailUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;



@Controller
public class ExcelController {

	@Autowired
	private TripService tripService;

	@Resource
	private ReserveService reserveService;

	private static final String modelPath = "/root/develop/resourceFile/model.xlsx";
	//private static final String modelPath2= "C:\\Users\\13162\\Desktop\\work\\model.xlsx";

	/**
	 * 导出行程Excel的方法
	 * @param tripId
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/trip/excel/{tripId}")
	public void createExcel(@PathVariable Integer tripId, HttpServletResponse response) throws Exception {
		response.setHeader("Content-disposition",
				"attachment; filename=" + new String("行程单".getBytes("gb2312"), "ISO-8859-1") + ".xlsx");
		response.setContentType("application/msexcel");// 定义输出类型
		Workbook workBook = getWorkBook(tripId);
		workBook.write(response.getOutputStream());
		
	}
	
	/**
	 * 发送邮件的方法
	 * @param tripId
	 * @return
	 */
	@RequestMapping("/trip/mail/{tripId}")
	@ResponseBody
	public TravelResult sendEmail(@PathVariable Integer tripId,@RequestParam String receiveEmail) {
		try {
			Workbook workBook = getWorkBook(tripId);
			String dirName = UUID.randomUUID().toString();
			//TODO修改Linux系统文件的分隔符
			File file = new File("/root/develop/resourceFile/"+dirName);
			if(file.exists()) {
				dirName = UUID.randomUUID().toString();
				file = new File("/root/develop/resourceFile/"+dirName);
			}
			if(file.exists()) {
				return TravelResult.buildError(1, "发送失败");
			}
			boolean a = file.mkdir();
			if(!a) {
				return TravelResult.buildError(1, "发送失败");
			}
			FileOutputStream outputStream = new FileOutputStream(file+"\\"+tripId+".xlsx");
			workBook.write(outputStream);
			Trip trip = getTripForCommon(tripId);
			SendEmailUtils.sendMail(receiveEmail,trip.getTitle(), "重庆舜天旅行社",file.getPath()+"\\"+tripId+".xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return TravelResult.buildOk("发送成功");
	}


	/**
	 * 导出行程预定资源明细Excel
	 * @param response
	 * @param tripId
	 */
	@RequestMapping("/reverse/excel")
	public void downReverse(HttpServletResponse response,@RequestParam Integer tripId) throws IOException {
		if (tripId != null && tripId != 0){
			ReserveVo reserveVo = reserveService.getReserve(tripId);
			Trip trip = tripService.getTripById(tripId);
			if (reserveVo != null && trip != null){
				XSSFWorkbook workbook = new XSSFWorkbook();
				Sheet sheet = workbook.createSheet("行程预定资源");
				Row row = sheet.createRow(0);
				for (int j = 0;j<11;j++){
					row.createCell(j);
				}
				row.getCell(1).setCellValue("订单号：");
				setWidthAndHeight(row,row.getCell(1),sheet,true);
				row.getCell(1).setCellStyle(setBorder(workbook));
				row.getCell(2).setCellValue(trip.getItemNumber());
				setWidthAndHeight(row,row.getCell(2),sheet,true);
				row.getCell(2).setCellStyle(setBorder(workbook));
				row.getCell(3).setCellValue("出行日期：");
				setWidthAndHeight(row,row.getCell(3),sheet,true);
				row.getCell(3).setCellStyle(setBorder(workbook));
				row.getCell(4).setCellValue(dateToString("yyyy-MM-dd HH:mm:ss",trip.getTravel_date()));
				row.getCell(4).setCellStyle(setBorder(workbook));
				setCellRangeAdress(sheet,row.getRowNum(),row.getRowNum(),4,5);
				row.getCell(4).setCellStyle(getCellCenter(workbook));



				int num = 1;
				if(reserveVo.getTicketEntities() !=null && reserveVo.getTicketEntities().size()>0){
					num = num+reserveVo.getTicketEntities().size()+2;//9
					createCellAndRow(1,num,11,sheet,workbook);
					sheet.getRow(2).getCell(1).setCellValue("景点门票");
					setCellRangeAdress(sheet,sheet.getRow(1).getRowNum(),sheet.getRow(1).getRowNum(),0,10);
					setCellRangeAdress(sheet,sheet.getRow(2).getRowNum(),sheet.getRow(2).getRowNum(),1,10);


					sheet.getRow(2).getCell(2).setCellStyle(getCellStyleCenter(workbook));
					setCellValueString(sheet.getRow(3).getCell(1),"景点名称");
					setCellValueString(sheet.getRow(3).getCell(2),"出行人数");
					setCellValueString(sheet.getRow(3).getCell(3),"出行人姓名（全部出行人姓名）");
					setCellValueString(sheet.getRow(3).getCell(4),"人员构成");
					setCellValueString(sheet.getRow(3).getCell(5),"门票使用日期");
					setCellValueString(sheet.getRow(3).getCell(6),"客人门票报价");
					setCellValueString(sheet.getRow(3).getCell(7),"是否预订成功");
					setCellValueString(sheet.getRow(3).getCell(8),"实际门票价格");
					setCellValueString(sheet.getRow(3).getCell(9),"利润");
					setCellValueString(sheet.getRow(3).getCell(10),"取票方式");
					for (int i= 4;i<reserveVo.getTicketEntities().size()+4;i++){
						TicketEntity entity = reserveVo.getTicketEntities().get(i-4);
						setCellValueString(sheet.getRow(i).getCell(1),entity.getName());
						setCellValueString(sheet.getRow(i).getCell(2),String.valueOf(entity.getPnumber()));
						setCellValueString(sheet.getRow(i).getCell(3),entity.getPname());
						setCellValueString(sheet.getRow(i).getCell(4),entity.getPtype());
						setCellValueString(sheet.getRow(i).getCell(5),entity.getPdate()==null?"":
								dateToString("yyyy-MM-dd HH:mm:ss",entity.getPdate()));
						setCellValueString(sheet.getRow(i).getCell(6),entity.getPmoney()==null?"":entity.getPmoney().toString());
						setCellValueString(sheet.getRow(i).getCell(7),entity.getSuccess());
						setCellValueString(sheet.getRow(i).getCell(8),entity.getTruemoney()==null?"":entity.getTruemoney().toString());
						setCellValueString(sheet.getRow(i).getCell(9),entity.getProfit()==null?"":entity.getProfit().toString());
						setCellValueString(sheet.getRow(i).getCell(10),entity.getGetway());
					}
				}
				//车票
				if(reserveVo.getBusTicketEntities() !=null && reserveVo.getBusTicketEntities().size()>0){
					//num = reserveVo.getBusTicketEntities().size()+3;
					if(num != 1){
						num = num + reserveVo.getBusTicketEntities().size()+3;//6
						createCellAndRow(num-reserveVo.getBusTicketEntities().size()-1,num,12,sheet,workbook);
					}else {
						num = num + reserveVo.getBusTicketEntities().size()+2;//5
						createCellAndRow(num-reserveVo.getBusTicketEntities().size(),num,12,sheet,workbook);
					}

					setCellValueString(sheet.getRow(num-reserveVo.getBusTicketEntities().size()-1).getCell(1),"车票");
					setCellRangeAdress(sheet,num-reserveVo.getBusTicketEntities().size()-1,num-reserveVo.
							getBusTicketEntities().size()-1,1,11);
					int rowNum = sheet.getRow(num-reserveVo.getBusTicketEntities().size()).getRowNum();
					setCellValueString(sheet.getRow(rowNum).getCell(1),"出发地");
					setCellValueString(sheet.getRow(rowNum).getCell(2),"目的地");
					setCellValueString(sheet.getRow(rowNum).getCell(3),"出行人姓名（全部出行人姓名）");
					setCellValueString(sheet.getRow(rowNum).getCell(4),"人员构成");
					setCellValueString(sheet.getRow(rowNum).getCell(5),"客人乘车时间（区间）");
					setCellValueString(sheet.getRow(rowNum).getCell(6),"客人取票地点（关西机场T1/J难波车站OCAT）");
					setCellValueString(sheet.getRow(rowNum).getCell(7),"客人取票时间");
					setCellValueString(sheet.getRow(rowNum).getCell(8),"客人门票报价");
					setCellValueString(sheet.getRow(rowNum).getCell(9),"是否预定成功");
					setCellValueString(sheet.getRow(rowNum).getCell(10),"实际门票价格");
					setCellValueString(sheet.getRow(rowNum).getCell(11),"利润");
					for (int i=num-reserveVo.getBusTicketEntities().size()+1;i<=num;i++){
						BusTicketEntity entity = reserveVo.getBusTicketEntities().get(i-(num-reserveVo.getBusTicketEntities().size())-1);
						setCellValueString(sheet.getRow(i).getCell(1),entity.getFrom());
						setCellValueString(sheet.getRow(i).getCell(2),entity.getTowhere());
						setCellValueString(sheet.getRow(i).getCell(3),entity.getPname());
						setCellValueString(sheet.getRow(i).getCell(4),entity.getPtype());
						setCellValueString(sheet.getRow(i).getCell(5),entity.getPsection());
						setCellValueString(sheet.getRow(i).getCell(6),entity.getGetplace());
						setCellValueString(sheet.getRow(i).getCell(7),entity.getGetdate()==null?"":
								dateToString("yyyy-MM-dd HH:mm:ss",entity.getGetdate()));
						setCellValueString(sheet.getRow(i).getCell(8),entity.getPmoney()==null?"":entity.getPmoney().toString());
						setCellValueString(sheet.getRow(i).getCell(9),entity.getSuccess());
						setCellValueString(sheet.getRow(i).getCell(10),entity.getTruemoney()==null?"":entity.getTruemoney().toString());
						setCellValueString(sheet.getRow(i).getCell(11),entity.getProfit()==null?"":entity.getProfit().toString());
					}
				}
				//包车
				if(reserveVo.getCharteredEntities() !=null && reserveVo.getCharteredEntities().size()>0){
					//num = reserveVo.getBusTicketEntities().size()+3;
					if(num != 1){
						num = num + reserveVo.getCharteredEntities().size()+3;//14
						createCellAndRow(num-reserveVo.getCharteredEntities().size()-1,num,18,sheet,workbook);
					}else {
						num = num + reserveVo.getCharteredEntities().size()+2;//5
						createCellAndRow(num-reserveVo.getCharteredEntities().size(),num,18,sheet,workbook);
					}

					setCellValueString(sheet.getRow(num-reserveVo.getCharteredEntities().size()-1).getCell(1),"包车");
					setCellRangeAdress(sheet,num-reserveVo.getCharteredEntities().size()-1,num-reserveVo.
							getCharteredEntities().size()-1,1,18);
					int rowNum = sheet.getRow(num-reserveVo.getCharteredEntities().size()).getRowNum();
					setCellValueString(sheet.getRow(rowNum).getCell(1),"包车类型");
					setCellValueString(sheet.getRow(rowNum).getCell(2),"客人姓名");
					setCellValueString(sheet.getRow(rowNum).getCell(3),"航班号");
					setCellValueString(sheet.getRow(rowNum).getCell(4),"人员构成");
					setCellValueString(sheet.getRow(rowNum).getCell(5),"行程日期，接机日期");
					setCellValueString(sheet.getRow(rowNum).getCell(6),"出发时间，送机时间");
					setCellValueString(sheet.getRow(rowNum).getCell(7),"行李数");
					setCellValueString(sheet.getRow(rowNum).getCell(8),"目的地酒店");
					setCellValueString(sheet.getRow(rowNum).getCell(9),"举牌接机");
					setCellValueString(sheet.getRow(rowNum).getCell(10),"上车地点");
					setCellValueString(sheet.getRow(rowNum).getCell(11),"车型");
					setCellValueString(sheet.getRow(rowNum).getCell(12),"途中停留城市（如市内包车可不填)");
					setCellValueString(sheet.getRow(rowNum).getCell(13),"行程结束城市/目的地");
					setCellValueString(sheet.getRow(rowNum).getCell(14),"结束时间");
					setCellValueString(sheet.getRow(rowNum).getCell(15),"客人报价");
					setCellValueString(sheet.getRow(rowNum).getCell(16),"真实金额");
					setCellValueString(sheet.getRow(rowNum).getCell(17),"利润");

					for (int i=num-reserveVo.getCharteredEntities().size()+1;i<=num;i++){//15
						CharteredEntity entity = reserveVo.getCharteredEntities().get(i-(num-reserveVo.getCharteredEntities().size())-1);
						setCellValueString(sheet.getRow(i).getCell(1),entity.getType());
						setCellValueString(sheet.getRow(i).getCell(2),entity.getPname());
						setCellValueString(sheet.getRow(i).getCell(3),entity.getPlanenumber());
						setCellValueString(sheet.getRow(i).getCell(4),entity.getPtype());
						setCellValueString(sheet.getRow(i).getCell(5),entity.getDate()==null?"无":dateToString
								("yyyy-MM-dd HH:mm:ss",entity.getDate()));
						setCellValueString(sheet.getRow(i).getCell(6),entity.getPdate()==null?"无":dateToString
								("yyyy-MM-dd HH:mm:ss",entity.getPdate()));
						setCellValueString(sheet.getRow(i).getCell(7),entity.getBagnumber());
						setCellValueString(sheet.getRow(i).getCell(8),entity.getTohotel());
						setCellValueString(sheet.getRow(i).getCell(9),entity.getCard());
						setCellValueString(sheet.getRow(i).getCell(10),entity.getCarplace());
						setCellValueString(sheet.getRow(i).getCell(11),entity.getCaretype());
						setCellValueString(sheet.getRow(i).getCell(12),entity.getStopplace());
						setCellValueString(sheet.getRow(i).getCell(13),entity.getEndplace());
						setCellValueString(sheet.getRow(i).getCell(14),entity.getEnddate()==null?"无":dateToString
								("yyyy-MM-dd HH:mm:ss",entity.getEnddate()));
						setCellValueString(sheet.getRow(i).getCell(15),entity.getPmoney()==null?"无":entity.getPmoney().toString());
						setCellValueString(sheet.getRow(i).getCell(16),entity.getTruemoney()==null?"无":entity.getTruemoney().toString());
						setCellValueString(sheet.getRow(i).getCell(17),entity.getProfit()==null?"无":entity.getProfit().toString());
					}
				}

				//其它项目15
				if(reserveVo.getScheduleList() !=null && reserveVo.getScheduleList().size()>0){
					if(num != 1){
						num = num + reserveVo.getScheduleList().size()+3;//6
						createCellAndRow(num-reserveVo.getScheduleList().size()-1,num,11,sheet,workbook);
					}else {
						num = num + reserveVo.getScheduleList().size()+2;//5
						createCellAndRow(num-reserveVo.getScheduleList().size(),num,11,sheet,workbook);
					}

					setCellValueString(sheet.getRow(num-reserveVo.getScheduleList().size()-1).getCell(1),"其他（餐厅、演出及体验活动等）");
					setCellRangeAdress(sheet,num-reserveVo.getScheduleList().size()-1,num-reserveVo.
							getScheduleList().size()-1,1,11);
					int rowNum = sheet.getRow(num-reserveVo.getScheduleList().size()).getRowNum();
					setCellValueString(sheet.getRow(rowNum).getCell(1),"出行人姓名");
					setCellValueString(sheet.getRow(rowNum).getCell(2),"预定项目");
					setCellValueString(sheet.getRow(rowNum).getCell(3),"预定时间");
					setCellValueString(sheet.getRow(rowNum).getCell(4),"客人报价");
					setCellValueString(sheet.getRow(rowNum).getCell(5),"是否预定成功");
					setCellValueString(sheet.getRow(rowNum).getCell(6),"实际价格");
					setCellValueString(sheet.getRow(rowNum).getCell(7),"利润");
					for (int i=num-reserveVo.getScheduleList().size()+1;i<=num;i++){
						Schedule entity = reserveVo.getScheduleList().get(i-(num-reserveVo.getScheduleList().size())-1);
						setCellValueString(sheet.getRow(i).getCell(1),entity.getName());
						setCellValueString(sheet.getRow(i).getCell(2),entity.getScheduledproject());
						setCellValueString(sheet.getRow(i).getCell(3),entity.getScheduledate()==null?"":dateToString
								("yyyy-MM-dd HH:mm:ss",entity.getScheduledate()));
						setCellValueString(sheet.getRow(i).getCell(4),entity.getPmoney()==null?"无":entity.getPmoney().toString());
						setCellValueString(sheet.getRow(i).getCell(5),entity.getSuccess());
						setCellValueString(sheet.getRow(i).getCell(6),entity.getTruemoney()==null?"无":entity.getTruemoney().toString());
						setCellValueString(sheet.getRow(i).getCell(7),entity.getProfit()==null?"无":entity.getProfit().toString());
					}
				}




				response.setHeader("Content-disposition",
						"attachment; filename=" + new String("行程单资源预定".getBytes("gb2312"), "ISO-8859-1") + ".xlsx");
				response.setContentType("application/msexcel");// 定义输出类型
				workbook.write(response.getOutputStream());
			}
		}
	}

	/**
	 * 合并单元格通用方法
	 * @param sheet
	 * @param rowStart
	 * @param rowEnd
	 * @param cellStart
	 * @param cellEnd
	 */
	private void setCellRangeAdress(Sheet sheet,int rowStart,int rowEnd,int cellStart,int cellEnd){
		CellRangeAddress cellRangeAddress = new CellRangeAddress(rowStart,rowEnd, cellStart, cellEnd);
		sheet.addMergedRegion(cellRangeAddress);
	}

	private void setCellValueString(Cell cell,String value){
		cell.setCellValue(value);
	}

	private void createCellAndRow(int start,int size,int cellEnd,Sheet sheet,Workbook workbook){
		for (int i = start; i <= size; i++) {
			Row row = sheet.createRow(i);
			for (int j =0;j<cellEnd;j++){
				row.createCell(j);
				if(j!=0) {
					row.getCell(j).setCellStyle(getCellCenter((XSSFWorkbook) workbook));
					setWidthAndHeight(row, row.getCell(j), sheet, false);
				}else {
					sheet.setColumnWidth((short) row.getCell(j).getColumnIndex(), (short) 200);
				}
			}
		}
	}
	/**
	 * 获取Excel对象的通用方法
	 * @param tripId
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private Workbook getWorkBook(Integer tripId) throws FileNotFoundException, IOException {
		Trip trip = getTripForCommon(tripId);
		List<Plane> planeTo = tripService.getPlaneTo(tripId);
		List<Plane> planeLnad = tripService.getPlaneLand(tripId);
		List<Plane> planeBack = tripService.getPlaneBack(tripId);
		//TODO
		XSSFWorkbook workBook = new XSSFWorkbook(new FileInputStream(new File(modelPath)));
		XSSFSheet sheet = workBook.getSheetAt(0);
		String cellValue = ExcelController.getStringValueFromCell(sheet.getRow(1).getCell(1));
		cellValue = cellValue.trim().substring(0, cellValue.indexOf(" "))+" "+
				trip.getCustomer_name()+cellValue.substring(cellValue.lastIndexOf(" "));
		sheet.getRow(1).getCell(1).setCellValue(cellValue);
		if(planeTo.size()+planeLnad.size()+planeBack.size()==0) {
			XSSFRow row = sheet.createRow(6);
			for(int j =1;j<=13;j++) {
				row.createCell(j);
			}
			row.getCell(1).setCellValue("无航班信息");
		}else {
			for(int i=6;i<(planeTo.size()+planeLnad.size()+planeBack.size()+6);i++) {
				XSSFRow row = sheet.createRow(i);
				for(int j =0;j<=13;j++) {
					row.createCell(j);
				}
			}
			sheet.getRow(6).getCell(1).setCellValue("航班信息");
			if(planeTo.size()>0) {
				sheet.getRow(6).getCell(2).setCellValue("去程：");
				for(int i=0;i<planeTo.size();i++) {
					sheet.getRow(i+6).getCell(3).setCellValue(dateToString("MM",planeTo.get(i).getTo_date())+"月");
					sheet.getRow(i+6).getCell(4).setCellValue(dateToString("dd",planeTo.get(i).getTo_date())+"日");
					sheet.getRow(i+6).getCell(5).setCellValue(planeTo.get(i).getTo_number());
					sheet.getRow(i+6).getCell(6).setCellValue(planeTo.get(i).getTo_desc());
					sheet.getRow(i+6).getCell(8).setCellValue(planeTo.get(i).getTo_start_end());
					sheet.addMergedRegion(new CellRangeAddress(i+6, i+6, 6, 7));
					sheet.addMergedRegion(new CellRangeAddress(i+6, i+6, 8, 13));
				}
				if(planeTo.size()>1) {
					sheet.addMergedRegion(new CellRangeAddress(6,6+planeTo.size()-1,2,2));
				}
				
			}
			if(planeLnad.size()>0) {
				sheet.getRow(6+planeTo.size()).getCell(2).setCellValue("内陆段：");
				for(int i=0;i<planeLnad.size();i++) {
					sheet.getRow(i+6+planeTo.size()).getCell(3).setCellValue(dateToString("MM",planeLnad.get(i).getLand_date())+"月");
					sheet.getRow(i+6+planeTo.size()).getCell(4).setCellValue(dateToString("dd",planeLnad.get(i).getLand_date())+"日");
					sheet.getRow(i+6+planeTo.size()).getCell(5).setCellValue(planeLnad.get(i).getLand_number());
					sheet.getRow(i+6+planeTo.size()).getCell(6).setCellValue(planeLnad.get(i).getLand_desc());
					sheet.getRow(i+6+planeTo.size()).getCell(8).setCellValue(planeLnad.get(i).getLand_start_end());
					sheet.addMergedRegion(new CellRangeAddress(i+6+planeTo.size(), i+6+planeTo.size(), 6, 7));
					sheet.addMergedRegion(new CellRangeAddress(i+6+planeTo.size(), i+6+planeTo.size(), 8, 13));
				}
				if(planeLnad.size()>1) {
					sheet.addMergedRegion(new CellRangeAddress(6+planeTo.size(),6+planeTo.size()+planeLnad.size()-1,2,2));
				}
			}

			if(planeBack.size()>0) {
				sheet.getRow(6+planeTo.size()+planeLnad.size()).getCell(2).setCellValue("返程：");
				for(int i=0;i<planeBack.size();i++) {
					sheet.getRow(i+6+planeTo.size()+planeLnad.size()).getCell(3).setCellValue(dateToString("MM",planeBack.get(i).getBack_date())+"月");
					sheet.getRow(i+6+planeTo.size()+planeLnad.size()).getCell(4).setCellValue(dateToString("dd",planeBack.get(i).getBack_date())+"日");
					sheet.getRow(i+6+planeTo.size()+planeLnad.size()).getCell(5).setCellValue(planeBack.get(i).getBack_number());
					sheet.getRow(i+6+planeTo.size()+planeLnad.size()).getCell(6).setCellValue(planeBack.get(i).getBack_desc());
					sheet.getRow(i+6+planeTo.size()+planeLnad.size()).getCell(8).setCellValue(planeBack.get(i).getBack_start_end());
					sheet.addMergedRegion(new CellRangeAddress(i+6+planeTo.size()+planeLnad.size(), i+6+planeTo.size()+planeLnad.size(), 6, 7));
					sheet.addMergedRegion(new CellRangeAddress(i+6+planeTo.size()+planeLnad.size(), i+6+planeTo.size()+planeLnad.size(), 8, 13));
				}
				if(planeBack.size()>1) {
					sheet.addMergedRegion(new CellRangeAddress(6+planeTo.size()+planeLnad.size(),6+planeTo.size()
						+planeLnad.size()+planeBack.size()-1,2,2));
				}
			}
			if(planeTo.size()+planeLnad.size()+planeBack.size()>1) {
				sheet.addMergedRegion(new CellRangeAddress(6,6+planeTo.size()+planeBack.size()+planeLnad.size()-1,1,1));
			}
			
		}
		Row rowEnd = sheet.createRow(6+planeTo.size()+planeLnad.size()+planeBack.size());
		for (int i = 1; i <=13; i++) {
			rowEnd.createCell(i);
		}
		sheet.addMergedRegion(new CellRangeAddress(rowEnd.getRowNum(),rowEnd.getRowNum(),1,13));
		XSSFFont font = workBook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);
		font.setBold(true);
		XSSFRichTextString str = new XSSFRichTextString("温馨提示："
				+ "\r\n国际航班办理登机及过安检与海关的时间较长，建议您至少于航班起飞时间前3小时到达机场，并自行办理值机，以免耽误行程。");
		str.applyFont(0,5,font);
		rowEnd.setHeightInPoints(30);
		rowEnd.getCell(1).setCellValue(str);
		XSSFCellStyle style = getCellStyle(workBook);
		style.setAlignment(HorizontalAlignment.CENTER);
		rowEnd.getCell(1).setCellStyle(style);
		sheet.createRow(rowEnd.getRowNum()+1);
		Row rowEN = sheet.createRow(rowEnd.getRowNum()+2);
		Row rowENN = sheet.createRow(rowEnd.getRowNum()+3);
		for(int i=1;i<=13;i++) {
			rowEN.createCell(i);
			rowENN.createCell(i);
		}
		sheet.addMergedRegion(new CellRangeAddress(rowEN.getRowNum(), rowEN.getRowNum(), 1, 3));
		rowEN.getCell(1).setCellValue("行程安排：");
		rowEN.getCell(1).setCellStyle(getCellStyleCenter(workBook));
		sheet.addMergedRegion(new CellRangeAddress(rowEN.getRowNum(), rowEN.getRowNum(), 4, 13));
		sheet.addMergedRegion(new CellRangeAddress(rowENN.getRowNum(), rowENN.getRowNum(), 1, 3));
		rowENN.getCell(1).setCellValue("日期");
		rowENN.getCell(1).setCellStyle(getCellStyleCenter(workBook));
		sheet.addMergedRegion(new CellRangeAddress(rowENN.getRowNum(), rowENN.getRowNum(), 4, 7));
		rowENN.getCell(4).setCellValue("参考行程");
		rowENN.getCell(4).setCellStyle(getCellStyleCenter(workBook));
		sheet.addMergedRegion(new CellRangeAddress(rowENN.getRowNum(), rowENN.getRowNum(), 8, 13));
		rowENN.getCell(8).setCellValue("注意事项(其它类目)");
		rowENN.getCell(8).setCellStyle(getCellStyleCenter(workBook));
		int m = rowEnd.getRowNum()+4;
		for(int i=0;i<trip.getTripItems().size();i++) {
			XSSFRow row = null;
			for(int j=0;j<=5;j++) {
				row = sheet.createRow(j+m);
				if(j==0) {
					row.setHeightInPoints(200);
				}
				for(int k=0;k<=13;k++) {
					row.createCell(k);
					if(k>=4) {
						sheet.setColumnWidth(k, 15*256);
					}
				}
			}
			sheet.addMergedRegion(new CellRangeAddress(row.getRowNum()-5, row.getRowNum(), 4, 7));
			sheet.addMergedRegion(new CellRangeAddress(row.getRowNum()-5, row.getRowNum(), 8, 13));
			sheet.addMergedRegion(new CellRangeAddress(row.getRowNum()-5, row.getRowNum(), 1, 1));
			sheet.addMergedRegion(new CellRangeAddress(row.getRowNum()-5, row.getRowNum(), 0, 0));
			sheet.addMergedRegion(new CellRangeAddress(row.getRowNum()-5, row.getRowNum(), 2, 2));
			sheet.addMergedRegion(new CellRangeAddress(row.getRowNum()-5, row.getRowNum(), 3, 3));
			sheet.getRow(row.getRowNum()-5).getCell(1).setCellValue("DAY"+trip.getTripItems().get(i).getDay());
			sheet.getRow(row.getRowNum()-5).getCell(2).setCellValue(ExcelController.
					dateToString("MM",trip.getTravel_date())+"月");
			sheet.getRow(row.getRowNum()-5).getCell(3).setCellValue(ExcelController.
					dateToString("dd",trip.getTravel_date())+"日");
			String s = "";
			for(TripItemDescription desc:trip.getTripItems().get(i).getItemDescriptions()) {
				s+=desc.getDescription()+"\r\n";
			}
			sheet.getRow(row.getRowNum()-5).getCell(4).setCellStyle(getCellStyle(workBook));
			sheet.getRow(row.getRowNum()-5).getCell(4).setCellValue(new XSSFRichTextString(s));
			
			ExcelController.setCellStyle(sheet.getRow(row.getRowNum()-5).getCell(4), workBook);
			m +=6;
		}
		for(int i=6;i<planeBack.size()+planeLnad.size()+planeTo.size()+6;i++) {
			Row row = sheet.getRow(i);
			for(int j=1;j<=13;j++) {
				row.getCell(j).setCellStyle(getCellCenter(workBook));
			}
		}
		sheet.getRow(5).getCell(1).setCellValue(trip.getItemNumber());
		sheet.getRow(5).getCell(1).setCellStyle(getCellCenter(workBook));
		return workBook;
	}
	
	
	/**
	 * 以指定格式转换日期类型
	 * @param pattern
	 * @param date
	 * @return
	 */
	private static String dateToString(String pattern,Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String result = " ";
		try {
			if(date==null){
				return result;
			}else {
				result = dateFormat.format(date);
			}
		}catch (Exception e){
			return " ";
		}
		return result;
	}
	
	/**
	 * 获取条目的信息
	 * @param tripId
	 * @return
	 */
	private Trip getTripForCommon(Integer tripId) {
		Trip trip = tripService.getTripById(tripId);
		if (trip!=null) {
			List<TripItemVo> itemList = tripService.getTripItemById(trip.getId());
			for (TripItemVo item : itemList) {
				List<TripItemDescription> desc = tripService.getTripItemDescById(item.getId());
				item.setItemDescriptions(desc);
			}
			trip.setTripItems(itemList);
		}
		return trip;
	}

	/**
	 * 获取单元格转为字符串
	 * @param cell
	 * @return
	 */
	@SuppressWarnings("all")
	public static String getStringValueFromCell(Cell cell) {
		SimpleDateFormat sFormat = new SimpleDateFormat("MM/dd/yyyy");
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			cellValue = cell.getStringCellValue();
		}

		else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Date date = HSSFDateUtil.getJavaDate(d);
				cellValue = sFormat.format(date);
			} else {
				cellValue = decimalFormat.format((cell.getNumericCellValue()));
			}
		} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			cellValue = "";
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			cellValue = String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
			cellValue = "";
		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			cellValue = cell.getCellFormula().toString();
		}
		return cellValue;
	}
	
	/**
	 * 设置单元格样式
	 * @param cell
	 * @param wb
	 */
	public static void setCellStyle(Cell cell,XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 10);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.JUSTIFY);
		style.setVerticalAlignment(VerticalAlignment.JUSTIFY);
		cell.setCellStyle(style);
	}
	
	/**
	 * 获得强制换行的字体样式对象
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle getCellStyle(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle();
		style.setWrapText(true);
		return style;
	}
	
	/**
	 * 获取字体样式加粗，居中的样式对象
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle getCellStyleCenter(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle();
		XSSFFont font = wb.createFont();
		font.setBold(true);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		return style;
	}

	/**
	 * 字体居中样式
	 * @param wb
	 * @return
	 */
	public XSSFCellStyle getCellCenter(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle();
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}

	public XSSFCellStyle setBorder(XSSFWorkbook wb) {
		XSSFCellStyle style = wb.createCellStyle();
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderBottom(BorderStyle.THIN);
		return style;
	}

	/**
	 * 设置行高宽
	 * @param row
	 * @param cell
	 * @param sheet
	 * @param flag
	 */
	private void setWidthAndHeight(Row row,Cell cell,Sheet sheet,boolean flag){
		if(flag == true){
			row.setHeight((short) 500);
		}
		sheet.setColumnWidth((short) cell.getColumnIndex(), (short) 250*20);
	}
}
