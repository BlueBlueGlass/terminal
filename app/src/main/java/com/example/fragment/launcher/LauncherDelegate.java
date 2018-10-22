package com.example.fragment.launcher;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.WindowManager;

import com.example.fragment.BaseFragment;
import com.example.fragment.sign.SignInDelegate;
import com.example.fragment.sign.SignListener;
import com.example.net.ConnectSocketTask;
import com.example.net.ReceiveListener;
import com.example.terminal.R;
import com.example.terminal.R2;
import com.example.util.WdPreference;
import com.example.util.sign.AcountMagnager;
import com.example.util.sign.UserChecker;
import com.example.util.timer.BaseTimerTask;
import com.example.util.timer.ITimerListener;


import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/3.
 */

public class LauncherDelegate extends BaseFragment implements ITimerListener{
    Timer  mTimer;

    private int count = 4;

    private void initTimer(){
       mTimer = new Timer();
     final BaseTimerTask task = new BaseTimerTask(this);
     mTimer.schedule(task,0,1000);
    }
    @Override
    public Object setLayout() {
        return R.layout.fragmengt_launcher;
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        initTimer();
    }
    //判断是否展示滑动页面
     private void checkIsSign(){
            //检查用户是否登录
            AcountMagnager.checkAccount(new UserChecker() {
                @Override
                public void onSign() {
//                    BaseFragment.socketTask =  new ConnectSocketTask(getContext()
//                            , new ReceiveListener(getContext()));
//                    BaseFragment.socketTask.execute(WdPreference.getUsr());
                }

                @Override
                public void onNotSign() {
                  startWithPop(new SignInDelegate());
                }
            });

     }
    @Override
    public void onTimer() {
         getActivity().runOnUiThread(new Runnable() {
             @Override
             public void run() {
                     count--;
                     if(count<0){
                         if(mTimer!=null){
                             mTimer.cancel();
                             mTimer=null;
                             checkIsSign();
                         }
                     }
             }
         });
    }
}

