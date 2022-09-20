package com.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.ReadListener;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vod.service.SubjectService;
import com.vod.mapper.SubjectMapper;
import com.vod.service.result.Result;
import com.vod.utils.SubjectListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sky
 * @description 针对表【subject(课程科目)】的数据库操作Service实现
 * @createDate 2022-09-05 15:10:40
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject>
        implements SubjectService {

    // 获取 课程分类 列表；包含 一级二级三级 是懒加载，前台实现 懒加载
    @Override
    public List<Subject> selectSubjectList(Long id) {
        // 获取 条件封装 对象
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        // 设置查询 条件
        wrapper.eq("parent_id", id);
        // 查询 返回结果
        List<Subject> subjectList = baseMapper.selectList(wrapper);
        // 遍历结果， 获取id 查询是否存在子级，有子级设置 HasChildren == true
        for (Subject subject : subjectList) {
            Long subjectId = subject.getId();
            //查询 是否 有子级
            boolean chidren = this.isChidren(subjectId);
            if (chidren == true) {
                //设置 为true
                subject.setHasChildren(true);
            }
        }
        return subjectList;
    }

    // 查询 是否存在 子级 课程、
    public boolean isChidren(Long id) {
        // 获取查询 条件对象
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        // 查询条件 select * from subject where parent_id = 16
        wrapper.eq("parent_id", id);
        // 查询统计  > 0 表示 该课程 有子级 课程
        Long count = baseMapper.selectCount(wrapper);
        return count > 0;
    }

    /* excel 导出 功能
     * 引入依赖 ，创建 和 excel 绑定的类，调用方法 实现
     * */
    @Override
    public void exportData(HttpServletResponse response) {

        try {
            // 设置下载信息，这是微软的 excel  类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            // 设置 头信息 以上固定写法
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 获取 需要 下载的 数据
            List<Subject> subjects = baseMapper.selectList(null);
            // 将数据 遍历 封装 到 excel 绑定对应的 对象中
            List<SubjectEeVo> subjectEeVos = new ArrayList<>();
            for (Subject subject : subjects) {
                SubjectEeVo subEevo = new SubjectEeVo();
                // 直接 将一个对象属性 赋值 给另一个对象；原理是属性名一致 进行赋值，否则不操作
                BeanUtils.copyProperties(subject, subEevo);
//                subEevo.setId(subject.getId());
//                subEevo.setTitle(subject.getTitle());
//                subEevo.setParentId(subject.getParentId());
//                subEevo.setSort(subject.getSort());
                subjectEeVos.add(subEevo);
            }
            // 调用 输出方法
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类")
                    .doWrite(subjectEeVos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 自动装载 课程分类 监听器
    @Autowired
    private SubjectListener subjectListener;

    /* excel 导入功能 */
    @Override
    public void importData(MultipartFile file) {
        try {
            // 实现 写入功能
            EasyExcel.read(file.getInputStream(), SubjectEeVo.class, subjectListener).sheet().doRead();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}




