package com.example.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/9/4.
 */

public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fragmentList;

    public MyFragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
//        View v=viewList.get(position);
//        ViewGroup parent = (ViewGroup) v.getParent();
//        //Log.i("ViewPaperAdapter", parent.toString());
//        if (parent != null) {
//            parent.removeAllViews();
//        }
//        container.addView(viewList.get(position));
//        return viewList.get(position);

    }
}
