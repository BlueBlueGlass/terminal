package com.example.dialog;

/**
 * Created by Administrator on 2018/9/5.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.terminal.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建自定义的dialog
 */
public class AddButtonDialog extends Dialog {


    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    RadioGroup radioGroup;
    TextInputEditText buttonName;
    TextInputEditText buttonSendText;

    Button yes;
    Button no;

    private String checkedButtonName;
    private String mButtonName;
    private String mButtonSendText;

    private Context mContext;


    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param onNoOnclickListener
     */
    public AddButtonDialog setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {

        this.noOnclickListener = onNoOnclickListener;
        return this;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public AddButtonDialog setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {

        this.yesOnclickListener = onYesOnclickListener;
        return this;
    }

    public AddButtonDialog(@NonNull Context context) {
        super(context,R.style.loading_dialog);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.add_button_dialog,null);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_button_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        radioGroup =  findViewById(R.id.button_type_radiogroup);
        buttonSendText = findViewById(R.id.button_send_text);
        buttonName = findViewById(R.id.button_name);
         yes = findViewById(R.id.yes);
         no = findViewById(R.id.no) ;
        //初始化界面控件的事件
        initEvent();
        checkedButtonName = ((RadioButton)findViewById( radioGroup
                .getCheckedRadioButtonId())).getText().toString();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton choise = (RadioButton)findViewById( radioGroup.getCheckedRadioButtonId());
                checkedButtonName = choise.getText().toString();
            }
        });

    }
    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }

    public String getCheckedButtonName() {
        return checkedButtonName;
    }

    public String getButtonName() {
        mButtonName = buttonName.getText().toString();
        return mButtonName;
    }

    public String getButtonSendText() {
        mButtonSendText= buttonSendText.getText().toString();
        return mButtonSendText;
    }

    public boolean checkFrom(){
       if(TextUtils.isEmpty(getButtonName())){
           buttonName.setError("不能为空");
           return false;
       }
        if(TextUtils.isEmpty(getButtonSendText())){
            buttonSendText.setError("不能为空");
            return false;
        }
        return true;
    }
}

