package com.study.weibo.service.impl;

import com.study.weibo.domain.User;
import com.study.weibo.repository.UserRepository;
import com.study.weibo.service.UserService;
import com.study.weibo.util.CheckValue;
import com.study.weibo.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean checkUserData(User user) {

        String userName = user.getUserName();
        boolean flag = CheckValue.checkNotNull(userName) && CheckValue.checkLengthLeftLimit(userName,2)
                && CheckValue.checkLengthRightLimit(userName,20) && CheckValue.checkOnlyNumberAndChar(userName);

        String password = user.getPassword();
        flag = flag && CheckValue.checkNotNull(userName)  && CheckValue.checkLengthLeftLimit(password,8)
                && CheckValue.checkLengthRightLimit(userName,20) && CheckValue.checkMustAllIncludeNumberAndChar(password);

        String email = user.getEmail();
        flag = flag && CheckValue.checkEmail(email);

        return flag;
    }

    @Override
    public boolean isExit(User user) {
        User u = userRepository.getUserByUserName(user.getUserName().toLowerCase());
        if(u==null){
            return true;
        }
        return false;
    }

    @Override
    public User createFillUser(User user) {
        user.setCreateTime(new Date().getTime());
        return user;
    }

    @Override
    public long addUser(User user) {
        User save = userRepository.save(user);
        return save.getId();
    }

    @Override
    public void md5Password(User user) {
        String password = user.getPassword();
        try {
            user.setPassword(MD5.MD5_32bit(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByUserNameAndPassword(String userName,String password) {
        return userRepository.getUserByUserNameAndPassword(userName,password);
    }
}
