package com.vod.service.impl;

import com.atguigu.ggkt.model.vod.*;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vod.service.*;
import com.vod.mapper.CourseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author sky
 * @description 针对表【course(课程)】的数据库操作Service实现
 * @createDate 2022-09-05 15:10:26
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
        implements CourseService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    /* 课程基本信息 的列表 和 分页查询 */
    @Override
    public Map<String, Object> coursePage(Page<Course> pageObj, CourseQueryVo courseQueryVo) {
        // 获取 条件封装 对象
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        // 封装 查询条件
        if (null != courseQueryVo) {
            queryWrapper.eq(null != courseQueryVo.getSubjectParentId(), "subject_parent_id", courseQueryVo.getSubjectParentId())
                    .eq(null != courseQueryVo.getSubjectId(), "subject_id", courseQueryVo.getSubjectId())
                    .eq(null != courseQueryVo.getTeacherId(), "teacher_id", courseQueryVo.getTeacherId())
                    .like(StringUtils.isNotBlank(courseQueryVo.getTitle()), "title", courseQueryVo.getTitle());
        }
        Page<Course> coursePage = baseMapper.selectPage(pageObj, queryWrapper);
        List<Course> records = coursePage.getRecords();
        long total = coursePage.getTotal();// 总记录数
        long size = coursePage.getSize(); // 每页记录数
        long current = coursePage.getCurrent(); //当前页数
        long pages = coursePage.getPages(); //总页数
        // 获取 集合中的 课程信息，进行 数据补全
        records.stream().forEach(iteam -> {
            this.findTeacherAndSubById(iteam);
        });
        // 将 课程基本信息  和 总记录数 总页数，数据 进行 封装返回
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", total);
        map.put("totalPage", pages);
        map.put("records", records);
        return map;
    }


    // 根据 讲师 id 和 课程分类id 查询讲师 和 课程分类信息，补全课程基本信息
    public Course findTeacherAndSubById(Course iteam) {
        Long teacherId = iteam.getTeacherId();
        Long subjectId = iteam.getSubjectId();
        Long parentId = iteam.getSubjectParentId();

        Teacher teacher = teacherService.getById(teacherId);
        // 封装 讲师的名字，Param 是是sql 不能满足查询时 作为数据封装的
        if (null != teacher) {
            iteam.getParam().put("teacherName", teacher.getName());
        }

        //封装 课程分类 ，父类
        Subject subjectOne = subjectService.getById(parentId);
        if (null != subjectOne) {
            iteam.getParam().put("subjectParentTitle", subjectOne.getTitle());
        }

        //封装 课程分类 ，子级
        Subject subjectTwo = subjectService.getById(subjectId);
        // 封装 课程分类，标题
        if (null != subjectTwo) {
            iteam.getParam().put("subjectTitle", subjectTwo.getTitle());
        }
        return iteam;
    }

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    // 添加 course 和 desc 描述
    @Override
    public Long saveCourseAndDescInfo(CourseFormVo courseInfo) {
        // 添加 课程信息 course
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo, course);
        baseMapper.insert(course);
        // 调价 课程 描述
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setCourseId(course.getId());
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescriptionService.save(courseDescription);
        return course.getId();
    }

    // 修改页面 数据回显， 封装成 CourseFormVo 对象 给 页面
    @Override
    public CourseFormVo getCourseFormVo(Long id) {
        // 根据 course 课程 id 查询
        Course course = baseMapper.selectById(id);
        CourseFormVo courseFormVo = new CourseFormVo();
        if (null != course) {
            // 根据 course id 查询 课程描述信息
            QueryWrapper<CourseDescription> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", id);
            CourseDescription courseDescription = courseDescriptionService.getOne(wrapper);
            // 封装 CourseFormVo 返回
            BeanUtils.copyProperties(course, courseFormVo);
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return courseFormVo;
    }

    // 修改 课程信息 和 描述 信息
    @Override
    public Long updateCourseAndDesc(CourseFormVo courseInfo) {
        // 数据封装 调用修改函数
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo, course);
        int courseUpdate = baseMapper.updateById(course);
        // 封装 修改条件 进行修改
        UpdateWrapper<CourseDescription> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("course_id", course.getId());
        updateWrapper.set("description", courseInfo.getDescription());
        boolean isUpdate = courseDescriptionService.update(null, updateWrapper);
        return course.getId();
    }

    @Autowired
    private ChapterService chapterService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private VodService vodService;

    // 删除 course 和 课程 描述
    @Override
    public void delCourseInfo(Long id) {
        //删除 课程信息
        int delCourse = baseMapper.deleteById(id);
        //删除描述
        courseDescriptionService.remove(new QueryWrapper<CourseDescription>().eq("course_id", id));
        //删除章节
        chapterService.remove(new QueryWrapper<Chapter>().eq("course_id", id));
        //删除 视频；
        List<Video> videoList = videoService.list(new QueryWrapper<Video>().eq("course_id", id));
        // 获取video 中的 视频id ，删除 云点播的 视频
        for (Video video : videoList) {
            if (null != video && StringUtils.isNotBlank(video.getVideoSourceId())) {
                vodService.delVideo(video.getVideoSourceId());
            }
        }
        //删除小节
        videoService.remove(new QueryWrapper<Video>().eq("course_id", id));
    }

    @Autowired
    private CourseMapper courseMapper;

    // 根据id 查询 发布的 课程信息
    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return courseMapper.selectCoursePublishVoById(id);
    }

    // 发布课程
    @Override
    public boolean publishCourseById(Long id) {
        // 修改  课程状态 为发布
        Course course = new Course();
        course.setId(id);
        course.setPublishTime(new Date());
        course.setStatus(1);
        return this.updateById(course);
    }
}




