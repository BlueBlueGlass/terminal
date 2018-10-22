package com.example.fragment;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.ConfigType;
import com.example.app.Initializer;
import com.example.dialog.AddButtonDialog;
import com.example.dialog.AddDialog;
import com.example.dialog.BlueBoothDialog;
import com.example.dialog.DialogHolder;
import com.example.dialog.SampleDialog;
import com.example.dialog.SettingDialog;
import com.example.dialog.WarningDialog;
import com.example.service.NetBgService;
import com.example.fragment.sign.SignInDelegate;
import com.example.net.WriterHolder;
import com.example.notification.NotificationHolder;
import com.example.recyclerview.DoorBean;
import com.example.terminal.R;
import com.example.terminal.R2;
import com.example.util.jsonutil.DataConvertor;
import com.example.util.speech_voice.MyRecognizeListner;
import com.example.util.speech_voice.RecognizerUtil;
import com.example.util.SerializationUtil;
import com.example.util.speech_voice.SpeechUtil;
import com.example.util.speech_voice.TTSUtils;
import com.example.util.bluetooth.BlueToothUtil;
import com.example.util.bluetooth.ConnectedListener;
import com.example.util.detail.ButtonBean;
import com.example.util.detail.SwitchBean;
import com.example.util.detail.SwitchCreator;
import com.example.util.sign.AcountMagnager;
import com.example.util.timer.CompareTimeUtil;
import com.trycatch.mysnackbar.TSnackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/9/4.
 */

public class IndexFragment extends BaseFragment {

    private List<BaseFragment> fragmentList;
    private MyFragmentAdapter adapter;
    private String mDeviceName;
    private IndexRecevier indexRecevier;
    @BindView(R2.id.view_pager)
    ViewPager viewPager;
    @BindView(R2.id.container_bottom_bar)
    LinearLayoutCompat container;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;

    @Override
    public Object setLayout() {
        return R.layout.fragment_index;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Bundle bundle= getArguments();
        IntentFilter intentFilter = new IntentFilter();
       switch (  AcountMagnager.getSignStatus()){
           case BLUE_TOOTH_SIGN:
               Toast.makeText(getActivity(),"蓝牙模式登录",Toast.LENGTH_SHORT).show();
               mDeviceName= bundle != null ? bundle.getString(ConfigType.DEVICE_NAME.name()) : null;
               intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
               intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
               intentFilter.addAction("DOOR_UPDATED");
               indexRecevier = new IndexRecevier();
               Initializer.getApplicationContext().registerReceiver(indexRecevier,intentFilter);
               break;
           case USER_SIGN:
               Toast.makeText(getActivity(),"网络用户登录",Toast.LENGTH_SHORT).show();
               intentFilter.addAction("DEVICE_DISCONNECTED");
                 intentFilter.addAction("DISCONNECTED_FROM_SERVER");
                 intentFilter.addAction("WRITE_SUCCESS");
                 intentFilter.addAction("INFO_UPDATED");
               intentFilter.addAction("DOOR_UPDATED");
               intentFilter.addAction("PRESERVE_SUCCESS");
               intentFilter.addAction("KNEW");
               intentFilter.addAction("DOOR_INIT");
               intentFilter.addAction("CALL_POLICE");
               indexRecevier = new IndexRecevier();
               getActivity().registerReceiver(indexRecevier,intentFilter);
               Log.e("dsa","设置DEVICE_DISCONNECTED成功");
               break;
           case DEVELOPER_SIGN:
               Toast.makeText(getActivity(),"开发者模式登录",Toast.LENGTH_SHORT).show();
               break;
       }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationHolder.cancelNotif(2);
    }

    @Nullable
    public Toolbar initToolbar() {
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) getActivity();;
        mAppCompatActivity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                SettingDialog setting = new SettingDialog(getContext(),getFragmentManager());
                setting.show();
                break;
            case R.id.reset:
                final SampleDialog prompt_reset = new SampleDialog(getContext(),R.style.loading_dialog);
                 prompt_reset.setTitle("重置按钮")
                       .setMessage("此操作将重置按钮页面，并断开连接")
                       .setYesOnclickListener("确定", new SampleDialog.onYesOnclickListener() {
                           @Override
                           public void onYesOnclick() {
                           SerializationUtil.clearParentButton();
                               prompt_reset.cancel();
                               BaseFragment.closeAsyncTask();
                               getActivity().stopService(new Intent(getContext(), NetBgService.class));
                               startWithPop(new SignInDelegate());
                           }
                       })
                       .setNoOnclickListener("取消", new SampleDialog.onNoOnclickListener() {
                           @Override
                           public void onNoClick() {
                            prompt_reset.cancel();
                           }
                       }).show();
                break;
            case R.id.exit_login:
                final SampleDialog prompt_exit = new SampleDialog(getContext(),R.style.loading_dialog);
                prompt_exit.setTitle("退出登录")
                        .setMessage("此操作将断开连接，确定退出？")
                        .setYesOnclickListener("确定退出", new SampleDialog.onYesOnclickListener() {
                            @Override
                            public void onYesOnclick() {
                                prompt_exit.cancel();
                                BaseFragment.closeAsyncTask();
                                getActivity().stopService(new Intent(getContext(), NetBgService.class));
                                startWithPop(new SignInDelegate());
                            }
                        })
                        .setNoOnclickListener("取消", new SampleDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                prompt_exit.cancel();
                            }
                        }).show();
                break;
            case  R.id.add_switch:
            final AddDialog addDialog = new AddDialog(getContext());
              addDialog.setNoOnclickListener(new AddDialog.onNoOnclickListener() {
                  @Override
                  public void onNoClick() {
                      addDialog.dismiss();
                  }
              }).setYesOnclickListener(new AddDialog.onYesOnclickListener() {
                  @SuppressLint("NewApi")
                  @Override
                  public void onYesClick() {
                       final String switch_type;
                      if(addDialog.checkFrom()){
                          LinearLayout switchLayoutContainer=  fragmentList.get(0)
                                  .getView().findViewById(R.id.switch_layout_container);
                          SwitchBean item = new SwitchBean(addDialog.getCheckedButtonName()
                                  ,addDialog.getSwitchName(),addDialog.getSwitchOnSendText()
                                  ,addDialog.getSwitchOffSendText());
                          SwitchCreator.showSwitch(item,getContext(),switchLayoutContainer,viewPager);
                          addDialog.dismiss();
                      }
                  }
              }).show();
                break;
            case R.id.add_button:
                final AddButtonDialog addButtonDialog = new AddButtonDialog(getContext());
                   addButtonDialog.setNoOnclickListener(new AddButtonDialog.onNoOnclickListener() {
                       @Override
                       public void onNoClick() {
                           addButtonDialog.cancel();
                       }
                   }).setYesOnclickListener(new AddButtonDialog.onYesOnclickListener() {
                       @Override
                       public void onYesClick() {
                           if(addButtonDialog.checkFrom()){
                               LinearLayout switchLayoutContainer=  fragmentList.get(0)
                                       .getView().findViewById(R.id.switch_layout_container);
                               ButtonBean item = new ButtonBean(addButtonDialog.getCheckedButtonName()
                                       ,addButtonDialog.getButtonName(),addButtonDialog.getButtonSendText());
                               SwitchCreator.showSwitch(item,getContext(),switchLayoutContainer,viewPager);
                               addButtonDialog.dismiss();
                           }
                       }
                   }).show();
                break;
        }
        return true;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
       initToolbar();
        toolbar.inflateMenu(R.menu.toolbar);
        if(TextUtils.isEmpty(mDeviceName)){
            toolbar.setTitle("南通");
        }else{
            toolbar.setTitle("南通   "+mDeviceName);
        }
          fragmentList = new ArrayList<>();
          fragmentList.add(new IndexDetail());
          fragmentList.add(new IndexDoor());
          adapter = new MyFragmentAdapter(getChildFragmentManager(),fragmentList);
          viewPager.setAdapter(adapter);
         resetColor(0);
        initOnclickListner();
          viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
              @Override
              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

              }
              @Override
              public void onPageSelected(int position) {
                   resetColor(position);
              }
              @Override
              public void onPageScrollStateChanged(int state) {

              }
          });
    }
    private void resetColor(int position){
        int mPosition = position==1?2:0;

      if(mPosition==0) {
          LinearLayoutCompat item = (LinearLayoutCompat) container.getChildAt(mPosition);
          ImageView item_img = (ImageView) item.getChildAt(0);
          TextView  item_text = (TextView) item.getChildAt(1) ;
          item_img.setImageResource(R.mipmap.house_selected);
          item_text.setTextColor(Color.parseColor("#1296db"));

          LinearLayoutCompat item2 = (LinearLayoutCompat) container.getChildAt(mPosition+2);
          ImageView item_img2 = (ImageView) item2.getChildAt(0);
          item_img2.setImageResource(R.mipmap.door_unselect);
          TextView  item_text2 = (TextView) item2.getChildAt(1) ;
          item_text2.setTextColor(Color.parseColor("#8a8a8a"));
      }else{
          LinearLayoutCompat item = (LinearLayoutCompat) container.getChildAt(mPosition-2);
          ImageView item_img = (ImageView) item.getChildAt(0);
          item_img.setImageResource(R.mipmap.house_unselect);
          TextView  item_text = (TextView) item.getChildAt(1) ;
          item_text.setTextColor(Color.parseColor("#8a8a8a"));

          LinearLayoutCompat item2 = (LinearLayoutCompat) container.getChildAt(mPosition);
          ImageView item_img2 = (ImageView) item2.getChildAt(0);
          item_img2.setImageResource(R.mipmap.door_selected);
          TextView  item_text2 = (TextView) item2.getChildAt(1) ;
          item_text2.setTextColor(Color.parseColor("#1296db"));
      }

    }

    private void initOnclickListner(){
        TTSUtils.getInstance().speak(null);
        LinearLayoutCompat item = (LinearLayoutCompat) container.getChildAt(0);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0,true);
            }
        });
        LinearLayoutCompat item3 = (LinearLayoutCompat) container.getChildAt(2);
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1,true);
            }
        });

        ImageView item2 = (ImageView) container.getChildAt(1);
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecognizerUtil util= new RecognizerUtil();
      util.initSpeech(new MyRecognizeListner(getContext()) {
          @Override
          public void updateUI(String str) {
              SpeechUtil.dealWithVoice(str);
          }
      });
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(indexRecevier!=null) {
            getActivity().unregisterReceiver(indexRecevier);
        }
    }
    class IndexRecevier extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            IndexDoor indexDoor = (IndexDoor)fragmentList.get(1);
            switch (intent.getAction()) {
                case BluetoothDevice.ACTION_ACL_CONNECTED:
                   // BlueToothUtil.setIsConnected(true);
                    Log.e("TAG","蓝牙串口连接成功");break;
                case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                    BlueToothUtil.bluetoothDisconnected();
                    toolbar.setTitle("南通   蓝牙连接丢失");
                    TSnackbar snackBar = TSnackbar.make(toolbar.getRootView(), "蓝牙串口已断开连接"
                            , TSnackbar.LENGTH_INDEFINITE, TSnackbar.APPEAR_FROM_TOP_TO_DOWN);
                    snackBar.setAction("重连", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new BlueBoothDialog(getContext(), getActivity(), new ConnectedListener() {
                                @Override
                                public void onSeccess(String deviceName) {
                                    toolbar.setTitle("南通   "+deviceName);
                                    Toast.makeText(getActivity(),"重新连接成功",Toast.LENGTH_LONG).show();
                                }
                            }).show();
                        }
                    }).show();
                    Log.e("TAG","蓝牙串口连接丢失");break;
                case  "DEVICE_DISCONNECTED":

                    DialogHolder.showDialogForWarning(getContext(), "硬件已断线", new WarningDialog.onYesOnclickListener() {
                        @Override
                        public void onYesOnclick() {
                            Log.e("dsa","硬件设备断开");
                            SwitchCreator.clearContainList();
                            DialogHolder.stopWarner();
                            startWithPop(new SignInDelegate());
                            BaseFragment.closeAsyncTask();
                        }
                    },"退出");
                    break;
                case "DISCONNECTED_FROM_SERVER":
                    Log.e("dsa","与服务器断开");
                    DialogHolder.showDialogForWarning(getContext(), "与服务器断开", new WarningDialog.onYesOnclickListener() {
                        @Override
                        public void onYesOnclick() {
                            BaseFragment.closeAsyncTask();
                            DialogHolder.stopWarner();
                            SwitchCreator.clearContainList();
                            startWithPop(new SignInDelegate());
                        }
                    },"退出");
                    break;
                case "WRITE_SUCCESS":
                    if(WriterHolder.CHANGELISTENER!=null){
                        WriterHolder.CHANGELISTENER.change();}
                    Toast.makeText(getContext(),"发送网络请求成功",Toast.LENGTH_SHORT).show();break;
                case "PRESERVE_SUCCESS":
                    DataConvertor.transferData(WriterHolder.TEMPINFO.TEMPUSERINFO);
                    if(WriterHolder.TEMPINFO.isVoice){
                        if(WriterHolder.TEMPINFO.TEMPUSERINFO.isMonitor()){
                            TTSUtils.getInstance().speak("全天监控开启成功");
                        }else{
                            TTSUtils.getInstance().speak("全天监控关闭成功");
                        }
                    }else{
                        Toast.makeText(getContext(),"保存成功",Toast.LENGTH_SHORT).show();
                    }
                   break;
                case "INFO_UPDATED":
                  TextView  gasValue = fragmentList.get(0).getView().findViewById(R.id.gas_detail);
                   TextView humidityValue = fragmentList.get(0).getView().findViewById(R.id.humidity_detail);
                   TextView tempValue = fragmentList.get(0).getView().findViewById(R.id.temp_detail);
                   TextView brightnessValue = fragmentList.get(0).getView().findViewById(R.id.brightness_detail);
                   if(Integer.valueOf(DataConvertor.getInfo().getGas())>50){
                       gasValue.setText("危险");
                       NotificationHolder.showDanger(getContext(),"警告，检测到有毒气体！");
                   }else {
                       gasValue.setText("安全");}

                    humidityValue.setText(DataConvertor.getInfo().getHumidity()+"%");
                    tempValue.setText(DataConvertor.getInfo().getTemperature()+"℃");
                    int bn = Integer.valueOf(DataConvertor.getInfo().getBrightness());
                    if(bn>=60){
                        brightnessValue.setText("明亮");
                    }else if(bn<70&&bn>20){
                        brightnessValue.setText("暗淡");
                    }else if(bn<=20){
                        brightnessValue.setText("黑暗");
                    }break;
                case "DOOR_UPDATED" :
                    Log.e("dsa","DOOR_UPDATED");
                    Collections.reverse(indexDoor.doorList);
                     if(AcountMagnager.isSignInUsr()){
                    indexDoor.doorList.add(new DoorBean(DataConvertor.getDoorBean().getDoorAction()
                            ,DataConvertor.getDoorBean().getActionTime()));
                     }else if(AcountMagnager.isSignInBlueTooth()){
                         indexDoor.doorList.add(new DoorBean(BlueToothUtil.getBlueDoor().getContent()
                                 ,BlueToothUtil.getBlueDoor().getTime()));
                     }

                    Collections.reverse(indexDoor.doorList);
                    int sise = indexDoor.doorList.size();
                    if(sise>13){
                        indexDoor.doorList.remove(sise-1);}
                    indexDoor.adapter.notifyDataSetChanged();
                        if(CompareTimeUtil.belongCalendar(DataConvertor.getDoorBean().getActionTime()
                                ,DataConvertor.getUserBean().getStartTime()
                                ,DataConvertor.getUserBean().getEndTime())||DataConvertor.getUserBean().isMonitor()){
                            NotificationHolder.showDanger(getContext(),DataConvertor.getDoorBean().getDoorAction());
                        }else {
                            NotificationHolder.showSafe(getContext());
                        }

                    break;
                case "DOOR_INIT":
                    IndexDoor.doorList.clear();
                    IndexDoor.doorList.addAll(DataConvertor.getDoorList());
                    indexDoor.adapter.notifyDataSetChanged();
                    break;
                case "KNEW":
                    Toast.makeText(getContext(),"知道了",Toast.LENGTH_SHORT).show();
                   NotificationHolder.cancelNotif(2);
                    break;
                case "CALL_POLICE":
                    NotificationHolder.cancelNotif(2);
                    if(PhoneNumberUtils.isGlobalPhoneNumber(DataConvertor.getUserBean().getEmergencyContact())){
                        Intent msg = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+DataConvertor.getUserBean().getEmergencyContact()));
                        msg.putExtra("sms_body",DataConvertor.getUserBean().getContent());
                        startActivity(msg);
                    }
                    Toast.makeText(getContext(),"已打开短信页面",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
