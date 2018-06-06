package com.ss.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.ss.pojo.Attachment;
import com.ss.utils.ImgException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

/**
 * Ali yun oss service
 */
@Component
public class OSSClientService {
    Logger log = LoggerFactory.getLogger(OSSClientService.class);

    String endpoint = "http://oss-cn-shanghai.aliyuncs.com";

    public static String ACCESS_ENDPOINT = "qinker.oss-cn-shanghai.aliyuncs.com";

    String accessKeyId = "LTAIjk2JWI23CTJ4";

    String accessKeySecret = "jlS1uvzkqQBJPJivMtGMfruUbcoKmo";

    String bucketName = "qinker";

    OSSClient client = new OSSClient(this.getEndpoint(), this.getAccessKeyId(), this.getAccessKeySecret());

    //文件存储目录
    private String fileDir = "test/";

    /**
     * get URL to access this image
     *
     * @param name image name stored in ali yun oss client
     * @return
     */
    private String getResourceName(String name) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(endpoint.replace("http://", "http://" + bucketName + "."))
//                .append("/").append(fileDir).append(name);
//        return sb.toString();
        return fileDir + name;
    }

    /**
     * get URL to access this image
     *
     * @param resourceName resource uri stored in ali yun oss client
     * @return
     */
    public static String getDirectUrl(String resourceName) {
        return ACCESS_ENDPOINT + resourceName;
    }

    /**
     * Upload resource to ali-yun oss.
     *
     * @param file
     * @throws IOException
     */
    public Attachment uploadObject(MultipartFile file) throws IOException {
        if (file == null || file.getSize() <= 0) {
            throw new ImgException("文件不存在");
        }
        String name = uploadImg2Oss(file);
        Attachment info = new Attachment();
        info.setRemoteName(name);
        info.setName(file.getOriginalFilename());
        info.setUrl(fileDir + "/" + name);
        return info;
    }

    protected OSSClient getClient() {
        return client;
    }

    /**
     * 销毁
     */
    public void shutdown() {
        if (client != null) {
            client.shutdown();
            client = null;
        }
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    /**
     * 上传图片
     *
     * @param url
     */
    public void uploadImg2Oss(String url) throws Exception {
        File fileOnServer = new File(url);
        InputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(fileInputStream, split[split.length - 1]);
        } catch (FileNotFoundException e) {
            throw new Exception("图片上传失败");
        }
    }

    public String uploadImg2Oss(InputStream inputStream, String originalFilename) {
        String extension;
        if (originalFilename.lastIndexOf(".") > 0) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        } else {
            extension = ".jpg";
        }
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + extension;
        try {
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            log.error("failed to upload", e);
            throw new ImgException("图片上传失败");
        }
    }

    public String uploadImg2Oss(MultipartFile file) {
        if (file.getSize() > 10240 * 1024) {
            throw new ImgException("上传图片大小不能超过10M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        Random random = new Random();
        String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadFile2OSS(inputStream, name);
            return name;
        } catch (Exception e) {
            throw new ImgException("图片上传失败");
        }
    }

    /**
     * 获得图片路径
     *
     * @param fileUrl
     * @return
     */
    public String getImgUrl(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.fileDir + split[split.length - 1]);
        }
        return null;
    }

    public InputStream getFile(String fileName){
        OSSObject ossObject = client.getObject(bucketName, fileDir+fileName);
        InputStream inputStream = ossObject.getObjectContent();
        return inputStream;
    }

    public void deleteFile(String fileName){
        client.deleteObject(bucketName, fileDir+fileName);
    }

    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param inputStream 文件流
     * @param fileName    文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public String uploadFile2OSS(InputStream inputStream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            if (log.isDebugEnabled()) {
                log.debug("Upload file " + fileDir + fileName);
            }
            PutObjectResult putResult = getClient().putObject(getBucketName(), fileDir + fileName, inputStream, objectMetadata);
            ret = putResult.getETag();
            log.info("ETag:" + ret);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return ret;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase("jpeg") ||
                FilenameExtension.equalsIgnoreCase("jpg") ||
                FilenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase("pptx") ||
                FilenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase("docx") ||
                FilenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = getClient().generatePresignedUrl(getBucketName(), key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }
}