package com.example.gson.weather_gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/7/22.
 */

public class Suggestion {
    @SerializedName("comf")
    public Confort confort;
    @SerializedName("cw")
    public Carwash carwash;
    public Sport sport;

    public class Confort{
        @SerializedName("txt")
        public String info;
    }
    public class Carwash{
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("txt")
        public String info;
    }
}
