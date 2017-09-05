package com.study.weibo.util;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
@Component
public class Oid {
    UUID uuid = UUID.randomUUID();

    public String getOid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    public static void main(String args[]){

        String ss = "insert into values (1,',')";
        String[] split = ss.split(Pattern.quote(","));
        System.out.println(split.length);
    }
}
