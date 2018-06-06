package com.ss.controller;

import com.ss.pojo.ModelResult;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class ExpenditureController {
	
	@RequestMapping("/expenditure")
	@ResponseBody
	public ModelResult getExpenditureExcel(@RequestParam MultipartFile file) throws IOException {
		Workbook wb;
		if(file != null) {
			if(file.getOriginalFilename().endsWith(".xls")) {
				wb = new HSSFWorkbook(file.getInputStream());
			}else {
				wb = new XSSFWorkbook(file.getInputStream());
			}
			Sheet sheet = wb.getSheetAt(0);
			for(int i = 0;i<sheet.getLastRowNum();i++) {
				Row row = sheet.getRow(i);
				for(int j = 0;j< row.getLastCellNum();j++) {
					/*String val  = row.getCell(j).getStringCellValue();
					System.out.println(val);*/
				}
			}
		}
		return ModelResult.buildOk("上传成功");
	}
	
	//判断后缀为xlsx的excel文件的数据类  
    @SuppressWarnings("deprecation")  
    private static String getValue(XSSFCell xssfRow) {  
        if(xssfRow==null){  
            return "---";  
        }  
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {  
            return String.valueOf(xssfRow.getBooleanCellValue());  
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {  
            double cur=xssfRow.getNumericCellValue();  
            long longVal = Math.round(cur);  
            Object inputValue = null;  
            if(Double.parseDouble(longVal + ".0") == cur)    
                    inputValue = longVal;    
            else    
                    inputValue = cur;   
            return String.valueOf(inputValue);  
        } else if(xssfRow.getCellType() == xssfRow.CELL_TYPE_BLANK || xssfRow.getCellType() == xssfRow.CELL_TYPE_ERROR){  
            return "---";  
        }  
        else {  
            return String.valueOf(xssfRow.getStringCellValue());  
        }  
    }  
      
    //判断后缀为xls的excel文件的数据类型  
    @SuppressWarnings("deprecation")  
    private static String getValue(HSSFCell hssfCell) {  
        if(hssfCell==null){  
            return "---";  
        }  
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {  
            return String.valueOf(hssfCell.getBooleanCellValue());  
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {  
            double cur=hssfCell.getNumericCellValue();  
            long longVal = Math.round(cur);  
            Object inputValue = null;  
            if(Double.parseDouble(longVal + ".0") == cur)    
                    inputValue = longVal;    
            else    
                    inputValue = cur;   
            return String.valueOf(inputValue);  
        } else if(hssfCell.getCellType() == hssfCell.CELL_TYPE_BLANK || hssfCell.getCellType() == hssfCell.CELL_TYPE_ERROR){  
            return "---";  
        }  
        else {  
            return String.valueOf(hssfCell.getStringCellValue());  
        }  
    }  
      
  //字符串修剪  去除所有空白符号 ， 问号 ， 中文空格  
    static private String Trim_str(String str){  
        if(str==null)  
            return null;  
        return str.replaceAll("[\\s\\?]", "").replace("　", "");  
    }  

}
