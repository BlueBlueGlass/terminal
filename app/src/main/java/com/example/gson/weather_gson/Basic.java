package com.example.gson.weather_gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/7/22.
 */

public class Basic {
    @SerializedName("location")
    public String cityName;
    @SerializedName("cid")
    public String weatherId;
    public Update update;
    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }

}
