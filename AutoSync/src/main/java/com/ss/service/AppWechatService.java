package com.ss.service;


import com.ss.exception.CommonResultException;
import com.ss.pojo.app.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



public interface AppWechatService {

	public AppResult userLogin(AppWechat wechatPojo) throws CommonResultException;
	
	public AppResult addContent(AppDataVo appDatas);
	
	public AppResult addFile(MultipartFile file, String url) throws Exception;
	
	public AppResult addFiles(List<AppFile> appFiles, List<String> contentNames)throws Exception;
	
	public AppResult getAllPhone();
	
	public AppResult getContentByWechatId(Integer wechatId);
	
	void insertOutLineLog(AppLog log);
	
	AppResult uploadGroupWechat(AppDataVo appDataVo);
	
	AppResult addGroupRoom(AppRoom room);
	
	AppResult addWechatName(AppWechatName name);
}
