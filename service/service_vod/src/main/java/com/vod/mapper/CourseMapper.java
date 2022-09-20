package com.vod.mapper;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author sky
* @description 针对表【course(课程)】的数据库操作Mapper
* @createDate 2022-09-05 15:10:26
* @Entity com.vod.bean.Course
*/
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo selectCoursePublishVoById(Long id);
}




