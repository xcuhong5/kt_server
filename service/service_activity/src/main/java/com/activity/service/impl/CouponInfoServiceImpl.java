package com.activity.service.impl;

import com.activity.service.CouponUseService;
import com.atguigu.ggkt.model.activity.CouponInfo;
import com.atguigu.ggkt.model.activity.CouponUse;
import com.atguigu.ggkt.model.user.UserInfo;
import com.atguigu.ggkt.vo.activity.CouponUseQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.activity.service.CouponInfoService;
import com.activity.mapper.CouponInfoMapper;
import com.client.user.UserInfoFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author sky
 * @description 针对表【coupon_info(优惠券信息)】的数据库操作Service实现
 * @createDate 2022-09-19 15:36:06
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo>
        implements CouponInfoService {

    // 优惠券 使用 借口
    @Autowired
    private CouponUseService couponUseService;
    // 远程被调用 接口
    @Autowired
    private UserInfoFeignClient userInfoFeignClient;

    @Override
    public IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo) {
        //获取条件, 优惠券id
        Long couponId = couponUseQueryVo.getCouponId();
        // 优惠券状态
        String couponStatus = couponUseQueryVo.getCouponStatus();
        // 开始时间
        String getTimeBegin = couponUseQueryVo.getGetTimeBegin();
        // 结束时间
        String getTimeEnd = couponUseQueryVo.getGetTimeEnd();
        //封装条件
        QueryWrapper<CouponUse> wrapper = new QueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(couponId), "coupon_id", couponId);
        wrapper.eq(!StringUtils.isEmpty(couponStatus), "coupon_status", couponStatus);
        wrapper.ge(!StringUtils.isEmpty(getTimeBegin), "get_time", getTimeBegin);
        wrapper.le(!StringUtils.isEmpty(getTimeEnd), "get_time", getTimeEnd);

        //调用方法查询
        IPage<CouponUse> page = couponUseService.page(pageParam, wrapper);
        //封装用户昵称和手机号
        List<CouponUse> couponUseList = page.getRecords();
        couponUseList.stream().forEach(item -> {
            this.getUserInfoBycouponUse(item);
        });
        return page;
    }

    //封装用户昵称和手机号
    private CouponUse getUserInfoBycouponUse(CouponUse couponUse) {
        Long userId = couponUse.getUserId();
        if (!StringUtils.isEmpty(userId)) {
            // 远程调用 获取 用户信息的 接口
            UserInfo userInfo = userInfoFeignClient.getById(userId);
            if (userInfo != null) {
                couponUse.getParam().put("nickName", userInfo.getNickName());
                couponUse.getParam().put("phone", userInfo.getPhone());
            }
        }
        return couponUse;
    }
}




