package com.example.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.app.Initializer;
import com.example.fragment.BaseFragment;
import com.example.fragment.IndexFragment;
import com.example.fragment.sign.SignInDelegate;
import com.example.net.ConnectSocketTask;
import com.example.net.ReceiveListener;
import com.example.net.UserBean;
import com.example.notification.NotificationHolder;
import com.example.terminal.MainActivity;
import com.example.terminal.R;

/**
 * Created by Administrator on 2018/9/19.
 */

public class NetBgService extends Service{
    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("正在后台运行中")
                .setContentText("触摸可进入应用")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String userName = intent.getStringExtra("userName");
        String passWord = intent.getStringExtra("passWord");
        UserBean userBean = new UserBean(userName,passWord);
        BaseFragment.socketTask =  new ConnectSocketTask(SignInDelegate.THIS.getContext()
                , new ReceiveListener(SignInDelegate.THIS.getContext()) {
            @Override
            public void deviceSuddenlyOffLine() {
                Intent intent = new Intent();
                intent.setAction("DEVICE_DISCONNECTED");
                Initializer.getApplicationContext().sendBroadcast(intent);
                Log.d("dsa","发硬件断线广播成功");
            }
            @Override
            public void showInfo(){
                Intent intent = new Intent();
                intent.setAction("INFO_UPDATED");
                Initializer.getApplicationContext().sendBroadcast(intent);
                Log.d("dsa","有环境数据更新了");
            }
            @Override
            public void onSignSuccess() {
                SignInDelegate.THIS.startWithPop( new IndexFragment());
            }
            @Override
            public void showDoor(){
                Intent intent = new Intent();
                intent.setAction("DOOR_UPDATED");
                Initializer.getApplicationContext().sendBroadcast(intent);
                Log.d("dsa","有Door数据更新了");
            }
        });
        BaseFragment.socketTask.execute(userBean);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
