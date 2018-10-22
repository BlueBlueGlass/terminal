package com.example.util.weather;


import com.example.gson.weather_gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/7/21.
 */

public class JsonUtil {

    public static Weather handleWeatherRespone(String respone){
        try {
            JSONObject jsonObject = new JSONObject(respone);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
           return new Gson().fromJson(weatherContent,Weather.class);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
