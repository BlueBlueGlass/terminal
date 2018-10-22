package com.example.util;

import android.content.Context;

import com.example.app.Initializer;

/**
 * Created by Administrator on 2018/9/4.
 */

public class ResourceUtil {

    public static int getResourceByReflect(String imageName){
        Context context = Initializer.getApplicationContext();
        int id = context.getResources().getIdentifier(imageName,
                "mipmap", context.getPackageName());
        return id;
    }
}
