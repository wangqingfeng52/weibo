package com.study.weibo.controller;

import com.study.weibo.Test11;
import com.study.weibo.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/8 0008.
 */
@RestController
public class TestController {
    @Autowired
    Test11 test11;
    @GetMapping("/test")
    public String test(){
        test11.bathPushData();
        return "完成";
    }
}
