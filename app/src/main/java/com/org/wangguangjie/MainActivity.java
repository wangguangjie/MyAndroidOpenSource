package com.org.wangguangjie;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.org.wangguangjie.application1.Application1Start;
import com.org.wangguangjie.application1.BeforeStartApplication;
import com.org.wangguangjie.application2.Application2Start;
import com.org.wangguangjie.application3.Application3Start;
import com.org.wangguangjie.application4.Application4Start;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangguangjie on 16/9/8.
 */
public class MainActivity extends Activity{
    private int list_count=15;
    private ArrayList<String> name=new ArrayList<>();
    //private String[] name=new String[list_count];
    private ArrayList<String> describe=new ArrayList<>();
    // private String[] describe=new String[list_count];
    private ArrayList<Integer> images=new ArrayList<>();
    //private int[] images=new int[list_count];
    private ArrayList<Map<String,Object>> list_item=new ArrayList<>();

    private ListView ls;
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        this.setContentView(R.layout.activity_main);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.activity_main_titile_1);
        initLists();

    }
    private void initLists()
    {

        getApplicationInformation();
        SimpleAdapter adapter=new SimpleAdapter(this,list_item,R.layout.activity_main_list,
                new String[]{"image","name","describe"},new int[]{R.id.image,R.id.name,R.id.desc});
        ls=(ListView)findViewById(R.id.ls);
        ls.setAdapter(adapter);
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        Intent intent1=new Intent(MainActivity.this,BeforeStartApplication.class);
                        startActivity(intent1);
                        break;
                    //Toast.makeText(MainActivity.this,"你点击了第"+i+"个列表",Toast.LENGTH_LONG).show();
                    case 1:
                        Intent intent2=new Intent(MainActivity.this, Application2Start.class);
                        Bundle bundle=new Bundle();
                        //bundle.p("sharepreference",preferences);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3=new Intent(MainActivity.this, Application3Start.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4=new Intent(MainActivity.this, Application4Start.class);
                        startActivity(intent4);
                        break;
                    default:break;
                }
            }
        });
    }
    public void getApplicationInformation()
    {
        for(int i=0;i<list_count;i++)
        {
            String s="应用"+i;
            if(i==0)
            {
                s="爬虫获取信息";
            }
            if(i==1)
            {
                s="计算器";
            }
            if(i==2)
            {
                s="我的新闻客户端";
            }
            if(i==3)
            {
                s="哈工大通知";
            }
            name.add(s);
            //name[i]=s;
        }
        for(int i=0;i<list_count;i++)
        {
            String d="应用描述";
            if(i==0)
            {
                d="使用jsoup实现爬虫获取网络的相关信息";
            }
            if(i==1)
            {
                d="我的计算器";
            }
            if(i==2)
            {
                d="开发属于我的新闻客户端";
            }
            if(i==3)
            {
                d="哈工大深圳官网消息";
            }
            describe.add(d);
            //describe[i]=d;
        }
        for(int i=0;i<list_count;i++)
        {
            //images[i]=R.mipmap.ic_launcher;
            switch (i)
            {
                case 0:
                    images.add(R.mipmap.search_message);
                    break;
                case 1:
                    images.add(R.mipmap.caculate);
                    break;
                case 2:
                    images.add(R.mipmap.news);
                    break;
                case 3:
                    images.add(R.mipmap.hit);
                    break;
                default:
                    images.add(R.mipmap.ic_launcher);
                    break;
            }
        }
        for(int i=0;i<list_count;i++)
        {
            Map<String,Object> item=new HashMap<>();
            item.put("image",images.get(i));
            item.put("name",name.get(i));
            item.put("describe",describe.get(i));
            list_item.add(item);
        }
    }
   //public final   SharedPreferences preferences=getSharedPreferences("hit",MODE_PRIVATE);
}
