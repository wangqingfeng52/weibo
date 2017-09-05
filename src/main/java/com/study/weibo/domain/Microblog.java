package com.study.weibo.domain;

import javax.persistence.*;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
@Entity
@Table(name="micro_blog")
public class Microblog {

    //主键
    private Long id;

    //标题
    private String title;

    //内容
    private String content;

    //创建人
    private Long createUserID;

    //创建时间
    private Long createTime;

    //最后一次修改时间
    private Long updateTime;

    public Microblog() {
    }


    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Column(name = "CREATE_USER_ID")
    public Long getCreateUserID() {
        return createUserID;
    }

    public void setCreateUserID(Long createUserID) {
        this.createUserID = createUserID;
    }

    @Column(name = "CREATE_TIME")
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UPDATE_TIME")
    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Microblog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createUserID=" + createUserID +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

