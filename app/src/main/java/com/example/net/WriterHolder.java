package com.example.net;

import com.example.util.detail.ChangeIconListener;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/9/2.
 */

public class WriterHolder {
    private  PrintWriter mWriter;
    public static ChangeIconListener CHANGELISTENER;

    public static TempInfo TEMPINFO;

    private static class Holder{
        private static final WriterHolder INSTANCE = new WriterHolder();
    }

    public static WriterHolder getInstance(){
        return Holder.INSTANCE;
    }

    private   PrintWriter getmWriter() {
        return mWriter;
    }

    public void  setmWriter(PrintWriter mWriter) {
        this.mWriter = mWriter;
    }

    public Boolean sendMsgToServer(String msg ,ChangeIconListener listener){
        if(mWriter!=null){
            CHANGELISTENER = listener;
            mWriter.println(msg);
            mWriter.flush();
            return true;
        }
        return false;
    }

    public Boolean sendMsgToServer(String msg ,TempInfo tempInfo){
        if(mWriter!=null){
            TEMPINFO = tempInfo;
            mWriter.println(msg);
            mWriter.flush();
            return true;
        }
        return false;
    }

    public Boolean sendMsgToServer(String msg){
        if(mWriter!=null){
            mWriter.println(msg);
            mWriter.flush();
            return true;
        }
        return false;
    }

}
