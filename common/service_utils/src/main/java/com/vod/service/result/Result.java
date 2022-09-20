package com.vod.service.result;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:webkt
 *
 * @Author: sky
 * DateTime: 2022-09-06 10:09
 */
/* 统一结果 数据 格式封装,封装成 json */
@Data
public class Result<T> {
    private Integer code;  // 结果状态嘛
    private String message; //结果信息  成功/失败
    private T data;          //返回需要 渲染的数据

    // 结果 成功的 数据封装
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        if (data != null) {
            result.setData(data);
        }
        result.setCode(20000);
        result.setMessage("成功");
        return result;
    }

    // 结果失败
    public static <T> Result<T> fail(T data) {
        Result<T> result = new Result<>();
        if (data != null) {
            result.setData(data);
        }
        result.setCode(20001);
        result.setMessage("失败");
        return result;
    }


    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}
