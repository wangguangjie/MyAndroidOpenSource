package com.org.wangguangjie.application3;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.org.wangguangjie.R;
import com.org.wangguangjie.RefreshableView;
import com.org.wangguangjie.application1.Application1Start;

import java.util.ArrayList;

/**
 * Created by wangguangjie on 16/9/14.
 */
public class Application3Start extends Activity{

    /**
     * 定义界面组件变量和其他变量;
     */
    private LinearLayout linearLayout;
    private ActionBar mActionBar;
    private TextView mTextView;
    private Button mButtonC;
    private Button mButtonE;

    /**
     * 加载MainActivity调用函数;
     */
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.application3_start);
        initView();
        initData();
    }

    /**
     * 初始化界面;
     */
    @Nullable
    private void initView()
    {
        initActionBar();
        //对加载的界面组件进行初始化设置
        mTextView=(TextView)findViewById(R.id.tv_content);
        mButtonC=(Button)findViewById(R.id.bt_correct);
        mButtonE=(Button)findViewById(R.id.bt_error);
    }

    /**
     * 初始化菜单条（可进行个性化设置）;
     */
    private void initActionBar()
    {
        mActionBar=getActionBar();
        mActionBar.setTitle("我正在开发的应用");
        mActionBar.setIcon(R.mipmap.b3_icon);
    }

    /**
     * 初始化MainActivity的数据;
     */
    public void initData()
    {

    }

}
