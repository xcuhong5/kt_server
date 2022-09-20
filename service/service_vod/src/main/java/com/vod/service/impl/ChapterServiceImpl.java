package com.vod.service.impl;

import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vo.vod.VideoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vod.service.ChapterService;
import com.vod.mapper.ChapterMapper;
import com.vod.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sky
 * @description 针对表【chapter(课程)】的数据库操作Service实现
 * @createDate 2022-09-05 15:09:44
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter>
        implements ChapterService {
    
    @Autowired
    private VideoService videoService;

    // 章节 小节 查询
    @Override
    public List<ChapterVo> getChapterList(Long id) {
        //根据 course id 获取 章节
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(null != id, "course_id", id);
        List<Chapter> chapterList = baseMapper.selectList(queryWrapper);

        //根据 course id 获取 课程下的 小节  LambdaQueryWrapper 换个写法 效果一样
        LambdaQueryWrapper<Video> videoWrapper = new LambdaQueryWrapper<>();
        videoWrapper.eq(null != id, Video::getCourseId, id);
        List<Video> videoList = videoService.list(videoWrapper);

        // 封装 章节 小节 数据
        List<ChapterVo> chapterVoList = new ArrayList<>();
        // 遍历 章节
        for (Chapter chapter : chapterList) {
            // 章节中的 课程id
            Long ch_courseId = chapter.getCourseId();
            // 章节 id
            Long chapterId = chapter.getId();
            // 封装 章节自定义 对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            // 遍历 小节
            for (Video video : videoList) {
                // 获取 章节下 所有小节
                if (ch_courseId == video.getCourseId() && chapterId == video.getChapterId()) {
                    //封装 小节数据
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    // 将 小节 封装 到 相应 章节
                    chapterVo.getChildren().add(videoVo);
                }
            }
            // 将 封装的数据 添加 到 章节 小节 集合
            chapterVoList.add(chapterVo);
        }

        return chapterVoList;
    }
}




