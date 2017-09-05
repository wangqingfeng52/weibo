package com.study.weibo.repository;

import com.study.weibo.domain.Microblog;
import org.springframework.data.jpa.repository.JpaRepository;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
public interface MicroblogRepository extends JpaRepository<Microblog,Long>{

}
