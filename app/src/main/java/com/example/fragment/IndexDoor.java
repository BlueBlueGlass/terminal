package com.example.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.app.Initializer;
import com.example.fragment.BaseFragment;
import com.example.recyclerview.Door;
import com.example.recyclerview.DoorAdapter;
import com.example.recyclerview.DoorBean;
import com.example.terminal.R;
import com.example.util.sign.AcountMagnager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/9/4.
 */

public class IndexDoor extends BaseFragment {
    public static List<DoorBean> doorList = new ArrayList<>();
    public static  DoorAdapter adapter;

    @Override
    public Object setLayout() {
        return R.layout.fragment_index_door;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
         adapter = new DoorAdapter(doorList);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
         if(AcountMagnager.isSignInUsr()){
            Intent intent = new Intent();
            intent.setAction("DOOR_INIT");
            Initializer.getApplicationContext().sendBroadcast(intent);
            Log.d("dsa","初始化DoorAction数据");}

    }
}
