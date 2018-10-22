package com.example.gson.weather_gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/7/22.
 */

public class Forecast {
    public String date;
    public Cond cond;
    @SerializedName("tmp")
    public Temperture temperture;
    public class Cond{
        @SerializedName("txt_d")
        public String info;
    }
    public class Temperture{
       public String max;
       public String min;
    }
}
