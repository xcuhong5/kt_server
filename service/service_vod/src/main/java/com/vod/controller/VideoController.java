package com.vod.controller;

import com.atguigu.ggkt.model.vod.Video;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.vod.service.VideoService;
import com.vod.service.VodService;
import com.vod.service.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-12 15:20
 */
//service_gateway 实现，则无需注解
//@CrossOrigin
@Api("小节 课时  控制层 接口")
@RequestMapping("/admin/vod/video")
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private VodService vodService;


    @ApiOperation("添加 小节 课时")
    @PostMapping("/save")
    public Result addVideo(@RequestBody(required = true) Video video) {
        // 执行添加
        boolean isSave = videoService.save(video);
        return Result.ok("");
    }

    // 根据 id 查询 课时小节
    @ApiOperation("根据 id 获取 小节")
    @GetMapping("/get/{id}")
    public Result getVideo(@PathVariable("id") Long id) {
        Video video = videoService.getById(id);
        return Result.ok(video);
    }

    //修改小节
    @ApiOperation("修改小节")
    @PutMapping("/update")
    public Result updateVideo(@RequestBody(required = true) Video video) {
        boolean isUpdate = videoService.updateById(video);
        return Result.ok("");
    }

    // 删除小节
    @ApiOperation("删除小节")
    @DeleteMapping("/remove/{id}")
    public Result delVideo(@PathVariable("id") Long id) {
        // 根据id 获取 video 对象 中的 视频id
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        // 删除 vod 服务 后台的 视频
        if (StringUtils.isNotBlank(videoSourceId)) {
            vodService.delVideo(videoSourceId);
        }
        // 删除 小节
        videoService.removeById(id);
        return Result.ok("");
    }
}
