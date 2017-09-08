package com.study.weibo.service.impl;

import com.study.weibo.domain.Microblog;
import com.study.weibo.repository.MicroblogRepository;
import com.study.weibo.service.MicroblogService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MicroblogRepository microblogRepository;

    @Override
    public void addBlog(Microblog bolg) throws Throwable{
        microblogRepository.save(bolg);
    }

    @Override
    public Microblog getBlogByID(Long articieId) throws Throwable{
        return microblogRepository.getOne(articieId);
    }

    @Override
    public void updateBlog(Microblog blog) throws Throwable {
        microblogRepository.save(blog);
    }

    @Override
    public void deleteBlog(List<Microblog> microblogs) throws Throwable{
        microblogRepository.delete(microblogs);
    }

    @Override
    public List<Microblog> getBlogByIDAndPage(long userID, Pageable page) {
        return microblogRepository.getMicroblogsByOwnerAndPageAble(userID, page);
    }

    @Override
    public void getBlogByManyID(String articleIds) {
        String[] splits = articleIds.split(Pattern.quote(","));
        List<Long> ids = new ArrayList<Long>();
        for(String sid : splits){
            ids.add(Long.parseLong(sid));
        }

        List<Microblog> blogs = microblogRepository.getBlogs(ids);
        System.out.println(blogs.size());

        //Arrays.asList(a)
    }
}
