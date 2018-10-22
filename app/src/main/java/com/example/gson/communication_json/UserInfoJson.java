package com.example.gson.communication_json;

import com.example.net.UserBean;

/**
 * Created by Administrator on 2018/9/26.
 */

public class UserInfoJson {
    public UserInfoJson(){

    }
    public UserInfoJson(UserBean userInfo) {
        this.userInfo = userInfo;
    }

    public UserBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserBean userInfo) {
        this.userInfo = userInfo;
    }

    public UserBean userInfo;
}
