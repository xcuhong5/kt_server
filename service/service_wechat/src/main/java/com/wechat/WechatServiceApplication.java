package com.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created with IntelliJ IDEA.
 * Description:kt_server
 *
 * @Author: sky
 * DateTime: 2022-09-20 10:52
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.wechat.mapper")
public class WechatServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WechatServiceApplication.class, args);
    }
}
