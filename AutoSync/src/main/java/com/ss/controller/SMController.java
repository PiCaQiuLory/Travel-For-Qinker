package com.ss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class SMController {

	@RequestMapping("/shimojs/zipdownload")
	public String downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/");
		path = path+"shimojs/msg.js";
		try {
			String content = read(request).get("new");
			write(path, content);
			List<File> listFile = new ArrayList<>();
			File allFile = new File(request.getSession().getServletContext().getRealPath("/") + "shimojs/");
			if (allFile.exists()) {
				File[] fileArr = allFile.listFiles();
				for (File file2 : fileArr) {
					listFile.add(file2);
				}
			}
			String filename = "shimojs.zip";
			String outFilePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
			File fileZip = new File(outFilePath + filename);
			FileOutputStream outStream = new FileOutputStream(fileZip);
			ZipOutputStream zipOutStream = new ZipOutputStream(outStream);
			zipFile(listFile, zipOutStream);
			zipOutStream.close();
			outStream.close();
			this.downloadFile(fileZip, response, true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			String content = read(request).get("old");
			write(path, content);
		}
		return null;
	}
	private void zipFile(List<File> files, ZipOutputStream outputStream) throws IOException, ServletException {
        try {
            int size = files.size();
            // 压缩列表中的文件
            for (int i = 0; i < size; i++) {
                File file = (File) files.get(i);
                zipFile(file, outputStream);
            }
        } catch (IOException e) {
            throw e;
        }
    }
	private void zipFile(File inputFile, ZipOutputStream outputstream) throws IOException, ServletException {
		try {
			if (inputFile.exists()) {
				if (inputFile.isFile()) {
					FileInputStream inStream = new FileInputStream(inputFile);
					BufferedInputStream bInStream = new BufferedInputStream(inStream);
					ZipEntry entry = new ZipEntry(inputFile.getName());
					outputstream.putNextEntry(entry);

					final int MAX_BYTE = 10 * 1024 * 1024; // 最大的流为10M
					long streamTotal = 0; // 接受流的容量
					int streamNum = 0; // 流需要分开的数量
					int leaveByte = 0; // 文件剩下的字符数
					byte[] inOutbyte; // byte数组接受文件的数据

					streamTotal = bInStream.available(); // 通过available方法取得流的最大字符数
					streamNum = (int) Math.floor(streamTotal / MAX_BYTE); // 取得流文件需要分开的数量
					leaveByte = (int) streamTotal % MAX_BYTE; // 分开文件之后,剩余的数量

					if (streamNum > 0) {
						for (int j = 0; j < streamNum; ++j) {
							inOutbyte = new byte[MAX_BYTE];
							// 读入流,保存在byte数组
							bInStream.read(inOutbyte, 0, MAX_BYTE);
							outputstream.write(inOutbyte, 0, MAX_BYTE); // 写出流
						}
					}
					// 写出剩下的流数据
					inOutbyte = new byte[leaveByte];
					bInStream.read(inOutbyte, 0, leaveByte);
					outputstream.write(inOutbyte);
					outputstream.closeEntry(); // Closes the current ZIP entry
					// and positions the stream for
					// writing the next entry
					bInStream.close(); // 关闭
					inStream.close();
				}
			} else {
				throw new ServletException("文件不存在！");
			}
		} catch (IOException e) {
			throw e;
		}
	}
	private void downloadFile(File file,HttpServletResponse response,boolean isDelete) {
        try {
            // 以流的形式下载文件。
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"),"ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            if(isDelete)
            {
                file.delete();        //是否将生成的服务器端文件删除
            }
         } 
         catch (IOException ex) {
            ex.printStackTrace();
        }
    } 
	
	private Map<String, String> read(HttpServletRequest request) throws Exception {
		BufferedReader	bufferReader = null;
		StringBuffer buffer = new StringBuffer();
		StringBuffer bufferOld = new StringBuffer();
		try {
			String path = request.getSession().getServletContext().getRealPath("/");
			File file2 = new File(path+"shimojs/msg.js");
			bufferReader = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
			String temp = null;
			while((temp = bufferReader.readLine())!=null) {
				if(temp.contains("userName")) {
					bufferOld.append(temp);
					String te = "const userName = '郁佳用'";
					buffer.append(te);
				}else {
					bufferOld.append(temp);
					buffer.append(temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			bufferReader.close();
		}
		Map<String, String> contentMap = new HashMap<>();
		contentMap.put("new", buffer.toString());
		contentMap.put("old", bufferOld.toString());
		return contentMap;
		
	}
	
	private void write(String filePath, String content) {  
        BufferedWriter bw = null;  
          
        try {  
            // 根据文件路径创建缓冲输出流  
            bw = new BufferedWriter(new FileWriter(filePath));  
            // 将内容写入文件中  
            bw.write(content);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭流  
            if (bw != null) {  
                try {  
                    bw.close();  
                } catch (IOException e) {  
                    bw = null;  
                }  
            }  
        }  
    }  
}
