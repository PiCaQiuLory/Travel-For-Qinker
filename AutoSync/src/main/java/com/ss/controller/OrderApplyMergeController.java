package com.ss.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ss.pojo.*;
import com.ss.pojo.vo.OrderItemVo;
import com.ss.service.OrderApplyItemService;
import com.ss.utils.PageSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 财务订单审核模块合并功能控制层
 * @author lory
 * @description TODO
 * @Package com.ss.controller
 * @date 2018年2月24日
 */
@Controller
@RequestMapping("/orderMerge")
public class OrderApplyMergeController {

	
	@Autowired
	private OrderApplyItemService mergeService;
	
	
	/**
	 * 将多笔订单支付申请合并为一条申请功能
	 * @author lory
	 * @Description TODO
	 * @Params @param request
	 * @Params @param mergePo
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ModelResult mergeOrderItems(HttpServletRequest request,@RequestBody OrderMergePojo mergePo) {
		
		if(mergePo == null || mergePo.getArr() == null || mergePo.getArr().length == 0) {
			return ModelResult.buildError(1, "参数错误");
		}
		User user = (User) request.getSession().getAttribute("user");
		return mergeService.transMergeOrderItems(mergePo, user);
	}
	
	/**
	 * 获取所有合并项订单
	 * @author lory
	 * @Description TODO
	 * @Params @param page
	 * @Params @param status
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getAll/{status}/{page}")
	@ResponseBody
	public ModelResult getAllOrderItems(@PathVariable Integer page,@PathVariable Integer status) {
		if(page == null) {
			page =0;
		}
		OrderApplyMerge merge = new OrderApplyMerge();
		if(status == 0) {
			merge.setStatus("审核中");
		}else if (status == 1) {
			merge.setStatus("已付款");
		}
		PageHelper.startPage(page, PageSize.APPLY_SIZE);
		List<OrderApplyMerge> applyItems = mergeService.getAllOrderItems(merge);
		PageInfo<OrderApplyMerge> info = new PageInfo<>(applyItems);
		return ModelResult.buildOk(info);
	}
	
	/**
	 * 取消合并项功能，取消合并后删除合并列，多笔支付申请订单还原
	 * @author lory
	 * @Description TODO
	 * @Params @param applyItem
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/deleteMergeItems")
	@ResponseBody
	public ModelResult deleteOrderMergeItems(@RequestBody OrderApplyItem applyItem) {
		if(applyItem == null) {
			return ModelResult.buildError(1, "参数错误");
		}
		return mergeService.transDeleteOrderMergeItems(applyItem);
	}
	/**
	 * 取消合并功能
	 * @author lory
	 * @Description TODO
	 * @Params @param orderApplyMerge
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/robackMerge")
	@ResponseBody
	public ModelResult robackMerge(@RequestBody OrderApplyMerge orderApplyMerge) {
		if(orderApplyMerge ==  null || orderApplyMerge.getName() == null) {
			return ModelResult.buildError(1, "参数错误");
		}
		return mergeService.transRollBackOrder(orderApplyMerge);
	}
	
	/**
	 * 查看合并项订单条目
	 * @author lory
	 * @Description TODO
	 * @Params @param id
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getItemsByMergeId/{id}")
	@ResponseBody
	public ModelResult getItemsByMergeId(@PathVariable(required = true) Integer id) {
		List<OrderApplyItem> applyItems = mergeService.getItemsByMergeId(id);
		return ModelResult.buildOk(applyItems);
	}
	/**
	 * 模糊查询支付申请订单
	 * @author lory
	 * @Description TODO
	 * @Params @param vo
	 * @Params @param page
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/queryOrderApplyByMany/{page}")
	@ResponseBody
	public ModelResult queryOrderApplyByMany(@RequestBody OrderItemVo vo,@PathVariable Integer page) {
		if(page == null) {
			page = 0;
		}
		PageHelper.startPage(page,PageSize.APPLY_SIZE);
		if(vo.getOrderNo() != null) {
			vo.setOrderNo("%"+vo.getOrderNo()+"%");
		}
		if(vo.getName() != null) {
			vo.setName("%"+vo.getName()+"%");
		}
		if(vo.getItemNumber() !=null) {
			vo.setItemNumber("%"+vo.getItemNumber()+"%");
		}
		List<OrderItemVo> itemVos = mergeService.selectFromOrderApplyByMany(vo);
		PageInfo<OrderItemVo> info = new PageInfo<>(itemVos);
		return ModelResult.buildOk(info);
	}
	/**
	 * 模糊查询合并项
	 * @author lory
	 * @Description TODO
	 * @Params @param merge
	 * @Params @param page
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/queryOrderMergeByMany/{page}")
	@ResponseBody
	public ModelResult queryOrderMergeByMany(@RequestBody OrderApplyMerge merge,@PathVariable Integer page) {
		if(page ==null) {
			page = 0;
		}
		PageHelper.startPage(page, PageSize.APPLY_SIZE);
		if(merge.getMergeNumber() != null) {
			merge.setMergeNumber("%"+merge.getMergeNumber()+"%");
		}
		if(merge.getName() != null) {
			merge.setName("%"+merge.getName()+"%");
		}
		
		List<OrderApplyMerge> merges = mergeService.selectFromOrderApplyMergeByMany(merge);
		PageInfo<OrderApplyMerge> info = new PageInfo<>(merges);
		return ModelResult.buildOk(info);
	}
	
	/**
	 * 修改合并项订单状态
	 * @author lory
	 * @Description TODO
	 * @Params @param merge
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/updateOrderMergeBill")
	@ResponseBody
	public ModelResult updateOrderMergeBill(@RequestBody OrderApplyMerge merge) {
		if(merge == null) {
			return ModelResult.buildError(1, "参数错误");
		}
		mergeService.updateOrderMergeBill(merge);
		return ModelResult.buildOk("添加成功");
	}
}
