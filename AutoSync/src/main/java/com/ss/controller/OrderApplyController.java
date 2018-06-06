package com.ss.controller;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.ss.pojo.ModelResult;
import com.ss.pojo.Order;
import com.ss.pojo.User;
import com.ss.pojo.vo.OrderItemVo;
import com.ss.service.IUserService;
import com.ss.service.OrderApplyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 
 * @author lory
 * @description 订单相关业务控制层
 * @Package com.ss.controller
 * @date 2018年1月11日
 */
@Controller
@RequestMapping("/orderApply")
@Slf4j
public class OrderApplyController {
	

	@Resource
	private SqlSessionFactory sqlSessionFactory;
	@Autowired
	private OrderApplyService orderApplyService;
	
	
	
	
	@Resource
    private IUserService userService;
	
	/**
	 * 获取当前登录用户状态
	 * @author lory
	 * @Description TODO
	 * @Params @param request
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getUserStatus")
	public @ResponseBody ModelResult getUserStatus(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		return ModelResult.buildOk(user);
	}
	
	
	/**
	 * 
	 * @author lory
	 * @Description 多笔订单条目申请
	 * @Params @param orderApplies
	 * @Params @return ModelResult 公用返回类
	 * @return ModelResult
	 * @throws Exception 
	 */
	@RequestMapping("/applyOrderList")
	@Transactional("transactionManager")
	public @ResponseBody ModelResult applyOrderList(HttpServletRequest request) throws Exception {
		String arr = request.getParameter("orderApplies");
		if(arr != null && arr != "") {
			//自定义json日期转换
			GsonBuilder builder = new GsonBuilder();
		    builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
	
				@Override
				public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context){
					try {
						return new Date(json.getAsJsonPrimitive().getAsLong());
					}catch(JsonParseException e) {
						log.error("gson 转换错误");
						return null;
					}
				}
		    });
	
		    Gson gson = builder.create();
		    //gson转换为List<>
		    List<OrderItemVo> orderApplies = gson.fromJson(arr, new TypeToken<List<OrderItemVo>>() {}.getType());
			User currUser = (User) request.getSession().getAttribute("user");
			ModelResult result = orderApplyService.transApplyOrder(orderApplies, currUser);
			return result;
		}else {
			return ModelResult.buildError(1, "申请失败");
		}
	}
	
	/**
	 * 
	 * @author lory
	 * @Description 财务未审核订单查询
	 * @Params @param page
	 * @Params @return ModelResult公用返回类
	 * @return ModelResult
	 */
	@RequestMapping("/getFinanceNonOrder")
	public @ResponseBody ModelResult getFinanceNonOrder(@RequestParam(required = true,defaultValue = "1") Integer page,
			HttpServletRequest request) {
		User auditorUser = (User) request.getSession().getAttribute("user");
		return orderApplyService.getNonAuditingOrderForFinance(auditorUser, page);
	}
	
	/**
	 * 获取财务未审核订单数目
	 * @author lory
	 * @Description TODO
	 * @Params @param request
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getNonAuditingOrderNumberForFinance")
	public @ResponseBody ModelResult getNonAuditingOrderNumberForFinance(HttpServletRequest request) {
		User auditorUser = (User) request.getSession().getAttribute("user");
		if(auditorUser == null) {
			return ModelResult.buildError(1, "请先登录");
		}
		if(!auditorUser.getRole().trim().equals("admin") && !auditorUser.getRole().trim().equals("inspector")) {
			return ModelResult.buildError(108, "权限不足，无法查看");
		}
		return ModelResult.buildOk(orderApplyService.getNonAuditingOrderNumberForFinance());
	}
	
	/**
	 * 
	 * @author lory
	 * @Description 获取财务已审核订单
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getFinanceOrder")
	public @ResponseBody ModelResult getAuditingOrderForFinance(@RequestParam(required = true,defaultValue = "1") Integer page,
			HttpServletRequest request) {
		User auditorUser = (User) request.getSession().getAttribute("user");
		return orderApplyService.getAuditingOrderForFinance(auditorUser, page);
	}
	
	/**
	 * 
	 * @author lory
	 * @Description 定制师未审核订单
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getUserNonOrder")
	public @ResponseBody ModelResult getNonAuditingOrderForUser(@RequestParam(required = true,defaultValue = "1") Integer page,
			HttpServletRequest request) {
		User auditorUser = (User) request.getSession().getAttribute("user");
		return orderApplyService.getNonAuditingOrderForUser(auditorUser, page);
	}
	
	/**
	 * 
	 * @author lory
	 * @Description 定制师已审核结束订单
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getUserOrder")
	public @ResponseBody ModelResult getAuditingOrderForUser(@RequestParam(required = true,defaultValue = "1") Integer page,
			HttpServletRequest request) {
		User auditorUser = (User) request.getSession().getAttribute("user");
		return orderApplyService.getAuditingOrderForUser(auditorUser, page);
	}
	
	/**
	 * 
	 * @author lory
	 * @Description 更新订单状态，对应业务为财务付款结算修改订单状态
	 * @Params @param id
	 * @Params @param request
	 * @Params @return
	 * @return ModelResult
	 * @throws Exception 
	 */
	@RequestMapping("/updateOrderStatus/{url}")
	public @ResponseBody ModelResult updateOrderStatusByOrderId(@RequestBody OrderItemVo itemVo,HttpServletRequest request,@PathVariable Integer url) throws Exception {
		if(itemVo.getOrderNo() == null) {
			itemVo.setOrderNo("0");
		}
		if(itemVo == null || itemVo.getId() <= 0 || itemVo.getOrderNo() == null) {
			log.warn("未选择结算订单");
			return ModelResult.buildError(100,"未选择结算订单");
		}
		User userSession = (User) request.getSession().getAttribute("user");
		return orderApplyService.transUpdateOrderStatusByOrderId(itemVo, userSession, url);
	}
	/**
	 * 添加发票相关字段方法
	 * @author lory
	 * @Description TODO
	 * @Params @param itemVo
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/updateOrderBill")
	public @ResponseBody ModelResult updateOrderBill(@RequestBody OrderItemVo itemVo) {
		if(itemVo == null || itemVo.getBillCost() == null || itemVo.getBillDate()==null || itemVo.getBillStatus() == null) {
			return ModelResult.buildError(110, "错误，金额或日期或状态为空");
		}
		try {
			orderApplyService.updateOrderBill(itemVo);
			return ModelResult.buildOk("修改成功");
		}catch (Exception e) {
			return ModelResult.buildError(1, "修改失败");
		}
	}
	
	/**
	 * 
	 * @author lory
	 * @Description 分页显示定制师的订单
	 * @Params @param request
	 * @Params @param page
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getUserOrders")
	public @ResponseBody ModelResult getUserOrders(HttpServletRequest request,@RequestParam(required = true,defaultValue = "1") Integer page) {
		User userSession = (User) request.getSession().getAttribute("user");
		return orderApplyService.getAllOrders(userSession,page);
	}
	/**
	 * 获取用户所有订单
	 * @author lory
	 * @Description TODO
	 * @Params @param request
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getUserOrdersWithNoPage")
	public @ResponseBody ModelResult getUserOrdersWithNoPage(HttpServletRequest request) {
		User userSession = (User) request.getSession().getAttribute("user");
		int uid = userSession.getId();
		List<Order> orders = orderApplyService.getAllOrdersWithNoPage(uid);
		return ModelResult.buildOk(orders);
	}
	
	/**
	 * 
	 * @author lory
	 * @Description 获取订单明细
	 * @Params @param itemNumber
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getOrderItems")
	public @ResponseBody ModelResult getUserOrderItems(@RequestParam(required=true) String itemNumber) {
		if(itemNumber == null) {
			log.warn("OrderApplyController class getUserOrderItems method 有订单为空的查询进入系统");
			return ModelResult.buildError(105, "订单为空，非法提交");
		}
		List<OrderItemVo> itemsVo = orderApplyService.getOrderItems(itemNumber);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(itemsVo !=null && itemsVo.size()>0) {
			for(OrderItemVo itemVo : itemsVo) {
				itemVo.setItemNumber(itemNumber);
				if(itemVo.getStatus() == null) {
					itemVo.setStatus("未付款");
				}
				if(itemVo.getUseDate() !=null) {
					String dat = simpleDateFormat.format(itemVo.getUseDate());
					itemVo.setFormatDate(dat);
				}
				
			}
		}
		return ModelResult.buildOk(itemsVo);
	}
	/**
	 * 修改结算单状态方法
	 * @author lory
	 * @Description TODO
	 * @Params @param doS
	 * @Params @param id
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/updateItemOrder/{doS}/{id}")
	public @ResponseBody ModelResult deleteOrderItem(@PathVariable Integer doS,@PathVariable Integer id) {
		if(id == null ) {
			return ModelResult.buildError(1, "非法操作");
		}
		return orderApplyService.deleteOrderItem(doS, id);
	}
	
	/**
	 * 模糊查询用户订单功能
	 * @author lory
	 * @Description TODO
	 * @Params @param request
	 * @Params @param pageNumber
	 * @Params @param order
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/selectByParameters/{pageNumber}")
	public @ResponseBody ModelResult selectByParameters(HttpServletRequest request,@PathVariable Integer pageNumber,@RequestBody Order order) {
		User user = (User) request.getSession().getAttribute("user");
		if(pageNumber == null) {
			pageNumber = 1;
		}
		return orderApplyService.selectByParameters(pageNumber,order,user);
	}
}
