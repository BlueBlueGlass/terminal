package com.example.util.speech_voice;

import android.annotation.SuppressLint;
import android.provider.ContactsContract;

import com.alibaba.fastjson.JSON;
import com.example.gson.communication_json.UserInfoJson;
import com.example.gson.weather_gson.Forecast;
import com.example.net.TempInfo;
import com.example.net.UserBean;
import com.example.net.WriterHolder;
import com.example.util.jsonutil.DataConvertor;
import com.example.util.weather.WeatherUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/9/12.
 */

public class SpeechUtil {
    public static void dealWithVoice(String voiceStr){
        if(voiceStr.contains("明天")&&voiceStr.contains("天气")){
          speakWeather(getWeatherStr(1));
        }else if(voiceStr.contains("今天")&&voiceStr.contains("天气")){
            speakWeather(getWeatherStr(0));
        }else if(voiceStr.contains("后天")&&voiceStr.contains("天气")){
            speakWeather(getWeatherStr(2));
        }else if(voiceStr.contains("3")||voiceStr.contains("三")&&voiceStr.contains("天气")){
            StringBuilder builder = new StringBuilder();
            for(int i=0;i<=2;i++){
               builder.append(getWeatherStr(i));
            }
            speakWeather(builder.toString());
        }else if(voiceStr.contains("开启全天监控")||voiceStr.contains("打开全天监控")){
            final UserBean tempUserInfo = new UserBean();
            tempUserInfo.setMonitor(true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WriterHolder.getInstance().sendMsgToServer(JSON.toJSONString(
                            new UserInfoJson(tempUserInfo)),new TempInfo(true,tempUserInfo));
                }
            }).start();
        }else if(voiceStr.contains("关闭全天监控")||voiceStr.contains("取消全天监控")){
            final UserBean tempUserInfo = new UserBean();
            tempUserInfo.setMonitor(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    WriterHolder.getInstance().sendMsgToServer(JSON.toJSONString(
                            new UserInfoJson(tempUserInfo)),new TempInfo(true,tempUserInfo));
                }
            }).start();
        }
    }
    @SuppressLint("SimpleDateFormat")
    private static void speakWeather(String weatherStr){
        TTSUtils.getInstance().speak(weatherStr);
    }
    private static String getWeatherStr(int day){
        String cityName =  WeatherUtil.mWeather.basic.cityName;
        Forecast forecastWeather =  WeatherUtil.mWeather.threeDayForecast.get(day);
        String info = forecastWeather.cond.info;
        String max = forecastWeather.temperture.max+"度";
        String min = forecastWeather.temperture.min+"度";
        String date =null;
        switch (day){
            case 0:date = "今天天气";break;
            case 1:date = "明天天气";break;
            case 2:date = "后天天气";break;
            default:
                DateFormat simpleDateFormat2= new SimpleDateFormat("MM月dd日");
                SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date1= simpleDateFormat1.parse(forecastWeather.date);
                    date = simpleDateFormat2.format(date1)+"天气";
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
        return cityName+date+info+"，最高温度"+max+"，最低温度"+min+"。";
    }
}
