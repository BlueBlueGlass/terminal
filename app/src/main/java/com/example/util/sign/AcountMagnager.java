package com.example.util.sign;

import com.example.net.UserBean;
import com.example.util.WdPreference;

/**
 * Created by Administrator on 2018/9/2.
 */

public class AcountMagnager {
    private static  SignStatus mStatus  = SignStatus.DEVELOPER_SIGN;
    public static void setSignState(SignStatus status){
        mStatus = status;
    }
    public static boolean isSignInUsr(){
        return mStatus==SignStatus.USER_SIGN;
    }

    public static boolean isSignInBlueTooth(){
        return mStatus==SignStatus.BLUE_TOOTH_SIGN;
    }

    public static boolean isSignInDeveloper(){
        return mStatus==SignStatus.DEVELOPER_SIGN;
    }
    public static SignStatus getSignStatus(){
        return mStatus;
    }
    public static void checkAccount(UserChecker checker){
        if(isSignInUsr()){
            checker.onSign();
        }else {
            checker.onNotSign();
        }
    }
}