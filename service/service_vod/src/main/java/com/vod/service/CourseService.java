package com.vod.service;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author sky
* @description 针对表【course(课程)】的数据库操作Service
* @createDate 2022-09-05 15:10:26
*/
/* 课程基本信息  */
public interface CourseService extends IService<Course> {


    //课程基本信息的 列表 分页查询
    Map<String, Object> coursePage(Page<Course> pageObj, CourseQueryVo courseQueryVo);
    // 新增 课程 id 和 课程描述
    Long saveCourseAndDescInfo(CourseFormVo courseInfo);

    // 修改 信息 数据回显。封装成 CourseFormVo 返回给 页面
    CourseFormVo getCourseFormVo(Long id);

    // 修改 course 课程信息 和描述
    Long updateCourseAndDesc(CourseFormVo courseInfo);

    // 删除课程信息 和 描述 章节 小节
    void delCourseInfo(Long id);

    //根据id获取课程发布信息; 自定义对象CoursePublishVo 封装 结果数据
    CoursePublishVo getCoursePublishVo(Long id);

    //根据id发布课程
    boolean publishCourseById(Long id);
}
