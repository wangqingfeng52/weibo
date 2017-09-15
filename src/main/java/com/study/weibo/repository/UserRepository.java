package com.study.weibo.repository;

import com.study.weibo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
public interface UserRepository extends JpaRepository<User,Long>{

    public User getUserByUserNameAndPassword(String userName,String password);

    public User getUserByUserName(String userName);

}
