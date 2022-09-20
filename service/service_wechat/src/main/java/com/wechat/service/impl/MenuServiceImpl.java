package com.wechat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.ggkt.model.wechat.Menu;
import com.atguigu.ggkt.vo.wechat.MenuVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wechat.service.MenuService;
import com.wechat.mapper.MenuMapper;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sky
 * @description 针对表【menu(订单明细 订单明细)】的数据库操作Service实现
 * @createDate 2022-09-20 10:50:44
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {

    /* 获取所有菜单，按照一级和二级菜单封装 */
    @Override
    public List<MenuVo> findMenuInfo() {
        List<MenuVo> list = new ArrayList<>();
        // 调用查询函数
        List<Menu> menuList = baseMapper.selectList(null);
        // 结果过滤 ；获取数据 的父id，获取 父id 为0的数据，则是一级菜单
        List<Menu> oneMenuList = menuList.stream()
                .filter(menu -> menu.getParentId().longValue() == 0).collect(Collectors.toList());
        for (Menu oneMenu : oneMenuList) {
            MenuVo oneMenuVo = new MenuVo();
            BeanUtils.copyProperties(oneMenu, oneMenuVo);
            // 根据一级菜单 的id 获取 二级菜单
            List<Menu> twoMenuList = menuList.stream()
                    .filter(menu -> menu.getParentId().longValue() == oneMenu.getId())
                    .sorted(Comparator.comparing(Menu::getSort))
                    .collect(Collectors.toList());
            // 将 该一级菜单下的二级菜单 进行封装
            List<MenuVo> children = new ArrayList<>();
            for (Menu twoMenu : twoMenuList) {
                MenuVo twoMenuVo = new MenuVo();
                BeanUtils.copyProperties(twoMenu, twoMenuVo);
                children.add(twoMenuVo);
            }
            oneMenuVo.setChildren(children);
            list.add(oneMenuVo);
        }
        return list;
    }

    /* 获取所有一级菜单 */
    @Override
    public List<Menu> findMenuOneInfo() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 0);
        List<Menu> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Autowired
    private WxMpService wxMpService;

    /* 菜单同步；将 数据 中的 菜单 同步到 微信公众号
     * 菜单格式 https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html
     * */
    @Override
    public void syncMenu() throws WxErrorException {
        // 查询所有菜单； 一级二级 格式
        List<MenuVo> menuVoList = this.findMenuInfo();
        // 菜单
        JSONArray buttonList = new JSONArray();
        // 遍历 菜单
        for (MenuVo oneMenuVo : menuVoList) {
            JSONObject one = new JSONObject();
            //获取 一级菜单
            one.put("name", oneMenuVo.getName());
            JSONArray subButton = new JSONArray();
            // 遍历 一级菜单中的 二级菜单
            for (MenuVo twoMenuVo : oneMenuVo.getChildren()) {
                JSONObject view = new JSONObject();
                view.put("type", twoMenuVo.getType());
                // 判断 当前二级菜单 的 类型 ； view 是页面
                if (twoMenuVo.getType().equals("view")) {
                    view.put("name", twoMenuVo.getName());
                    view.put("url", "http://ggkt2.vipgz1.91tunnel.com/#"
                            + twoMenuVo.getUrl());
                } else {
                    // 否则是 点击类型 菜单
                    view.put("name", twoMenuVo.getName());
                    view.put("key", twoMenuVo.getMeunKey());
                }
                subButton.add(view);
            }
            // 进行 菜单层级 封装
            one.put("sub_button", subButton);
            buttonList.add(one);
        }
        // 菜单 层级封装
        JSONObject button = new JSONObject();
        button.put("button", buttonList);
        System.out.println("菜单 == ： " + button);
        // 调用 微信服务 创建 菜单； 配置了 wxMpService，可以自动获取 access_token
        String menuCreate = this.wxMpService.getMenuService().menuCreate(button.toJSONString());
        System.out.println("结果== ：" + menuCreate);
    }

    /* 删除 微信 菜单  */
    @Override
    public void removeMenu() throws WxErrorException {
        wxMpService.getMenuService().menuDelete();
    }
}




