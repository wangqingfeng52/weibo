package com.study.weibo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
/**
 * Created by Administrator on 2017/9/5 0005.
 */
public class CheckValue {

    //非空检查
    public static boolean checkNotNull(Object o){
        if(o==null||o.toString().length()==0){
            return false;
        }
        return true;
    }

    //长度检查
    public static boolean checkLengthLeftLimit(Object o,int length)
    {
        int count = 0;
        if(o!=null){
            count = o.toString().length();
        }

        if(count<length){
            return false;
        }

        return true;
    }


    //长度检查
    public static boolean checkLengthRightLimit(Object o,int length)
    {
        int count = 0;
        if(o!=null){
            count = o.toString().length();
        }

        if(count>length){
            return false;
        }

        return true;
    }


    //判断是不是都以字符和数字组成
    public static boolean checkOnlyNumberAndChar(Object o){

        if(o==null){
            return true;
        }

        char[] chars = o.toString().toCharArray();
        for(char ch : chars){
           if ((ch>='0' && ch<='9') || (ch>='A' && ch<='Z'  ||  ch>='a' && ch<='z')){
               //
           }else{
               return false;
           }
        }

        return true;

    }

    //判断是否只包含数字或者字符并且 数字和字符都要包含
    public static boolean checkMustAllIncludeNumberAndChar(Object o){

        if(o==null){
            return true;
        }

        boolean numberFlag = false;
        boolean charFlag = false;

        char[] chars = o.toString().toCharArray();
        for(char ch : chars) {
            if ((ch >= '0' && ch <= '9')) {
                numberFlag = true;
            } else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')){
                charFlag = true;
            } else{
                return false;
            }
        }

        return numberFlag & charFlag;

    }



    //检查邮件格式
    public static boolean checkEmail(String email){
        if (null==email || "".equals(email)) return false;
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


}
