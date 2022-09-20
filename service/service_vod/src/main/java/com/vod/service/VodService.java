package com.vod.service;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-13 17:37
 */
/* vod 腾讯云 云点播 业务接口 */
public interface VodService {
    String uploadVideo(InputStream inputStream, String originalFilename);

    void delVideo(String videoSourceId);
}
