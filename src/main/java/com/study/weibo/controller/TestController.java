package com.study.weibo.controller;

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
    RedisService redisService;
    @GetMapping("/test")
    public String test(){
        redisService.set("11","11");
        return redisService.get("11")==null?"":redisService.get("11").toString();
    }
}
