package com.vod;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-05 15:42
 */
/* 启动类 ； 将 工具模块 Swagger2 引入pom，包扫描的形式将配置类 加载到spring容器
* @ComponentScan  扫描 包下 的配置注解 的 类
* @EnableDiscoveryClient  开启 nacos 服务
* */

@SpringBootApplication
@ComponentScan(basePackages = "com.vod.*")
@EnableDiscoveryClient
public class VodServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodServiceApplication.class, args);
    }
}
