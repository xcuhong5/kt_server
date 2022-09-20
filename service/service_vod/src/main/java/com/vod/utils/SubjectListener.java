package com.vod.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.vod.mapper.SubjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-10 16:16
 */
/* easyexcel 导入功能，原理 是一行一行的 读取数据，写一个 监听器
 * extends AnalysisEventListener<SubjectEeVo>  继承该类 重写 方法
 *  @Component 交给spring 容器
 * */
@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {
    @Autowired
    private SubjectMapper subjectMapper;

    // 监听器 监听 读取excel , 一行 一行的读取
    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext context) {
        // 调用 数据库 添加方法
        Subject subject = new Subject();
        // 将excel 绑定的对象值 赋值给 Subject
        BeanUtils.copyProperties(subjectEeVo, subject);
        // 调用新增方法
        subjectMapper.insert(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
