package com.ss.controller;

import com.ss.pojo.POIDatePojo;
import com.ss.pojo.User;
import com.ss.pojo.vo.ApplyReimbursement;
import com.ss.service.ApplyReimbursementService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class POIController {
	
	@Autowired
	private ApplyReimbursementService applyService;
	
	@RequestMapping("/createExcel")
	public void createExcel(HttpServletRequest request,HttpServletResponse response,POIDatePojo datePojo) throws IOException {
		 response.setHeader("Content-disposition", "attachment; filename="+new String("报销申请单".getBytes("gb2312"),"ISO-8859-1")+".xls");   
	     response.setContentType("application/msexcel");// 定义输出类型 
	     HSSFWorkbook wb = new HSSFWorkbook();
	     datePojo.setStatus("已付款");
	     User user = (User) request.getSession().getAttribute("user");
	     datePojo.setUserId(user.getId());
	     //设置字体
	     Sheet sheet = wb.createSheet("报销");
	     Row row0 = sheet.createRow(0);
	     //this.setBorder(wb, row0,0,7);
	     Row row1 = sheet.createRow(1);
	     for(int i=0;i<=7;i++) {
	    	 row1.createCell(i);
	     }
	     for(Cell cell : row1) {
	    	 this.setHeadBorder(wb, cell);
	     }
	     Cell cell0 =row0.createCell(0);
	     cell0.setCellValue("报销申请单");
	     row0.createCell(1);
	     row0.createCell(2);
	     row0.createCell(3);
	     row0.createCell(4);
	     row0.createCell(5);
	     row0.createCell(6);
	     row0.createCell(7);
	     for(Cell cell : row0) {
	    	 this.setHeadBorder(wb, cell);
	     }
	     CellRangeAddress rangeAddress = new CellRangeAddress(0, 1, 0, 7);
	     sheet.addMergedRegion(rangeAddress);
	     List<ApplyReimbursement> applyReimbursements =  applyService.selectByGroupWithNoJudyForExcel(datePojo);
	     if(applyReimbursements.size()>0) {
		     Row row2  = sheet.createRow(2);
		     Cell cell21 = row2.createCell(0);
		     cell21.setCellValue("申请人：");
		     row2.createCell(1);
		     row2.createCell(2).setCellValue(applyReimbursements.get(0).getNickName());
		     row2.createCell(3);
		     row2.createCell(4).setCellValue("所属部门：");;
		     row2.createCell(5);
		     row2.createCell(6).setCellValue(applyReimbursements.get(0).getDepartment());
		     row2.createCell(7);
		     for(Cell cell : row2) {
		    	 this.setBorder(wb, cell);
		     }
		     sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));
		     sheet.addMergedRegion(new CellRangeAddress(2,2,2,3));
		     sheet.addMergedRegion(new CellRangeAddress(2,2,4,5));
		     sheet.addMergedRegion(new CellRangeAddress(2,2,6,7));
		     Row row3 = sheet.createRow(3);
		     row3.createCell(0).setCellValue("日期：");
		     row3.createCell(1);
		     row3.createCell(2).setCellValue(this.dateFormat(datePojo.getStartDate())+" 至   "+this.dateFormat(datePojo.getEndDate()));
		     row3.createCell(3);
		     row3.createCell(4);
		     row3.createCell(5);
		     row3.createCell(6);
		     row3.createCell(7);
		     for(Cell cell : row3) {
		    	this.setBorder(wb, cell); 
		     }
		     sheet.addMergedRegion(new CellRangeAddress(3,3,0,1));
		     sheet.addMergedRegion(new CellRangeAddress(3,3,2,7));
		     Row row4 = sheet.createRow(4);
		     row4.createCell(0).setCellValue("申请日期：");
		     row4.createCell(1);
		     row4.createCell(2);
		     row4.createCell(3).setCellValue(this.dateFormat(datePojo.getEndDate()));
		     row4.createCell(4);
		     row4.createCell(5);
		     row4.createCell(6);
		     row4.createCell(7);
		     for(Cell cell : row4) {
		    	 this.setBorder(wb, cell);
		     }
		     sheet.addMergedRegion(new CellRangeAddress(4,4,0,2));
		     sheet.addMergedRegion(new CellRangeAddress(4,4,3,7));
		     Row row5 = sheet.createRow(5);
		     row5.createCell(0).setCellValue("日期");
		     row5.createCell(1);
		     row5.createCell(2).setCellValue("项目");
		     row5.createCell(3);
		     row5.createCell(4).setCellValue("金额");
		     row5.createCell(5);
		     row5.createCell(6).setCellValue("凭证");
		     row5.createCell(7);
		     for(Cell cell:row5) {
		    	 this.initColumnCenterstyle(wb, cell);
		     }
		     sheet.addMergedRegion(new CellRangeAddress(5,5,0,1));
		     sheet.addMergedRegion(new CellRangeAddress(5,5,2,3));
		     sheet.addMergedRegion(new CellRangeAddress(5,5,4,5));
		     sheet.addMergedRegion(new CellRangeAddress(5,5,6,7));
		     for(int i=0;i<applyReimbursements.size();i++) {
		    	 Row row = sheet.createRow(i+6);
		    	 row.createCell(0).setCellValue(applyReimbursements.get(i).getApplyTimeStr());
		    	 row.createCell(1);
		    	 row.createCell(2).setCellValue(applyReimbursements.get(i).getEvent());
		    	 row.createCell(3);
		    	 row.createCell(4).setCellValue(this.numberFormat(applyReimbursements.get(i).getCost()));
		    	 row.createCell(5);
		    	 row.createCell(6);
		    	 row.createCell(7);
		    	 for(Cell cell : row) {
		    		 this.setBorder(wb, cell);
		    	 }
		    	 sheet.addMergedRegion(new CellRangeAddress(i+6,i+6,0,1));
		    	 sheet.addMergedRegion(new CellRangeAddress(i+6,i+6,2,3));
		    	 sheet.addMergedRegion(new CellRangeAddress(i+6,i+6,4,5));
		    	 sheet.addMergedRegion(new CellRangeAddress(i+6,i+6,6,7));
		     }
	     }
		     wb.write(response.getOutputStream());
		   
	}
	
	@RequestMapping("/getTermExcel")
	public void getAllByTerm(HttpServletResponse response,POIDatePojo datePojo) {
		 try {
			response.setHeader("Content-disposition", "attachment; filename="+new String("报销申请单".getBytes("gb2312"),"ISO-8859-1")+".xls");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}   
		 datePojo.setStatus("已付款");
	     response.setContentType("application/msexcel");// 定义输出类型 
	     HSSFWorkbook wb = new HSSFWorkbook();
	     Sheet sheet = wb.createSheet("报销单");
	     Row row0 = sheet.createRow(0);
	     Row row1 = sheet.createRow(1);
	     for(int i=0;i<=5;i++) {
	    	 row1.createCell(i);
	     }
	     for(Cell cell : row1) {
	    	 this.setHeadBorder(wb, cell);
	     }
	     Cell cell0 =row0.createCell(0);
	     cell0.setCellValue("员工月份报销申请单");
	     row0.createCell(1);
	     row0.createCell(2);
	     row0.createCell(3);
	     row0.createCell(4);
	     row0.createCell(5);
	     for(Cell cell : row0) {
	    	 this.setHeadBorder(wb, cell);
	     }
	     CellRangeAddress rangeAddress = new CellRangeAddress(0, 1, 0, 5);
	     sheet.addMergedRegion(rangeAddress);
	     List<ApplyReimbursement> aList = applyService.selectByGroupWithMonth(datePojo);
	     if(aList !=null && aList.size()>0) {
	    	 Row row2  = sheet.createRow(2);
		     Cell cell21 = row2.createCell(0);
		     cell21.setCellValue("申请人：");
		     row2.createCell(1);
		     row2.createCell(2);
		     row2.createCell(3).setCellValue("所有员工");
		     row2.createCell(4);
		     row2.createCell(5);
		     for(Cell cell : row2) {
		    	 this.setBorder(wb, cell);
		     }
		     sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 2));
		     sheet.addMergedRegion(new CellRangeAddress(2,2,3,5));
		     Row row3 = sheet.createRow(3);
		     row3.createCell(0).setCellValue("日期：");
		     row3.createCell(1);
		     row3.createCell(2);
		     row3.createCell(3).setCellValue(this.dateFormat(datePojo.getStartDate())+" 至   "+this.dateFormat(datePojo.getEndDate()));;
		     row3.createCell(4);
		     row3.createCell(5);
		     for(Cell cell : row3) {
		    	this.setBorder(wb, cell); 
		     }
		     sheet.addMergedRegion(new CellRangeAddress(3,3,0,1));
		     sheet.addMergedRegion(new CellRangeAddress(3,3,2,5));
		     Row row4 = sheet.createRow(4);
		     row4.createCell(0).setCellValue("申请日期：");
		     row4.createCell(1);
		     row4.createCell(2);
		     row4.createCell(3).setCellValue(this.dateFormat(datePojo.getEndDate()));
		     row4.createCell(4);
		     row4.createCell(5);
		     for(Cell cell : row4) {
		    	 this.setBorder(wb, cell);
		     }
		     sheet.addMergedRegion(new CellRangeAddress(4,4,0,2));
		     sheet.addMergedRegion(new CellRangeAddress(4,4,3,5));
		     Row row5 = sheet.createRow(5);
		     row5.createCell(0).setCellValue("申请人");
		     row5.createCell(1);
		     row5.createCell(2).setCellValue("总金额");
		     row5.createCell(3);
		     row5.createCell(4).setCellValue("凭证");
		     row5.createCell(5);
		     for(Cell cell:row5) {
		    	 this.initColumnCenterstyle(wb, cell);
		     }
		     sheet.addMergedRegion(new CellRangeAddress(5,5,0,1));
		     sheet.addMergedRegion(new CellRangeAddress(5,5,2,3));
		     sheet.addMergedRegion(new CellRangeAddress(5,5,4,5));
		     for(int i = 0;i<aList.size();i++) {
		    	 Row row = sheet.createRow(i+6);
		    	 row.createCell(0).setCellValue(aList.get(i).getNickName());
		    	 row.createCell(1);
		    	 row.createCell(2).setCellValue(this.numberFormat(aList.get(i).getCost()));
		    	 row.createCell(3);
		    	 row.createCell(4);
		    	 row.createCell(5);
		    	 for(Cell cell : row) {
		    		 this.setBorder(wb, cell);
		    	 }
		    	 sheet.addMergedRegion(new CellRangeAddress(i+6,i+6,0,1));
		    	 sheet.addMergedRegion(new CellRangeAddress(i+6,i+6,2,3));
		    	 sheet.addMergedRegion(new CellRangeAddress(i+6,i+6,4,5));
		     }
	     }
	     try {
			wb.write(response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setBorder(Workbook wb,Cell cell) {
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
     * <b>@param wb 
     * <b>@return</b> 
     */  
    public  void initColumnCenterstyle(Workbook wb,Cell cell) {  
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
        style.setVerticalAlignment(VerticalAlignment.CENTER);;// 上下居中  
        style.setWrapText(true);  
        style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．  
        cell.setCellStyle(style);
    }  
    
    private void setHeadBorder(Workbook wb,Cell cell) {
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
        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);;// 左右居中  
        style.setVerticalAlignment(VerticalAlignment.CENTER);;// 上下居中  
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
