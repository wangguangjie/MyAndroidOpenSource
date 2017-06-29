package com.org.wangguangjie.application3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.org.wangguangjie.R;

/**
 * Created by wangguangjie on 2017/6/22.
 */

public abstract class SingleFragmentActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.application3_start);
        initView();

    }
    private void initView()
    {
        getActionBar().setTitle("生活记录器");
        getActionBar().setIcon(R.mipmap.b3_icon);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment1=fm.findFragmentById(R.id.fragment_container1);
        if(fragment1==null)
        {
            fragment1=createFragment();
            fm.beginTransaction().add(R.id.fragment_container1,fragment1).commit();
        }
    }
    protected abstract Fragment createFragment();
}
