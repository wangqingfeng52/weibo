package com.study.weibo.repository;

import com.study.weibo.domain.Microblog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
public interface MicroblogRepository extends JpaRepository<Microblog,Long>{

    public List<Microblog> getMicroblogsByArticleIdContains(List<Long> ids);

    @Query("SELECT blog from Microblog blog where blog.owner =?1 ")
    public List<Microblog> getMicroblogsByOwnerAndPageAble(long userid, Pageable pageable);
}
