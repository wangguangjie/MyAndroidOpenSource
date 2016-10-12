package com.org.wangguangjie;

import android.app.ActionBar;
import android.content.Context;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by wangguangjie on 16/10/1.
 */

public class MyActionProvider extends ActionProvider{

    private Context context;
    private LayoutInflater inflater;
    private View view;
    public MyActionProvider(Context c)
    {
        super(c);
        context=c;
        inflater=LayoutInflater.from(context);
        view=inflater.inflate(R.layout.myactionprovider,null);
    }
    @Override
    public View onCreateActionView() {

        return view;
    }
}
