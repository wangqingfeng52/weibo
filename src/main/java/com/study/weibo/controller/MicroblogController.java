package com.study.weibo.controller;

import com.alibaba.fastjson.JSON;
import com.study.weibo.domain.Microblog;
import com.study.weibo.service.MicroblogService;
import com.study.weibo.util.TimeTool;
import com.study.weibo.util.TockenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
@RestController
public class MicroblogController {

    @Autowired
    private TockenService tockenService;

    @Autowired
    private MicroblogService microblogService;

    @PostMapping(value = "create")
    public String create(@RequestParam(value = "userid" ,required = true) String userid, @RequestParam(value = "token" ,required = true)
            String token, @RequestParam(value = "title",required = true) String title, @RequestParam(value = "content",required = true) String content) {

        boolean flag = tockenService.isToken(userid,token);

        String returnStr = "";
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("userid",userid);
        if(!flag){
            returnMap.put("code","03");
            returnMap.put("data","");
            returnStr = JSON.toJSONString(returnMap);
            return returnStr;
        }


        Microblog blog = new Microblog();
        blog.setOwner(Long.parseLong(userid));
        blog.setTitle(title);
        blog.setContent(content);
        long time = new Date().getTime();
        blog.setPosted_on(TimeTool.getLongDate(time));
        blog.setUpdate_time(TimeTool.getLongDate(time));

        try {
            microblogService.addBlog(blog);
            returnMap.put("code","00");
            Map<String,String> innerMap = new HashMap<String,String>();
            innerMap.put("content",content);
            innerMap.put("title",title);
            returnMap.put("data",innerMap);
            returnStr = JSON.toJSONString(returnMap);

            return returnStr;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            returnMap.put("code","03");
            returnMap.put("data","");
            returnStr = JSON.toJSONString(returnMap);
            return returnStr;
        }
    }



    @GetMapping(value="/getBlogContent/{articieId}")
    public String getBlogContent(@PathVariable(value = "articieId",required = true) Long articieId) {

        Map<String,Object> returnMap = new HashMap<String,Object>();

        Microblog blog = null;
        try {
            blog = microblogService.getBlogByID(articieId);
            if(blog!=null){
                List<Microblog> lists = new ArrayList<Microblog>();
                lists.add(blog);
                returnMap.put("code","00");
                returnMap.put("data",lists);
                return JSON.toJSONString(returnMap);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        returnMap.put("code","03");
        returnMap.put("data","");
        return JSON.toJSONString(returnMap);
    }


    @PutMapping(value="update")
    public String updateBlog(@RequestParam(value = "userid" ,required = true) String userid, @RequestParam(value = "token" ,required = true)
            String token, @RequestParam(value = "articieId",required = true) Long articleId ,@RequestParam(value = "title",required = true) String title, @RequestParam(value = "content",required = true) String content){

        boolean flag = tockenService.isToken(userid,token);
        Map<String,Object> returnMap = new HashMap<String,Object>();

 /*       {"articleId": 11, "update_time": "2016-09-06 12:08:10", "code": "00", "userid": 3}*/
        long updateTime = new Date().getTime();
        returnMap.put("articleId",articleId);
        returnMap.put("userid",userid);
        returnMap.put("update_time", TimeTool.getLongDate(updateTime));
        Microblog blog = null;
        if(flag){
            try {
                blog = microblogService.getBlogByID(articleId);
                if (blog != null) {
                    blog.setTitle(title);
                    blog.setContent(content);
                    blog.setUpdate_time(TimeTool.getLongDate(updateTime));

                    microblogService.updateBlog(blog);

                    returnMap.put("code","00");
                    return JSON.toJSONString(blog);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        }

        returnMap.put("code","03");
        return JSON.toJSONString(blog);

    }

    @PostMapping(value="delete")
    public String delBlog(@RequestParam(value = "userid" ,required = true) String userid, @RequestParam(value = "token" ,required = true)
            String token, @RequestParam(value = "articieId",required = true) String articleIds ){
        //建议改成 1个1个删除

        boolean flag = tockenService.isToken(userid,token);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("userid",userid);
        returnMap.put("articieId",articleIds);
        /*{"articleId": [1, 2, 3], "code": "00", "userid": 1}*/
        if(articleIds.startsWith("[")){
            articleIds = articleIds.substring(1,articleIds.length());
        }
        if(articleIds.endsWith("]")){
            articleIds = articleIds.substring(0,articleIds.length()-1);
        }

        String[] splits = articleIds.split(",");


        List<Microblog> lists = new ArrayList<Microblog>();

        for(String ids : splits){
            Microblog blog = new Microblog();
            blog.setArticleId(Long.parseLong(ids.trim()));
            lists.add(blog);
        }
        if(flag){
            try {
                microblogService.deleteBlog(lists);
                returnMap.put("code","00");

                return JSON.toJSONString(returnMap);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        returnMap.put("code","03");

        return JSON.toJSONString(returnMap);
    }


    @GetMapping(value="getBlogsContent/{articieId}")
    public String getBlogsContent(@PathVariable(value = "articieId",required = true) String articleIds){


        List<Microblog> blogByManys = microblogService.getBlogByManyID(articleIds);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("code","00");
        returnMap.put("data",blogByManys);
        return JSON.toJSONString(returnMap);
    }


    @PostMapping(value="getBlogsOfUser")
    public String getBlogsContent(@RequestParam(value = "userid" ,required = true) String userid, @RequestParam(value = "token" ,required = true)
            String token, @RequestParam(value = "offset",required = true) int pagenumber,@RequestParam(value = "lines",required = true) int lines){
        boolean flag = tockenService.isToken(userid,token);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("userid",userid);

        if(flag){
            Pageable page =new PageRequest(pagenumber,lines);
            try{
                List<Microblog> blogByIDAndPage = microblogService.getBlogByIDAndPage(Long.parseLong(userid), page);
                returnMap.put("data",blogByIDAndPage);
                returnMap.put("code","00");
                return JSON.toJSONString(returnMap);
            }catch (Throwable throwable){
                throwable.printStackTrace();
            }

        }
        returnMap.put("code","03");
        returnMap.put("data","");

        return JSON.toJSONString(returnMap);
    }

}