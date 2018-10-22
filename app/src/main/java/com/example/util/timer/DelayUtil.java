package com.example.util.timer;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Administrator on 2018/9/7.
 */

public class DelayUtil {
    public static void delay(final long time, final Activity activity, final DelayListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDelayFinish();
                        }
                    });

                }
            }
        }).start();
    }


}
