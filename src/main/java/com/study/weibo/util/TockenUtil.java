package com.study.weibo.util;


import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class TockenUtil {

    public static boolean isToken(HttpServletRequest req, String userid, String token){
        Object o = req.getSession().getAttribute(userid);
        boolean flag = true;
        if(o==null){
            flag = false;
        }else{
            try{
                Map map = JSON.parseObject(o.toString(), Map.class);
                Object tokenSession = map.get("token");
                if(!tokenSession.toString().equals(token)){
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
