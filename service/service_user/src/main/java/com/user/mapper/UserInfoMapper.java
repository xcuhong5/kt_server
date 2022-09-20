package com.user.mapper;

import com.atguigu.ggkt.model.user.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author sky
* @description 针对表【user_info(用户表)】的数据库操作Mapper
* @createDate 2022-09-19 16:18:08
* @Entity com.user.bean.UserInfo
*/
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}




