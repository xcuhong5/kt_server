package com.vod.controller;

import com.vod.service.VodService;
import com.vod.service.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-13 17:36
 */
//service_gateway 实现，则无需注解
//@CrossOrigin
/* 腾讯云 云点播 上传 读取操作接口*/
@Api(tags = "云点播 视频操作 接口")
@RequestMapping("/admin/vod")
@RestController
public class VodController {

    @Autowired
    private VodService vodService;

    //上传视频
    @ApiOperation("上传视频")
    @PostMapping("/upload")
    public Result uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String originalFilename = file.getOriginalFilename();
        String videoId = vodService.uploadVideo(inputStream, originalFilename);
        return Result.ok(videoId);
    }

    //删除视频
    @ApiOperation("删除视频")
    @DeleteMapping("/remove/{videoSourceId}")
    public Result delVideoById(@PathVariable String videoSourceId) {
        vodService.delVideo(videoSourceId);

        return Result.ok("");
    }


}
