package com.order.service.impl;

import com.atguigu.ggkt.model.order.OrderDetail;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.order.service.OrderDetailService;
import com.order.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author sky
* @description 针对表【order_detail(订单明细 订单明细)】的数据库操作Service实现
* @createDate 2022-09-18 14:28:04
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




