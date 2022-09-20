package com.vod.service.impl;

import com.atguigu.ggkt.model.vod.Teacher;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vod.service.TeacherService;
import com.vod.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sky
 * @description 针对表【teacher(讲师)】的数据库操作Service实现
 * @createDate 2022-09-05 15:10:43
 */
/* 讲师的  业务逻辑 层 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
        implements TeacherService {
    //将 mapper 注入 到 业务层
    @Autowired
    private TeacherMapper teacherMapper;
}




