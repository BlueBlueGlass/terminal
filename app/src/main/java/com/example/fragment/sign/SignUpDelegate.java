package com.example.fragment.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.example.fragment.BaseFragment;
import com.example.terminal.R;
import com.example.terminal.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/7.
 */

public class SignUpDelegate extends BaseFragment{

    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone= null;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.edit_sign_up_password2)
    TextInputEditText mPassword2= null;
    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp(){
        if(checkForm()){

        }

    }
    @OnClick(R2.id.link_sign_in)
    void linkSignIn(){
    startWithPop(new SignInDelegate());
    }
 private SignListener listener = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof SignListener){
             listener = (SignListener) activity;
        }
    }

    private boolean checkForm(){
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String password2 = mPassword2.getText().toString();
        boolean isPass = true ;
        if(name.isEmpty()){
            mName.setError("请输入姓名");
            isPass = false;
        }else{
            mName.setError(null);
        }
        if(email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("邮箱错误");
            isPass = false;
        }else{
            mEmail.setError(null);
        }
        if(phone.isEmpty()||phone.length()!=11){
            mPhone.setError("手机号码错误");
            isPass = false;
        }else{
            mPhone.setError(null);
        }
        if(password.isEmpty()||password.length()<6){
            mPassword.setError("密码至少大于6位");
            isPass = false;
        }else{
            mPassword.setError(null);
        }
        if(!password.equals(password2)){
            mPassword2.setError("密码不一致");
            isPass = false;
        }else{
            mPassword2.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_signup;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
