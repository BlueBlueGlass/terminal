package com.example.app;

import android.bluetooth.BluetoothSocket;
import android.widget.LinearLayout;

import com.example.terminal.MyLinearLayout;
import com.example.util.SerializationUtil;
import com.example.util.detail.ParentButton;
import com.example.util.detail.SwitchBean;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/2.
 */

public class Configurator {
    private static final HashMap<Object,Object> CONFIGS = new HashMap<>();



    public static Configurator getInstance(){
     return Holder.INSTANCE;
    }

    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }

    public static HashMap<Object, Object> getCONFIGS() {
        return CONFIGS;
    }
    @SuppressWarnings("unchecked")
    final <T> T getAConfiguration(Enum<ConfigType> key){

        return (T) CONFIGS.get(key.name());
    }

    public final   Configurator withApiHost(String apiHost){
        CONFIGS.put(ConfigType.API_HOST.name(),apiHost);
        return this;
    }

    public final   Configurator withPort(int port){
        CONFIGS.put(ConfigType.PORT.name(),port);
        return this;
    }

    public final Configurator initiaXF(){
// 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
// 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(Initializer.getApplicationContext()
                , SpeechConstant.APPID +"=5b8d566a");
        return this;
    }
    public final Configurator withSwitchView(){
       ArrayList<ParentButton> list =  SerializationUtil.deseri();
       if(list==null){
           list = new ArrayList<>();
       }
        CONFIGS.put(ConfigType.SWITCH_VIEW.name(),list);
       return this;
    }
}
