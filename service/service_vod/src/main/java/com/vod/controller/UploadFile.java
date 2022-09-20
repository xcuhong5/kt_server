package com.vod.controller;

import com.vod.service.FileService;
import com.vod.service.result.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-09 14:42
 * SecretId: AKIDxqXzCG8qKaorrxwiK4iDW7cxOnFZicJx
 * <p>
 * SecretKey: c4MqFYozxkN6vd9UfdO1Wq11oSzQkwz5
 */
//service_gateway 实现，则无需注解
//@CrossOrigin
/* 文件上传 控制层，@CrossOrigin  跨域 注解 */
@Api(tags = "文件上传")
@RequestMapping("/admin/vod/file")
@RestController
public class UploadFile {

    @Autowired
    private FileService fileService;
    // 上传头像 到 cos
    @PostMapping("/upload")
    public Result uoloadTeacherHeadFile(MultipartFile file) throws IOException {
        String url = null;
        if (!file.isEmpty()) {
            url = fileService.upload(file);
            return Result.ok(url);
        } else {
            return Result.fail(url);
        }

    }

}
