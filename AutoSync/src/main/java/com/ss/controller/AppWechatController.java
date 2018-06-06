package com.ss.controller;

import com.ss.pojo.app.*;
import com.ss.service.AppWechatService;
import com.ss.utils.FastDFSUtil;
import com.sun.mail.iap.CommandFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AppWechatController {

	private final String DIR_NAME = "/root/develop/configuration/";
	private final String DIR_NAME_WIN = "D://";

	@Value("${SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@Resource
	private AppWechatService wechatService;

	@ResponseBody
	@RequestMapping("/app/userLogin")
	public AppResult userLogin(@RequestBody(required = true) AppWechat appWechat) throws CommandFailedException {
		AppResult res = wechatService.userLogin(appWechat);
		if (res.getCode() != 200) {
			return res;
		}
		return res;
	}

	@ResponseBody
	@RequestMapping("/app/addContent")
	public AppResult addContent(@RequestBody AppDataVo appDataVo) {
		return wechatService.addContent(appDataVo);
	}

	@ResponseBody
	@RequestMapping("/app/addFile")
	public AppResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
		String url = FastDFSUtil.uploadFile(file, DIR_NAME);
		if (url == null || url == "") {
			return AppResult.buildError("上传失败");
		}
		AppResult res = wechatService.addFile(file, IMAGE_SERVER_URL + url);
		return res;
	}

	@ResponseBody
	@RequestMapping("/app/addFiles")
	public AppResult filesUpload(@RequestParam("files") MultipartFile[] files) throws Exception {
		if (files == null || files.length == 0) {
			return AppResult.buildErrorParam("参数错误");
		}
		List<AppFile> fileApps = new ArrayList<>();
		List<String> contentNames = new ArrayList<>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].getOriginalFilename() == null || files[i].getOriginalFilename().equals("")) {
				return AppResult.buildErrorParam("文件名参数错误");
			}
			AppFile file = new AppFile();
			String url = FastDFSUtil.uploadFile(files[i], DIR_NAME);
			String fileName = files[i].getOriginalFilename().substring(0, files[i].getOriginalFilename().indexOf("."));
			file.setFileName(fileName);
			file.setUrl(IMAGE_SERVER_URL + url);
			fileApps.add(file);
			contentNames.add(fileName);
		}
		return wechatService.addFiles(fileApps, contentNames);
	}

	@RequestMapping("/app/alive/{wechatId}")
	@ResponseBody
	public AppResult changeStatusForApp(@PathVariable(required = true) String wechatId) {
		AppLog log = new AppLog();
		log.setMsg("alive");
		log.setTime(new Date());
		log.setWechatId(wechatId);
		wechatService.insertOutLineLog(log);
		return AppResult.buildOK("realive ok");
	}

	@RequestMapping("/app/getAllPhone")
	@ResponseBody
	public AppResult getAllPhone() {
		return wechatService.getAllPhone();
	}

	@RequestMapping("/app/getContent/{wechatId}")
	@ResponseBody
	public AppResult getContentByWechatId(@PathVariable(required = true) Integer wechatId) {
		return wechatService.getContentByWechatId(wechatId);
	}

	@RequestMapping("/app/upload/groupchat")
	@ResponseBody
	public AppResult uploadGroupChat(@RequestBody AppDataVo appDataVo) {
		if (appDataVo == null || appDataVo.getIemi() == null || appDataVo.getIemi().equals("")
				|| appDataVo.getAppData() == null || appDataVo.getAppData().size() == 0) {
			return AppResult.buildErrorParam("error params");
		}
		for (AppData data : appDataVo.getAppData()) {
			if(data.getRoomId() == null || data.getRoomId() == 0) {
				return AppResult.buildErrorParam("error params");
			}
		}
		return wechatService.uploadGroupWechat(appDataVo);
	}
	
	@RequestMapping("/app/add/room")
	@ResponseBody
	public AppResult addGroupRoom(@RequestBody AppRoom room) {
		if(room == null || room.getRoomName() == null || room.getRoomName().equals("")) {
			return AppResult.buildErrorParam("error params");
		}
		return wechatService.addGroupRoom(room);
	}
	
	@RequestMapping("/app/add/wechatname")
	@ResponseBody
	public AppResult addGroupRoom(@RequestBody AppWechatName name) {
		if(name == null || name.getWechatName() == null || name.getWechatName().equals("")) {
			return AppResult.buildErrorParam("error params");
		}
		return wechatService.addWechatName(name);
	}
}
