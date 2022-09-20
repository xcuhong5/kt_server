package com.vod.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.vod.service.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-07 23:52
 */
//service_gateway 实现，则无需注解
//@CrossOrigin
/*  登录   @CrossOrigin  跨域 */
@Api(tags = "登录 接口")
@RequestMapping("admin/vod/user")
@RestController
public class UserLogin {

    @PostMapping("/login")
    public Result userLogin(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", "admin-token");
        return Result.ok(map);
    }

    /**/
    @GetMapping("/info")
    public Result info() {
        //{"code":20000,"data":
        // {"roles":["admin"],
        // "introduction":"I am a super administrator",
        // "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
        // "name":"Super Admin"}}
        Map<String, Object> map = new HashMap<>();
        map.put("roles", "admin");
        map.put("introduction", "I am a super administrator");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", "Super Admin");
        return Result.ok(map).code(20000);
    }

}
