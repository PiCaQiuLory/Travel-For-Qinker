package com.ss.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ss.pojo.User;
import com.ss.pojo.VendorResponse;
import com.ss.pojo.vo.LoginUser;
import com.ss.pojo.vo.Message;
import com.ss.pojo.vo.UserVo;
import com.ss.service.IUiService;
import com.ss.service.IUserService;
import com.ss.utils.Constant;
import com.ss.utils.Util;
import com.taobao.api.internal.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	@Resource
	private IUserService userService;

	
	@Resource
	private IUiService uiService;

	@ResponseBody
	@RequestMapping(value="/alibaba", method= RequestMethod.GET)
	public Message auth(@RequestParam("code") String code){
		Message message = new Message();
		log.info("code is: " + code);
		
		String url="https://oauth.taobao.com/token";
		Map<String,String> props=new HashMap<String,String>();
	    props.put("grant_type","authorization_code");
	    props.put("code",code);
	    props.put("client_id","23481215");
	    props.put("client_secret","1e5061c660f996ecd925e3f8c22d12d1");
	    props.put("redirect_uri","http://112.124.107.209/api");
	    props.put("view","web");
	    String s="";
     	try{
     		s=WebUtils.doPost(url, props, 30000, 30000);
     		JSONObject jo = (JSONObject)JSON.parse(s);
     		String token = jo.get("access_token").toString();
     		VendorResponse vr = new VendorResponse();
     		vr.setVendorId("alibaba");
     		
     		List<VendorResponse> vrList = uiService.queryVendorResponse(vr);
     		if(vrList!=null && vrList.size()>0){
     			vr = vrList.get(0);
     			vr.setToken(token);
     			uiService.updateVendorResponse(vr);
     		}else{
     			vr.setToken(token);
         		uiService.insertVendorResponse(vr);
     		}
     		
         	log.info("token is " + token );
         	
         }catch(IOException e){
             e.printStackTrace();
         }
       
	
		message.setCode(Constant.SUCCESS);
		return message;
	}

	/*@ResponseBody
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public Message login(@RequestBody LoginUser loginUser, HttpServletRequest request){
		Message message = new Message();
		String token = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length>0) {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equalsIgnoreCase("TT_TOKEN")) {
					token = cookie.getValue();
				}
			}
			if(token == null) {
				message.setCode(Constant.FAILURE);
				message.setValue("Login fail");
				return message;
			}
			String val = jedisClient.get("SESSION:"+token);
			if(val == null) {
				message.setCode(Constant.FAILURE);
				message.setValue("Login fail");
				return message;
			}else {
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				UserVoForRedis user = gson.fromJson(val, UserVoForRedis.class);
				User currUser = new User();
				currUser.setId(user.getId());
				currUser.setEmail(user.getEmail());
				currUser.setNickname(user.getNickname());
				currUser.setPassword(user.getPassword());
				currUser.setRole(user.getRole());
				UserVo userVo = new UserVo();
				request.getSession().setAttribute("user", currUser);
				userVo.setUsername(user.getUser_name());
				userVo.setLoginSuccess(true);
				userVo.setEmail(user.getEmail());
				userVo.setRole(user.getRole());
				userVo.setId(user.getId());
				userVo.setNickname(user.getNickname());
				message.setCode(Constant.SUCCESS);
				message.setValue(userVo);
				return message;
			}
		}else {
			message.setCode(Constant.FAILURE);
			message.setValue("Login fail");
			return message;
		}
	}*/
	
	@ResponseBody
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public Message login(@RequestBody LoginUser loginUser, HttpServletRequest request){
		Message message = new Message();
		UserVo userVo = new UserVo();
		String userName = loginUser.getUserName();
		String password = loginUser.getPassword();
		User user = userService.login(userName, password);
		if(user == null){
			message.setCode(Constant.FAILURE);
			message.setValue("Login fail");
			return message;
		}
		user.setLastLogin(new Date().getTime());
		userService.update(user);

		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		userVo.setUsername(user.getUserName());
		userVo.setLoginSuccess(true);
		userVo.setEmail(user.getEmail());
		userVo.setRole(user.getRole());
		userVo.setId(user.getId());
		userVo.setNickname(user.getNickname());
		message.setCode(Constant.SUCCESS);
		message.setValue(userVo);
		return message;
	}

	//admingetUserCount
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Message saveAdminUser(@RequestBody User vo){
		String error = validate(vo);
		if(error != null){
			return new Message(Constant.FAILURE, error);
		}
		User user;
		if(vo.getId() == 0){
			user = new User();
		} else {
			user = userService.getUserById(vo.getId());
		}
		user.setRole(vo.getRole());
		user.setEmail(vo.getEmail());
		user.setPassword(vo.getPassword());
		user.setNickname(vo.getNickname());
		if(vo.getId() == 0) {
			user.setUserName(vo.getUserName());
			userService.save(user);
		}else{
			userService.update(user);
		}
		Message message = new Message();
		message.setCode(Constant.SUCCESS);
		return message;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateUser",method = RequestMethod.POST)
	public Message updateUser(@RequestBody User vo) {
		String error = validate(vo);
		if(error != null){
			return new Message(Constant.FAILURE, error);
		} 
		User user;
		if(vo.getId() == 0){
			user = new User();
		} else {
			user = userService.getUserById(vo.getId());
		}
		user.setRole(vo.getPassword());
		
		Message message = new Message();
		try {
			userService.update(user);
		}catch (Exception e) {
			log.error("修改用户信息失败:"+e.getStackTrace());
			message.setCode(Constant.FAILURE);
			return message;
		}
		message.setCode(Constant.SUCCESS);
		return message;
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<User> getAllAdminUsers(){
		return userService.findAll();
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getAdmin(@PathVariable("id") Integer id){
		User user = userService.getUserById(id);
		if(user.getRole().equals(Constant.ROLE_ADMIN)){
			return user;
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Message deleteAdminUser(@PathVariable("id") long id){
		userService.delete(id);
		Message message = new Message();
		message.setCode(Constant.SUCCESS);
		return message;
	}

	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}

	@ResponseBody
	private String validate(User vo){
		if(!Util.isEmpty(vo.getUserName())){
			User user = userService.findByUserName(vo.getUserName());
			if(user != null && user.getId() != vo.getId()){
				return "This User Name is already used!";
			}
		}

		return null;
	}

}
