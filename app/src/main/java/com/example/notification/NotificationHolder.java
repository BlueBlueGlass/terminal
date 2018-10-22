package com.example.notification;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;

import com.example.app.Initializer;
import com.example.clock.ClockTestUtil;
import com.example.terminal.MainActivity;
import com.example.terminal.R;
import com.example.util.bitmap.BitmapUtil;
import com.example.util.jsonutil.DataConvertor;

/**
 * Created by Administrator on 2018/9/17.
 */

public class NotificationHolder {
     private static NotificationManager mNotifyMgr  =(NotificationManager) Initializer
             .getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

    public static void showDanger(Context context,String msg){
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent knew = PendingIntent.getBroadcast(context, 1
                , new Intent("KNEW"), 0);
        PendingIntent callPolice = PendingIntent.getBroadcast(context, 2
                , new Intent("CALL_POLICE"), 0);
        Notification notification =
                new NotificationCompat.Builder(context)
                        .setContentTitle("New Message")
                        .setContentText(msg)
                        .setSmallIcon(R.mipmap.door_notifi)
                        .setLargeIcon(BitmapUtil.ResToBM(context, R.mipmap.door_notifi))
                        .setContentIntent(pi)
                        .addAction(R.mipmap.gas, "知道了", knew)
                        .addAction(R.mipmap.mouse, "报警", callPolice)
                        .setFullScreenIntent(pi, true)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setDefaults(Notification.DEFAULT_LIGHTS)
                        .setVibrate(new long[]{0,1000,1200,1000,1200,1000,1200,1000,1200,1000,1200,1000,1200,1000,1200})
                        .setSound(Uri.parse("android.resource://"+ context.getPackageName()+ "/" + R.raw.warning))
                        .setAutoCancel(true)
                        .build();

        notification.flags = Notification.FLAG_AUTO_CANCEL;

        cancelNotif(2);
        mNotifyMgr.notify(2, notification);

        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (km.isKeyguardLocked()) {   //为true就是锁屏状态下
            //启动Activity
            ClockTestUtil.wakeUpAndUnlock(context);
        }
    }

    public static void showSafe(Context context){
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification =
                new NotificationCompat.Builder(context)
                        .setContentTitle("New Message")
                        .setContentText(DataConvertor.getDoorBean().getDoorAction())
                        .setSmallIcon(R.mipmap.door_notifi)
                        .setLargeIcon(BitmapUtil.ResToBM(context, R.mipmap.door_notifi))
                        .setContentIntent(pi)
                        .setFullScreenIntent(pi, true)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .build();

        cancelNotif(2);
        mNotifyMgr.notify(2, notification);

        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        if (km.isKeyguardLocked()) {   //为true就是锁屏状态下
            //启动Activity
            ClockTestUtil.wakeUpAndUnlock(context);
        }
    }

    public static void cancelNotif(int id){
        mNotifyMgr.cancel(id);
    }
}
