package com.order.service.impl;

import com.atguigu.ggkt.model.order.OrderDetail;
import com.atguigu.ggkt.model.order.OrderInfo;
import com.atguigu.ggkt.vo.order.OrderInfoQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.order.service.OrderDetailService;
import com.order.service.OrderInfoService;
import com.order.mapper.OrderInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sky
 * @description 针对表【order_info(订单表 订单表)】的数据库操作Service实现
 * @createDate 2022-09-18 14:28:04
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>
        implements OrderInfoService {

    /**
     * 订单 分页查询
     *
     * @param orderPage        分页对象
     * @param orderInfoQueryVo 条件查询对象
     * @return
     */
    @Override
    public Map<String, Object> findPageOrderInfo(Page<OrderInfo> orderPage, OrderInfoQueryVo orderInfoQueryVo) {
        // 获取 查询条件 构造
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        //orderInfoQueryVo获取查询条件
        //用户 id
        Long userId = orderInfoQueryVo.getUserId();
        //订单交易编号（第三方支付用)
        String outTradeNo = orderInfoQueryVo.getOutTradeNo();
        // 电话
        String phone = orderInfoQueryVo.getPhone();
        // 开始结束 时间
        String createTimeEnd = orderInfoQueryVo.getCreateTimeEnd();
        String createTimeBegin = orderInfoQueryVo.getCreateTimeBegin();
        //  订单状态
        Integer orderStatus = orderInfoQueryVo.getOrderStatus();
        // 封装  条件
        queryWrapper.eq(null != orderStatus, "order_status", orderStatus);
        queryWrapper.eq(null != userId, "user_id", userId);
        queryWrapper.eq(StringUtils.isNotEmpty(outTradeNo), "out_trade_no", outTradeNo);
        queryWrapper.eq(StringUtils.isNotEmpty(phone), "phone", phone);
        queryWrapper.ge(StringUtils.isNotEmpty(createTimeBegin), "create_time", createTimeBegin);
        queryWrapper.le(StringUtils.isNotEmpty(createTimeEnd), "create_time", createTimeEnd);
        // 调用 分页查询
        Page<OrderInfo> page = this.page(orderPage, queryWrapper);
        // 获取分数据中的 订单数据
        List<OrderInfo> records = page.getRecords();
        // 遍历 订单数据，封装 订单的详情信息
        records.stream().forEach(item -> {
            // 将 详情 数据 封装；进行返回
            this.getOrderDetail(item);
        });

        // 获取 总页数
        long pageCount = page.getPages();
        // 获取 总记录数
        long totalCount = page.getTotal();
        // 封装 结果数据
        Map<String, Object> map = new HashMap<>();
        map.put("total", totalCount);
        map.put("pageCount", pageCount);
        map.put("records", records);
        return map;
    }

    @Autowired
    private OrderDetailService orderDetailService;

    //查询订单详情数据
    private OrderInfo getOrderDetail(OrderInfo orderInfo) {
        //订单id
        Long id = orderInfo.getId();
        //查询订单详情
        OrderDetail orderDetail = orderDetailService.getById(id);
        if (orderDetail != null) {
            String courseName = orderDetail.getCourseName();
            orderInfo.getParam().put("courseName", courseName);
        }
        return orderInfo;
    }
}




