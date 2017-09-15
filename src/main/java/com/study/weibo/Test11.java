package com.study.weibo;

import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.study.weibo.domain.Microblog;
import com.study.weibo.repository.MicroblogRepository;
import com.study.weibo.util.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/11 0011.
 */
@Component
public class Test11 {


    @Autowired
    private MicroblogRepository microblogRepository;


    public Test11() {
        //bathPushData();
    }

    public static void main(String args[]){
        getData(4l,"b98ba461021d4768ab975b624fa581d1");
    }


    public  void bathPushData(){

        for(int i=1;i<10000;i++){
            try {
                System.out.println("开始创建"+i+"用户!");
                HttpResponse<String> response = Unirest.post("http://127.0.0.1:8080/register")
                        .header("content-type", "application/x-www-form-urlencoded")
                        .header("cache-control", "no-cache")
                        .header("postman-token", "7de74cfe-146d-cbb2-e8c2-a64c033674e9")
                        .body("username=wqf"+i+"&password=wang863452&email=331047128%40qq.com")
                        .asString();


                HttpResponse<String> response1 = Unirest.post("http://127.0.0.1:8080/login")
                        .header("content-type", "application/x-www-form-urlencoded")
                        .header("cache-control", "no-cache")
                        .header("postman-token", "5e4a69eb-da2f-4c21-7f80-88c21cff0dea")
                        .body("username=wqf"+i+"&password=wang863452")
                        .asString();

                Map map = JSON.parseObject(response1.getBody(), Map.class);
                long userid = Long.parseLong(map.get("userid").toString());

                List<Microblog> lists = new ArrayList<Microblog>();
                for(int m=0;m<10000;m++){
                    Microblog blog = new Microblog();
                    blog.setOwner(userid);
                    blog.setTitle(m+"");
                    blog.setContent(m+""+m);
                    blog.setUpdate_time(TimeTool.getLongDate(new Date().getTime()));
                    blog.setPosted_on(TimeTool.getLongDate(new Date().getTime()));
                    lists.add(blog);
                }


                microblogRepository.save(lists);

               // pushData(userid,token);
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }


    }


    public static void getData(long userid,String token){
        long startTime = new Date().getTime();
        try {
            for(int i=0;i<1;i++){
                HttpResponse<String> response = Unirest.post("http://127.0.0.1:8080/getBlogsOfUser")
                        .header("content-type", "application/x-www-form-urlencoded")
                        .header("cache-control", "no-cache")
                        .header("postman-token", "5f4fe876-a1c0-53d3-a754-fe12984592c9")
                        .body("userid="+userid+"&token="+token+"&offset="+i+"&lines=2")
                        .asString();

               System.out.println(response.getBody());
            }

        } catch (UnirestException e) {
            e.printStackTrace();
        }

        System.out.println("共使用:"+(new Date().getTime()-startTime)+"毫秒");

    }


    public static void pushData(long userid,String token){
        long startTime = new Date().getTime();
        for(int i=0;i<10000;i++){
            try {
                HttpResponse<String> stringHttpResponse = Unirest.post("http://127.0.0.1:8080/create")
                        .header("content-type", "application/x-www-form-urlencoded")
                        .header("cache-control", "no-cache")
                        .header("postman-token", "6e0fcfd5-7fa0-7d76-fece-60d91f253bb4")
                        .body("userid="+userid+"&token="+token+"&title="+i+"&content="+i)
                        .asString();

                //System.out.println(stringHttpResponse.getBody());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("共使用:"+(new Date().getTime()-startTime)+"毫秒");
    }

}
