package com.order.mapper;

import com.atguigu.ggkt.model.order.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author sky
 * @description 针对表【order_detail(订单明细 订单明细)】的数据库操作Mapper
 * @createDate 2022-09-18 14:28:04
 * @Entity com.order.bean.OrderDetail
 */
@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




