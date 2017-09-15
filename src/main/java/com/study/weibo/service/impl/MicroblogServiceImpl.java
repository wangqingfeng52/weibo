package com.study.weibo.service.impl;

import com.alibaba.fastjson.JSON;
import com.study.weibo.domain.Microblog;
import com.study.weibo.repository.MicroblogRepository;
import com.study.weibo.service.MicroblogService;
import com.study.weibo.util.BlogCacheService;
import com.study.weibo.util.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
@Service
public class MicroblogServiceImpl implements MicroblogService {

    @Value(value = "${redis.cache.blog}")
    public String REDIS_CACHE_BLOG;

    @Autowired
    private MicroblogRepository microblogRepository;

    @Autowired
    private BlogCacheService blogCacheService;

    @Override
    public void addBlog(Microblog bolg) throws Throwable{
        microblogRepository.save(bolg);
        if(Boolean.parseBoolean(REDIS_CACHE_BLOG)){
            blogCacheService.saveBlog(bolg);
        }
    }


    @Override
    public Microblog getBlogByID(Long articieId) throws Throwable{
        if(Boolean.parseBoolean(REDIS_CACHE_BLOG)){
            Object o =blogCacheService.getBlogByID(articieId);
            if(o!=null&&o.toString().length()>0){
                 try{
                     Microblog blog = JSON.parseObject( o.toString(),Microblog.class);
                     return blog;
                 }catch (Exception e){
                     e.printStackTrace();
                 }
            }
        }
        Microblog one = microblogRepository.getOne(articieId);
        if(Boolean.parseBoolean(REDIS_CACHE_BLOG)){
            blogCacheService.saveBlog(one);
        }
        return one;
    }

    @Override
    public void updateBlog(Microblog blog) throws Throwable {
        microblogRepository.save(blog);
        if(Boolean.parseBoolean(REDIS_CACHE_BLOG)){
            blogCacheService.saveBlog(blog);
        }
    }

    @Override
    public void deleteBlog(List<Microblog> microblogs) throws Throwable{
        microblogRepository.delete(microblogs);
        if(Boolean.parseBoolean(REDIS_CACHE_BLOG)){
            for(Microblog bolg :microblogs){
                blogCacheService.deleteBlogByID(bolg);
            }
        }
    }

    @Override
    public List<Microblog> getBlogByIDAndPage(long userID, Pageable page) throws Throwable{
        if(Boolean.parseBoolean(REDIS_CACHE_BLOG)){
            List<Microblog> blogByUserAndPage = blogCacheService.getBlogByUserAndPage(userID, page);
            return blogByUserAndPage;
        }
        List<Microblog> blogs = microblogRepository.getMicroblogsByOwnerAndPageAble(userID, page);
        if(blogs!=null && blogs.size()>0 &&  Boolean.parseBoolean(REDIS_CACHE_BLOG)){
            for(Microblog bolg :blogs){
                blogCacheService.saveBlog(bolg);
            }
        }

        return blogs;
    }

    @Override
    public List<Microblog> getBlogByManyID(String articleIds) {
        //articleIds=1,2,3
        String[] split1 = articleIds.split(Pattern.quote("="));

        String[] splits = split1[1].split(Pattern.quote(","));
        List<Long> ids = new ArrayList<Long>();
        List<Microblog> caches = new ArrayList<Microblog>();
        for(String sid : splits){
            ids.add(Long.parseLong(sid));
            if(Boolean.parseBoolean(REDIS_CACHE_BLOG)){
                Object o = blogCacheService.getBlogByID(Long.parseLong(sid));
                if(o!=null&&o.toString().length()>0){
                    try{
                        Microblog microblog = JSON.parseObject(o.toString(), Microblog.class);
                        caches.add(microblog);
                        ids.remove(Long.parseLong(sid));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }



        List<Microblog> blogs = microblogRepository.getBlogs(ids);
        if(blogs!=null &&  Boolean.parseBoolean(REDIS_CACHE_BLOG)){
            for(Microblog blog : blogs){
                blogCacheService.saveBlog(blog);
            }
        }
        blogs.addAll(caches);
        return blogs;
    }


}
