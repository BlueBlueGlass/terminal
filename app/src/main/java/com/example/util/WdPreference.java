package com.example.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.app.Initializer;
import com.example.net.UserBean;
import com.example.util.sign.SignStatus;
import com.example.util.sign.SignTag;

/**
 * Created by Administrator on 2018/8/3.
 */

public final class WdPreference {
    private static final SharedPreferences PREFERENCES=
            PreferenceManager.getDefaultSharedPreferences(Initializer.getApplicationContext());
    private static SharedPreferences getPreferences(){
        return  PREFERENCES;
    }
    public static void clearAppPreferences(){
        PREFERENCES
                .edit()
                .clear()
                .apply();
    }
    public static void setSignFlag(SignStatus status){
        PREFERENCES
                .edit()
                .putString(SignTag.SIGN_TAG.name(),status.name())
                .apply();
    }
    public static String getSignFlag(){
        return   PREFERENCES.getString(SignTag.SIGN_TAG.name(),SignStatus.DEVELOPER_SIGN.name());
    }
    public static void setUsr(String usrName,String password){
        PREFERENCES
                .edit()
                .putString("PASSWORD",password)
                .putString("USRNAME",usrName)
                .apply();
    }
    public static UserBean getUsr(){
        String usrName = PREFERENCES.getString("USRNAME",null);
        String password = PREFERENCES.getString("PASSWORD",null);
        if(TextUtils.isEmpty(usrName)||TextUtils.isEmpty(password)){
            return null;
        }
        return new UserBean(usrName,password);
    }
    }
