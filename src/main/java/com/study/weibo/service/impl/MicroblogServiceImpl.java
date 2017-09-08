package com.study.weibo.service.impl;

import com.study.weibo.domain.Microblog;
import com.study.weibo.repository.MicroblogRepository;
import com.study.weibo.service.MicroblogService;
import org.springframework.stereotype.Service;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
@Service
public class MicroblogServiceImpl implements MicroblogService {

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
    public void deleteBlog(Long articleId) throws Throwable{

    }
}
