package com.vod.controller;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vod.service.CourseService;
import com.vod.service.SubjectService;
import com.vod.service.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.naming.Name;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-10 22:13
 */
//service_gateway 实现，则无需注解
//@CrossOrigin
@Api(tags = "课程 基本信息 控制层")
@RequestMapping("/admin/vod/course")
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("课程信息 分页+条件查询")
    @GetMapping("{page}/{limit}")
    public Result findCoursePage(@ApiParam(name = "page", value = "当前页码", required = true)
                                 @PathVariable("page") Integer page,
                                 @ApiParam(name = "limit", value = "每页显示记录数", required = true)
                                 @PathVariable("limit") Integer limit,
                                 @ApiParam(name = "courseQueryVo", value = "查询条件 对象", required = false)
                                         CourseQueryVo courseQueryVo) {

        Page<Course> pageObj = new Page<>(page, limit);
        Map<String, Object> map = courseService.coursePage(pageObj, courseQueryVo);
        return Result.ok(map);
    }

    // 新增 课程描述 CourseDescription 和 课程基本信息 course
    @ApiOperation("新增课程")
    @PostMapping("/save")
    public Result saveCourse(@ApiParam(name = "course", value = "新增课程对象", required = true)
                             @RequestBody CourseFormVo courseInfo) {
        if (null != courseInfo) {
            // 返回 新增的 course id
            Long courseId = courseService.saveCourseAndDescInfo(courseInfo);
            return Result.ok(courseId);
        } else {
            return Result.fail("数据为空！");
        }
    }

    // 修改 course 信息 ；先做数据回显
    @ApiOperation("course 数据回显")
    @GetMapping("get/{id}")
    public Result getCourseFormVo(@PathVariable("id") Long id) {
        CourseFormVo courseFormVo = courseService.getCourseFormVo(id);
        if (null != courseFormVo) {
            return Result.ok(courseFormVo);
        } else {
            return Result.fail("数据为空!");
        }
    }

    // 修改 course 课程信息 和 描述信息
    @ApiOperation("课程信息 和 描述信息 修改")
    @PostMapping("/update")
    public Result update(@ApiParam(name = "courseInfo", value = "修改课程信息 对象", required = true)
                         @RequestBody CourseFormVo courseInfo) {
        Long course_id = null;
        if (null != courseInfo) {
            course_id = courseService.updateCourseAndDesc(courseInfo);
        }
        return Result.ok(course_id);
    }

    //删除 course 课程
    @ApiOperation(value = "删除 课程和 描述")
    @DeleteMapping("/remove/{id}")
    public Result removeCourseById(@PathVariable("id") Long id) {
        courseService.delCourseInfo(id);

        return Result.ok("");

    }

    @ApiOperation("根据id获取课程发布信息")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVoById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long id) {

        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);
    }

    @ApiOperation("根据id发布课程")
    @PutMapping("publishCourse/{id}")
    public Result publishCourseById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long id) {

        boolean result = courseService.publishCourseById(id);
        return Result.ok("");
    }


}
