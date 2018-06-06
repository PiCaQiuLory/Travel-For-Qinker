package com.ss.controller;

import com.github.pagehelper.PageInfo;
import com.ss.pojo.ModelResult;
import com.ss.pojo.User;
import com.ss.pojo.vo.ApplyReimbursement;
import com.ss.service.ApplyReimbursementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 报销模块控制层：流程为定制师提交报销，到一级审核人这里，审核人通过状态改为待付款，再到财务这里审核
 * @author lory
 * @description TODO
 * @Package com.ss.controller
 * @date 2018年2月24日
 */
@Controller
@RequestMapping("/applyRei")
@Slf4j
public class ApplyReimbursementController{
	
	@Autowired
	private ApplyReimbursementService applyService;
	


	/**
	 * 获取用户所有报销单方法
	 * @author lory
	 * @Description TODO
	 * @Params @param page
	 * @Params @param request
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getAllApplyByUser")
	public @ResponseBody ModelResult getAllApplyWithUser(@RequestParam(required = true,defaultValue = "0") Integer page, HttpServletRequest request) {
		User currUser = (User) request.getSession().getAttribute("user");
		//PageHelper.startPage(page, pageSize);
		try {
			List<ApplyReimbursement> reimbursements = applyService.getAllForUser(currUser.getId());
			for(ApplyReimbursement reimbursement : reimbursements) {
				if(reimbursement.getAuditorId() != null) {
					reimbursement.setNickName(applyService.getNickName(reimbursement.getAuditorId()));
				}
			}
			PageInfo<ApplyReimbursement> pageInfo = new PageInfo<ApplyReimbursement>(reimbursements);
			return ModelResult.buildOk(pageInfo);
		}catch (Exception e) {
			log.error("查询出错:"+e.getMessage());
			return null;
		}
	}
	/**
	 * 根据当前用户状态获取所有的待审核订单
	 * @author lory
	 * @Description TODO
	 * @Params @param request
	 * @Params @param page
	 * @Params @param status
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getAllApplyWithAuditor/{status}")
	public @ResponseBody ModelResult getAllNonApplyWithAuditor(HttpServletRequest request,@RequestParam(required = true,defaultValue = "0") Integer page,@PathVariable Integer status) {
		//PageHelper.startPage(page, pageSize);
		ApplyReimbursement applyReimbursement = new ApplyReimbursement();
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			return ModelResult.buildError(1, "请先登录");
		}
		if(status == 0) {
			if(user.getRole().trim().equals("inspector")) {
				applyReimbursement.setStatus("审核中");
			}else {
				applyReimbursement.setStatus("待付款");
			}
			
		}else if (status == 1) {
			if(user.getRole().trim().equals("inspector")) {
				applyReimbursement.setStatus("待付款");
			}else {
				applyReimbursement.setStatus("已付款");
			}
		}else {
			return ModelResult.buildError(1, "路径错误");
		}
		List<ApplyReimbursement> reimbursements = applyService.selectByGroupWithNoJudy(applyReimbursement);
		PageInfo<ApplyReimbursement> pageInfo = new PageInfo<ApplyReimbursement>(reimbursements);
		return ModelResult.buildOk(pageInfo);
	}
	
	@RequestMapping("/getAllApplyWithAuditorUserItems/{status}")
	public @ResponseBody ModelResult getAllApplyWithAuditorUserItems(HttpServletRequest request,@PathVariable Integer status,@RequestBody ApplyReimbursement applyReimbursement) {
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			return ModelResult.buildError(1, "请先登录");
		}
		if(status == 0) {
			if(user.getRole().trim().equals("inspector")) {
				applyReimbursement.setStatus("审核中");
			}else {
				applyReimbursement.setStatus("待付款");
			}
		}else if (status == 1) {
			if(user.getRole().trim().equals("inspector")) {
				applyReimbursement.setStatus("待付款");
			}else {
				applyReimbursement.setStatus("已付款");
			}
		}else {
			return ModelResult.buildError(1, "路径错误");
		}
		List<ApplyReimbursement> reimbursements = applyService.selectByGroupWithNoJudyUserItem(applyReimbursement);
		return ModelResult.buildOk(reimbursements);
	}
	
	

	/**
	 * 用户新增报销单方法
	 * @author lory
	 * @Description TODO
	 * @Params @param reimbursement
	 * @Params @param request
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/addOneEvent")
	public @ResponseBody ModelResult addOneEvent(@RequestBody ApplyReimbursement reimbursement,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if(reimbursement == null) {
			return ModelResult.buildError(101, "报销单为空");
		}
		reimbursement.setStatus("审核中");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		reimbursement.setApplyTime(cal.getTime());
		reimbursement.setUserId(user.getId());
		try{
			applyService.addOneEvent(reimbursement);
			return ModelResult.buildOk("新增成功");
		}catch (Exception e) {
			log.error("报销单新增失败："+e.getMessage());
			return ModelResult.buildError(102, "系统错误");
		}
	}

	/**
	 * 审核人审核报销单对应方法
	 * @author lory
	 * @Description TODO
	 * @Params @param status
	 * @Params @param request
	 * @Params @param reimbursement
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/applyAndJuby/{status}")
	public @ResponseBody ModelResult applyAndJudy(@PathVariable int status,HttpServletRequest request,@RequestBody ApplyReimbursement reimbursement) {
		User currUser = (User) request.getSession().getAttribute("user");
		if(currUser == null) {
			return ModelResult.buildError(1, "请先登录");
		}
		if(!currUser.getRole().equals("admin") && !currUser.getRole().trim().equals("inspector")) {
			return ModelResult.buildError(102, "非法操作，无权限");
		}
		try {
			if(status == 1) {
				if(currUser.getRole().trim().equals("admin")) {
					reimbursement.setStatus("已付款");
				}else {
					reimbursement.setStatus("待付款");
				}
				reimbursement.setAuditorId(currUser.getId());
				reimbursement.setAuditorDate(new Date());
				applyService.applyAndJudy(reimbursement);
				return ModelResult.buildOk("修改成功");
			}else if(status == 2) {
				reimbursement.setAuditorId(currUser.getId());
				reimbursement.setStatus("已拒绝");
				reimbursement.setAuditorDate(new Date());
				applyService.applyAndJudy(reimbursement);
				return ModelResult.buildOk("修改成功");
			}
		} catch (Exception e) {
			log.error("操作失败："+e.getStackTrace());
			return ModelResult.buildError(104, "修改失败");
		}
		return ModelResult.buildError(104, "修改失败");
	}
	
	/**
	 * 模糊查询报销单
	 * @author lory
	 * @Description TODO
	 * @Params @param userId
	 * @Params @param page
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/searchUser/{userId}/{page}")
	@ResponseBody
	public ModelResult searchByUser(@PathVariable(required=true) Integer userId,@PathVariable(required=true) Integer page) {
		if(page==null) {
			page=0;
		}
		//PageHelper.startPage(page, PageSize.APPLY_RE);
		List<ApplyReimbursement> applyReimbursements = applyService.searchByUser(userId);
		PageInfo<ApplyReimbursement> info = new PageInfo<>(applyReimbursements);
		return ModelResult.buildOk(info);
	}
	/**
	 * 根据用户分页查询其报销单
	 * @author lory
	 * @Description TODO
	 * @Params @param userId
	 * @Params @param page
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/searchUser/judy/{userId}/{page}")
	@ResponseBody
	public ModelResult searchByUserJudy(@PathVariable(required=true) Integer userId,@PathVariable(required=true) Integer page) {
		if(page==null) {
			page=0;
		}
		//PageHelper.startPage(page, PageSize.APPLY_RE);
		List<ApplyReimbursement> applyReimbursements = applyService.searchByUserJudy(userId);
		PageInfo<ApplyReimbursement> info = new PageInfo<>(applyReimbursements);
		return ModelResult.buildOk(info);
	}

	/**
	 * 获取未审核报销单数目方法
	 * @author lory
	 * @Description TODO
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/getNumber")
	public @ResponseBody ModelResult getNumber() {
		return ModelResult.buildOk(applyService.getNumber());
	}
	
	/**
	 * 批量审核方法
	 * @author lory
	 * @Description TODO
	 * @Params @param request
	 * @Params @param applyReimbursements
	 * @Params @return
	 * @return ModelResult
	 */
	@RequestMapping("/judyList")
	@ResponseBody
	public ModelResult judyList(HttpServletRequest request,@RequestBody List<ApplyReimbursement> applyReimbursements) {
		User currUser = (User) request.getSession().getAttribute("user");
		if(currUser == null) {
			return ModelResult.buildError(1, "请先登录");
		}
		if(applyReimbursements !=null && applyReimbursements.size()>0) {
			if(currUser.getRole().trim().equals("inspector")) {
				applyService.judyListForAuditor(applyReimbursements);
			}else if(currUser.getRole().trim().equalsIgnoreCase("admin")) {
				applyService.judyList(applyReimbursements);
			}
			return ModelResult.buildOk("付款成功");
		}
		return ModelResult.buildError(1, "参数错误");
	}

}
