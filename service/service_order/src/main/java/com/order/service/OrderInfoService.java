package com.order.service;

import com.atguigu.ggkt.model.order.OrderInfo;
import com.atguigu.ggkt.vo.order.OrderInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author sky
 * @description 针对表【order_info(订单表 订单表)】的数据库操作Service
 * @createDate 2022-09-18 14:28:04
 */
public interface OrderInfoService extends IService<OrderInfo> {
    /**
     * 订单 分页查询
     *
     * @param orderPage        分页对象
     * @param orderInfoQueryVo 条件查询对象
     * @return
     */
    Map<String, Object> findPageOrderInfo(Page<OrderInfo> orderPage, OrderInfoQueryVo orderInfoQueryVo);
}
