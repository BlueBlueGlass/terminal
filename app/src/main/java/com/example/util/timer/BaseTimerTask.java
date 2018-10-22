package com.example.util.timer;

import java.util.TimerTask;

/**
 * Created by Administrator on 2018/8/3.
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener mITimerListener) {
        this.mITimerListener = mITimerListener;
    }

    @Override
    public void run() {
      if(mITimerListener!=null){
           mITimerListener.onTimer();
      }
    }
}
