package com.vod.mapper;

import com.atguigu.ggkt.model.vod.Chapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author sky
* @description 针对表【chapter(课程)】的数据库操作Mapper
* @createDate 2022-09-05 15:09:44
* @Entity com.vod.bean.Chapter
*/
//模块分离  将实体模块 引入 pom
public interface ChapterMapper extends BaseMapper<Chapter> {

}




