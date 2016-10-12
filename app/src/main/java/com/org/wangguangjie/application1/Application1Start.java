package com.org.wangguangjie.application1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.org.wangguangjie.R;
import com.org.wangguangjie.RefreshableView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created by wangguangjie on 16/9/10.
 */
public class Application1Start extends Activity{
    private TextView info_text;
    ArrayList<Map<String,Object>> info_list=new ArrayList<>();
    private ListView list;
    private TextView tx;
    private RefreshableView refreshableView;

    private String s="";
    //private ImageView ad_image;
    Runnable runable=new Runnable() {
        @Override
        public void run() {
           if(isNetWorkAvailable()) {
               getWebMessage();
               Message msg = new Message();
               msg.what = 0x123;
               handle.sendMessage(msg);
           }
            else
           {
               Message msg=new Message();
               msg.what=0x111;
               handle.sendMessage(msg);
           }
        }
    };
    Handler handle=new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if(msg.what==0x123)
            {
                //getWebMessage();
                showInformation();
            }
            else if(msg.what==0x111)
            {
                new AlertDialog.Builder(Application1Start.this)
                        .setTitle("提示")
                        .setMessage("无法连接网络")
                        .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //重新连接网络;
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).show();
            }
        }
    };
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.application1_start);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.activity_main_titile_1);
        //info_text=(TextView)findViewById(R.id.info);
        list=(ListView)findViewById(R.id.list_info);
        //tx=(TextView)findViewById(R.id.tv);
        refreshableView=(RefreshableView)findViewById(R.id.refresh_view);

        new Thread(runable).start();
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(runable).start();
               // refreshableView.finishRefreshing();

                //refreshableView.finishRefreshing();
            }
        },0);
    }
    public void showInformation()
    {
        TextView tv1=(TextView)findViewById(R.id.tv1);
        tv1.setVisibility(View.GONE);
        //tv1.setText(s);
        SimpleAdapter adapter=new SimpleAdapter(this,info_list,R.layout.application1_simple_info
                ,new String[]{"company","time","address"},new int[]{R.id.company,R.id.time,R.id.address});
        list.setAdapter(adapter);
        refreshableView.finishRefreshing();
    }
    public void getWebMessage()
    {
        String url = "http://xjh.haitou.cc/wh/uni-1/after/hold/page-1/";
        Connection connect = Jsoup.connect(url);
        connect.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
        try
        {
            Document doc = connect.get();
            Elements els=doc.select("tbody tr");

            info_list.clear();
            //解析公司;
            Elements elements,elements1,elements2;
            ArrayList<String> ls_company=new ArrayList<>();
            ArrayList<String> ls_date=new ArrayList<>();
            ArrayList<String> ls_address=new ArrayList<>();
           // String s="";
            int i=0;
            for(Element el:els)
            {
               // s+=(i++)+el.text()+"\n";
                ls_company.add(i,el.getElementsByClass("text-success").first().text());

               //解析时间
                ls_date.add(i,el.getElementsByClass("hold-ymd").first().text());

//                //解析地点;
                ls_address.add(i++,el.getElementsByClass("text-ellipsis").first().text());
            }
            for(int ij=0;ij<i;ij++)
            {
                Map<String,Object> item=new HashMap<>();
                item.put("company",ls_company.get(ij));
                item.put("time",ls_date.get(ij));
                item.put("address",ls_address.get(ij));
                info_list.add(item);
            }


        }
        catch (IOException e)
        {
                Toast.makeText(this, "网络信息抓取失败!", Toast.LENGTH_LONG).show();
        }
    }
    public boolean isNetWorkAvailable()
    {
        Context context=Application1Start.this.getApplicationContext();
        ConnectivityManager connectmanger=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo network=connectmanger.getActiveNetworkInfo();
        if(connectmanger!=null)
        {
            NetworkInfo ninfo=connectmanger.getActiveNetworkInfo();
            if(ninfo!=null&&ninfo.isConnected())
              return true;
            else
                return false;
        }
        else{
            return false;
        }
    }
}
