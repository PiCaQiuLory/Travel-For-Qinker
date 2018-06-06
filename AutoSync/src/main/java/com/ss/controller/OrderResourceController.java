package com.ss.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ss.pojo.ModelResult;
import com.ss.pojo.OrderResource;
import com.ss.pojo.Provider;
import com.ss.pojo.User;
import com.ss.service.IUiService;
import com.ss.service.OrderResourceService;
import com.ss.utils.PageSize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * 资源方模块，对应申请，付款扣除等功能
 * @author lory
 * @description TODO
 * @Package com.ss.controller
 * @date 2018年2月24日
 */
@Controller
@RequestMapping("/resource")
@Slf4j
public class OrderResourceController {
	

	@Autowired
	private OrderResourceService resourceService;
	
	@Autowired 
	private IUiService uiService;
	

	/**
	 * 单笔资源申请
	 * @author lory
	 * @Description TODO
	 * @Params @param request
	 * @Params @param orderResource
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping(method= RequestMethod.POST)
	@Transactional("transactionManager")
	public @ResponseBody ModelResult applyOneResource(HttpServletRequest request,@RequestBody OrderResource orderResource) {
		if(orderResource == null || orderResource.getBalance() == null || orderResource.getTotal() == null) {
			return ModelResult.buildError(1, "参数错误");
		}
		
		User currUser = (User) request.getSession().getAttribute("user");
		if(currUser == null) {
			return ModelResult.buildError(1, "请先登录");
		}
		orderResource.setApply_user_id(currUser.getId());
		orderResource.setApply_time(new Date());
		orderResource.setStatus("审核中");
		orderResource.setApply_user_id(currUser.getId());
		try {
			resourceService.applyOneResource(orderResource);
			return ModelResult.buildOk("申请成功");
		}catch (Exception e) {
			log.error("系统错误："+e.getStackTrace());
			throw e;
		}
	}
	
	/**
	 * 获取不同状态的所有资源申请
	 * @author lory
	 * @Description TODO
	 * @Params @param page
	 * @Params @param status
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getAllWithNoJudy/{status}/{page}")
	public @ResponseBody ModelResult getAllWithNoJudy(@PathVariable Integer page,@PathVariable Integer status) {
		if(page == null) {
			page = 0;
		}
		OrderResource resource = new OrderResource();
		if(status == 0) {
			resource.setStatus("审核中");
		}else if(status == 1) {
			resource.setStatus("已付款");
		}else{
			return ModelResult.buildError(1, "路径错误");
		}
		PageHelper.startPage(page, PageSize.APPLY_SIZE);
		List<OrderResource> resourceList = resourceService.getAllWithNoJudy(resource);
		PageInfo<OrderResource> info = new PageInfo<>(resourceList);
		return ModelResult.buildOk(info);
	}
	/**
	 * 审核资源申请项功能
	 * @author lory
	 * @Description TODO
	 * @Params @param request
	 * @Params @param status
	 * @Params @param resource
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/judyOrderResource/{status}")
	@Transactional("transactionManager")
	public @ResponseBody ModelResult judyOrderResource(HttpServletRequest request,@PathVariable Integer status,@RequestBody OrderResource resource) {
		User currUser = (User) request.getSession().getAttribute("user");
		resource.setAuditor_id(currUser.getId());
		resource.setPay_time(new Date());
		try {
			if(status == 0) {
				resource.setStatus("已付款");
				resourceService.judyOrderResouceToPay(resource);
				Provider pro = new Provider();
				pro.setName(resource.getName().trim());
				pro.setCost(resource.getTotal());
				pro.setBalance(resource.getBalance());
				uiService.addProviderBalance(pro);
			}
			if(status == 1) {
				resource.setStatus("已拒绝");
				resourceService.judyOrderResouceToPay(resource);
			}
		}catch (Exception e) {
			throw e;
		}
		return ModelResult.buildOk("付款成功");
	}
	/**
	 * 模糊查询资源申请订单
	 * @author lory
	 * @Description TODO
	 * @Params @param resource
	 * @Params @param page
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/search/{page}")
	public @ResponseBody ModelResult search(@RequestBody OrderResource resource,@PathVariable Integer page) {
		if(page ==null) {
			page =0;
		}
		PageHelper.startPage(page,PageSize.APPLY_SIZE);
		if(resource == null) {
			return ModelResult.buildError(1, "参数错误");
		}
		List<OrderResource> resourceList = resourceService.search(resource);
		PageInfo<OrderResource> info = new PageInfo<>(resourceList);
		return ModelResult.buildOk(info);
		
	}
	
	/**
	 * 对应资源申请项的审核功能
	 * @author lory
	 * @Description TODO
	 * @Params @param resource
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/updateOrderResourceBill")
	@ResponseBody
	public ModelResult updateOrderResourceBill(@RequestBody OrderResource resource) {
		if(resource == null) {
			return ModelResult.buildError(1, "参数错误");
		}
		resourceService.updateOrderResourceBill(resource);
		return ModelResult.buildOk("修改成功");
	}
}
