package com.vod.service.impl;

import com.atguigu.ggkt.model.vod.Comment;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vod.service.CommentService;
import com.vod.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author sky
* @description 针对表【comment(评论)】的数据库操作Service实现
* @createDate 2022-09-05 15:10:21
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




