package com.example.net;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.dialog.DialogHolder;
import com.example.app.Initializer;
import com.example.fragment.BaseFragment;
import com.example.gson.communication_json.SignJson;
import com.example.util.jsonutil.DataConvertor;
import com.example.util.WdPreference;
import com.example.util.sign.AcountMagnager;
import com.example.util.sign.SignStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/9/1.
 */

public class ConnectSocketTask extends AsyncTask<UserBean,String,Boolean> {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader read;
    private Boolean connectedFlag = false;
    private  ReceiveListener listener;
    private Timer sendPingTimer;
    private UserBean userBean;
    private boolean threadContinue = true;
    private Timer timer;
    private int count = 0;
    private int lastCount = 0;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    public ConnectSocketTask(Context context,ReceiveListener listener) {
        mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        DialogHolder.showDialogForLoading(mContext,"Loading...");
        //DialogHolder.showDialogForWarning(mContext,"服务器错误");
    }

    @Override
    protected Boolean doInBackground(UserBean... userBeans){

        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
           userBean = userBeans[0];
        userBean.setMonitor(true);
        String host = Initializer.getApiHost();
        int port = Initializer.getPort();
        try {
            socket = new Socket(host,port);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        if(!connectedFlag&&!socket.isClosed()){
                            listener.serverTimeOut();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            writer = new PrintWriter(socket.getOutputStream());
         /*
           待改为JSON
          */
         String userJson = JSON.toJSONString(new SignJson(userBean));
            writer.println(userJson);
//            writer.println(mPassword);
//            writer.println(mPassword);
          writer.flush();

            read = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
        } catch (IOException e) {
            closeSocket();
            listener.serverError();
            e.printStackTrace();
        }

        String message;
        try {
            while (threadContinue&&(message = read.readLine()) != null){
                publishProgress(message);
              //  Log.d("message",message);
            }
        } catch (IOException e) {
            Log.e("dsa","接收消息超时");
            BaseFragment.closeAsyncTask();
            Initializer.getApplicationContext()
                    .sendBroadcast(new Intent("DISCONNECTED_FROM_SERVER"));
          //  e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if(values[0].equals("ping")){
            ++count;
            count%=10;
            return;}else if(values[0].equals("Write_Success")){
           Initializer.getApplicationContext().sendBroadcast(new Intent("WRITE_SUCCESS"));
            return;
        }else if(values[0].equals("Preserve_Success")){
            Initializer.getApplicationContext().sendBroadcast(new Intent("PRESERVE_SUCCESS"));
            return;
        }
        Log.e("dsad",values[0]);
        DataConvertor.convert(values[0]);
        if(!connectedFlag){
           switch (DataConvertor.getStatus()){
               case "password_error":
                   listener.passwordError();   break;
               case "device_offline":
                   DialogHolder.stopLoading();
                   listener.deviceOffLine(); break;
               case "ok" :
                   AcountMagnager.setSignState(SignStatus.USER_SIGN);
                   connectedFlag = true;
                   WdPreference.setUsr(userBean.getUserName(),userBean.getPassWord());
                   WriterHolder.getInstance().setmWriter(writer);
                   createHoldStatusTask();
                   try {
                       socket.setSoTimeout(10000);
                   } catch (SocketException e) {
                       e.printStackTrace();
                       BaseFragment.closeAsyncTask();
                   }
                   DialogHolder.stopLoading();
                   listener.onSignSuccess();
                   Log.e("SignIinTest","登录成功");break;
           }
        }else {
            Log.e("receive:",values[0]);
            listener.receive();
        }
    }
    public void closeSocket(){
        DialogHolder.stopLoading();
        threadContinue = false;
        destroyHoldStatusTask();
        WriterHolder.getInstance().setmWriter(null);
       if(socket!=null){
           if(!socket.isClosed()){
               try {
                   socket.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
    }
    private void destroyHoldStatusTask(){
        if(timer!=null){
            timer.cancel();
        }
    }
    private void createHoldStatusTask(){

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                WriterHolder.getInstance().sendMsgToServer("ping");
            }
        }, 1000, 3000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(lastCount==count){
                    Log.e("dsa","超时未收到ping，关闭");
                    BaseFragment.closeAsyncTask();
                    Initializer.getApplicationContext()
                            .sendBroadcast(new Intent("DISCONNECTED_FROM_SERVER"));
                }else{lastCount=count;}
            }
        }, 3000, 7000);
    }
}
