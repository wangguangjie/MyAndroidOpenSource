package com.org.wangguangjie.application5;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        setContentView(R.layout.application5_start);
        tx=(TextView)findViewById(R.id.text);
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(this,"hello",123);
            }
        });
    }

}
