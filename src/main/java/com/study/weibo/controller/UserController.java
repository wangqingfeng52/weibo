package com.study.weibo.controller;

import com.alibaba.fastjson.JSON;
import com.study.weibo.domain.User;
import com.study.weibo.service.UserService;
import com.study.weibo.util.Oid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private Oid oid;

    @Autowired
    private UserService userService;


    @PostMapping(value="register")
    public String register(@Param(value = "username") String userName,@Param(value = "password")
            String password,@Param(value = "email") String email){


        String returnStr = "";

        Map<String,Object> returnMap = new HashMap<String,Object>();

        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);

        boolean checkFlag = userService.checkUserData(user);
        if(!checkFlag){
            returnMap.put("code","02");
            returnMap.put("userid",-1);
            returnStr = JSON.toJSONString(returnMap);
            return returnStr;
        }

        userService.createFillUser(user);
        userService.md5Email(user);
        long id =  userService.addUser(user);

        returnMap.put("code","02");
        returnMap.put("userid",id);
        returnStr = JSON.toJSONString(returnMap);
        return returnStr;
    }

    @PostMapping(value="login")
    public String login(@Param(value = "username") String userName,@Param(value = "password")
            String password){
        User user = userService.getUser(userName);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("login_time",new Date().getTime());

        long loginTime = new Date().getTime();
        String returnStr = "";
        if(user==null){
            returnMap.put("code","03");
            returnMap.put("userid",-1);
            returnMap.put("token","");
            returnStr = JSON.toJSONString(returnMap);
            return returnStr;
        }

        returnMap.put("code","00");
        returnMap.put("userid",user.getId());
        returnMap.put("token", oid.getOid());
        returnMap.put("login_time",loginTime);
        user.setLoginTime(loginTime);

        return JSON.toJSONString(user);
    }

}
