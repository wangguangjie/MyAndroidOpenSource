package com.org.wangguangjie.application5;

import android.os.Bundle;
import android.widget.TextView;

import com.org.wangguangjie.MainActivity;
import com.org.wangguangjie.R;

/**
 * Created by wangguangjie on 16/10/19.
 */

public class Application5Start extends MainActivity {

    private TextView tx;
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        //tx=(TextView)findViewById(R.id.)
        //test!;
        tx=(TextView)findViewById(R.id.tx);
    }
}
