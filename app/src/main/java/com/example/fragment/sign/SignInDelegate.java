package com.example.fragment.sign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;

import com.example.app.ConfigType;
import com.example.dialog.BlueBoothDialog;
import com.example.fragment.BaseFragment;
import com.example.fragment.IndexFragment;

import com.example.service.NetBgService;
import com.example.terminal.R;
import com.example.terminal.R2;
import com.example.util.bluetooth.ConnectedListener;
import com.example.util.sign.AcountMagnager;
import com.example.util.sign.SignStatus;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/7.
 */

public class SignInDelegate extends BaseFragment {

    public static SignInDelegate THIS;

    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword ;
    @BindView(R2.id.edit_sign_in_user)
    TextInputEditText mUser ;
    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn(){
        if(checkForm()){
            Intent serviceIntent = new Intent(getContext(),NetBgService.class);
            serviceIntent.putExtra("userName",mUser.getText().toString());
            serviceIntent.putExtra("passWord",mPassword.getText().toString());
            getActivity().startService(serviceIntent);
        }
    }
    @OnClick(R2.id.bluebooth_sign_in)
    void onClickBlueBooth(){
        new BlueBoothDialog(getContext(), getActivity(), new ConnectedListener() {
            @Override
            public void onSeccess(String deviceName) {
                BaseFragment fragment = new IndexFragment();
                Bundle bundle = new Bundle();
                bundle.putString(ConfigType.DEVICE_NAME.name(),deviceName);
                fragment.setArguments(bundle);
                startWithPop(fragment);
            }
        }).show();
    }
    @OnClick(R2.id.test_sign_in)
    void onClickTest(){
      AcountMagnager.setSignState(SignStatus.DEVELOPER_SIGN);
       startWithPop(new IndexFragment());

    }

     @OnClick(R2.id.link_sign_up)
     void onClickLinkSignUp(){
         startWithPop(new SignUpDelegate());
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
        final String user = mUser.getText().toString();
        final String password = mPassword.getText().toString();
        boolean isPass = true ;
        if(user.isEmpty()){
            mUser.setError("请输入姓名");
            isPass = false;
        }else{
            mUser.setError(null);
        }

        if(password.isEmpty()){
            mPassword.setError("请填写密码");
            isPass = false;
        }else{
            mPassword.setError(null);
        }
        return isPass;
    }
    @Override
    public Object setLayout() {
        return R.layout.fragment_signin;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
       THIS = this;
    }
}
