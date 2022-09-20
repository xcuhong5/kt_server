package com.vod.controller;

import com.vod.service.VideoVisitorService;
import com.vod.service.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-13 10:21
 */
//service_gateway 实现，则无需注解
//@CrossOrigin
@Api(tags = "课程 播放 统计 控制层接口")
@RestController
@RequestMapping("/admin/vod/videoVisitor")
public class VideoVisitorController {

    @Autowired
    private VideoVisitorService videoVisitorService;

    @ApiOperation("播放课程 统计 echar 统计图")
    @GetMapping("findCount/{courseId}/{startDate}/{endDate}")
    public Result showChart(@PathVariable Long courseId,
                            @PathVariable String startDate,
                            @PathVariable String endDate) {

        Map<String, Object> map = videoVisitorService.findCount(courseId, startDate, endDate);
        return Result.ok(map);
    }
}
