package com.ss.mapper;

import java.util.List;

import com.ss.pojo.RemoteQuery;

public interface IRemoteQueryDao {
	public RemoteQuery queryById(String id);
	
	public List<RemoteQuery> queryAll();
}
