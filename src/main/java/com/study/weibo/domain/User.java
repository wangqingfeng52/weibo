package com.study.weibo.domain;

import javax.persistence.*;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
@Entity
@Table(name="weibo_user")
public class User {

    //主键
    private Long id;

    //用户名
    private String userName;

    //密码
    private String password;

    //email
    private String email;

    //创建时间
    private long createTime;

    //最后一次登录时间
    private long loginTime;


    public User() {
    }


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name="USER_PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Column(name="USER_EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name="CREATE_TIME")
    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }


    @Column(name="LOGIN_TIME")
    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                ", loginTime=" + loginTime +
                '}';
    }
}
