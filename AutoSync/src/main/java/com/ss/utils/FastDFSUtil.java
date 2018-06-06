package com.ss.utils;


import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.multipart.MultipartFile;

public class FastDFSUtil {
	
	public static String uploadFile(MultipartFile file,String path) throws Exception {
		String configFileName = path+"client.conf";
		try {
			ClientGlobal.init(configFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] files = null;
		String fileExtName = "";
		if (file.getOriginalFilename().contains(".")) {
			fileExtName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		} else {
			System.out.println("Fail to upload file, because the format of filename is illegal.");
			return null;
		}
		// 建立连接
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient client = new StorageClient(trackerServer, storageServer);

		// 设置元信息
		NameValuePair[] metaList = new NameValuePair[3];
		String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf("."));
		metaList[0] = new NameValuePair("fileName", fileName);
		metaList[1] = new NameValuePair("fileExtName", fileExtName);
		metaList[2] = new NameValuePair("fileLength", String.valueOf(file.getSize()));

		// 上传文件
		try {
			files = client.upload_file(file.getBytes(), fileExtName, metaList);
		} catch (Exception e) {
			System.out.println("Upload file \"" + fileName + "\"fails");
		}
		trackerServer.close();
		String fileNameUrl = files[0].trim()+"/"+files[1].trim();
		return fileNameUrl;
	}
}
