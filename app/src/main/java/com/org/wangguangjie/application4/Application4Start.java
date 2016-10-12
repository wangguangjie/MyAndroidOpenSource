package com.org.wangguangjie.application4;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.org.wangguangjie.R;
import com.org.wangguangjie.StoreInformation;
import com.org.wangguangjie.application1.Application1Start;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangguangjie on 16/9/26.
 * 本程序实现爬取哈工大教务处的信息,并做分类处理,用户可根据需要分类查看;
 * 本程序主要有三个亮点:1.使用Jsoup实现爬去网络信息(掌握了使用Jsoup爬取信息)。
 *                  2.自定义ListViw,实现下拉刷新,上拉获取更多信心,并记录上次刷新时间。
 *                  3.使用缓存对每一次刷新的信息进行存储,让用户下次使用程序时候不用等待很长的时间就可进行查询信息。
 *                  4.定义菜单栏,进行个性化设置和功能扩展。
 */

public class Application4Start extends Activity{
    //列表信息;
   // private ArrayList<NewItem> lists=new ArrayList<>();
    private StoreInformation store_lists;
    //pages;
    private int pages=0;
    //url;
    private final String HIT1="http://www.hitsz.edu.cn/index/news/fid/138/cid/234.html";
    private final String HIT2="http://www.hitsz.edu.cn/index/news/fid/138/cid/231.html";
    private final String HIT3="http://www.hitsz.edu.cn/index/news/fid/138/cid/243.html";
    private final String HIT4="http://www.hitsz.edu.cn/index/news/fid/138/cid/232.html";
    private final String HIT5="http://www.hitsz.edu.cn/index/news/fid/138/cid/233.html";
    private final String HIT6="http://www.hitsz.edu.cn/index/news/fid/138/cid/235.html";

    //
    private String url1=HIT1+"?p=";
    private String url2=HIT2+"?p=";
    private String url3=HIT3+"?p=";
    private String url4=HIT4+"?p=";
    private String url5=HIT5+"?p=";
    private String url6=HIT6+"?p=";
    //
    private String url=url1;
    private String page_url;
    //页码数;初始页码为1;
    private int  page_number=1;
    //
    final private String HIT="http://www.hitsz.edu.cn";
    //
    private PullListView listView;
    //
    private InformationAdapter adapter;
    //
    //private Boolean hasMore=false;
    //
    private TextView tv;
    //
    private String message="";
    //
    private boolean first=true;
    //
    private SharedPreferences sharedPreferences;
    int select;
    //主线程执行信息显示,如果出现异常情况通知用户;
    SpinnerAdapter spinnerAdapter;
    ActionBar.OnNavigationListener navigationListener;
    ActionBar actionBar;
    private String[] classify={"重要通知","讲座会议","热门专题"};
    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            //更改解析出信息,更新界面;
            if(msg.what==0x123)
            {
                showInfo();
                //缓存数据;
                store_lists.storeData();
            }
            //通过actionbar的选择进行解析数据
            else if(msg.what==0x124)
            {
                first=true;
                page_number=1;
                page_url=url+page_number++;
                new Thread(new getThread()).start();
            }
            //如果无更多页面不许进行加载更多;
            else if(msg.what==0x125)
            {
                Toast.makeText(Application4Start.this,"无更多信息!",Toast.LENGTH_LONG).show();
                listView.getMoreComplete();
            }
            else if(msg.what==0x126){
                adapter = new InformationAdapter(Application4Start.this, store_lists.getLists());
                listView.setAdapter(adapter);
                listView.deferNotifyDataSetChanged();
            }
            //处理异常信息;
            else if(msg.what==0x111)
            {
                Toast.makeText(Application4Start.this,"无法获取信息",Toast.LENGTH_LONG).show();
            }
            //获取信息失败;
            else if(msg.what==0x222)
            {
                Toast.makeText(Application4Start.this,"信息获取失败,请重新尝试!",Toast.LENGTH_LONG).show();
            }
            //无法连接网络;
            else if(msg.what==0x333)
            {
                Toast.makeText(Application4Start.this,"无法连接网络,请重新尝试!",Toast.LENGTH_LONG).show();
            }
        }
    };
    //子线程执行网络信息的获取任务;
    class getThread implements Runnable
    {

        @Override
        public void run() {
            getMessage();
        }
    }
    class WastTime implements Runnable
    {

        @Override
        public void run() {
            try
            {
                Thread.sleep(2000);
                Message message=new Message();
                message.what=0x125;
                handler.sendMessage(message);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }
    }

    //刚启动程序时,为防止用户长时间的等待,次线程加载缓存数据;
    class RecoveryThread implements Runnable
    {

        @Override
        public void run() {
            Message msg=new Message();
            msg.what=0x126;
            handler.sendMessage(msg);
        }
    }
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        this.setContentView(R.layout.application4_start);
        //
        //test
        initView();
        store_lists=new StoreInformation(getSharedPreferences("hit1",MODE_PRIVATE));
        store_lists.recoveryData();

        if(store_lists.getLists().size()>0)
        {
            Toast.makeText(this,"lists:"+store_lists.getLists().size(),Toast.LENGTH_LONG).show();
            new Thread(new RecoveryThread()).start();
            try{
                Thread.sleep(100);
            }catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }


        new Thread(new getThread()).start();
        //SharedPreferences p=get
    }

    //设置菜单;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.application4_menu,menu);
        /*
        getMenuInflater().inflate(R.menu.application4_menu, menu);

        //
        SearchView searchView = (SearchView) menu.findItem(R.id.item_search)
                .getActionView();

        //分享视窗，因为showAsAction="never"，所以只能在溢出菜单中才看见到
        ShareActionProvider mShareActionProvider = (ShareActionProvider) menu
                .findItem(R.id.item_share).getActionProvider();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        mShareActionProvider.setShareIntent(shareIntent);

        //设置视窗，MyActionProvider就是我们自定义的ActionProvider
        MyActionProvider myactionprovider = (MyActionProvider) menu.findItem(
                R.id.menu_setting).getActionProvider();
        return super.onCreateOptionsMenu(menu);
        */

        return super.onCreateOptionsMenu(menu);
    }
    //菜单操作响应;
    @Override
    public boolean onOptionsItemSelected(MenuItem menu)
    {
        switch (menu.getItemId())
        {
            case R.id.item_search:
                //业务代码;
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(menu);
    }
    //初始化界面;
    private void initView()
    {
        //actionBar的相关设置;
        spinnerAdapter= ArrayAdapter.createFromResource(this,R.array.classifies,android.R.layout.simple_spinner_dropdown_item);
        navigationListener=new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int position, long l) {
                switch (position)
                {
                    case 0:
                        url=url1;
                        break;
                    case 1:
                        url=url2;
                        break;
                    case 2:
                        url=url3;
                        break;
                    case 3:
                        url=url4;
                        break;
                    case 4:
                        url=url5;
                        break;
                    case 5:
                        url=url6;
                        break;
                    default:
                        break;
                }
                Message msg=new Message();
                msg.what=0x124;
                handler.sendMessage(msg);
                //new Thread(new getThread()).start();
                return true;
            }
        };
        actionBar=getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(spinnerAdapter,
                navigationListener);
        actionBar.setTitle("HIT消息");
        actionBar.setIcon(R.mipmap.hit);

        //设置是否显示标题和图标;
        //actionBar.setDisplayShowHomeEnabled(false);
        //actionBar.setDisplayShowTitleEnabled(false);
        //
        //获取每个页面的url;
        page_url=url+page_number++;
        //对listview设置下拉刷新,底部刷新和点击操作监听;
        listView=(PullListView)findViewById(R.id.pulllist);
        listView.addSharePreference(getSharedPreferences("pull_listview",MODE_PRIVATE));
        //根据用户选择不同的打开方式;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(Application4Start.this);
                builder.setTitle("提示");
                //builder.setMessage("需要使用浏览器打开吗?");
                builder.setSingleChoiceItems(new String[]{"浏览器打开", "本地打开查看"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        select=i;
                    }
                });
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (select)
                        {
                            case 0:
                                Intent intent=new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri uri= Uri.parse(store_lists.getLists().get(position-1).getUrl());
                                intent.setData(uri);
                                // intent.setClassName("com.android.browser","om.android.browser.BrowserActivity");
                                startActivity(intent);
                                builder.create().dismiss();
                                break;
                            case 1:
                                Intent intent1=new Intent(Application4Start.this,WebInformation.class);
//                                Intent intent1=new Intent();
//                                intent1.setAction("com.org.wangguangjie.action.webinfo");
//                                intent1.addCategory("com.org.wangguangjie.category.webinfo");
                                Bundle bundle=new Bundle();
                                NewItem item=store_lists.getLists().get(position-1);
                                bundle.putString("url",item.getUrl());
                                intent1.putExtras(bundle);
                                startActivity(intent1);
                                builder.create().dismiss();
                                break;
                            default:break;
                        }
                        select=0;
                    }
                });
                builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //finish();
                    }
                });
                builder.create().show();
            }
        });
        //刷新;
        listView.setOnRefreshListener(new PullListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                first=true;
                page_number=1;
                page_url=url+page_number++;
                //page_number++;
                //page_url+=page_number;
                //pages=0;
                //lists.clear();
                new Thread(new getThread()).start();
            }
        },1);
        //加载更多;
        listView.setOnGetMoreListener(new PullListView.OnGetMoreListener() {
            @Override
            public void onGetMore() {
                first=false;
                if(page_number<=pages)
                {
                    page_url=url+page_number++;
                    new Thread(new getThread()).start();
                }
               else
                {
                   new Thread(new WastTime()).start();
                }

            }
        });
    }

    //主线程显示信息;
    private void showInfo()
    {
        //判断是否是第一次刷新;
        if(first) {
            adapter = new InformationAdapter(this, store_lists.getLists());
            listView.setAdapter(adapter);
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
        //信息获取完毕,技术刷新操作;
        listView.refreshComplete();
        listView.getMoreComplete();

    }
    //获取网络信息;
    private void getMessage()
    {
        //getDataByGet(url);
        if(isNetWorkAvailable()) {
            analyHtml();
            Message msg = new Message();
            msg.what = 0x123;
            handler.sendMessage(msg);
        }
        else
        {
            Message message=new Message();
            message.what=0x333;
            handler.sendMessage(message);
        }
    }

    //直接从获取页码的document进行解析;
    //本程序采用这种方法解析html;
    public void  analyHtml()
    {
       try {
           Connection connect = Jsoup.connect(page_url).timeout(3000);
           //伪装成浏览器对url进行访问,防止无法获取某些网站的document;
           connect.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
           Document doc = connect.get();
           //解析出body 标签下的div标签;
           Elements elements = doc.select("body div");
           //上拉刷新或者第一次刷新,清除数据;
           if(first)
               store_lists.clear();
           //获取相关信息:Jsoup;
           for(Element el1:elements)
           {
               //解析出制定类下的内容;
               if(el1.className().equals("news_lista-left f_l"))
               {
                   //message+=el1.text();
                   //Elements el2=el1.getElementsByTag("h4");
                   String title;
                   String ur=HIT;
                   String time;
                   Elements el0=el1.getElementsByTag("h4");
                   for(Element ell:el0)
                   {
                       Elements els_ur=ell.getElementsByTag("a");
                       for(Element el_ur:els_ur)
                       {
                           ur+=el_ur.attr("href").toString();
                       }
                      //ur+=ell.attr("href").toString();
                      // Elements el2=ell.getElementsByTag("h4");
                       title=ell.getElementsByTag("a").text();
                       time=ell.getElementsByTag("span").text();
                       store_lists.addItem(new NewItem(title,ur,time));
                   }

                   //解析出制定类下的div标签下的内容;
                   Elements els=el1.getElementsByTag("li");
                   for(Element el:els)
                   {
                       Elements el3=el.getElementsByTag("a");
                       for(Element el4:el3)
                       {
                           ur=HIT+el4.attr("href").toString();
                           break;
                       }
                       title=el.getElementsByTag("a").text();
                       time=el.getElementsByTag("span").text();
                       store_lists.addItem(new NewItem(title,ur,time));
                   }
                   //判断是否已经解析出页数;
                   //获取通知的总共页数;
                  if(first)
                  {
                      //first=false;
                      pages=0;
                      Elements els_p = el1.getElementsByTag("a");
                      for (Element el_p : els_p) {
                          if (el_p.text().equals("尾页")) {
                              String s = el_p.attr("href").toString();
                              pages = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.length()));
                              break;
                          }
                      }
                  }
                   //找到相关信息不需要再继续进行解析;
                   break;
               }
           }
       }
       catch (IOException ie)
       {
           //无法获取document时候提醒用户;
           Message message=new Message();
           message.what=0x222;
           handler.sendMessage(message);
           ie.printStackTrace();
       }
    }
    //先获取页码源码再进行解析;
    //本程序没有才有这种方法;
    public String getDataByGet(String url){
        String content ="";
        HttpClient httpClient = new DefaultHttpClient();
        /*使用HttpClient发送请求、接收响应很简单，一般需要如下几步即可。
        1. 创建HttpClient对象。
        2. 创建请求方法的实例，并指定请求URL。如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象。
        3. 如果需要发送请求参数，可调用HttpGet、HttpPost共同的setParams(HetpParams params)方法来添加请求参数；对于HttpPost对象而言，也可调用setEntity(HttpEntity entity)方法来设置请求参数。
        4. 调用HttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse。
        5. 调用HttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头；调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容。
        6. 释放连接。无论执行方法是否成功，都必须释放连接*/
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            // HttpReponse是服务器接收到浏览器的请求后，处理返回结果常用的一个类。
            if(httpResponse.getStatusLine().getStatusCode() == 200) {
                /*getStatusLine()
               获得此响应的状态行。状态栏可以设置使用setstatusline方法之一，也可以在构造函数初始化*/
                InputStream is = httpResponse.getEntity().getContent();
                /*getEntity()
                获取此响应的消息实体，如果有。实体是通过调用setentity提供。*/
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null){
                    content += line;
                }
            }
        }catch (IOException e)
        {
            Message msg=new Message();
            msg.what=0x111;
            handler.sendMessage(msg);
        }
        return  content;
    }

    //判断是否有网络连接;
    public boolean isNetWorkAvailable()
    {
        Context context=Application4Start.this.getApplicationContext();
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
