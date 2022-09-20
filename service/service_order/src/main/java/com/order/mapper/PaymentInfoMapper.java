package com.order.mapper;

import com.atguigu.ggkt.model.order.PaymentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author sky
* @description 针对表【payment_info(支付信息表)】的数据库操作Mapper
* @createDate 2022-09-18 14:28:04
* @Entity com.order.bean.PaymentInfo
*/
@Repository
public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {

}




