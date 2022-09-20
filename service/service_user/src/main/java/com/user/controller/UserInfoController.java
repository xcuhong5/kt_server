package com.user.controller;

import com.atguigu.ggkt.model.user.UserInfo;
import com.user.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:kt_server
 *
 * @Author: sky
 * DateTime: 2022-09-19 16:24
 */
@Api("用户管理 控制层 接口 ")
@RestController
@RequestMapping("/admin/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userService;

    @ApiOperation(value = " 根据id 获取用户 ")
    @GetMapping("inner/getById/{id}")
    public UserInfo getById(@PathVariable Long id) {
        // 此处 作为 远程 接口调用
        return userService.getById(id);
    }
}