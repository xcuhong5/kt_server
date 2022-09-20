package com.vod.service;

import com.atguigu.ggkt.model.vod.VideoVisitor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author sky
* @description 针对表【video_visitor(视频来访者记录表)】的数据库操作Service
* @createDate 2022-09-05 15:10:50
*/
public interface VideoVisitorService extends IService<VideoVisitor> {
    // 播放统计
    Map<String,Object> findCount(Long courseId, String startDate, String endDate);
}
