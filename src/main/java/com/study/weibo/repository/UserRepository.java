package com.study.weibo.repository;

import com.study.weibo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
public interface UserRepository extends JpaRepository<User,Long>{

    public User getPersonByUserName(String userName);

}