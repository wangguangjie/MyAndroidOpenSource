package com.org.wangguangjie.application5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.org.wangguangjie.R;

/**
 * Created by wangguangjie on 16/10/19.
 */

public class Application5Start extends Activity {

    private TextView tx;

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        //tx=(TextView)findViewById(R.id.)
        //test!;
        setContentView(R.layout.application5_start);
    }

}
