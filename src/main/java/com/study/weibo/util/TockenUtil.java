package com.study.weibo.util;


import com.alibaba.fastjson.JSON;
import com.study.weibo.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
@Component
public class TockenUtil {

    @Autowired
    RedisService redisService;

    public boolean isToken(String userid, String token){
        Object o = redisService.get(userid);
        boolean flag = true;
        if(o==null){
            flag = false;
        }else{
            try{
                Map map = JSON.parseObject(o.toString(), Map.class);
                Object tokenSession = map.get("token");
                if(tokenSession==null || !tokenSession.toString().equals(token)){
                    flag = false;
                }
            }catch (Exception e){
                e.printStackTrace();
                flag = false;
            }
        }

        return flag;
    }
}
