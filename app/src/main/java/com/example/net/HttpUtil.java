package com.example.net;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2018/7/21.
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        String msg;
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(address).build();

             client.newCall(request).enqueue(callback);

    }
}
