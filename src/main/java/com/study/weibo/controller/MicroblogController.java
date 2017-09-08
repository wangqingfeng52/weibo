package com.study.weibo.controller;

import com.alibaba.fastjson.JSON;
import com.study.weibo.domain.Microblog;
import com.study.weibo.service.MicroblogService;
import com.study.weibo.util.TimeTool;
import com.study.weibo.util.TockenUtil;
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
    private TockenUtil tockenUtil;

    @Autowired
    private MicroblogService microblogService;

    @PostMapping(value = "create")
    public String register(@RequestParam(value = "userid" ,required = true) String userid, @RequestParam(value = "token" ,required = true)
            String token, @RequestParam(value = "title",required = true) String title, @RequestParam(value = "content") String content) {

        boolean flag = tockenUtil.isToken(userid,token);

        String returnStr = "";
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("userid",userid);
        if(!flag){
            returnMap.put("code","03");
            returnMap.put("data","");
            returnStr = JSON.toJSONString(returnMap);
            return returnStr;
        }


        Microblog bolg = new Microblog();
        bolg.setOwner(Long.parseLong(userid));
        bolg.setTitle(title);
        bolg.setContent(content);
        long time = new Date().getTime();
        bolg.setPosted_on(time);
        bolg.setUpdate_time(time);

        try {
            microblogService.addBlog(bolg);
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
            String token, @RequestParam(value = "articieId",required = true) Long articleId ,@RequestParam(value = "title",required = true) String title, @RequestParam(value = "content") String content){

        boolean flag = tockenUtil.isToken(userid,token);
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
                    blog.setUpdate_time(updateTime);

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
            String token, @RequestParam(value = "articieId",required = true) long articleId ){
        //建议改成 1个1个删除

        boolean flag = tockenUtil.isToken(userid,token);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("userid",userid);
        returnMap.put("articieId",articleId);
        /*{"articleId": [1, 2, 3], "code": "00", "userid": 1}*/
        if(flag){
            try {
                microblogService.deleteBlog(articleId);
                returnMap.put("code","00");
                return JSON.toJSONString(returnMap);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        returnMap.put("code","03");

        return JSON.toJSONString(returnMap);
    }


    @GetMapping(value="getBlogsContent")
    public String getBlogsContent(@RequestParam(value = "articieId",required = true) String articleIds){

        return "";
    }


    @PostMapping(value="getBlogsOfUser")
    public String getBlogsContent(@RequestParam(value = "userid" ,required = true) String userid, @RequestParam(value = "token" ,required = true)
            String token, @RequestParam(value = "offset",required = true) int offset,@RequestParam(value = "lines",required = true) int lines){
        boolean flag = tockenUtil.isToken(userid,token);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("userid",userid);

        if(flag){
            Pageable page =new PageRequest(offset,lines);
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