package com.example.app;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.terminal.MyLinearLayout;
import com.example.util.detail.ParentButton;
import com.example.util.detail.SwitchBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/9/2.
 */

public class Initializer {
    public static Configurator init(Context context){
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }
    public static HashMap<Object,Object> getConfigurations(){
        return Configurator.getInstance().getCONFIGS();
    }
    public static Context getApplicationContext(){
        return (Context)getConfigurations().get( ConfigType.APPLICATION_CONTEXT.name());
    }

    public static String getApiHost(){
        return (String)getConfigurations().get(ConfigType.API_HOST.name());
    }
   @SuppressWarnings("unchecked")
    public static ArrayList<ParentButton> getSwitchView(){
        return (ArrayList<ParentButton>) getConfigurations().get(ConfigType.SWITCH_VIEW.name());
    }

    public static int getPort(){
        return (int)getConfigurations().get(ConfigType.PORT.name());
    }
}
