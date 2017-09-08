package com.study.weibo.repository.impl;

import com.study.weibo.domain.Microblog;
import com.study.weibo.repository.UserDealBlogRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Administrator on 2017/9/8 0008.
 */
public class MicroblogRepositoryImpl implements UserDealBlogRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Microblog> getBlogs(List<Long> blogIds) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Microblog> query = builder.createQuery(Microblog.class);
        Root<Microblog> root = query.from(Microblog.class);

        Predicate[] p = new Predicate[blogIds.size()];
        for(int i= 0;i<p.length;i++){
            p[i] = builder.equal(root.get("articleId"),blogIds.get(i));
        }

        query.where(builder.or(p));
        return em.createQuery(query.select(root)).getResultList();
    }
}
