package com.Dealpf.demo.Util;


import ch.qos.logback.core.util.FileUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 使用腾讯云的cos服务搭建类似于图床的存储，并在数据库中保存图片路径
 */
public class Uploader {

    public static final Set<String> ALLOW_FILE_SUFFIX = new HashSet<>(Arrays.asList("jpg", "png", "jpeg", "gif"));
    public static String secretId ="AKIDnLflv67TlqhJflThV2TVIn20jlW0xE0W";
    public static String secretKey ="O5WfYBaizrb7Mozua5KIrhGZcR0IQ1NU";

    // 指定文件将要存放的存储桶
    private static String bucketName ="image-bucket-1304061950";
    private static String region ="ap-beijing";
    private static String url ="https://image-bucket-1304061950.cos.ap-beijing.myqcloud.com/";
    //存储根路径
    private static String prefix ="/images/";


    /**
     * 上传File类型的文件
     * @param file
     * @return 上传文件在存储桶的链接
     */
    public  String upload(File file) {
        //生成唯一文件名
        String newFileName = generateUniqueName(file.getName());
        //文件在存储桶中的key
        String key = prefix+newFileName;
            //初始化用户身份信息(secretId,secretKey)
            COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
            //设置bucket的区域
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            //生成cos客户端
            COSClient cosClient = new COSClient(cosCredentials, clientConfig);
            //创建存储对象的请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            //执行上传并返回结果信息
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            return url+key;
    }

    /**
     * upload()重载方法
     * @param multipartFile
     * @return 上传文件在存储桶的链接
     */
    public String upload(MultipartFile multipartFile) {
        //生成唯一文件名
        String newFileName = generateUniqueName(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        //文件在存储桶中的key
        String key = prefix+newFileName;
        //声明客户端
        COSClient cosClient = null;
        //准备将MultipartFile类型转为File类型
        File file = null;
        try {
            //生成临时文件
            file = File.createTempFile("temp",null);
            //将MultipartFile类型转为File类型
            multipartFile.transferTo(file);
            //初始化用户身份信息(secretId,secretKey)
            COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
            //设置bucket的区域
            ClientConfig clientConfig = new ClientConfig(new Region(region));
            //生成cos客户端
            cosClient = new COSClient(cosCredentials, clientConfig);
            //创建存储对象的请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
            //执行上传并返回结果信息
            cosClient.putObject(putObjectRequest);
            return url+key;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
        return null;
    }
    /**
     * 根据UUID生成唯一文件名
     * @param originalName
     * @return
     */
    public String generateUniqueName(String originalName) {
        return UUID.randomUUID() + originalName.substring(originalName.lastIndexOf("."));
    }

    /**
     //     * 获取文件的后缀
     //     * @param fileName
     //     * @return
     //     */
    protected String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index + 1);
            if (!suffix.isEmpty()) {
                return suffix;
            }
        }
        throw new IllegalArgumentException("非法的文件名称：" + fileName);
    }

}
