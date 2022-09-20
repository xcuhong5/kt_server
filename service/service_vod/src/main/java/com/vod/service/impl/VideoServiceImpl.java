package com.vod.service.impl;

import com.atguigu.ggkt.model.vod.Video;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vod.service.VideoService;
import com.vod.mapper.VideoMapper;
import org.springframework.stereotype.Service;

/**
* @author sky
* @description 针对表【video(课程视频)】的数据库操作Service实现
* @createDate 2022-09-05 15:10:46
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{

}




