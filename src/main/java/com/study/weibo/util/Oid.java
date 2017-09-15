package com.study.weibo.util;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
@Component
public class Oid {

    public String getOid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


}
