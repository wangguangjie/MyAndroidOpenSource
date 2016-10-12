package com.org.wangguangjie.application3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.org.wangguangjie.R;
import com.org.wangguangjie.RefreshableView;
import com.org.wangguangjie.application1.Application1Start;

import java.util.ArrayList;

/**
 * Created by wangguangjie on 16/9/14.
 */
public class Application3Start extends Activity{
    public static String url;
    ListView mListView ;
    ArrayList<String> list=new ArrayList<>();
    String[] lists=new String[]{"1","2","3"};
    RefreshableView refreshableView;

    SearchView searchView;
    WebView webView;
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.application3_start);
        mListView=(ListView)findViewById(R.id.news_content);
        refreshableView=(RefreshableView)findViewById(R.id.refresh_view);

        searchView=(SearchView)findViewById(R.id.search);
        webView=(WebView)findViewById(R.id.web_info);

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                url=s;
                Intent intent=new Intent(Application3Start.this,WebInfoimation.class);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,lists);
        mListView.setAdapter(adapter);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try{
                    Thread.sleep(3000);
                }
                catch (InterruptedException ie)
                {
                    ie.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        },1);
    }


}
