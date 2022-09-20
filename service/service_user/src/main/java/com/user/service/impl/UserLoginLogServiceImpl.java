package com.user.service.impl;

import com.atguigu.ggkt.model.user.UserLoginLog;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.user.service.UserLoginLogService;
import com.user.mapper.UserLoginLogMapper;
import org.springframework.stereotype.Service;

/**
* @author sky
* @description 针对表【user_login_log(用户登陆记录表)】的数据库操作Service实现
* @createDate 2022-09-19 16:18:08
*/
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog>
    implements UserLoginLogService{

}




