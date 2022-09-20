package com.wechat.service;

import com.atguigu.ggkt.model.wechat.Menu;
import com.atguigu.ggkt.vo.wechat.MenuVo;
import com.baomidou.mybatisplus.extension.service.IService;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.List;

/**
 * @author sky
 * @description 针对表【menu(订单明细 订单明细)】的数据库操作Service
 * @createDate 2022-09-20 10:50:44
 */
public interface MenuService extends IService<Menu> {
    /* 获取所有菜单，按照一级和二级菜单封装 */
    List<MenuVo> findMenuInfo();

    /* 获取所有一级菜单 */
    List<Menu> findMenuOneInfo();

    /* 数据库 微信 菜单 同步  */
    void syncMenu() throws WxErrorException;

    /* 删除微信菜单 */
    void removeMenu() throws WxErrorException;
}
