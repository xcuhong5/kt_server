package com.order.mapper;

import com.atguigu.ggkt.model.order.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author sky
* @description 针对表【order_info(订单表 订单表)】的数据库操作Mapper
* @createDate 2022-09-18 14:28:04
* @Entity com.order.bean.OrderInfo
*/
@Repository
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}




