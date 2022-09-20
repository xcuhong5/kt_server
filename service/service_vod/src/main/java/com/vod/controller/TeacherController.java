package com.vod.controller;

import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vod.service.TeacherService;
import com.vod.service.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-05 15:56
 */
/*
 * @RequestBody 前段传过来的数据 封装 json数组;
 * @Api(tags = "讲师信息 控制层") swagger 类注解 声明信息
 * @ApiOperation("查询 全部 讲师")  swagger 方法解 声明信息
 * @ApiParam(name = "name", value = "讲师 名字",required = false)  swagger 参数解 声明信息 ,required 是否必须
 * @CrossOrigin 跨域
 * */

/* 讲师 ；讲师 控制层 */
//service_gateway 实现，则无需注解
//@CrossOrigin
@Api(tags = "讲师信息 控制层")
@RequestMapping("admin/vod/teacher")
@RestController
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /* 查询 全部 讲师 ;  Result 是 统一结果 返回的 风装扮 */
    @ApiOperation("查询 全部 讲师")
    @GetMapping("/findAll")
    public Result findAllTeacher() {
        List<Teacher> teachers = teacherService.list();
        return Result.ok(teachers);
    }

    /* 查询 讲师 ，分页查询, 查询条件： 姓名 头衔 开始时间 结束时间
      可以 写个 对象 作为接收 参数对象
    * @RequestParam(value = "name", required = false)  这个必须写，否则 swagger 测试请求不到
    * */
    @ApiOperation("讲师 列表 分页查询")
    @PostMapping("/findTeacherPage/{current}/{limit}")
    public Result findTeacherPage(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();
        //封装查询 对象
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        // 封装 条件，查询 名字 头像 入驻开始时间 和 结束时间，根据 头衔 降序 和 入驻时间 升序
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                .eq(null != level, "level", level)
                .ge(StringUtils.isNotBlank(joinDateBegin), "join_date", joinDateBegin)
                .le(StringUtils.isNotBlank(joinDateEnd), "join_date", joinDateEnd)
                .orderByDesc("level")
                .orderByAsc("join_date");

        // 设置 分页策略
        Page<Teacher> page = new Page<>(current, limit);
        // 调用 分页函数
        IPage<Teacher> teacherIPage = teacherService.page(page, queryWrapper);
        // 返回 结果，统一格式
        return Result.ok(teacherIPage);
    }

    /* 单独删除  讲师 ；{id} 占位符  remove/3 ; result 是 统一结果 返回的 封装*/
    @ApiOperation("根据 id 逻辑删除 讲师")
    @DeleteMapping("/remove/{id}")
    public Result delTeacherById(@ApiParam(name = "id", value = "被删除讲师的ID", required = true)
                                 @PathVariable("id") Long id) {
        // 批量删 和 单独删 都可以
        boolean isSuccerr = teacherService.removeById(id);
        if (isSuccerr == true) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    @ApiOperation("根据 id 批量 逻辑删除 讲师")
    @DeleteMapping("/removeBatch")
    public Result delTeacherBatch(@ApiParam(name = "ids", value = "批量删除 讲师", required = true)
                                  @RequestBody List<Long> ids) {
        // 批量删 和 单独删 都可以
        boolean isSuccerr = teacherService.removeByIds(ids);
        if (isSuccerr == true) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    /* 根据id 查询  讲师 */
    @ApiOperation("根据 id 查询 讲师")
    @GetMapping("/getTeacher/{id}")
    public Result getTeacherById(@PathVariable("id") Long id) {
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            return Result.ok(teacher);
        } else {
            return Result.fail(teacher);
        }
    }

    /* 修改 讲师 信息 */
    @ApiOperation("修改讲师 信息")
    @PostMapping("/updateTeacher")
    public Result updateTeacherById(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.updateById(teacher);
        if (isSuccess == true) {
            return Result.ok(isSuccess);
        } else {
            return Result.fail(isSuccess);
        }
    }

    /* 新增 讲师 */
    @ApiOperation("新增 讲师")
    @PostMapping("/saveTeacher")
    public Result addTeacher(@RequestBody(required = false) Teacher teacher) {
        boolean isSave = teacherService.save(teacher);
        if (isSave = true) {
            return Result.ok(isSave);
        } else {
            return Result.fail(isSave);
        }
    }

}
