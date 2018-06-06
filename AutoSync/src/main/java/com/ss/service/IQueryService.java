package com.ss.service;

public interface IQueryService {
	public <T> T query(String querySql, String name);
	
	public void query(String id);
}
