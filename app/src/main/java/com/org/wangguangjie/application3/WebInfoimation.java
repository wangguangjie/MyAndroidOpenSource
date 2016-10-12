package com.org.wangguangjie.application3;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.org.wangguangjie.R;

/**
 * Created by wangguangjie on 16/9/15.
 */
public class WebInfoimation extends Activity {

    private WebView web_info;

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.application3_webinfo);
        web_info=(WebView)findViewById(R.id.web_info);
        showWebInformation();
    }
    public void showWebInformation()
    {
        web_info.loadUrl(Application3Start.url);
    }
}
