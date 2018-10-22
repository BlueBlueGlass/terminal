package com.example.gson.weather_gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/7/22.
 */

public class Now {
    public String cond_code;

    @SerializedName("tmp")
    public String temp;
    @SerializedName("cond")
    public More more;
    public class More{
        @SerializedName("txt")
        public String info;
    }
}
