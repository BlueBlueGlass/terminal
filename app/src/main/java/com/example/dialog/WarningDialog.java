package com.example.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.terminal.R;
/**
 * Created by Administrator on 2018/8/29.
 */
public class WarningDialog extends Dialog {
    private Button yes;//确定按钮
    private TextView titleTV;//消息标题文本
    private String titleStr;//从外界设置的title文本
    //确定文本
    private String yesStr;
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private  int layoutId = -1;
    public WarningDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    public WarningDialog(@NonNull Context context, @StyleRes int themeResId, int layout) {
        super(context, themeResId);
        this.layoutId = layout;
    }
    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param yesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener yesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = yesOnclickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(layoutId==-1) {
         setContentView(R.layout.warning_dialog);
        }else{
            setContentView(layoutId);
        }
        //空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();

        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = findViewById(R.id.yes);
        titleTV = (TextView) findViewById(R.id.title);
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title
        if (titleStr != null) {
            titleTV.setText(titleStr);
        }
        //如果设置按钮文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
    }

    /**
     * 初始化界面的确定和取消监听
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesOnclick();
                }
            }
        });
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }

    public interface onYesOnclickListener {
        public void onYesOnclick();
    }
}
