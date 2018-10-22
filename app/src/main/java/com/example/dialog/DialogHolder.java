package com.example.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.terminal.R;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.ArrayList;
/**
 * Created by Administrator on 2018/9/1.
 */

public class DialogHolder {
    /**
     * 显示加载对话框
     *
     * @param context    上下文
     * @param msg        对话框显示内容
     */
    private static ArrayList<Dialog> LOADER = new ArrayList<>();
    private static ArrayList<Dialog> WARNER = new ArrayList<>();

    public static void showDialogForLoading(Context context, String msg){
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
        TextView loadingText =  view.findViewById(R.id.id_tv_loading_dialog_text);
        com.wang.avi.AVLoadingIndicatorView avLoadingIndicatorView =  view.findViewById(R.id.AVLoadingIndicatorView);
        loadingText.setText(msg);
        final Dialog mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        avLoadingIndicatorView.smoothToShow();
        LOADER.add(mLoadingDialog);
    }
    public static void showDialogForWarning(Context context, String titleMsg) {
       final WarningDialog mWarningDialog = new WarningDialog(context,R.style.loading_dialog);
       mWarningDialog.setTitle(titleMsg);
       mWarningDialog.setYesOnclickListener("确定", new WarningDialog.onYesOnclickListener() {
           @Override
           public void onYesOnclick() {
               mWarningDialog.cancel();
           }
       });
       mWarningDialog.show();
        WARNER.add(mWarningDialog);
    }
    public static void showDialogForWarning(Context context, String titleMsg
            ,WarningDialog.onYesOnclickListener yesListener,String yes) {
        final WarningDialog mWarningDialog = new WarningDialog(context,R.style.loading_dialog);
        mWarningDialog.setTitle(titleMsg);
        mWarningDialog.setYesOnclickListener(yes,yesListener);
        mWarningDialog.show();
        WARNER.add(mWarningDialog);
    }
    public static void stopLoading(){
        for(Dialog dialog:LOADER){
            if(dialog!=null){
                if(dialog.isShowing()){
                    dialog.cancel();
                }
            }
        }
    }
    public static void stopWarner(){
        for(Dialog dialog:WARNER){
            if(dialog!=null){
                if(dialog.isShowing()){
                    dialog.cancel();
                }
            }
        }
    }
}

