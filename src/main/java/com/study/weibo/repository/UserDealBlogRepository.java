package com.study.weibo.repository;

import com.study.weibo.domain.Microblog;

import java.util.List;

/**
 * Created by Administrator on 2017/9/8 0008.
 */
public interface UserDealBlogRepository {

    List<Microblog> getBlogs(List<Long> blogIds);
}
