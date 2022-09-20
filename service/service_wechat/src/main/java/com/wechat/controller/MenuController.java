package com.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.ggkt.model.wechat.Menu;
import com.atguigu.ggkt.vo.wechat.MenuVo;
import com.vod.service.result.Result;
import com.wechat.service.MenuService;
import com.wechat.utils.ConstantPropertiesUtil;
import com.wechat.utils.HttpClientUtils;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:kt_server
 *
 * @Author: sky
 * DateTime: 2022-09-20 10:58
 */
@RestController
@RequestMapping("/admin/wechat/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    //获取所有菜单，按照一级和二级菜单封装
    @GetMapping("findMenuInfo")
    public Result findMenuInfo() {
        List<MenuVo> list = menuService.findMenuInfo();
        return Result.ok(list);
    }

    //获取所有一级菜单
    @GetMapping("findOneMenuInfo")
    public Result findOneMenuInfo() {
        List<Menu> list = menuService.findMenuOneInfo();
        return Result.ok(list);
    }

    @ApiOperation(value = "根据 id 获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        return Result.ok(menu);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody Menu menu) {
        menuService.save(menu);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        menuService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        menuService.removeByIds(idList);
        return Result.ok(null);
    }

    //获取access_token   此处 是自己实现的 ，可以忽略； 在 wxMpService 中 已经 封装了 ;
    // access_token 默认是 2小时刷新一次，请求一次刷新一次，所以要获取 最新的
    @GetMapping("getAccessToken")
    public Result getAccessToken() {
        try {
            //拼接请求地址
            StringBuffer buffer = new StringBuffer();
            buffer.append("https://api.weixin.qq.com/cgi-bin/token");
            buffer.append("?grant_type=client_credential");
            buffer.append("&appid=%s");
            buffer.append("&secret=%s");

            //封装 请求地址设置参数；%s 占位符 string，根据顺序 填充 变量
            String url = String.format(buffer.toString(),
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //发送http请求; 返回 access_token 字符串
            String tokenString = HttpClientUtils.get(url);
            //转json 对象 ； 获取access_token
            JSONObject jsonObject = JSONObject.parseObject(tokenString);
            String access_token = jsonObject.getString("access_token");
            //返回
            return Result.ok(access_token);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(null);
        }
    }

    @ApiOperation(value = " 数据库 和 微信公众号 同步菜单; 结果不会马上呈现，取消关注 再关注就可以了 ")
    @GetMapping("syncMenu")
    public Result createMenu() throws WxErrorException {
        menuService.syncMenu();
        return Result.ok(null);
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("removeMenu")
    public Result removeMenu() throws WxErrorException {
        menuService.removeMenu();
        return Result.ok(null);
    }
}
