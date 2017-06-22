package com.org.wangguangjie.application1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.org.wangguangjie.R;

import java.util.TimerTask;

/**
 * Created by wangguangjie on 16/9/20.
 */
public class BeforeStartApplication extends Activity{

    private Button ad_content;
    @Override
    public void onCreate(Bundle saveIntanceState)
    {
        super.onCreate(saveIntanceState);
        //全屏显示;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.beforepplication1);
        ad_content=(Button) findViewById(R.id.before_image);
        Handler handler=new Handler();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                //ad_content.setVisibility(View.GONE);
                Intent intent=new Intent(BeforeStartApplication.this,Application1Start.class);
                startActivity(intent);
                finish();
            }
        };
        //五秒之后进入应用;
        handler.postDelayed(task,700);
    }

}
