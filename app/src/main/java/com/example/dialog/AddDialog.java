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
import com.example.terminal.R2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建自定义的dialog，主要学习其实现原理
 * Created by chengguo on 2016/3/22.
 */
public class AddDialog extends Dialog {


    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    RadioGroup radioGroup;
    TextInputEditText switchName;
    TextInputEditText switchOnSendText;
    TextInputEditText switchOffSendText;
    Button yes;
    Button no;

    private String checkedButtonName;
    private String mSwitchName;
    private String mSwitchOnSendText;
    private String mSwitchOffSendText;

    private Context mContext;


    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param onNoOnclickListener
     */
    public AddDialog setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {

        this.noOnclickListener = onNoOnclickListener;
        return this;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public AddDialog setYesOnclickListener( onYesOnclickListener onYesOnclickListener) {

        this.yesOnclickListener = onYesOnclickListener;
        return this;
    }

    public AddDialog(@NonNull Context context) {
        super(context, R.style.loading_dialog);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.add_dialog,null);
        Unbinder unbinder= ButterKnife.bind(this,view);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        radioGroup = (RadioGroup) findViewById(R.id.switch_type_radiogroup);
        switchOnSendText =( TextInputEditText) findViewById(R.id.switch_on_send_text);
        switchOffSendText =( TextInputEditText) findViewById(R.id.switch_off_send_text);
        switchName = ( TextInputEditText)findViewById(R.id.switch_name);
         yes = (Button)findViewById(R.id.yes);
         no = (Button)findViewById(R.id.no) ;
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

    public String getSwitchName() {
        mSwitchName = switchName.getText().toString();
        return mSwitchName;
    }

    public String getSwitchOnSendText() {
        mSwitchOnSendText = switchOnSendText.getText().toString();
        return mSwitchOnSendText;
    }
    public String getSwitchOffSendText() {
        mSwitchOffSendText = switchOffSendText.getText().toString();
        return mSwitchOffSendText;
    }

    public boolean checkFrom(){
       if(TextUtils.isEmpty(getSwitchName())){
           switchName.setError("不能为空");
           return false;
       }
        if(TextUtils.isEmpty(getSwitchOnSendText())){
            switchOnSendText.setError("不能为空");
            return false;
        }
        if(TextUtils.isEmpty(getSwitchOffSendText())){
            switchOffSendText.setError("不能为空");
            return false;
        }
        return true;
    }
}

