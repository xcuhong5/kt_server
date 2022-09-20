package com.vod.service.impl;

import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.vod.service.FileService;
import com.vod.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-09 16:17
 */
/*
*  引入 依赖，在腾讯云 cos 找到相关 地域 id key； 将key id 地域 等信息 配置到yml，通过实体读取
* */
@Service
public class FileServiceImpl implements FileService {
    // 上传 文件 到 cos 的实现
    @Override
    public String upload(MultipartFile file) {
        // 地域 ，Endpoint 以 北京 为例，其它Region请按实际情况填写。
        // ConstantPropertiesUtil 是工具类，将cos 的信息配置在yml；该类 是获取yml 信息的
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 获取 桶 名字
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        // 1. 初始化用户身份信息（secretId, secretKey）。
        String secretId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String secretKey = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // 初始化 cos， 传入 id 和 key
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // 2. 设置 bucket 的 地域，将地域 传入，创建 地域 对象
        Region region = new Region(endpoint);
        // clientConfig 中包含了设置 region, https(默认 http),超时, 代理等 set 方法
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            // 指定要上传的文件
            InputStream inputStream = file.getInputStream();
            // 指定文件将要存放的存储桶
            // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            String key = UUID.randomUUID().toString().replaceAll("-", "") +
                    file.getOriginalFilename();
            //获取当前 年月日，作为 桶下面的目录结构
            String dateUrl = new DateTime().toString("yyyy/MM/dd");
            key = dateUrl + "/" + key;
            // 记录对象的 元信息
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 上传对象
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
            // 执行上传
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(JSON.toJSONString(putObjectResult));
            //https://webkt-1259440123.cos.ap-beijing.myqcloud.com/2022/09/09/bb5c775b230a4f3fbebfd0fcf0c40351001.jpg
            String url = "https://" + bucketName + "." + "cos" + "." + endpoint + ".myqcloud.com" + "/" + key;
            return url;
        } catch (Exception clientException) {
            clientException.printStackTrace();
            return null;
        }
    }
}
