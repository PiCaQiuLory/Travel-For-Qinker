package com.ss.service.impl;

import com.ss.code.ErrorCode;
import com.ss.exception.CommonResultException;
import com.ss.mapper.WechatDao;
import com.ss.pojo.app.*;
import com.ss.service.AppWechatService;
import com.ss.utils.DateConventer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;



@Service
public class AppWechatImpl implements AppWechatService {

	@Resource
	private WechatDao wechatDao;

	public AppResult userLogin(AppWechat wechatPojo) throws CommonResultException {
		if (wechatPojo == null || wechatPojo.getUsername() == null || wechatPojo.getWechatId() == null) {
			return AppResult.buildErrorParam("参数错误");
		}
		Integer userId = wechatDao.appFindByName(wechatPojo.getUsername());
		if (userId == null || userId == 0) {
			return AppResult.buildError("用户姓名不存在");
		}
		Integer hasUser = wechatDao.findByWechatId(wechatPojo.getWechatId());
		if (hasUser != null) {
			return AppResult.buildError("用户已存在");
		}
		wechatPojo.setUserId(userId);
		try {
			wechatDao.appUserLogin(wechatPojo);
			return AppResult.buildOK("注册成功");
		} catch (Exception e) {
			return AppResult.buildError("System error");
		}
	}

	@Override
	@Transactional
	public AppResult addContent(AppDataVo appDatas) {
		if (appDatas == null || appDatas.getIemi() == null || appDatas.getIemi() == "" || appDatas.getAppData() == null
				|| appDatas.getAppData().size() == 0) {
			return AppResult.buildErrorParam("error params");
		}
		List<String> filename = new ArrayList<>();
		for (AppData data : appDatas.getAppData()) {
			if (data.getType() == 1) {
				if (!data.getFilename().trim().equals("null")) {
					return AppResult.buildErrorParam("error params");
				} else {
					data.setFilename("string");
				}
			} else {
				if (data.getFilename() == null || data.getFilename().trim().equals("")) {
					return AppResult.buildErrorParam("error params");
				} else {
					if (data.getFilename().contains(".")) {
						return AppResult.buildErrorParam("error params");
					} else {
						filename.add(data.getFilename().trim());
					}
				}
			}
		}
		Integer id = wechatDao.getIdByWechatId(appDatas.getIemi());
		if (id == null) {
			return AppResult.buildErrorParam("该手机未注册");
		}
		try {
			for (AppData appData : appDatas.getAppData()) {
				appData.setTime(DateConventer.getTimeFromStamp(Long.valueOf(appData.getTimestamp())));
				appData.setAppWechatId(id);
			}
			if (filename != null && filename.size() > 0) {
				List<AppData> names = wechatDao.findFileNameExist(filename);
				if (names != null && names.size() != 0) {
					return AppResult.buildErrorParam("filename already in use");
				}
			}

			wechatDao.addContent(appDatas.getAppData());
		} catch (Exception e) {
			throw new CommonResultException(ErrorCode.ERROR_SYSTEM);
		}
		return AppResult.buildOK("upload success");
	}

	@Override
	public AppResult addFile(MultipartFile file, String url) throws Exception {
		String contentName = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf("."));
		Integer id = wechatDao.findContentByUrl(contentName);
		if (id == null || id == 0) {
			return AppResult.buildErrorParam("upload failed,filename not exist in content table");
		}
		AppFile fileApp = new AppFile();
		fileApp.setFileName(contentName);
		fileApp.setUrl(url);
		wechatDao.addFile(fileApp);
		return AppResult.buildOK("upload success");
	}

	@Override
	@Transactional
	public AppResult addFiles(List<AppFile> fileApps, List<String> contentNames) throws Exception {
		for (String filename : contentNames) {
			AppData data = wechatDao.checkExistFileName(filename.trim());
			if (data == null) {
				return AppResult.buildErrorParam("error upload,filename is not exist in content table");
			}
		}
		try {
			wechatDao.addFiles(fileApps);
		} catch (Exception e) {
			throw new CommonResultException(ErrorCode.ERROR_SYSTEM);
		}
		return AppResult.buildOK("upload success");
	}

	@Override
	public AppResult getAllPhone() {
		List<AppWechat> appWechats = wechatDao.getAllPhone();
		return AppResult.buildOK(appWechats);
	}

	@Override
	public AppResult getContentByWechatId(Integer wechatId) {
		List<AppDataPo> datapos = wechatDao.getContentByWechatId(wechatId);
		return AppResult.buildOK(datapos);
	}

	@Override
	public void insertOutLineLog(AppLog log) {
		wechatDao.insertOutLineLog(log);
	}

	@Override
	public AppResult uploadGroupWechat(AppDataVo appDatas) {
		List<String> filename = new ArrayList<>();
		for (AppData data : appDatas.getAppData()) {
			if (data.getType() == 1) {
				if (!data.getFilename().trim().equals("null")) {
					return AppResult.buildErrorParam("error params");
				} else {
					data.setFilename("string");
				}
			} else {
				if (data.getFilename() == null || data.getFilename().trim().equals("")) {
					return AppResult.buildErrorParam("error params");
				} else {
					if (data.getFilename().contains(".")) {
						return AppResult.buildErrorParam("error params");
					} else {
						filename.add(data.getFilename().trim());
					}
				}
			}
		}
		Integer id = wechatDao.getIdByWechatId(appDatas.getIemi());
		if (id == null) {
			return AppResult.buildErrorParam("该手机未注册");
		}
		try {
			for (AppData appData : appDatas.getAppData()) {
				Integer roomId = wechatDao.findRoomExist(appData.getRoomId());
				if(roomId == null || roomId == 0) {
					return AppResult.buildErrorParam("room is not exist");
				}
				appData.setTime(DateConventer.getTimeFromStamp(Long.valueOf(appData.getTimestamp())));
				appData.setAppWechatId(id);
			}
			if (filename != null && filename.size() > 0) {
				List<AppData> names = wechatDao.findFileNameExist(filename);
				if (names != null && names.size() != 0) {
					return AppResult.buildErrorParam("filename already in use");
				}
			}
			wechatDao.addGroupWechat(appDatas.getAppData());
		} catch (Exception e) {
			throw new CommonResultException(ErrorCode.ERROR_SYSTEM);
		}
		return AppResult.buildOK("upload success");
	}

	@Override
	public AppResult addGroupRoom(AppRoom room) {
		try {
			wechatDao.addGroupRoom(room);
			return AppResult.buildOK(room.getId());
		} catch (Exception e) {
			throw new CommonResultException(ErrorCode.ERROR_SYSTEM);
		}
	}

	@Override
	public AppResult addWechatName(AppWechatName name) {
		try {
			wechatDao.addWechatName(name);
			return AppResult.buildOK(name.getId());
		} catch (Exception e) {
			throw new CommonResultException(ErrorCode.ERROR_SYSTEM);
		}
	}

}
