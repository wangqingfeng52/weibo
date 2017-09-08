package com.study.weibo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
@Component
public class RequestOne {

    @Autowired
    private static HttpSession request;

    public HttpSession getRequest() {
        return request;
    }

    @Autowired
    public void setRequest(HttpSession request) {
        this.request = request;
    }
}
