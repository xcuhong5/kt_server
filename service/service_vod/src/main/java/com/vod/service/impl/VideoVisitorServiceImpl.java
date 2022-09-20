package com.vod.service.impl;

import com.atguigu.ggkt.model.vod.VideoVisitor;
import com.atguigu.ggkt.vo.vod.VideoVisitorCountVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vod.service.VideoVisitorService;
import com.vod.mapper.VideoVisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sky
 * @description 针对表【video_visitor(视频来访者记录表)】的数据库操作Service实现
 * @createDate 2022-09-05 15:10:50
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor>
        implements VideoVisitorService {

    // 可以注入 调用 mapper的自定义 sql ，也可以用 baseMapper
    @Autowired
    private VideoVisitorMapper videoVisitorMapper;

    // 课程 播放统计
    @Override
    public Map<String, Object> findCount(Long courseId, String startDate, String endDate) {
        // 获取查询 结果，播放统计
        List<VideoVisitorCountVo> visitorCountVos = videoVisitorMapper.findCount(courseId, startDate, endDate);
        // 将数据 封装 到 map，日期为 key，v 是 日期对应的所有值
        Map<String, Object> map = new HashMap<>();
        // x 轴 放日期
        List<String> listData = new ArrayList<>();
        // y 轴 放结果数据
        List<Integer> listCount = new ArrayList<>();
        for (VideoVisitorCountVo countVo : visitorCountVos) {
            listData.add(countVo.getJoinTime());
            listCount.add(countVo.getUserCount());
        }
        map.put("xData", listData);
        map.put("yData", listCount);
        return map;
    }
}




