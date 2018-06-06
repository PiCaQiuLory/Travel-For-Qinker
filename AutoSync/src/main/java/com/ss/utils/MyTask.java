package com.ss.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;


public class MyTask extends TimerTask{
	private Timer timer;
	private ConcurrentHashMap<String, Boolean> hashMap;
	private String wechatId;
	
	public  MyTask(Timer timer,ConcurrentHashMap<String, Boolean> hashMap,String wechatId) {
		this.timer = timer;
		this.hashMap = hashMap;
		this.wechatId = wechatId;
	}
	int i =1;
	@Override
	public void run() {
		System.out.println("*-----"+wechatId+"**********"+hashMap.get(wechatId));
		hashMap.put(wechatId, false);
		i++;
		if(i == 2) {
			timer.cancel();
		}
	}

	
}
