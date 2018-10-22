package com.example.util.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.example.app.Configurator;
import com.example.app.Initializer;
import com.example.recyclerview.Door;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/10.
 */

public class BlueToothUtil {
    private static BluetoothSocket BLUETOOTH_SOCKET;

    private static Door blueDoor = new Door();
    private static boolean isContinue = true;
    private static Thread receiveThread;

    private static final class BlueToothHolder{
       private static final BluetoothAdapter MBLUETOOTHADAPTER = BluetoothAdapter.getDefaultAdapter();
    }

    public static BluetoothAdapter getInstance(){
        return BlueToothHolder.MBLUETOOTHADAPTER;
    }

    public static void  connectDevice(BluetoothDevice device,Handler handler){
        new ConnectThread(device,handler).start();
    }

    public static void startReceiveThread(final BluetoothSocket socket){
        BLUETOOTH_SOCKET = socket;


            receiveThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    final DataInputStream in;
                    try {
                        byte[] car = new byte[1024];
                       in = new DataInputStream(socket.getInputStream());
                        int length = 0;
                        Log.e("dsa","reader creader");

                        while (isContinue&&(length=in.read(car))!=-1){
                            Log.e("dsa","蓝牙消息");
                            String msg = new String(car,0,length);
                            SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
                            String currentTime  = df.format(new Date());
                            Log.e("das",msg);
                            blueDoor.setTime(currentTime);
                            blueDoor.setContent(msg);
                            Initializer.getApplicationContext().sendBroadcast(new Intent("DOOR_UPDATED"));
                        }
                        Log.e("dsa","while finish");
                    } catch (IOException e) {
                        isContinue = false;
                    }
                }
            });
        receiveThread.start();
    }

    public static void bluetoothDisconnected(){
        isContinue=false;
        BLUETOOTH_SOCKET = null;
    }

    public static boolean sendText(String content){
        BluetoothSocket socket = BlueToothUtil.getBluetoothSocket();
        if(socket==null||!socket.isConnected()){
            return false;
        }else{
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println(content);
                writer.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static BluetoothSocket getBluetoothSocket() {
        return BLUETOOTH_SOCKET;
    }

    public static Door getBlueDoor(){
        return blueDoor;
    }
    
}
