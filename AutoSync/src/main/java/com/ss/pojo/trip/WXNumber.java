package com.ss.pojo.trip;

import java.io.Serializable;

public class WXNumber implements Serializable{

	private String encrypted;
	
	private String session_key;
	
	private String iv;

	public String getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}
	
	
}
