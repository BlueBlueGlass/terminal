package com.example.util.jsonutil;

import com.alibaba.fastjson.JSON;
import com.example.recyclerview.DoorBean;
import com.example.net.UserBean;

import java.util.List;

/**
 * Created by Administrator on 2018/9/2.
 */

public class DataConvertor {
   private static Responce responce;

    public static void convert(String json){
          responce = JSON.parseObject(json,Responce.class);
    }

    public static String getStatus(){
        return responce.getConnect_status();
    }

    public static Info getInfo(){
        return responce.getInfo();
    }
    public static DoorBean getDoorBean(){
        return responce.getDoorBean();
    } //单条doorAction
    public static List<DoorBean> getDoorList(){return  responce.getDoorList();} //初始10条数据
    public static void setUserInfo(UserBean userInfo){
        responce.setUserBean(userInfo);
    }

    public static void transferData(UserBean info){
        String tempStartTime =  info.getStartTime();
        String tempEndTime =  info.getEndTime();
        String tempEmergencyContact =  info.getEmergencyContact();
        String tempContent =  info.getContent();
        responce.getUserBean().setMonitor(info.isMonitor());
        if(tempStartTime!=null){
            responce.getUserBean().setStartTime(info.getStartTime());
        }
        if(tempEndTime!=null){
            responce.getUserBean().setEndTime(info.getEndTime());
        }
        if(tempEmergencyContact!=null){
            responce.getUserBean().setEmergencyContact(info.getEmergencyContact());
        }
        if(tempContent!=null){
            responce.getUserBean().setContent(info.getContent());
        }
    }

    public static UserBean getUserBean(){
       if(responce.userBean==null){
           responce.userBean = new UserBean();
       }
        return  responce.userBean;
    }


}
