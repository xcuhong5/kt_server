package com.vod.mapper;

import com.atguigu.ggkt.model.vod.VideoVisitor;
import com.atguigu.ggkt.vo.vod.VideoVisitorCountVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sky
 * @description 针对表【video_visitor(视频来访者记录表)】的数据库操作Mapper
 * @createDate 2022-09-05 15:10:50
 * @Entity com.vod.bean.VideoVisitor
 */
@Repository
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {
    //显示统计数据
    List<VideoVisitorCountVo> findCount(@Param("courseId") Long courseId,
                                        @Param("startDate") String startDate,
                                        @Param("endDate") String endDate);
}




