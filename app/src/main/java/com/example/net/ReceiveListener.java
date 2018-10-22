package com.example.net;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.dialog.DialogHolder;
import com.example.dialog.WarningDialog;
import com.example.fragment.BaseFragment;
import com.example.fragment.sign.SignInDelegate;
import com.example.util.jsonutil.DataConvertor;

/**
 * Created by Administrator on 2018/9/2.
 */

public abstract class ReceiveListener {
    private final Context signContext;
    private Activity mActivity ;
    public ReceiveListener(Context context) {
        signContext = context;
        mActivity =  (Activity)context;
    }
     void receive(){
        if(DataConvertor.getStatus().equals("device_offline")){
            deviceSuddenlyOffLine();
        }else if(DataConvertor.getInfo()!=null&&DataConvertor.getDoorBean()==null){
            showInfo();
        }else{
             showDoor();
        }
     }
    public abstract void deviceSuddenlyOffLine();
     public abstract void showInfo();
    public abstract void onSignSuccess();
    public abstract void showDoor();

     void deviceOffLine() {
         updateUI("硬件设备不在线");
        BaseFragment.closeAsyncTask();
    }

     void serverTimeOut() {
       BaseFragment.closeAsyncTask();
         updateUI("服务器超时");
    }

     void passwordError() {
        BaseFragment.closeAsyncTask();
         updateUI("密码错误");
    }
   void serverError() {
        BaseFragment.closeAsyncTask();
     updateUI("服务器错误");
    }

    private void updateUI(final String title){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogHolder.showDialogForWarning(signContext,title);
            }
        });
    }
    private void updateUI(final String title
            , final WarningDialog.onYesOnclickListener yesListener, final String yes){
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogHolder.showDialogForWarning(signContext,title,yesListener,yes);
            }
        });
    }
//    public abstract void serverError();
}
