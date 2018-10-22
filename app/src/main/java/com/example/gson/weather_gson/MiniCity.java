package com.example.gson.weather_gson;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/23.
 */

public class MiniCity implements Serializable{
    String cityName;
    String cityWeatherId;

    public MiniCity(String cityName, String cityWeatherId) {
        this.cityName = cityName;
        this.cityWeatherId = cityWeatherId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityWeatherId() {
        return cityWeatherId;
    }
}
