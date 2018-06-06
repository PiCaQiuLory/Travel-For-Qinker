package com.ss.mapper;

import com.ss.pojo.app.*;

import java.util.List;



public interface WechatDao {

	void appUserLogin(AppWechat wechatPojo);
	Integer appFindByName(String username);
	void addContent(List<AppData> appData);
	Integer getIdByWechatId(String IEMI);
	void addFile(AppFile file);
	AppFile getBytes();
	void addFiles(List<AppFile> fileApps);
	List<AppWechat> getAllPhone();
	List<AppDataPo> getContentByWechatId(Integer wechartId);
	void insertOutLineLog(AppLog log);
	Integer findByWechatId(String wechatId);
	Integer findContentByUrl(String contentName);
	List<AppData> findContentNames(List<String> contentNames);
	List<AppData> findFileNameExist(List<String> filename);
	List<AppLog> getLastAliveWechat();
	void insertErrorLog(List<AppLog> logs);
	AppData checkExistFileName(String filename);
	void addGroupWechat(List<AppData> appData);
	void addGroupRoom(AppRoom room);
	Integer findRoomExist(Integer roomId);
	void addWechatName(AppWechatName name);
}
