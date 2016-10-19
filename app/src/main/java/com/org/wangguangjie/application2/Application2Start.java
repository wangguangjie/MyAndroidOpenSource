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
    }
}
