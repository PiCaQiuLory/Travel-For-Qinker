package com.ss.pojo;


public class RemoteQuery {
	private long id;
	
	private String queryName;
	
	private String querySql;
	
	private String path;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
