package com.vod.service;

import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author sky
* @description 针对表【chapter(课程)】的数据库操作Service
* @createDate 2022-09-05 15:09:44
*/
public interface ChapterService extends IService<Chapter> {

    //查询 章节，根据 课程id 查
    List<ChapterVo> getChapterList(Long id);
}
