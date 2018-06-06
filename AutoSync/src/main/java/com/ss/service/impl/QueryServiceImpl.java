package com.ss.service.impl;

import com.ss.mapper.IRemoteQueryDao;
import com.ss.pojo.RemoteQuery;
import com.ss.service.IQueryService;
import com.ss.utils.DBHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

@Service("queryServiceImpl")
public class QueryServiceImpl implements IQueryService {

	@Resource
	private IRemoteQueryDao remoteQueryDao;
	
	// for windows
	//private final String path = "C:\\Users\\jie\\OneDrive\\文档\\";
	
	// for server
	private final String path = "/root/files/export/";
	
	
	public void query(String id) {
		
		RemoteQuery rq = remoteQueryDao.queryById(id);

		if(rq!=null){
			query(rq.getQuerySql(), path + rq.getQueryName());
		}else{
			//FIXME
			System.out.println(1111);
		}
	}

	@Override
	public <T> T query(String querySql, String name) {
		DBHelper db1 = null;
		ResultSet ret = null;

		db1 = new DBHelper(querySql);// 创建DBHelper对象

		try {
			ret = db1.pst.executeQuery();// 执行语句，得到结果集

			ResultSetMetaData rsmd = ret.getMetaData();

			int count = rsmd.getColumnCount();

			String[] columnName = new String[count];
			for (int i = 0; i < count; i++) {
				columnName[i] = rsmd.getColumnName(i + 1);
			}

			Hashtable<Integer, String[]> table = new Hashtable<>(30);
			table.put(0, columnName);
			Integer rowCnt = 1;
			while (ret.next()) {

				String[] row = new String[count];
				for (int i = 1; i <= count; i++) {
					row[i - 1] = ret.getObject(i) == null ? "" : ret.getObject(i).toString();
				}
				table.put(rowCnt, row);
				rowCnt++;
			}
			createFile(table, name);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
				if (db1 != null) {
					db1.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private String generateFilename(String t) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		return t + "_" + sdf.format(new Date()) + ".csv";
	}

	private void createFile(Hashtable<Integer, String[]> table, String name) {
		OutputStreamWriter fw = null;
		try {
			fw = new OutputStreamWriter(new FileOutputStream(generateFilename(name)), "GBK");
			for (int i = 0; i < table.size(); i++) {
				String[] temp = table.get(i);
				for (String string : temp) {
					string = string.replaceAll(",", "&%&");
					string = string.replaceAll("\n\r|\r|\n|\r\n", "&#&");
				}
				fw.write(String.join(",", temp));
				fw.write("\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
