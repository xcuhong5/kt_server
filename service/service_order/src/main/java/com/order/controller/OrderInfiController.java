package com.order.controller;

import com.atguigu.ggkt.model.order.OrderInfo;
import com.atguigu.ggkt.vo.order.OrderInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.order.service.OrderInfoService;
import com.vod.service.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:kt_server
 *
 * @Author: sky
 * DateTime: 2022-09-18 14:42
 */
@Api(tags = "订单管理 控制层 接口 ")
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfiController {

    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation("订单 分页 列表,条件查询 ")
    @GetMapping("/{page}/{limit}")
    public Result orderInfoPageList(@ApiParam(value = "当前页码", required = true)
                                    @PathVariable Long page,
                                    @ApiParam(value = "每页显示数量", required = true)
                                    @PathVariable Long limit,
                                    @ApiParam(value = "查询对象", required = false) OrderInfoQueryVo orderInfoQueryVo) {
        // 获取分页对象
        Page<OrderInfo> orderPage = new Page<>(page, limit);
        Map<String, Object> map = orderInfoService.findPageOrderInfo(orderPage, orderInfoQueryVo);
        return Result.ok(map);
    }

}
