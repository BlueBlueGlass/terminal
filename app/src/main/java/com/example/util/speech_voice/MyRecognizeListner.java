package com.example.util.speech_voice;

import android.content.Context;

/**
 * Created by Administrator on 2018/9/4.
 */

public abstract class MyRecognizeListner {
   private Context context;

    public MyRecognizeListner(Context context) {
        this.context = context;
    }

    public abstract void updateUI(String str);

    public Context getContext(){
        return context;
    }


}
