package com.client.user;

import com.atguigu.ggkt.model.user.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created with IntelliJ IDEA.
 * Description:kt_server
 *
 * @Author: sky
 * DateTime: 2022-09-19 17:16
 * 被远程 调用接口，该接口 是中间层
 */
/* @FeignClient  此接口 执行 指向的 具体执行的 服务名  */
@FeignClient(value = "service-user")
public interface UserInfoFeignClient {
    /* 目标执行 接口 全路径; 远程调用者 调用此接口，然后映射到 该路径指向的 接口 ，进行数据操作  */
    @GetMapping("/admin/user/userInfo/inner/getById/{id}")
    UserInfo getById(@PathVariable Long id);

}
