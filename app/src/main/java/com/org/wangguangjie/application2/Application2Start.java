package com.org.wangguangjie.application2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.org.wangguangjie.R;

import java.net.URL;

/**
 * Created by wangguangjie on 16/9/11.
 */
public class Application2Start extends Activity {

    EditText edt_number;
    Button bt_call;
    Button bt_baidu;
    WebView webView;
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.application2_start);
        edt_number=(EditText)findViewById(R.id.edt_number);
        bt_call=(Button)findViewById(R.id.button_call);
        webView=(WebView)findViewById(R.id.web_info);
        bt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Application2Start.this, edt_number.getText(), Toast.LENGTH_LONG).show();
                String url=edt_number.getText().toString();
                if(url!=null)
                {
                    //webView.getSettings().setJavaScriptEnabled(true);
                    //webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                    //webView.getSettings().setSupportMultipleWindows(true);
                    webView.setWebViewClient(new WebViewClient());
                    //webView.setWebChromeClient(new WebChromeClient());
                    webView.loadUrl(url);
                }
            }
        });
        bt_baidu=(Button)findViewById(R.id.button_baidu);
        bt_baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://www.baidu.com";
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webView.getSettings().setSupportMultipleWindows(true);
                webView.setWebViewClient(new WebViewClient());
                webView.setWebChromeClient(new WebChromeClient());
                webView.loadUrl(url);
            }
        });

    }
}
