package com.org.wangguangjie.application4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.org.wangguangjie.R;

/**
 * Created by wangguangjie on 16/9/26.
 */

public class WebInformation extends Activity {
    private String url;
    private WebView webView;

    Button button;
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.application4_webinfo);
        webView=(WebView)findViewById(R.id.ap4_webinf);
        button=(Button) findViewById(R.id.t1);
        Bundle bundle=getIntent().getExtras();
        url=bundle.getString("url");
        showWebInf();
    }
    public void showWebInf()
    {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        //webView.getSettings().setSupportZoom(true);
        //webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setBuiltInZoomControls(true);
       // webView.getSettings().setBlockNetworkImage(true);
       // webView.getSettings().setLightTouchEnabled(true);

        webView.loadUrl(url);
        //
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri= Uri.parse(url);
                intent.setData(uri);
               // intent.setClassName("com.android.browser","om.android.browser.BrowserActivity");
                startActivity(intent);
            }
        });
    }
}
