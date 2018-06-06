package com.ss.quartz;


import com.ss.mapper.WechatDao;
import com.ss.pojo.app.AppLog;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class OutLineJob implements Job{

	@Resource
	private WechatDao wechatDao;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		List<AppLog> logs = wechatDao.getLastAliveWechat();
		if(logs == null || logs.size() == 0) {
			return;
		}
		List<AppLog> error = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		Date now = calendar.getTime();
		for(AppLog log : logs) {
			if(log.getTime().before(now)) {
				error.add(log);
			}
		}
		wechatDao.insertErrorLog(error);
	}
	
	
	
}
