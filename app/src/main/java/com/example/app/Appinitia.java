package com.example.app;

import android.app.Application;

/**
 * Created by Administrator on 2018/9/2.
 */

public class Appinitia extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Initializer.init(this)
               .withApiHost("119.23.27.224")
               // .withApiHost("192.168.31.132")
               // .withApiHost("192.168.43.204")
                .withPort(5255)
                .initiaXF()
                .withSwitchView();
    }
}
