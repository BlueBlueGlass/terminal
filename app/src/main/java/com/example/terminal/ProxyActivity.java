package com.example.terminal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.example.fragment.BaseFragment;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2018/9/2.
 */

public abstract class ProxyActivity extends SupportActivity {
    public abstract BaseFragment getRootDelegate();//fragment
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
    }
    private void initContainer(@Nullable Bundle savedInstanceState){
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){//第一次加载
            loadRootFragment(R.id.fragment_container,getRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
