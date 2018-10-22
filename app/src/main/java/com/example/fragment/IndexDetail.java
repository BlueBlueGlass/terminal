package com.example.fragment;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.app.Configurator;
import com.example.app.Initializer;
import com.example.clock.ClockTestUtil;
import com.example.gson.weather_gson.Weather;
import com.example.terminal.MyLinearLayout;
import com.example.terminal.R;
import com.example.terminal.R2;
import com.example.util.detail.ParentButton;
import com.example.util.detail.SwitchBean;
import com.example.util.detail.SwitchCreator;
import com.example.util.sign.AcountMagnager;
import com.example.util.weather.WeatherUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/9/4.
 */

public class IndexDetail extends BaseFragment {
    private ArrayList<ParentButton> views;
    private BluetoothSocket mBlueToothSocket;
    private boolean isBlueToothSign = false;
    @BindView(R2.id.switch_container)
    LinearLayout switchContainer;

    @BindView(R2.id.data_container)
    LinearLayout dataContainer;

    @BindView(R2.id.switch_layout_container)
    LinearLayout switchLayoutContainer;
    @BindView(R2.id.temp)
    AppCompatTextView temperature;
    @BindView(R2.id.info)
    AppCompatTextView info;
    @BindView(R2.id.quality)
    AppCompatTextView quality;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_index_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        views = Initializer.getSwitchView();

        WeatherUtil.requestWeather("CN101190501", getContext(), new WeatherUtil.WeatherShower() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void showWeatherInfo(Weather weather) {
                        temperature.setText(weather.now.temp+"°");
                        info.setText(weather.now.more.info);
                        quality.setText(weather.aqi.city.qlty);
                    }
                }
        );

       int size =  views.size();
        for(int i=0;i<size;i++){
            SwitchCreator.showSwitch(views.get(i),getContext(),switchLayoutContainer);
        }
        if(AcountMagnager.isSignInUsr()){
        Intent intent = new Intent();
        intent.setAction("INFO_UPDATED");
        Initializer.getApplicationContext().sendBroadcast(intent);
        Log.d("dsa","初始化Info数据");}
    }
}
