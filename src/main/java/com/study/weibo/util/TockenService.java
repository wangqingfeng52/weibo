package com.study.weibo.util;


import com.alibaba.fastjson.JSON;
import com.study.weibo.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@SuppressWarnings("ALL")
/**
 * Created by Administrator on 2017/9/7 0007.
 */
@Service
public class TockenService {

    @Autowired
    private RedisService redisService;

    public boolean isToken(String userid, String token){
        Object o = redisService.get(Context.USER_LOGIN+userid);
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


    public void putToken(String userid,String token,long timeLimit){
        redisService.set(Context.USER_LOGIN+userid,token,timeLimit);
    }

    public Object getToken(String userid){
        return  redisService.get(Context.USER_LOGIN+userid);
    }
}
