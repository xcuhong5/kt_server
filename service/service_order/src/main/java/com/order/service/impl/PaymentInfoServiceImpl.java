package com.order.service.impl;

import com.atguigu.ggkt.model.order.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.order.service.PaymentInfoService;
import com.order.mapper.PaymentInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author sky
* @description 针对表【payment_info(支付信息表)】的数据库操作Service实现
* @createDate 2022-09-18 14:28:04
*/
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo>
    implements PaymentInfoService{

}




