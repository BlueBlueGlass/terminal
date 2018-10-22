package com.example.util.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.example.gson.weather_gson.Weather;
import com.example.net.HttpUtil;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/9/6.
 */

public class WeatherUtil {
    public static Weather mWeather;

    public interface WeatherShower{
        void   showWeatherInfo(Weather weather);
    }

    public static void requestWeather(String weatherId, final Context context, final WeatherShower shower) {
        String url = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=9a1a1e14e10e46d596ed114bd86bffd8";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, final IOException e) {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "获取网络天气失败......", Toast.LENGTH_SHORT).show();
                        SharedPreferences editor = PreferenceManager.getDefaultSharedPreferences(context);
                       String weatherContent =  editor.getString("weather",null);
                        final Weather weather = JsonUtil.handleWeatherRespone(weatherContent);
                        shower.showWeatherInfo(weather);

                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String weatherContent = response.body().string();
                Log.d("wudan", weatherContent);
                final Weather weather = JsonUtil.handleWeatherRespone(weatherContent);
                mWeather = weather;
                if(weather!=null){
                Log.d("wudan", weather.status);}
                ((Activity)context). runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && weather.status.equals("ok")) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                            editor.putString("weather", weatherContent);
                            editor.apply();
                            shower.showWeatherInfo(weather);
                        } else {
                            Toast.makeText(context, "获取天气失败...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
    }
}
