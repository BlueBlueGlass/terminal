package com.example.util.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.Configurator;
import com.example.app.Initializer;
import com.example.net.UserBean;
import com.example.net.WriterHolder;
import com.example.terminal.MyLinearLayout;
import com.example.terminal.R;
import com.example.util.SerializationUtil;
import com.example.util.bluetooth.BlueToothUtil;
import com.example.util.sign.AcountMagnager;
import com.trycatch.mysnackbar.TSnackbar;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/9/6.
 */

public class SwitchCreator {
    private static ArrayList<LinearLayout> CONTAINERLIST = new ArrayList<>();
    public static void clearContainList(){
        CONTAINERLIST.clear();
    }
    @SuppressLint("NewApi")
    public static void showSwitch(final ParentButton item, final Context context, final LinearLayout layoutcontainer){
        Log.e("fd","CONTAINERLIST sise:  "+CONTAINERLIST.size());
        final LinearLayout switchContainerLayout = getChildLineraLayout(context,layoutcontainer);

        final String type ;
        String   switchName ;
        final String switchOnSendText ;
        final String switchOffSendText;
        final String buttonSendText;

       final LinearLayout linearLayout;

        if(item instanceof SwitchBean){
           SwitchBean switchBean = (SwitchBean)item;
           type = switchBean.getCheckedButtonName();
          switchName = switchBean.getSwitchName();
          switchOnSendText = switchBean.getSwitchOnSendText();
           switchOffSendText = switchBean.getSwitchOffSendText();

           linearLayout = createSwitchLinearLayout(context);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Switch swi = (Switch)linearLayout.getChildAt(2);
                    swi.callOnClick();
                }
            });
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }else{
            final ButtonBean buttonBean = (ButtonBean)item;
            type = buttonBean.getCheckedButtonName();
            switchName = buttonBean.getButtonName();
            buttonSendText = buttonBean.getButtonSendText();

           linearLayout =createSwitchLinearLayout(context);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean[] sendStatus = {false};
                 if(AcountMagnager.isSignInBlueTooth()){
                   sendStatus[0] =  BlueToothUtil.sendText(buttonSendText);
                     Toast.makeText(context,"发送串口数据"+(sendStatus[0]?"成功":"失败"),Toast.LENGTH_SHORT).show();
                 }else if(AcountMagnager.isSignInUsr()){
                     new Thread(new Runnable(){
                         @Override
                         public void run() {
                            WriterHolder.getInstance().sendMsgToServer(buttonSendText);
                         }
                     }).start();
                 }
                }
            });
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }
       final String switch_type ;
        final ImageView imageView = new ImageView(context);
        MyLinearLayout.LayoutParams imglp = new MyLinearLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,0);
        imglp.weight=5;
        imglp.gravity= Gravity.CENTER;
      //  imageView.setId(R.id.switch_img_x);
        imageView.setLayoutParams(imglp);
        if((switch_type=type).equals("电灯")){
            imageView.setImageResource(R.mipmap.light);
        }else if((switch_type).equals("窗户")){
            imageView.setImageResource(R.mipmap.window);
        }else if(switch_type.equals("手柄")){
            imageView.setPaddingRelative(9,9,9,9);
            imageView.setImageResource(R.mipmap.handle);
        }else if(switch_type.equals("鼠标")){
            imageView.setImageResource(R.mipmap.mouse);
        }

        TextView textView = new TextView(context);
        MyLinearLayout.LayoutParams tvlp=new MyLinearLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,0);
        if(item instanceof SwitchBean){
            tvlp.weight=2;
        }else{
            tvlp.weight=3;
        }
        tvlp.gravity=Gravity.CENTER;
        textView.setLayoutParams(tvlp);
       // textView.setId(R.id.switch_name_x);
        textView.setText(switchName);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);

        if(item instanceof SwitchBean){
        final Switch mSwitch = new Switch(context);
        MyLinearLayout.LayoutParams swlp=new MyLinearLayout
                .LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,0);
        swlp.weight=2;
        swlp.gravity= Gravity.CENTER;
        mSwitch.setLayoutParams(swlp);
        mSwitch.setTextOff("关闭");
        mSwitch.setTextOn("开启");
        mSwitch.setChecked(true);
        mSwitch.setThumbResource(R.drawable.thumb_selector);
        mSwitch.setTrackResource(R.drawable.track_selector);
        mSwitch.setOnClickListener(new View.OnClickListener() {
            private boolean ischecked = true;
            @Override
            public void onClick(View v) {
                ischecked = mSwitch.isChecked();
              sendSwichText(context,ischecked, ((SwitchBean) item).getSwitchOffSendText()
                      , ((SwitchBean) item).getSwitchOnSendText(), new ChangeIconListener() {
                          @Override
                          public void change() {
                              changeIcon(ischecked,switch_type,mSwitch,imageView);
                          }
                      });
            }
        });
         linearLayout.addView(mSwitch);
        }
        switchContainerLayout.addView(linearLayout);
    }

    private static void changeIcon(boolean ischecked,String switch_type,Switch mSwitch,ImageView imageView){
        if (switch_type.equals("电灯")) {
            if (ischecked) {
                imageView.setImageResource(R.mipmap.light_off);
            } else {
                imageView.setImageResource(R.mipmap.light);
            }
        } else {
            if (ischecked) {
                imageView.setImageResource(R.mipmap.window_off);
            } else {
                imageView.setImageResource(R.mipmap.window);
            }
        }
        mSwitch.setChecked(!ischecked);
    }

    public static void showSwitch( ParentButton item,Context context,
                                   LinearLayout layoutContainer, ViewPager viewPager){
        showSwitch(item,context,layoutContainer);
        ArrayList<ParentButton> views = Initializer.getSwitchView();
        views.add(item);
        SerializationUtil.seri(views);
        viewPager.getAdapter().notifyDataSetChanged();
    }
    private static LinearLayout getChildLineraLayout(Context context,LinearLayout parentLinearLayout){
        if(CONTAINERLIST.size()==0){
            CONTAINERLIST.add((LinearLayout) parentLinearLayout.getChildAt(0));
        }
        if(CONTAINERLIST.get(CONTAINERLIST.size()-1).getChildCount()>=3){
            LinearLayout nextContainer = new LinearLayout(context);
            LinearLayout.LayoutParams lp = new LinearLayout
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
            lp.weight=1;
            nextContainer.setLayoutParams(lp);
            nextContainer.setOrientation(LinearLayout.HORIZONTAL);
            parentLinearLayout.addView(nextContainer);
            CONTAINERLIST.add(nextContainer);
        }
        return CONTAINERLIST.get(CONTAINERLIST.size()-1);
    }
    @SuppressLint("NewApi")
    private static LinearLayout createSwitchLinearLayout(Context context
           ){

        final MyLinearLayout linearLayout = new MyLinearLayout(context);
        MyLinearLayout.LayoutParams lp = new MyLinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        lp.gravity= Gravity.CENTER_HORIZONTAL;
        lp.setMargins(10,10,10,10);
        linearLayout.setLayoutParams(lp);
        linearLayout.setClickable(true);
        linearLayout.setLongClickable(true);
        linearLayout.setBackground(context.getResources().getDrawable(R.drawable.selector_color));
        linearLayout.setOrientation(MyLinearLayout.VERTICAL);
        return linearLayout;
    }

    private static void sendSwichText(Context context,boolean checked, final String OffText, final String OnText, final ChangeIconListener listener) {
        if(checked){
            //send off
            if(AcountMagnager.isSignInBlueTooth()){
               if(BlueToothUtil.sendText(OffText)){
                   Toast.makeText(context,"发送串口数据成功",Toast.LENGTH_SHORT).show();
                   listener.change();}else{
                   Toast.makeText(context,"发送串口数据失败",Toast.LENGTH_SHORT).show();
               }
            }else if(AcountMagnager.isSignInUsr()){
                //网络用户登录，发送给服务器
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        WriterHolder.getInstance().sendMsgToServer(OffText,listener);
                    }
                }).start();
            }
        }else {
            //send on
            if(AcountMagnager.isSignInBlueTooth()){
               if(BlueToothUtil.sendText(OnText)) listener.change();
            }else if(AcountMagnager.isSignInUsr()){
                //网络用户登录，发送给服务器
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                     WriterHolder.getInstance().sendMsgToServer(OnText,listener);
                    }
                }).start();
            }
        }
    }
}
