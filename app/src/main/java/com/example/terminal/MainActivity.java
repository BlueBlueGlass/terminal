package com.example.terminal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.dialog.SampleDialog;
import com.example.fragment.BaseFragment;

import com.example.service.NetBgService;
import com.example.fragment.sign.SignInDelegate;
import com.example.util.bluetooth.BlueToothUtil;


import butterknife.ButterKnife;

public class MainActivity extends ProxyActivity {
    private OnePixelReceiver mOnepxReceiver;
    private long firstTime=0;
    SignInDelegate root = new SignInDelegate();
    @Override
    public BaseFragment getRootDelegate() {
        return root;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT>=23){
            @SuppressLint("InlinedApi") String[] permissionList ={
                    Manifest.permission.RECORD_AUDIO
                    ,Manifest.permission.READ_EXTERNAL_STORAGE
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                     ,Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this,permissionList,123);
        }

        mOnepxReceiver = new OnePixelReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        registerReceiver(mOnepxReceiver, intentFilter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2){
            if(resultCode==RESULT_OK){
                Boolean flag =  BlueToothUtil.getInstance().startDiscovery();
                Toast.makeText(MainActivity.this, "开启扫描"+ String.valueOf(flag),Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 123) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                 final SampleDialog dialog = new SampleDialog(MainActivity.this,R.style.loading_dialog);
                 dialog.setTitle("授权失败")
                         .setMessage("由于您拒绝授权，部分功能将无法使用，可以在系统设置中重新开启权限")
                         .setYesOnclickListener("确定", new SampleDialog.onYesOnclickListener() {
                             @Override
                             public void onYesOnclick() {
                                 dialog.cancel();
                             }
                         })
                         .setNoOnclickListener("取消", new SampleDialog.onNoOnclickListener() {
                             @Override
                             public void onNoClick() {
                                 dialog.cancel();
                             }
                         }).show();
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, NetBgService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Activiry_status","onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Activiry_status","onpause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Activiry_status","onresume");
    }

}
