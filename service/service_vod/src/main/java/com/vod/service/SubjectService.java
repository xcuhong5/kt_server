package com.vod.service;

import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author sky
* @description 针对表【subject(课程科目)】的数据库操作Service
* @createDate 2022-09-05 15:10:40
*/
public interface SubjectService extends IService<Subject> {
    // 查询 课程分类 数据，一级二级三级 类目
    List<Subject> selectSubjectList(Long id);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);


}
