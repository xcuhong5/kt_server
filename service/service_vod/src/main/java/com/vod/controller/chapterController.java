package com.vod.controller;

import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.vod.service.ChapterService;
import com.vod.service.VideoService;
import com.vod.service.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-12 13:28
 */
//service_gateway 实现，则无需注解
//@CrossOrigin
// 章节 控制层
@Api(tags = "章节 控制层 接口 ")
@RestController
@RequestMapping("/admin/vod/chapter")
public class chapterController {

    //章节 注入
    @Autowired
    private ChapterService chapterService;


    // 课程 章节 列表
    @ApiOperation("课程章节 列表；根据 course id 查询获取 相关章节 小节")
    @GetMapping("/getNestedTreeList/{id}")
    public Result chapterList(@ApiParam(value = "course id", required = true) @PathVariable("id") Long courseId) {
        List<ChapterVo> chapterList = chapterService.getChapterList(courseId);
        return Result.ok(chapterList);
    }

    //新增 章节
    @ApiOperation("新增章节 根据 course  id")
    @PostMapping("/save")
    public Result addChepter(@RequestBody Chapter chapter) {
        // chapter 封装了 course id
        boolean isSave = chapterService.save(chapter);
        if (isSave == true) {
            return Result.ok("");
        } else {
            return Result.fail("");
        }
    }

    // 根据 id 查询 章节 chapter
    @ApiOperation("根据 id 查询  章节")
    @GetMapping("/get/{id}")
    public Result getChapter(@PathVariable("id") Long id) {
        //根据id 查询
        Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }

    // 修改 章节信息 chapter
    @ApiOperation("修改 章节信息")
    @PostMapping("/update")
    public Result updateChapter(@RequestBody(required = true) Chapter chapter) {
        // 修改 章节
        boolean isUpdate = chapterService.updateById(chapter);
        if (isUpdate == true) {
            return Result.ok("");
        } else {
            return Result.fail("");
        }
    }

    // 删除章节
    @ApiOperation("删除章节")
    @DeleteMapping("/remove/{id}")
    public Result delChapter(@PathVariable("id") Long id) {
        boolean isRemove = chapterService.removeById(id);
        return Result.ok("");
    }

}
