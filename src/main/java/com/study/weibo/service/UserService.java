package com.study.weibo.service;

import com.study.weibo.domain.User;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
public interface UserService {
    boolean checkUserData(User user);

    boolean isExit(User user);

    User createFillUser(User user);

    long addUser(User user);

    void md5Password(User user);

    public User getPersonByUserNameAndPassword(String userName,String password);
}
