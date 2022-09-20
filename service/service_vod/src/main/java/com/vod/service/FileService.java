package com.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-09 16:16
 */
/* 文件上传 到 腾讯 cos  */
public interface FileService {
    //文件上传
    String upload(MultipartFile file);
}
