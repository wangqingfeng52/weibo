package com.study.weibo.service;


import com.study.weibo.domain.Microblog;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
public interface MicroblogService {
    void addBlog(Microblog bolg) throws Throwable;

    Microblog getBlogByID(Long articieId) throws Throwable;

    void updateBlog(Microblog blog) throws Throwable;

    void deleteBlog(List<Microblog> microblogs) throws Throwable;

    List<Microblog> getBlogByIDAndPage(long l, Pageable page);

    void getBlogByManyID(String articleIds);
}
