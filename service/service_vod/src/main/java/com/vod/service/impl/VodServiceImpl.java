package com.vod.service.impl;

import com.qcloud.vod.VodUploadClient;
import com.qcloud.vod.model.VodUploadRequest;
import com.qcloud.vod.model.VodUploadResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.DeleteMediaResponse;
import com.vod.service.VodService;
import com.vod.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-13 17:37
 */
/* vod 腾讯云 云点播 业务接口 */
@Service
public class VodServiceImpl implements VodService {

    // 上传视频
    @Override
    public String uploadVideo(InputStream inputStream, String originalFilename) {
        try {
            // 构建vod 上传 客户端 对象，传入 id 和 key
            VodUploadClient client =
                    new VodUploadClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                            ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 构建 上传 请求对象
            VodUploadRequest request = new VodUploadRequest();
            // 视频本地地址
            request.setMediaFilePath("C:\\Users\\sky\\Desktop\\笔记\\硅谷课堂\\01-尚硅谷-硅谷课堂-项目概述.mp4");
            // 指定任务流 播放转码 LongVideoPreset 是 vod 后台选择的，默认是这个 任务流
            request.setProcedure("LongVideoPreset");
            // 调用上传方法，传入接入点地域及上传请求。
            VodUploadResponse response = client.upload(ConstantPropertiesUtil.END_POINT, request);
            // 返回文件id保存到业务表，用于控制视频播放
            String fileId = response.getFileId();
            System.out.println("Upload FileId = {}" + response.getFileId());
            return fileId;
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public void delVideo(String videoSourceId) {
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            Credential cred =
                    new Credential(ConstantPropertiesUtil.ACCESS_KEY_ID,
                            ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 实例化要请求产品的client对象,clientProfile是可选的
            VodClient client = new VodClient(cred, "");
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DeleteMediaRequest req = new DeleteMediaRequest();
            // 设置 视频的 id
            req.setFileId(videoSourceId);
            // 调用 删除函数； 返回的resp是一个DeleteMediaResponse的实例，与请求对象对应
            DeleteMediaResponse resp = client.DeleteMedia(req);
            // 输出json格式的字符串回包
            System.out.println(DeleteMediaResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
}
