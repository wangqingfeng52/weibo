package com.study.weibo.controller;

import com.alibaba.fastjson.JSON;
import com.study.weibo.domain.User;
import com.study.weibo.redis.RedisService;
import com.study.weibo.service.UserService;
import com.study.weibo.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
@RestController
public class UserController {


    @Autowired
    private TockenService tockenService;

    @Autowired
    private Oid oid;

    @Autowired
    private UserService userService;


    @Value("${redis.time.limit}")
    private Long REDIS_TIME_LIMIT;


    @PostMapping(value="register")
    public String register(@RequestParam(value = "username" ,required = true) String userName,@RequestParam(value = "password",required = true)
            String password,@RequestParam(value = "email" ,required = true) String email){


        String returnStr = "";

        Map<String,Object> returnMap = new HashMap<String,Object>();

        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);


        if(!userService.isExit(user)){
            returnMap.put("code","01");
            returnMap.put("userid",-1);
            returnStr = JSON.toJSONString(returnMap);
            return returnStr;
        }

        boolean checkFlag = userService.checkUserData(user);
        if(!checkFlag){
            returnMap.put("code","02");
            returnMap.put("userid",-1);
            returnStr = JSON.toJSONString(returnMap);
            return returnStr;
        }

        userService.createFillUser(user);
        userService.md5Password(user);
        long id =  userService.addUser(user);

        returnMap.put("code","00");
        returnMap.put("userid",id);
        returnStr = JSON.toJSONString(returnMap);
        return returnStr;
    }

    @PostMapping(value="login")
    public String login( @RequestParam(value = "username" ,required = true)
            String userName, @RequestParam(value = "password" ,required = true)
            String password){

        String returnStr = "";
        Map<String,Object> returnMap = new HashMap<String,Object>();

        long loginTime = new Date().getTime();
        returnMap.put("login_time",loginTime);

        User user = null;
        try {
            user = userService.getUserByUserNameAndPassword(userName, MD5.MD5_32bit(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(user==null){
            returnMap.put("code","03");
            returnMap.put("userid",-1);
            returnMap.put("token","");
            returnStr = JSON.toJSONString(returnMap);
            return returnStr;
        }





        Object o = tockenService.getToken(user.getId()+"");
        //如果缓存中存在
        if(o!=null&&o.toString().length()>0){
            return o.toString();
        }


        String token =  oid.getOid();

        returnMap.put("code","00");
        returnMap.put("userid",user.getId());
        returnMap.put("token", token);
        returnMap.put("login_time",loginTime);
        user.setLoginTime(loginTime);

        tockenService.putToken(user.getId()+"",JSON.toJSONString(returnMap),REDIS_TIME_LIMIT);

        return JSON.toJSONString(returnMap);
    }

}
