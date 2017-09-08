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
    private Long articleId;

    //标题
    private String title;

    //内容
    private String content;

    //创建人
    private Long owner;

    //创建时间
    private Long posted_on;

    //最后一次修改时间
    private Long update_time;

    public Microblog() {
    }

    @Id
    @GeneratedValue
    @Column(name="id")
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }


    @Column(name = "TITLE",columnDefinition="text")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Column(name = "CONTENT",columnDefinition = "text")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "CREATE_USER_ID")
    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    @Column(name = "CREATE_TIME")
    public Long getPosted_on() {
        return posted_on;
    }

    public void setPosted_on(Long posted_on) {
        this.posted_on = posted_on;
    }

    @Column(name = "UPDATE_TIME")
    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }

}

