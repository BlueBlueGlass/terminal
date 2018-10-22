package com.example.util.bitmap;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.terminal.MainActivity;
import com.example.terminal.R;

/**
 * Created by Administrator on 2018/9/24.
 */

public class BitmapUtil {
    public static Bitmap ResToBM(Context context, int resId){
        Resources res = context.getResources();
        return BitmapFactory.decodeResource(res,resId);
    }
}
