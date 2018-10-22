package com.example.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.net.ConnectSocketTask;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by Administrator on 2018/8/20.
 */

public abstract class BaseFragment extends SwipeBackFragment {
    public abstract Object setLayout();

    public static ConnectSocketTask socketTask;

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if(setLayout() instanceof Integer){
            rootView = inflater.inflate((Integer) setLayout(),container,false);
        }else  if(setLayout() instanceof  View){
            rootView = (View)setLayout();
        }else {
            throw new ClassCastException("setLayout() Type must be int or View");
        }
        if(rootView!=null){
            unbinder = ButterKnife.bind(this,rootView);
            onBindView(savedInstanceState,rootView);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    public static void closeAsyncTask(){
        if(socketTask!=null&&!socketTask.isCancelled()){
            socketTask.closeSocket();
            socketTask.cancel(false);
        }
    }
  //  public final ProxyAcitivity getProxyActivity(){
  //      return (ProxyAcitivity) getActivity();
  //  }
//    @,
}
