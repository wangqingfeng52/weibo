package com.study.weibo.service;


import com.study.weibo.domain.Microblog;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
public interface MicroblogService {
    void addBlog(Microblog bolg) throws Throwable;

    Microblog getBlogByID(Long articieId) throws Throwable;

    void updateBlog(Microblog blog) throws Throwable;

    void deleteBlog(Long articleId) throws Throwable;
}
