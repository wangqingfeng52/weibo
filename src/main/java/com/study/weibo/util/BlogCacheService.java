package com.study.weibo.util;

import com.alibaba.fastjson.JSON;
import com.study.weibo.domain.Microblog;
import com.study.weibo.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@SuppressWarnings("ALL")
/**
 * Created by Administrator on 2017/9/15 0015.
 */
@Service
public class BlogCacheService {

    @Autowired
    private RedisService redisService;


    public Object getBlogByID(Long articieId) {
        try{
            return redisService.get(Context.BLOG_ID+articieId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public void saveBlog(Microblog bolg) {
        try{
            redisService.set(Context.BLOG_ID+bolg.getArticleId(),JSON.toJSONString(bolg));
            redisService.hmSet(Context.USER_BLOG+bolg.getOwner(),bolg.getArticleId()+"", JSON.toJSONString(bolg));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteBlogByID(Microblog bolg) {
        try{
            redisService.remove(Context.BLOG_ID+bolg.getArticleId());
            redisService.hmDel(Context.USER_BLOG+bolg.getOwner(),bolg.getArticleId()+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Microblog> getBlogByUserAndPage(long userID, Pageable page) {

        if(page.getSort()!=null){
            return null;
        }
        Object o = redisService.hmGetAll(Context.USER_BLOG + userID);
        Map<String,Microblog> maps = new HashMap<String,Microblog>();
        List<Long> blogIds = new ArrayList<Long>();
        List<Microblog> returnList = new ArrayList<Microblog>();
        if(o!=null){
            List<String> strList = (List<String>) o;
            for(String json : strList){
                try{
                    Microblog blog = JSON.parseObject(json, Microblog.class);
                    blogIds.add(blog.getArticleId());
                    maps.put(blog.getArticleId()+"",blog);
                    Collections.sort(blogIds);
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
            int pageNumber = page.getPageNumber();
            int pageSize = page.getPageSize();

            int initial_position = pageNumber * pageSize;
            for(int i=0;i<pageSize;i++){
                Microblog microblog = maps.get(i);
                returnList.add(microblog);
            }

            return returnList;

        }
        return null;
    }
}
