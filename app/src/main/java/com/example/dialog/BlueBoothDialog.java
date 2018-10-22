package com.example.dialog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app.ConfigType;
import com.example.app.Configurator;
import com.example.app.Initializer;
import com.example.terminal.MainActivity;
import com.example.terminal.R;
import com.example.util.bluetooth.BlueToothUtil;
import com.example.util.bluetooth.ConnectedListener;
import com.example.util.sign.AcountMagnager;
import com.example.util.sign.SignStatus;
import com.example.util.timer.DelayListener;
import com.example.util.timer.DelayUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/9/7.
 */

public class BlueBoothDialog extends Dialog {

private AppCompatButton cancel ;
private ListView deviceList;
private SwipeRefreshLayout refresh;
private ArrayAdapter<String> adapter;
private ArrayList<String> list = new ArrayList<>();
    private BluetoothSocket socket;
private LinkedHashSet<BluetoothDevice> linkedHashSetDevices = new LinkedHashSet<>();
private Context mContext;
private Activity mActivity;
    private MyBroadcastReceiver mREceiver;

    private BluetoothDevice clickedDevice;
    private ConnectedListener mListener;

    public BlueBoothDialog(@NonNull Context context, Activity activity, ConnectedListener listener) {
        super(context, R.style.loading_dialog);
        mContext = context;
        mActivity = activity;
        mListener = listener;
        setCancelable(false);
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:socket = (BluetoothSocket) msg.obj;
                    if(socket!=null){
                        cancel.callOnClick();
                        BlueToothUtil.startReceiveThread(socket);
                        AcountMagnager.setSignState(SignStatus.BLUE_TOOTH_SIGN);
                        mListener.onSeccess(clickedDevice.getName());
                    }break;
                case 2:       Toast.makeText(getContext(),"连接蓝牙失败",Toast.LENGTH_SHORT).show();
                    break;
                case 3:       Toast.makeText(getContext(),"再次连接失败",Toast.LENGTH_SHORT).show();
                    break;
                default: break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.bluebooth_dialog);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        mREceiver = new MyBroadcastReceiver();
        mActivity.registerReceiver(mREceiver, intentFilter);
        Log.e("dsa","注册蓝牙成功");
        initWidget();
        initListView();
        if (!BlueToothUtil.getInstance().isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            ((Activity)mContext).startActivityForResult(enableBtIntent, 2);
        }else{
            // showDeviceList();
           // Boolean flag =  BlueToothUtil.getInstance().startDiscovery();
         refresh.setRefreshing(true);
         searchDevice();
            DelayUtil.delay(2000,mActivity, new DelayListener() {
                @Override
                public void onDelayFinish() {
                    refresh.setRefreshing(false);
                }
            });
        }
    }


    private void initWidget(){
        cancel = findViewById(R.id.cancel_search);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.unregisterReceiver(mREceiver);
            Log.e("as","取消广播成功！！！！！！1");
              cancel();
            }
        });
        deviceList =  findViewById(R.id.device_list);
        refresh =  findViewById(R.id.swipe_refesh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchDevice();
                DelayUtil.delay(2000, mActivity,new DelayListener() {
                    @Override
                    public void onDelayFinish() {
                      refresh.setRefreshing(false);
                    }
                });
            }
        });
    }
    private void initListView(){
        adapter = new ArrayAdapter<String>
                (getContext(),R.layout.listview_text_layout,list);
        deviceList.setAdapter(adapter);
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String listContext = list.get(position);
                String deviceName = listContext.substring(0,listContext.indexOf("\n"));
                BluetoothDevice gotDevice =getDeviceFromSet(deviceName);
                if(gotDevice!=null){
                    BlueToothUtil.connectDevice(gotDevice,handler);
                    clickedDevice = gotDevice;
                }else{
                    Toast.makeText(getContext(),"无法连接，请确认连接设备是否打开",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private BluetoothDevice getDeviceFromSet(String deviceName){
        Iterator<BluetoothDevice> iterator = linkedHashSetDevices.iterator();
        while(iterator.hasNext()){
            BluetoothDevice tmpDevice = iterator.next();
            String tmpName = tmpDevice.getName();
            if(tmpName!=null){
                if(tmpName.equals(deviceName)){
                    return tmpDevice;
                }
            }else {
                //...............
            }
        }
        return null;
    }

    private void searchDevice(){
        list.clear();
        linkedHashSetDevices.clear();
        boolean flag =  BlueToothUtil.getInstance().startDiscovery();
        if(flag) {
            Toast.makeText(mContext, "刷新蓝牙", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext, "正在刷新，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }


    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //test
            //Toast.makeText(context, "收到个广播", Toast.LENGTH_SHORT).show();
            // Toast.makeText(MainActivity.this, "接收到一个广播", Toast.LENGTH_LONG).show();
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //~~~~~~~~~~~~~~~~~~~~
                // BlueToothUtil.connectDevice(device,handler,MainActivity.this);
                //           UUID uuid =  intent.getParcelableExtra(BluetoothDevice.EXTRA_UUID);

                linkedHashSetDevices.add(device);
                list.add(device.getName() + "\n" + device.getAddress());
                adapter.notifyDataSetChanged();
            }
        }
    }
}
