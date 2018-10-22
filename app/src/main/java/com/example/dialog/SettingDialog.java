package com.example.dialog;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.gson.communication_json.UserInfoJson;
import com.example.net.TempInfo;
import com.example.net.UserBean;
import com.example.net.WriterHolder;
import com.example.terminal.R;
import com.example.util.jsonutil.DataConvertor;
import com.example.util.timer.CompareTimeUtil;


/**
 * Created by Administrator on 2018/9/25.
 */

public class SettingDialog extends Dialog {

   private TextView startTime;
    private TextView endTime;
    private AppCompatButton startButton;
    private  AppCompatButton endButton;
    private SwitchCompat monitorMode;
    private TextInputEditText emergencyContact;
    private TextInputEditText content;
    private Button yes;
    private Button no;
    private Context mContext;
    private android.support.v4.app.FragmentManager fManager;


    public SettingDialog(@NonNull Context context,android.support.v4.app.FragmentManager fManager) {
        super(context, R.style.loading_dialog);
        mContext = context;
        this.fManager = fManager;
    }
    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化控件
         initWidget();
        //初始化界面控件的事件
        initEvent();
        loadData();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initWidget(){
        startTime = findViewById(R.id.start_time);
         endTime = findViewById(R.id.end_time);
         startButton = findViewById(R.id.startButton);
         endButton = findViewById(R.id.end_button);
         monitorMode = findViewById(R.id.monitor_switch);
        emergencyContact =findViewById(R.id.emergency_contacts);
        content =findViewById(R.id.content);

        yes = findViewById(R.id.preserve);
        no = findViewById(R.id.cancel) ;
    }

    private SettingDialog loadData(){
        startTime.setText(DataConvertor.getUserBean().getStartTime());
        endTime.setText(DataConvertor.getUserBean().getEndTime());
       monitorMode.setChecked(DataConvertor.getUserBean().isMonitor());
        emergencyContact.setText(DataConvertor.getUserBean().getEmergencyContact());
        content.setText(DataConvertor.getUserBean().getContent());
        return this;
    }

    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFrom()){
                    final UserBean tempUserInfo = new UserBean();
                    tempUserInfo.setStartTime(startTime.getText().toString());
                    tempUserInfo.setEndTime(endTime.getText().toString());
                    tempUserInfo.setMonitor(monitorMode.isChecked());
                    tempUserInfo.setEmergencyContact(emergencyContact.getText().toString());
                    tempUserInfo.setContent(content.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("das","保存内容："+JSON.toJSONString(
                                    new UserInfoJson(tempUserInfo)));
                            WriterHolder.getInstance().sendMsgToServer(JSON.toJSONString(
                                    new UserInfoJson(tempUserInfo)),new TempInfo(false,tempUserInfo));
                        }
                    }).start();
                }
                dismiss();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompareTimeUtil.showTimeSelect(getContext(), fManager, new CompareTimeUtil.OnTimeSelectListenner() {
                    @Override
                    public void onTimeSelected(String time) {
                        startTime.setText(time);
                    }
                });
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompareTimeUtil.showTimeSelect(getContext(), fManager, new CompareTimeUtil.OnTimeSelectListenner() {
                    @Override
                    public void onTimeSelected(String time) {
                        endTime.setText(time);
                    }
                });
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dismiss();
            }
        });
    }

    public boolean checkFrom(){
        if(TextUtils.isEmpty(emergencyContact.getText().toString())){
            emergencyContact.setError("不能为空");
            return false;
        }
        if(TextUtils.isEmpty(content.getText().toString())){
            content.setError("不能为空");
            return false;
        }
        return true;
    }
}