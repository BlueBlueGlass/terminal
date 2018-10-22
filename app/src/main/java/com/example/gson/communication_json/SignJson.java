package com.example.gson.communication_json;

import com.example.net.UserBean;

/**
 * Created by Administrator on 2018/9/8.
 */

public class SignJson {
    public SignJson(UserBean signin) {
        this.signin = signin;
    }
    public UserBean getSignin() {
        return signin;
    }
    public void setSignin(UserBean signin) {
        this.signin = signin;
    }
    public UserBean signin;
}
