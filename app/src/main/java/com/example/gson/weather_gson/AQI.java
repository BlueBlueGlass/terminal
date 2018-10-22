package com.example.gson.weather_gson;

/**
 * Created by Administrator on 2018/7/22.
 */

public class AQI {
    public AQICity city;

    public class AQICity{
        public String pm25;
        public String aqi;
        public String qlty;
    }
}
