package com.vod.controller;

import com.atguigu.ggkt.model.vod.Subject;
import com.vod.service.SubjectService;
import com.vod.service.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-10 14:25
 */
//service_gateway 实现，则无需注解
//@CrossOrigin
/* 课程 分类 控制层 */
@Api(tags = "课程分类 控制层")
@RequestMapping("/admin/vod/subject")
@RestController
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /* 课程 列表  */
    @ApiOperation("课程列表")
    @GetMapping("/getChildSubject/{id}")
    public Result findSubjectList(@PathVariable("id") Long id) {
        List<Subject> subjects = subjectService.selectSubjectList(id);
        return Result.ok(subjects);
    }

    /* 课程 导出 */
    @ApiOperation(value = "课程 导出")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        subjectService.exportData(response);
    }

    /* 导入excel 功能 */
    @ApiOperation(value = "课程导入")
    @PostMapping("/importData")
    public Result importData(MultipartFile file) {
        subjectService.importData(file);
        return Result.ok(null);
    }
}
