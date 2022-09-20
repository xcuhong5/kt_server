package com.user.service.impl;

import com.atguigu.ggkt.model.user.UserInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.user.service.UserInfoService;
import com.user.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author sky
* @description 针对表【user_info(用户表)】的数据库操作Service实现
* @createDate 2022-09-19 16:18:08
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




