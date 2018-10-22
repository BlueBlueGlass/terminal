package com.example.util;

import android.content.Context;
import android.os.Environment;
import android.widget.LinearLayout;

import com.example.app.Initializer;
import com.example.terminal.MyLinearLayout;
import com.example.util.detail.ParentButton;
import com.example.util.detail.SwitchBean;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/7/23.
 */

public class SerializationUtil {

    private static String destpath =Environment.getExternalStorageDirectory().getPath()+"/LinerLayout";
    private static String destpath2 =Environment.getExternalStorageDirectory().getPath()+"/StringList";

            //seri(arr,"D:/游戏/employee.txt");
            //deseri("D:/游戏/employee.txt");


    public    static void seri(Object obj)
        {  try {
            File dest = new File(destpath);
            if (!dest.exists()) {
                dest.createNewFile();
            }
            ObjectOutputStream seriout = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(dest)
                    )
            );
            seriout.writeObject(obj);
            seriout.flush();
            seriout.close();
            }catch (IOException e){
            e.printStackTrace();
        }
        }
        @SuppressWarnings("unchecked")
     public static ArrayList<ParentButton> deseri()
        {  ArrayList<ParentButton> items = null;
            try {
            File dest = new File(destpath);
            if (!dest.exists()) {
                return null;
            }

            ObjectInputStream seriin = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(dest)
                    )
            );
            items = (ArrayList<ParentButton>) seriin.readObject();
            seriin.close();
        }catch (Exception e){
            e.printStackTrace();
        }
            return items;
        }
    private static String getSystemFilePath(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
//            cachePath = context.getExternalCacheDir().getPath();//也可以这么写，只是返回的路径不一样，具体打log看
        } else {
            cachePath = context.getFilesDir().getAbsolutePath();
//            cachePath = context.getCacheDir().getPath();//也可以这么写，只是返回的路径不一样，具体打log看
        }
        return cachePath;
    }
    public static void clearParentButton(){
        File dest = new File(destpath);
        if (dest.exists()) {
            dest.delete();
        }
    }


}
