package com.study.weibo.service.impl;

import com.study.weibo.domain.Microblog;
import com.study.weibo.repository.MicroblogRepository;
import com.study.weibo.service.MicroblogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void deleteBlog(Long articleId) throws Throwable{
        microblogRepository.delete(articleId);
    }

    @Override
    public List<Microblog> getBlogByIDAndPage(long userID, Pageable page) {
        return microblogRepository.getMicroblogsByOwnerAndPageAble(userID, page);
    }
}
