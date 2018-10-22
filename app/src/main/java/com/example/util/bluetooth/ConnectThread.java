package com.example.util.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2018/8/11.
 */

public  class ConnectThread extends Thread {
    private  BluetoothSocket mmSocket;
   private  BluetoothDevice mmDevice;
    private Handler handler ;
  //  private final UUID uuid = UUID.fromString("00001105-0000-1000-8000-00805f9B34FB");
    private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public ConnectThread(BluetoothDevice device,Handler handler) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        this.handler = handler;
        mmDevice = device;
        // Get a BluetoothSocket to connect with the given BluetoothDevice
    }
  @Override
    public void run() {
        // Cancel discovery because it will slow down the connection
        BlueToothUtil.getInstance().cancelDiscovery();
      try {
          // MY_UUID is the app's UUID string, also used by the server code

          mmSocket  = mmDevice.createInsecureRfcommSocketToServiceRecord(uuid);
//          tmp =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);

      } catch (Exception e) {
          e.printStackTrace();
      }
                try {
                    // Connect the device through the socket. This will block
                    // until it succeeds or throws an exception
                    mmSocket.connect();

                } catch (IOException connectException) {
                    // Unable to connect; close the socket and get out
                    connectException.printStackTrace();
                    Message message2 = new Message();
                    message2.what=2;//第一次连接失败
                    handler.sendMessage(message2);
                    try {
                        mmSocket =(BluetoothSocket) mmDevice.getClass()
                                .getMethod("createRfcommSocket", new Class[] {int.class}).invoke(mmDevice,1);
                        mmSocket.connect();
                        // mmSocket.close();

                    } catch (Exception Exception) {
                        Message message3 = new Message();
                        message3.what=3;//第二次连接失败
                        handler.sendMessage(message3);
                        Exception.printStackTrace();
                    }
                    return;
                }

                // Do work to manage the connection (in a separate thread)
                Message message1 = new Message();
                message1.what=1;
                message1.obj = mmSocket;
                handler.sendMessage(message1);




    }

    /**
     * Will cancel an in-progress connection, and close the socket
     */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}
