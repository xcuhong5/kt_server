package com.order.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:kt_server
 *
 * @Author: sky
 * DateTime: 2022-09-18 14:37
 */
@Configuration
@MapperScan("com.order.mapper")
public class OrderConfig {
    // 分页插件
    @Bean
    public MybatisPlusInterceptor myBatisPlusConfig() {
        // 获取 mybatis 拦截器对象
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 获取分页 插件 对象 ， 设置 数据库类型
        PaginationInnerInterceptor innerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 设置 分页策略， 最后一页 到 第一页 循环
        innerInterceptor.setOverflow(true);
        // 单页最大记录数
        innerInterceptor.setMaxLimit(500L);
        // 将 分页插件 添加到拦截器
        interceptor.addInnerInterceptor(innerInterceptor);
        return interceptor;
    }
}
