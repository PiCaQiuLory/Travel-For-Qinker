package com.ss.controller;

import com.ss.mapper.CityDao;
import com.ss.pojo.*;
import com.ss.pojo.trip.City;
import com.ss.pojo.trip.Country;
import com.ss.pojo.trip.States;
import com.ss.pojo.vo.*;
import com.ss.service.AttachmentService;
import com.ss.service.IUiService;
import com.ss.service.IUserService;
import com.ss.utils.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by stella on 2017/9/12.
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final String ORDER_DATA_EXCEL_FILE_PATH = "data_export/ORDER_DATA.xlsx";
    private final String ZHICHU_EXCEL_FILE_PATH = "data_export/ZHICHU_DATA.xlsx";
    private final String COST_EXCEL_FILE_PATH = "data_export/COST_DATA.xlsx";
    private final String JIESUAN_EXCEL_FILE_PATH = "data_export/JIESUAN.xlsx";
    private final String ORDER_REPORT_EXCEL_FILE_PATH = "data_export/ORDER_REPORT.xlsx";

    @Resource
    private CityDao cityDao;
    @Resource
    private IUserService userService;
    @Resource
    private IUiService uiService;
    @Resource
    private AttachmentService attachmentService;


    @RequestMapping(value = "/export/jiesuan/{orderId}", method = RequestMethod.GET)
    public void exportJiesuan(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable("orderId") long orderId){
        OrderItem search = new OrderItem();
        search.setOrderId(orderId);
        List<OrderItem> itemsDb = uiService.queryOrderItem(search);
        OrderCostItem searchCost = new OrderCostItem();
        searchCost.setOrderId(orderId);
        List<OrderCostItem> costItems = uiService.queryOrderCostItem(searchCost);
        SearchOrderVo orderCheck = new SearchOrderVo();
        orderCheck.setId(Integer.valueOf((int) orderId));
        List<Order> orderChecks = uiService.queryOrders(orderCheck);
        if (orderChecks==null || orderChecks.size()!=1){
            return;
        }
        Order order = orderChecks.get(0);

        List<Provider> providers = uiService.queryProvider(null);
        HashMap<Long, Provider> providerHashMap = new HashMap<>();
        providers.forEach(provider -> providerHashMap.put(provider.getId(), provider));
        OutputStream fOut = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            String codedFileName = "结算单";
            codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(JIESUAN_EXCEL_FILE_PATH);

            XSSFWorkbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            sheet.getRow(1).getCell(1).setCellValue(order.getManName());
            sheet.getRow(1).getCell(3).setCellValue(order.getItemNumber());
            sheet.getRow(1).getCell(5).setCellValue(order.getDeparture());
            sheet.getRow(1).getCell(7).setCellValue(order.getUserNum());
            sheet.getRow(2).getCell(1).setCellValue(order.getManFlat());
            if (order.getOutDate()!=null) {
                sheet.getRow(2).getCell(3).setCellValue(DateUtils.formatDate(order.getOutDate(), "yyyy-MM-dd"));
            }
            sheet.getRow(2).getCell(5).setCellValue(order.getDestination());
            sheet.getRow(2).getCell(7).setCellValue(OrderStatus.valueOf(order.getStatus()).getName());


            if (itemsDb.size()>0) {
                double cost = 0;
                double actual = 0;
                double custo = 0;
                int startRow = 5;
                sheet.shiftRows(startRow, sheet.getLastRowNum(), itemsDb.size());
                for (int i = 0; i < itemsDb.size(); i++) {
                    Row sourceRow = null;
                    Row targetRow = null;
                    Cell sourceCell = null;
                    Cell targetCell = null;
                    targetRow = sheet.createRow(startRow + i);
                    sourceRow = sheet.getRow(startRow+itemsDb.size());
                    targetRow.setHeight(sourceRow.getHeight());

                    for (int m = 0; m < 8; m++) {
                        targetCell = targetRow.createCell(m);
                        sourceCell = sourceRow.getCell(m);
                        targetCell.setCellType(sourceCell.getCellType());
                        targetCell.setCellStyle(sourceCell.getCellStyle());
                    }
                }
                for (OrderItem item : itemsDb) {
                    if (item.getType()!=null) {
                        sheet.getRow(startRow).getCell(0).setCellValue(ItemType.valueOf(item.getType()).getName());
                    }
                    sheet.getRow(startRow).getCell(1).setCellValue(item.getCategory());
                    if (item.getProviderId()>0) {
                        sheet.getRow(startRow).getCell(2).setCellValue(providerHashMap.get(item.getProviderId()).getName());
                    }
                    if (item.getUseDate()!=null) {
                        sheet.getRow(startRow).getCell(3).setCellValue(DateUtils.formatDate(item.getUseDate(), "yyyy-MM-dd"));
                    }
                    sheet.getRow(startRow).getCell(4).setCellValue(item.getFapiao());
                    sheet.getRow(startRow).getCell(5).setCellValue(item.getOrderNo());
                    sheet.getRow(startRow).getCell(6).setCellValue(item.getCost());
                    sheet.getRow(startRow).getCell(7).setCellValue(item.getPaystatus());
                    cost += item.getCost();
                    startRow++;
                }
                startRow += 3;
                sheet.shiftRows(startRow, sheet.getLastRowNum(), costItems.size());
                for (int i = 0; i < costItems.size(); i++) {
                    Row sourceRow = null;
                    Row targetRow = null;
                    Cell sourceCell = null;
                    Cell targetCell = null;
                    targetRow = sheet.createRow(startRow + i);
                    sourceRow = sheet.getRow(startRow+costItems.size());
                    targetRow.setHeight(sourceRow.getHeight());

                    for (int m = 0; m < 8; m++) {
                        targetCell = targetRow.createCell(m);
                        sourceCell = sourceRow.getCell(m);
                        targetCell.setCellType(sourceCell.getCellType());
                        targetCell.setCellStyle(sourceCell.getCellStyle());
                    }
                }
                for (OrderCostItem item : costItems) {
                    if (item.getType()!=null) {
                        sheet.getRow(startRow).getCell(0).setCellValue(ItemType.valueOf(item.getType()).getName());
                        if (item.getType().equals(ItemType.actual.toString())){
                            actual += item.getCost();
                        }
                        if (item.getType().equals(ItemType.custo.toString())){
                            custo += item.getCost();
                        }
                    }
                    sheet.getRow(startRow).getCell(1).setCellValue(item.getCategory());
                    sheet.getRow(startRow).getCell(2).setCellValue(item.getNote());
                    if (item.getUseDate()!=null) {
                        sheet.getRow(startRow).getCell(3).setCellValue(DateUtils.formatDate(item.getUseDate(), "yyyy-MM-dd"));
                    }
                    sheet.getRow(startRow).getCell(6).setCellValue(item.getCost());
//                    cost += item.getCost();
                    startRow++;
                }

                sheet.getRow(startRow+1).getCell(1).setCellValue(order.getIncomeNew());
                sheet.getRow(startRow+2).getCell(1).setCellValue(actual);
                double revenue = actual - cost - custo;
                sheet.getRow(startRow+3).getCell(1).setCellValue(cost);
                sheet.getRow(startRow+4).getCell(1).setCellValue(revenue);
                sheet.getRow(startRow+5).getCell(1).setCellValue((double)revenue/order.getIncomeNew());
            }



            fOut = response.getOutputStream();
            workbook.write(fOut);


        }catch (Exception ex){ex.printStackTrace();}
        finally {
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
            }
        }
    }

    @RequestMapping(value = "/export/{type}/{status}/{from}/{to}/{userId}/{userRole}/{outFrom}/{outTo}", method = RequestMethod.GET)
    public void exportOrder(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable("type") int type, @PathVariable("status") String status,
                            @PathVariable("from") long from, @PathVariable("to") long to, @PathVariable("userId") long userId,
                            @PathVariable("userRole") String userRole, @PathVariable("outFrom") long outFrom,
                            @PathVariable("outTo") long outTo){
        SearchOrderVo vo = new SearchOrderVo();
        if (!status.equals("0")){
            vo.setStatus(status);
        }
        if (userRole.equals(Constant.ROLE_CUSTO)){
            vo.setUserId((int) userId);
        }
        if (type==1){
            vo.setUserId((int) userId);
        }
        if (from!=0){
            Calendar fromCal = Calendar.getInstance();
            fromCal.setTime(new Date(from));
            fromCal.set(Calendar.HOUR, 0);
            fromCal.set(Calendar.MINUTE, 0);
            fromCal.set(Calendar.SECOND, 0);
            fromCal.set(Calendar.MILLISECOND, 0);
            vo.setFrom(fromCal.getTime());
        }
        if (to!=0){
            Calendar toCal = Calendar.getInstance();
            toCal.setTime(new Date(to));
            toCal.set(Calendar.HOUR, 23);
            toCal.set(Calendar.MINUTE, 59);
            toCal.set(Calendar.SECOND, 59);
            toCal.set(Calendar.MILLISECOND, 0);
            vo.setTo(toCal.getTime());
        }
        if (outFrom!=0){
            Calendar fromCal = Calendar.getInstance();
            fromCal.setTime(new Date(outFrom));
            fromCal.set(Calendar.HOUR, 0);
            fromCal.set(Calendar.MINUTE, 0);
            fromCal.set(Calendar.SECOND, 0);
            fromCal.set(Calendar.MILLISECOND, 0);
            vo.setOutFrom(fromCal.getTime());
        }
        if (outTo!=0){
            Calendar toCal = Calendar.getInstance();
            toCal.setTime(new Date(outTo));
            toCal.set(Calendar.HOUR, 23);
            toCal.set(Calendar.MINUTE, 59);
            toCal.set(Calendar.SECOND, 59);
            toCal.set(Calendar.MILLISECOND, 0);
            vo.setOutTo(toCal.getTime());
        }
        List<Order> orders = uiService.queryOrders(vo);

        OutputStream fOut = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            String codedFileName = "OrderData";
            codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(ORDER_DATA_EXCEL_FILE_PATH);

            XSSFWorkbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            int startRow = 1;
            sheet.shiftRows(startRow+1, orders.size()+5, orders.size());
            for (int i = 0; i < orders.size(); i++) {
                Row sourceRow = null;
                Row targetRow = null;
                Cell sourceCell = null;
                Cell targetCell = null;
                targetRow = sheet.createRow(startRow+i);
                sourceRow = sheet.getRow(startRow);
                targetRow.setHeight(sourceRow.getHeight());

                for (int m = 0; m < 30; m++) {
                    targetCell = targetRow.createCell(m);
                    sourceCell = sourceRow.getCell(m);
                    targetCell.setCellType(sourceCell.getCellType());
                    targetCell.setCellStyle(sourceCell.getCellStyle());
                }
            }
            HashMap<Long, List<OrderLog>> orderLogMap = new HashMap<>();
            for(OrderLog log: uiService.queryAllOrderLogs()){
                if(orderLogMap.get(log.getOrderId())==null){
                    orderLogMap.put(log.getOrderId(), new ArrayList<>());
                }
                orderLogMap.get(log.getOrderId()).add(log);
            }
            for (Order order: orders){
                String orderLog ="";
                List<OrderLog> orderLogs = orderLogMap.get(Long.valueOf(order.getId()));
                if(orderLogs!=null) {
                    for (OrderLog oo : orderLogs) {
                        orderLog += oo.getContent() + "/" + oo.getAddBy() + "/" + DateUtils.formatDate(oo.getAddTime(), "yyyy-MM-dd");
                        orderLog += ";\n";
                    }
                }
                sheet.getRow(startRow).getCell(0).setCellValue(order.getItemNumber());
                sheet.getRow(startRow).getCell(1).setCellValue(order.getManFlat());
                sheet.getRow(startRow).getCell(2).setCellValue(order.getManAccount());
                sheet.getRow(startRow).getCell(3).setCellValue(order.getSonAccount());
                sheet.getRow(startRow).getCell(4).setCellValue(order.getManName());
                if (order.getCreateDate()!=null){
                    sheet.getRow(startRow).getCell(5).setCellValue(DateUtils.formatDate(order.getCreateDate(), "yyyy-MM-dd"));
                }
                sheet.getRow(startRow).getCell(6).setCellValue(order.getDeparture());
                sheet.getRow(startRow).getCell(7).setCellValue(order.getDestination());
                sheet.getRow(startRow).getCell(8).setCellValue(order.getManArea());
                sheet.getRow(startRow).getCell(9).setCellValue(order.getUserNum());
                sheet.getRow(startRow).getCell(10).setCellValue(order.getPlayDays());
                if (order.getStatus()!=null) {
                    sheet.getRow(startRow).getCell(11).setCellValue(OrderStatus.valueOf(order.getStatus()).getName());
                }
                if (order.getStartDate()!=null){
                    sheet.getRow(startRow).getCell(12).setCellValue(DateUtils.formatDate(order.getStartDate(), "yyyy-MM-dd"));
                }
                if (order.getOutDate()!=null){
                    sheet.getRow(startRow).getCell(13).setCellValue(DateUtils.formatDate(order.getOutDate(), "yyyy-MM-dd"));
                }
                sheet.getRow(startRow).getCell(14).setCellValue(order.getNeed());
                sheet.getRow(startRow).getCell(15).setCellValue(order.getCustomerName());
                sheet.getRow(startRow).getCell(16).setCellValue(order.getCustomerNumber());
                sheet.getRow(startRow).getCell(17).setCellValue(order.getCustomerWechat());
                sheet.getRow(startRow).getCell(18).setCellValue(order.getComment());
                sheet.getRow(startRow).getCell(19).setCellValue(order.getExperience());
                sheet.getRow(startRow).getCell(20).setCellValue(orderLog);
                if (order.getOrderTimer()!=null){
                    sheet.getRow(startRow).getCell(21).setCellValue(DateUtils.formatDate(order.getOrderTimer(), "yyyy-MM-dd"));
                }
                sheet.getRow(startRow).getCell(22).setCellValue(order.getIncomeNew()==null?0:order.getIncomeNew());
                sheet.getRow(startRow).getCell(23).setCellValue(order.getCost()==null?0:order.getCost());
                sheet.getRow(startRow).getCell(24).setCellValue(order.getRevenue()==null?0:order.getRevenue());
                startRow++;
            }



            fOut = response.getOutputStream();
            workbook.write(fOut);
        }
        catch (Exception ex){ex.printStackTrace();}
        finally {
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
            }
        }

    }

    @RequestMapping(value = "/export/{type}/{from}/{to}", method = RequestMethod.GET)
    public void exportZhichuReport(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable("type") String type,
                            @PathVariable("from") long from,
                                   @PathVariable("to") long to){
        if (type!=null && type.equals("zhichu")) {
            OrderItem searchItem = new OrderItem();
            if (from != 0) {
                Calendar fromCal = Calendar.getInstance();
                fromCal.setTime(new Date(from));
                fromCal.set(Calendar.HOUR, 0);
                fromCal.set(Calendar.MINUTE, 0);
                fromCal.set(Calendar.SECOND, 0);
                fromCal.set(Calendar.MILLISECOND, 0);
                searchItem.setPayDateFrom(fromCal.getTime());
            }
            if (to != 0) {
                Calendar toCal = Calendar.getInstance();
                toCal.setTime(new Date(from));
                toCal.set(Calendar.HOUR, 23);
                toCal.set(Calendar.MINUTE, 59);
                toCal.set(Calendar.SECOND, 59);
                toCal.set(Calendar.MILLISECOND, 0);
                searchItem.setPayDateTo(toCal.getTime());
            }
            List<Provider> providers = uiService.queryProvider(null);
            HashMap<Long, Provider> providerHashMap = new HashMap<>();
            providers.forEach(provider -> providerHashMap.put(provider.getId(), provider));
            List<OrderItem> items = uiService.queryOrderItem(searchItem);
            items.forEach(orderItem -> orderItem.setProvider(providerHashMap.get(orderItem.getProviderId())));


            OutputStream fOut = null;
            try {
                response.setContentType("application/vnd.ms-excel");
                String codedFileName = "支出统计";
                codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
                response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(ZHICHU_EXCEL_FILE_PATH);

                XSSFWorkbook workbook = new XSSFWorkbook(is);
                Sheet sheet = workbook.getSheetAt(0);
                int startRow = 1;
                sheet.shiftRows(startRow + 1, items.size() + 5, items.size());
                for (int i = 0; i < items.size(); i++) {
                    Row sourceRow = null;
                    Row targetRow = null;
                    Cell sourceCell = null;
                    Cell targetCell = null;
                    targetRow = sheet.createRow(startRow + i);
                    sourceRow = sheet.getRow(startRow);
                    targetRow.setHeight(sourceRow.getHeight());

                    for (int m = 0; m < 30; m++) {
                        targetCell = targetRow.createCell(m);
                        sourceCell = sourceRow.getCell(m);
                        targetCell.setCellType(sourceCell.getCellType());
                        targetCell.setCellStyle(sourceCell.getCellStyle());
                    }
                }
                for (OrderItem orderItem : items) {
                    sheet.getRow(startRow).getCell(0).setCellValue(orderItem.getProvider()==null?"":orderItem.getProvider().getName());
                    sheet.getRow(startRow).getCell(1).setCellValue(orderItem.getOrderNo());
                    sheet.getRow(startRow).getCell(2).setCellValue((orderItem.getType()==null||orderItem.getType().isEmpty())?"":ItemType.valueOf(orderItem.getType()).getName());
                    sheet.getRow(startRow).getCell(3).setCellValue(orderItem.getCategory());
                    sheet.getRow(startRow).getCell(4).setCellValue(orderItem.getCost());
                    sheet.getRow(startRow).getCell(5).setCellValue(orderItem.getPaystatus());
                    if (orderItem.getPayDate() != null) {
                        sheet.getRow(startRow).getCell(6).setCellValue(DateUtils.formatDate(orderItem.getPayDate(), "yyyy-MM-dd"));
                    }
                    sheet.getRow(startRow).getCell(7).setCellValue(orderItem.getFapiao());
                    if (orderItem.getFapiaoDate() != null) {
                        sheet.getRow(startRow).getCell(8).setCellValue(DateUtils.formatDate(orderItem.getFapiaoDate(), "yyyy-MM-dd"));
                    }
                    if (orderItem.getUseDate() != null) {
                        sheet.getRow(startRow).getCell(9).setCellValue(DateUtils.formatDate(orderItem.getUseDate(), "yyyy-MM-dd"));
                    }

                    sheet.getRow(startRow).getCell(10).setCellValue(orderItem.getStatus() == null ? "" : OrderStatus.valueOf(orderItem.getStatus()).getName());
                    sheet.getRow(startRow).getCell(11).setCellValue(orderItem.getItemNumber());
                    startRow++;
                }


                fOut = response.getOutputStream();
                workbook.write(fOut);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                }
            }
        }
        if (type!=null && type.equals("cost")) {
            OrderCostItem searchItem = new OrderCostItem();
            if (from != 0) {
                Calendar fromCal = Calendar.getInstance();
                fromCal.setTime(new Date(from));
                fromCal.set(Calendar.HOUR, 0);
                fromCal.set(Calendar.MINUTE, 0);
                fromCal.set(Calendar.SECOND, 0);
                fromCal.set(Calendar.MILLISECOND, 0);
                searchItem.setUseDateFrom(fromCal.getTime());
            }
            if (to != 0) {
                Calendar toCal = Calendar.getInstance();
                toCal.setTime(new Date(to));
                toCal.set(Calendar.HOUR, 23);
                toCal.set(Calendar.MINUTE, 59);
                toCal.set(Calendar.SECOND, 59);
                toCal.set(Calendar.MILLISECOND, 0);
                searchItem.setUseDateTo(toCal.getTime());
            }
            List<OrderCostItem> items = uiService.queryOrderCostItem(searchItem);
            OutputStream fOut = null;
            try {
                response.setContentType("application/vnd.ms-excel");
                String codedFileName = "结算统计";
                codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
                response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
                InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(COST_EXCEL_FILE_PATH);

                XSSFWorkbook workbook = new XSSFWorkbook(is);
                Sheet sheet = workbook.getSheetAt(0);
                int startRow = 1;
                sheet.shiftRows(startRow + 1, items.size() + 5, items.size());
                for (int i = 0; i < items.size(); i++) {
                    Row sourceRow = null;
                    Row targetRow = null;
                    Cell sourceCell = null;
                    Cell targetCell = null;
                    targetRow = sheet.createRow(startRow + i);
                    sourceRow = sheet.getRow(startRow);
                    targetRow.setHeight(sourceRow.getHeight());

                    for (int m = 0; m < 30; m++) {
                        targetCell = targetRow.createCell(m);
                        sourceCell = sourceRow.getCell(m);
                        targetCell.setCellType(sourceCell.getCellType());
                        targetCell.setCellStyle(sourceCell.getCellStyle());
                    }
                }
                for (OrderCostItem orderItem : items) {
                    sheet.getRow(startRow).getCell(0).setCellValue(orderItem.getNickname());
                    sheet.getRow(startRow).getCell(1).setCellValue((orderItem.getType()==null||orderItem.getType().isEmpty())?"":ItemType.valueOf(orderItem.getType()).getName());
                    sheet.getRow(startRow).getCell(2).setCellValue(orderItem.getCategory());
                    sheet.getRow(startRow).getCell(3).setCellValue(orderItem.getNote());
                    if (orderItem.getUseDate() != null) {
                        sheet.getRow(startRow).getCell(4).setCellValue(DateUtils.formatDate(orderItem.getUseDate(), "yyyy-MM-dd"));
                    }
                    sheet.getRow(startRow).getCell(5).setCellValue(orderItem.getCost());
                    sheet.getRow(startRow).getCell(6).setCellValue(orderItem.getItemNumber());
                    startRow++;
                }


                fOut = response.getOutputStream();
                workbook.write(fOut);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    fOut.flush();
                    fOut.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @RequestMapping(value = "/export/report/{manFlat}/{destination}/{from}/{to}/{userId}/{userRole}", method = RequestMethod.GET)
    public void exportOrderReport(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable("manFlat") String manFlat, @PathVariable("destination") String destination,
                            @PathVariable("from") long from, @PathVariable("to") long to, @PathVariable("userId") long userId,
                            @PathVariable("userRole") String userRole){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        SearchOrderVo vo = new SearchOrderVo();
        if (!manFlat.equals("0")){
            vo.setManFlat(manFlat);
        }else{
            manFlat = "所有";
        }
        if (userRole.equals(Constant.ROLE_CUSTO)){
            vo.setUserId((int) userId);
        }
        if (!destination.equals("0")){
            vo.setDestination(destination);
        }else{
            destination = "所有";
        }
        Calendar fromCal = Calendar.getInstance();
        if (from!=0){
            fromCal.setTimeInMillis(from);
            fromCal.set(Calendar.HOUR, 0);
            fromCal.set(Calendar.MINUTE, 0);
            fromCal.set(Calendar.SECOND, 0);
            fromCal.set(Calendar.MILLISECOND, 0);
            vo.setFrom(fromCal.getTime());
        }
        Calendar toCal = Calendar.getInstance();
        if (to!=0){
            toCal.setTimeInMillis(to);
            toCal.set(Calendar.HOUR, 23);
            toCal.set(Calendar.MINUTE, 59);
            toCal.set(Calendar.SECOND, 59);
            toCal.set(Calendar.MILLISECOND, 0);
            vo.setTo(toCal.getTime());
        }
        List<Order> orders = uiService.queryOrders(vo);
        HashMap<Integer, OrderReportVo> orderReportVoHashMap = calculateOrderReport(orders);
        OutputStream fOut = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            String codedFileName = "数据报表";
            codedFileName = java.net.URLEncoder.encode(codedFileName, "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(ORDER_REPORT_EXCEL_FILE_PATH);

            XSSFWorkbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);

            String date = "所有";
            if (from!=0){
                date = sdf.format(fromCal.getTime());
            }
            if (from!=0 && to==0){
                date+= "至今";
            }
            if (from ==0 && to!=0){
                date = "~";
            }
            if (from !=0 && to!=0){
                date += "至";
            }
            if (to!=0){
                date += sdf.format(toCal.getTime());
            }

            sheet.getRow(0).getCell(1).setCellValue(date);
            sheet.getRow(1).getCell(1).setCellValue(manFlat);
            sheet.getRow(2).getCell(1).setCellValue(destination);

            if (orderReportVoHashMap.size()>0) {
                int startRow = 5;
                sheet.shiftRows(startRow, sheet.getLastRowNum(), orderReportVoHashMap.size());
                for (int i = 0; i < orderReportVoHashMap.size(); i++) {
                    Row sourceRow = null;
                    Row targetRow = null;
                    Cell sourceCell = null;
                    Cell targetCell = null;
                    targetRow = sheet.createRow(startRow + i);
                    sourceRow = sheet.getRow(startRow + orderReportVoHashMap.size());
                    targetRow.setHeight(sourceRow.getHeight());

                    for (int m = 0; m < 8; m++) {
                        targetCell = targetRow.createCell(m);
                        sourceCell = sourceRow.getCell(m);
                        targetCell.setCellType(sourceCell.getCellType());
                        targetCell.setCellStyle(sourceCell.getCellStyle());
                    }
                }
                int totalNewOrder = 0;
                int totalEndOrder = 0;
                int totalAbortOrder = 0;
                int totalPendingTrackClient = 0;
                int totalAbortClient = 0;
                double totalIncome = 0;
                for (OrderReportVo order : orderReportVoHashMap.values()) {
                    totalNewOrder += order.getNewOrder().intValue();
                    totalEndOrder += order.getEndOrder().intValue();
                    totalAbortClient += order.getAbortClient().intValue();
                    totalPendingTrackClient += order.getPendingTrackClient().intValue();
                    totalAbortOrder += order.getAbortOrder().intValue();
                    totalIncome += order.getAllIncome();
                    sheet.getRow(startRow).getCell(0).setCellValue(order.getManName());
                    sheet.getRow(startRow).getCell(1).setCellValue(order.getNewOrder().intValue());
                    sheet.getRow(startRow).getCell(2).setCellValue(order.getEndOrder().intValue());
                    sheet.getRow(startRow).getCell(3).setCellValue(order.getAbortOrder().intValue());
                    sheet.getRow(startRow).getCell(4).setCellValue(order.getPendingTrackClient().intValue());
                    sheet.getRow(startRow).getCell(5).setCellValue(order.getAbortClient().intValue());
                    sheet.getRow(startRow).getCell(6).setCellValue(order.getTurnRate());
                    sheet.getRow(startRow).getCell(7).setCellValue(order.getAllIncome());
                    startRow++;
                }

                sheet.getRow(startRow).getCell(0).setCellValue("总计");
                sheet.getRow(startRow).getCell(1).setCellValue(totalNewOrder);
                sheet.getRow(startRow).getCell(2).setCellValue(totalEndOrder);
                sheet.getRow(startRow).getCell(3).setCellValue(totalAbortOrder);
                sheet.getRow(startRow).getCell(4).setCellValue(totalPendingTrackClient);
                sheet.getRow(startRow).getCell(5).setCellValue(totalAbortClient);
                sheet.getRow(startRow).getCell(6).setCellValue((double)totalEndOrder / totalNewOrder);
                sheet.getRow(startRow).getCell(7).setCellValue(totalIncome);

            }
            fOut = response.getOutputStream();
            workbook.write(fOut);
        }
        catch (Exception ex){ex.printStackTrace();}
        finally {
            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
            }
        }

    }

    @ResponseBody
    @RequestMapping(value="/search", method = RequestMethod.POST)
    public Message search(@RequestBody SearchOrderVo searchOrderVo,
                          @RequestHeader(value = "page", defaultValue = "0") int page,
                          @RequestHeader(value = "size", defaultValue = "25") int size){
        Message message = new Message();
        Integer userId = searchOrderVo.getUserId();
        if (searchOrderVo!=null){
            if(searchOrderVo.getSort()!=null){
                if(searchOrderVo.getSort().size()>0){
                    String newSort = "";
                    String sort = searchOrderVo.getSort().get(0).toString();
                    if(sort.startsWith("-")){
                        newSort = sort.substring(1) + " asc";
                    }
                    if(sort.startsWith("+")){
                        newSort = sort.substring(1) + " desc";
                    }
                    searchOrderVo.setSortStr(newSort);
                }else{
                    searchOrderVo.setSortStr(null);
                }
            }
            if(searchOrderVo.getFilter()!=null){
                LinkedHashMap map = (LinkedHashMap) searchOrderVo.getFilter();
                if(map!=null && map.size()>0){
                    final String[] filterStr = {""};
                    map.forEach((k,v)->{
                        if(k.toString().equals("manName")){
                            k="u.nickname";
                        }
                        filterStr[0] += ("and "+ k.toString() + " like '%" + v.toString() +"%'");
                    });
                    if(!filterStr[0].isEmpty())
                        searchOrderVo.setFilterStr(filterStr[0]);
                }
            }
            if(searchOrderVo.getRole()!=null && searchOrderVo.getRole().equals(Constant.ROLE_CUSTO)){
                searchOrderVo.setUserId(searchOrderVo.getUserId());
            }else{
                searchOrderVo.setUserId(null);
            }
            if (searchOrderVo.getType()!=null && searchOrderVo.getType().equals("1")){
                searchOrderVo.setUserId(userId);
            }
            if (searchOrderVo.getStatus()!=null && searchOrderVo.getStatus().equals("0")){
                searchOrderVo.setStatus(null);
            }
            if (searchOrderVo.getFrom()!=null){
                Calendar fromCal = Calendar.getInstance();
                fromCal.setTime(searchOrderVo.getFrom());
                fromCal.set(Calendar.HOUR, 0);
                fromCal.set(Calendar.MINUTE, 0);
                fromCal.set(Calendar.SECOND, 0);
                fromCal.set(Calendar.MILLISECOND, 0);
                searchOrderVo.setFrom(fromCal.getTime());
            }
            if (searchOrderVo.getTo()!=null){
                Calendar toCal = Calendar.getInstance();
                toCal.setTime(searchOrderVo.getTo());
                toCal.set(Calendar.HOUR, 23);
                toCal.set(Calendar.MINUTE, 59);
                toCal.set(Calendar.SECOND, 59);
                toCal.set(Calendar.MILLISECOND, 0);
                searchOrderVo.setTo(toCal.getTime());
            }
            if (searchOrderVo.getOutFrom()!=null){
                Calendar fromCal = Calendar.getInstance();
                fromCal.setTime(searchOrderVo.getOutFrom());
                fromCal.set(Calendar.HOUR, 0);
                fromCal.set(Calendar.MINUTE, 0);
                fromCal.set(Calendar.SECOND, 0);
                fromCal.set(Calendar.MILLISECOND, 0);
                searchOrderVo.setOutFrom(fromCal.getTime());
            }
            if (searchOrderVo.getOutTo()!=null){
                Calendar toCal = Calendar.getInstance();
                toCal.setTime(searchOrderVo.getOutTo());
                toCal.set(Calendar.HOUR, 23);
                toCal.set(Calendar.MINUTE, 59);
                toCal.set(Calendar.SECOND, 59);
                toCal.set(Calendar.MILLISECOND, 0);
                searchOrderVo.setOutTo(toCal.getTime());
            }
        }
        Integer total =  uiService.queryOrderCount(searchOrderVo);
        int pageFrom = 0;
        int pageTo = 0;
        pageFrom = page*size;
        pageTo = pageFrom + size;
        searchOrderVo.setPageFrom(pageFrom);
        searchOrderVo.setPageTo(pageTo);
        message.setCode(Constant.SUCCESS);
        Pageable pageable = new PageRequest(page, size);
        Page ret = new PageImpl(uiService.queryOrders(searchOrderVo),pageable, total);
        message.setValue(ret);
        return message;
    }

    @ResponseBody
    @RequestMapping(value = "/dele/{id}", method = RequestMethod.GET)
    public Message deleteOrder(@PathVariable("id") long id){

        uiService.deleteOrder(id);

        return new Message();
    }

    @ResponseBody
    @RequestMapping(value = "/log/{id}", method = RequestMethod.GET)
    public Message queryOrderLog(@PathVariable("id") long id){
        Message ret = new Message();
        ret.setCode(Constant.SUCCESS);
        ret.setValue(uiService.queryOrderLogs(id));
        return ret;
    }

    @ResponseBody
    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public Message addOrderLog(@RequestBody OrderLog orderLog){
        Message ret = new Message();
        uiService.addOrderLog(orderLog);
        return ret;
    }

    @ResponseBody
    @RequestMapping(value = "/log/{id}", method = RequestMethod.DELETE)
    public Message deleteOrderLog(@PathVariable("id") long id){
        Message ret = new Message();
        uiService.deleteOrderLogById(id);
        ret.setCode(Constant.SUCCESS);
        return ret;
    }

    @ResponseBody
    @RequestMapping(value = "/jiesuan/{id}", method = RequestMethod.GET)
    public Message queryJiesuan(@PathVariable("id") long id){
        Message ret = new Message();
        OrderItem search = new OrderItem();
        search.setOrderId(id);
        List<OrderItem> itemsDb = uiService.queryOrderItem(search);
        for (OrderItem item: itemsDb){
            Attachment att = new Attachment();
            att.setOrderItemId((int) item.getId());
            item.setAttachmentList(attachmentService.findByOrderItemId(att));
        }
        OrderCostItem costItem = new OrderCostItem();
        costItem.setOrderId(id);
        List<OrderCostItem> orderCostItems = uiService.queryOrderCostItem(costItem);
        JiesuanVo vo = new JiesuanVo();
        vo.setCostItems(orderCostItems);
        vo.setItems(itemsDb);
        ret.setCode(Constant.SUCCESS);
        ret.setValue(vo);
        return ret;
    }
//数据报表接口
    @ResponseBody
    @RequestMapping(value="/report", method = RequestMethod.POST)
    public Message queryOrderReport(@RequestBody SearchOrderVo searchOrderVo){
        Message message = new Message();
        Integer userId = searchOrderVo.getUserId();
        if (searchOrderVo!=null){

            if(searchOrderVo.getRole()!=null && searchOrderVo.getRole().equals(Constant.ROLE_CUSTO)){
                searchOrderVo.setUserId(searchOrderVo.getUserId());
            }else{
                searchOrderVo.setUserId(null);
            }
            if (searchOrderVo.getManFlat()!=null && searchOrderVo.getManFlat().equals("0")){
                searchOrderVo.setManFlat(null);
            }
            if (searchOrderVo.getDestination()!=null && searchOrderVo.getDestination().equals("0")){
                searchOrderVo.setDestination(null);
            }
            if (searchOrderVo.getFrom()!=null){
                Calendar fromCal = Calendar.getInstance();
                fromCal.setTime(searchOrderVo.getFrom());
                fromCal.set(Calendar.HOUR, 0);
                fromCal.set(Calendar.MINUTE, 0);
                fromCal.set(Calendar.SECOND, 0);
                fromCal.set(Calendar.MILLISECOND, 0);
                searchOrderVo.setFrom(fromCal.getTime());
            }
            if (searchOrderVo.getTo()!=null){
                Calendar toCal = Calendar.getInstance();
                toCal.setTime(searchOrderVo.getTo());
                toCal.set(Calendar.HOUR, 23);
                toCal.set(Calendar.MINUTE, 59);
                toCal.set(Calendar.SECOND, 59);
                toCal.set(Calendar.MILLISECOND, 0);
                searchOrderVo.setTo(toCal.getTime());
            }
        }
        List<Order> orderList = uiService.queryOrders(searchOrderVo);

        message.setCode(Constant.SUCCESS);
        message.setValue(calculateOrderReport(orderList).values());
        return message;
    }

    //支出统计接口
    @ResponseBody
    @RequestMapping(value="/finance", method = RequestMethod.POST)
    public Message queryFinanceReport(@RequestBody SearchOrderVo searchOrderVo){
        Message message = new Message();
        OrderItem searchItem = new OrderItem();
        if (searchOrderVo!=null){
            if (searchOrderVo.getFrom()!=null){
                Calendar fromCal = Calendar.getInstance();
                fromCal.setTime(searchOrderVo.getFrom());
                fromCal.set(Calendar.HOUR, 0);
                fromCal.set(Calendar.MINUTE, 0);
                fromCal.set(Calendar.SECOND, 0);
                fromCal.set(Calendar.MILLISECOND, 0);
                searchItem.setPayDateFrom(fromCal.getTime());
            }
            if (searchOrderVo.getTo()!=null){
                Calendar toCal = Calendar.getInstance();
                toCal.setTime(searchOrderVo.getTo());
                toCal.set(Calendar.HOUR, 23);
                toCal.set(Calendar.MINUTE, 59);
                toCal.set(Calendar.SECOND, 59);
                toCal.set(Calendar.MILLISECOND, 0);
                searchItem.setPayDateTo(toCal.getTime());
            }
        }
        List<Provider> providers = uiService.queryProvider(null);
        HashMap<Long, Provider> providerHashMap = new HashMap<>();
        providers.forEach(provider -> providerHashMap.put(provider.getId(), provider));
        List<OrderItem> items = uiService.queryOrderItem(searchItem);
        items.forEach(orderItem -> orderItem.setProvider(providerHashMap.get(orderItem.getProviderId())));
        message.setCode(Constant.SUCCESS);
        message.setValue(items);
        return message;
    }

    //结算统计接口
    @ResponseBody
    @RequestMapping(value="/cost", method = RequestMethod.POST)
    public Message queryCostReport(@RequestBody SearchOrderVo searchOrderVo){
        Message message = new Message();
        OrderCostItem searchItem = new OrderCostItem();
        if (searchOrderVo!=null){
            if (searchOrderVo.getFrom()!=null){
                Calendar fromCal = Calendar.getInstance();
                fromCal.setTime(searchOrderVo.getFrom());
                fromCal.set(Calendar.HOUR, 0);
                fromCal.set(Calendar.MINUTE, 0);
                fromCal.set(Calendar.SECOND, 0);
                fromCal.set(Calendar.MILLISECOND, 0);
                searchItem.setUseDateFrom(fromCal.getTime());
            }
            if (searchOrderVo.getTo()!=null){
                Calendar toCal = Calendar.getInstance();
                toCal.setTime(searchOrderVo.getTo());
                toCal.set(Calendar.HOUR, 23);
                toCal.set(Calendar.MINUTE, 59);
                toCal.set(Calendar.SECOND, 59);
                toCal.set(Calendar.MILLISECOND, 0);
                searchItem.setUseDateTo(toCal.getTime());
            }
        }
        List<OrderCostItem> items = uiService.queryOrderCostItem(searchItem);
        message.setCode(Constant.SUCCESS);
        message.setValue(items);
        return message;
    }

    private HashMap<Integer, OrderReportVo> calculateOrderReport(List<Order> orderList){
        HashMap<Integer, OrderReportVo> userOrderMap = new HashMap<>();
        for (Order order: orderList){
            if(userOrderMap.get(order.getManNameId())==null){
                userOrderMap.put(order.getManNameId(),new OrderReportVo());
            }
            userOrderMap.get(order.getManNameId()).setManName(order.getManName());
            userOrderMap.get(order.getManNameId()).getNewOrder().getAndIncrement(); // all order number
            if (order.getStatus()!=null && !order.getStatus().equals(OrderStatus.REFUND.toString()) && order.getOutDate()!=null) {
                userOrderMap.get(order.getManNameId()).getEndOrder().getAndIncrement(); // success order number
            }
            if (order.getStatus()!=null && order.getStatus().equals(OrderStatus.REFUND.toString())){
                userOrderMap.get(order.getManNameId()).getAbortOrder().getAndIncrement(); //failed order number
            }
            if (order.getClientStatus()!=null && order.getClientStatus().equals("待追踪客户")){
                userOrderMap.get(order.getManNameId()).getPendingTrackClient().getAndIncrement();
            }
            if (order.getClientStatus()!=null && order.getClientStatus().equals("已放弃客户")){
                userOrderMap.get(order.getManNameId()).getAbortClient().getAndIncrement();
            }
            double allincome = userOrderMap.get(order.getManNameId()).getAllIncome();
            if (order.getIncome()!=null ){
                allincome += order.getIncome();
                userOrderMap.get(order.getManNameId()).setAllIncome(allincome);
            }
            userOrderMap.get(order.getManNameId()).setTurnRate();

        }
        return userOrderMap;
    }

    //保存结算单UI接口
    @ResponseBody
    @RequestMapping(value = "/jiesuan", method = RequestMethod.POST)
    public Message addJiesuan(@RequestBody JiesuanVo jiesuanVo){
        Message ret = new Message();
        if (jiesuanVo.getOrderId()>0){
            SearchOrderVo vo = new SearchOrderVo();
            vo.setId(jiesuanVo.getOrderId());
            List<Order> orderDb = uiService.queryOrders(vo);
            OrderItem search = new OrderItem();
            search.setOrderId(jiesuanVo.getOrderId());
            if (orderDb!=null && orderDb.size()==1){
                Order orderOrg = orderDb.get(0);
                List<OrderItem> itemsDb = uiService.queryOrderItem(search);
                List<Long> removeList = new ArrayList<>();
                for (OrderItem item: itemsDb){
                    removeList.add(item.getId());
                }
                List<OrderItem> addList=  new ArrayList<>();
                List<OrderItem> updateList=  new ArrayList<>();
                double orderIncome = 0;
                for (OrderItem item: jiesuanVo.getItems()){
                    if(item.getId()==0){
                        addList.add(item);
                    }else{
                        for(OrderItem ii: itemsDb){
                            if(ii.getId()==item.getId()) {
                                removeList.remove(ii.getId());
                            }
                        }
                        updateList.add(item);
                    }
                }
                if(addList.size()>0) {
                    uiService.insertOrderItem(addList);
                }
                if(updateList.size()>0) {
                    uiService.updateOrderItem(updateList);
                }
                if(removeList.size()>0) {
                    uiService.deleteOrderItem(removeList);
                }
                OrderCostItem searchCostItem = new OrderCostItem();
                searchCostItem.setOrderId(jiesuanVo.getOrderId());
                List<OrderCostItem> costItemsDb = uiService.queryOrderCostItem(searchCostItem);
                List<Long> removeCostItemList = new ArrayList<>();
                for (OrderCostItem item: costItemsDb){
                    removeCostItemList.add(item.getId());
                }
                List<OrderCostItem> addCostItemList=  new ArrayList<>();
                List<OrderCostItem> updateCostItemList=  new ArrayList<>();
                for (OrderCostItem item: jiesuanVo.getCostItems()){
                    if (item.getType()!=null && item.getType().equals(ItemType.income.toString())){
                        orderIncome+=item.getCost();
                    }
                    if(item.getId()==0){
                        addCostItemList.add(item);
                    }else{
                        for(OrderCostItem ii: costItemsDb){
                            if(ii.getId()==item.getId()) {
                                removeCostItemList.remove(ii.getId());
                            }
                        }
                        updateCostItemList.add(item);
                    }
                }
                if(addCostItemList.size()>0) {
                    uiService.insertOrderCostItem(addCostItemList);
                }
                if(updateCostItemList.size()>0) {
                    uiService.updateOrderCostItem(updateCostItemList);
                }
                if(removeCostItemList.size()>0) {
                    uiService.deleteOrderCostItem(removeCostItemList);
                }
                orderOrg.setIncomeNew(orderIncome);
                uiService.updateOrder(orderOrg);
                ret.setCode(Constant.SUCCESS);
            }else{
                ret.setCode(Constant.FAILURE);
                ret.setValue("Invalid order id");
            }

        }else{
            ret.setCode(Constant.FAILURE);
            ret.setValue("Invalid order id");
        }

        return ret;
    }

    //For UI add and update
    @ResponseBody
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public Message update(@RequestBody Order order){
        Message message = new Message();
        User user = userService.findByUserName(order.getManName());
        if (user==null){
            message.setCode(1);
            message.setValue("User name is invalid");
            return message;
        }
        order.setManNameId(user.getId());
        if (order.getId()!=null && order.getId().equals(0)){
            uiService.insertOrder(order);
        }else{
            SearchOrderVo vo = new SearchOrderVo();
            vo.setId(order.getId());
            uiService.updateOrder(order);
        }
        message.setCode(Constant.SUCCESS);
        return message;
    }

    //For API
    @ResponseBody
    @RequestMapping(value="/new", method = RequestMethod.POST)
    public Message post(@RequestBody OrderVo orderVo) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Message message = new Message();
        if(orderVo.getItem()==null){
            message.setCode(1);
            message.setValue("Data is invalid");
            return message;
        }
        if (orderVo.getItem().getItemCustomer()==null){
            message.setCode(1);
            message.setValue("Data is invalid");
            return message;
        }
        if (orderVo.getItem().getMan()==null){
            message.setCode(1);
            message.setValue("Data is invalid");
            return message;
        }
        
        if(orderVo.getItem().getItemNumber() == null) {
        	message.setCode(1);
        	message.setValue("订单不能为空");
        	return message;
        }else {
        	List<SearchOrderVo> orderVoList = uiService.judyIsThereOrder(orderVo.getItem().getItemNumber().trim());
        	if(orderVoList != null && orderVoList.size() > 0) {
        		message.setCode(1);
        		message.setValue("错误，重复上传，上传失败");
        		return message;
        	}
        }
        
        Item item = orderVo.getItem();
        Man man = item.getMan();
        User user = userService.findByUserName(man.getName());
        if (user==null){
            message.setCode(1);
            message.setValue("User name is invalid");
            return message;
        }

        SearchOrderVo orderCheck = new SearchOrderVo();
        orderCheck.setItemNumber(orderVo.getItem().getItemNumber().trim());
        List<Order> orderChecks = uiService.queryOrders(orderCheck);
        Order order;
        boolean update = false;
        if (orderChecks!=null && orderChecks.size()>0){
            if(orderChecks.get(0).getManNameId()!=user.getId()){
                message.setCode(1);
                message.setValue("Duplicate Item Number, and upload user is not same as the previous one.");
                return message;
            }else{
                order = orderChecks.get(0);
                update = true;
            }
        }else{
            order = new Order();
        }

        ItemCustomer itemCustomer = item.getItemCustomer();
        order.setStartDate(sdf.parse(item.getStartDay()));
        order.setDeparture(item.getDeparture());
        order.setDestination(item.getDestination());
        String destination = item.getDestination().trim();
        if(destination.contains(",")) {
        	destination = destination.substring(0, destination.indexOf(",")).trim();
        }
        if(destination.contains("不限")) {
        	destination = destination.substring(0, destination.indexOf(" ")).trim();
        }
        if(destination.contains("-")) {
        	destination = destination.substring(0,destination.indexOf("-")).trim();
        }
        boolean flag = false;
        Country country = cityDao.getCountryByName(destination.trim());
        if(country != null) {
        	flag = true;
        }
        if(flag == false) {
        	States states = cityDao.getStatesByName(destination.trim());
        	if(states != null) {
        		String name = cityDao.getCountryById(states.getCo_id());
        		destination = name;
        		flag = true;
        	}
        }
        if(flag == false) {
        	City city = cityDao.getCityByName(destination.trim());
        	if(city != null) {
        		States states = cityDao.getStateByCid(city.getS_id());
        		String name = cityDao.getCountryById(states.getCo_id());
        		destination = name;
        		flag = false;
        	}
        }
        order.setManArea(destination);
        order.setNeed(item.getNeed());
        order.setItemNumber(item.getItemNumber().trim());
        order.setPlayDays(item.getPlayDays());
        if (item.getPlayDays()!=null && item.getStartDay()!=null){
            try{
                Integer.parseInt(item.getPlayDays());
            }catch (Exception e){
                System.out.println(e);
                message.setCode(Constant.FAILURE);
                message.setValue("天数非数字");
                return message;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(item.getStartDay()));
            calendar.add(Calendar.DATE, Integer.parseInt(item.getPlayDays()));
            order.setBackDate(calendar.getTime());
        }
        order.setUserNum(item.getUserNum());
        order.setCreateDate(new Date());
        order.setCustomerName(itemCustomer.getName());
        order.setCustomerNumber(itemCustomer.getNumber());
        order.setCustomerWechat(itemCustomer.getWeChat());
        order.setOrderTimer(sdfTime.parse(item.getOrderTimer()));
        order.setManFlat(man.getFlat());
        order.setManName(man.getName());
        order.setManNameId(user.getId());
        order.setManAccount(man.getAccount());
        order.setSonAccount(man.getSonAccount());
        order.setUrl(item.getUrl());
        order.setStatus(Constant.ORDER_STATUS_NEW);

        if (!update){
            uiService.insertOrder(order);
        }else{
            uiService.updateOrder(order);
        }

        if (item.getRemark()!=null){
            List<Order> orderSaved = uiService.queryOrders(orderCheck);
            if (orderSaved!=null && orderSaved.size()==1){
                for (RemarkVo remarkVo: item.getRemark()){
                    OrderLog log = new OrderLog();
                    log.setOrderId(orderSaved.get(0).getId());
                    log.setContent(remarkVo.getContent());
                    log.setAddTime(sdfTime.parse(remarkVo.getTime()));
                    log.setAddBy(man.getName());
                    uiService.addOrderLog(log);
                }
            }
        }

        log.info("i'm in!!!");
        message.setCode(Constant.SUCCESS);
        message.setValue("上传成功");


        return message;
    }
}
