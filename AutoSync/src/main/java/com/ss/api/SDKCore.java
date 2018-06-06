package com.ss.api;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class SDKCore {
	

//	private static String _root = "http://58.221.127.200:8080/api/";
//	private static String _root = "http://tourapi.ctrip.com/api/";
	public static <T> T XMLStringToObj(Class<T> cls , String str) throws Exception
	{
		Serializer serializer = new Persister();
		T res = (T) serializer.read(cls, str);
		
		return res;
	}
	
	public static <T> String ObjToXMLString(T entity) throws Exception
	{
		Serializer serializer = new Persister();
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        serializer.write(entity, bos);
		
		return bos.toString();
	}
	
	public static String PostRequest(String root, String request, String method)
	{
		String responseString = "";
		
		String url = root + method;
		
		try 
		{
			HttpURLConnection urlConn;
			URL mUrl = new URL(url);
			urlConn = (HttpURLConnection) mUrl.openConnection();
			urlConn.setDoOutput(true);
			
			urlConn.addRequestProperty("Content-Type", "application/" + "POST");
			if (request != null) {
				urlConn.setRequestProperty("Content-Length", Integer.toString(request.length()));
				urlConn.getOutputStream().write(request.getBytes("UTF8"));
			}
			
			int responseCode = urlConn.getResponseCode();
			log.info("\nSending 'POST' request to URL : " + url);
			log.info("Response Code : " + responseCode);
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(urlConn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			responseString = response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseString;
	}
}
