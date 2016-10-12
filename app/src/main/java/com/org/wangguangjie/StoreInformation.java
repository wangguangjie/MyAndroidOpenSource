package com.org.wangguangjie;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.org.wangguangjie.application4.NewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangguangjie on 16/10/1.
 */

public class StoreInformation {
    //存储列表数据;
    private ArrayList<NewItem> lists=new ArrayList<>();
    //private Context context;
    //用于保存列表数据;
    public final static String LIST_SIZE="SIZE";
    private SharedPreferences preferences;

    public StoreInformation(SharedPreferences p)
    {
        //preferences=new MainActivity().preferences;
        preferences=p;
        //preferences= PreferenceManager.getDefaultSharedPreferences(c);
    }

    public void addItem(NewItem item)
    {
        lists.add(item);
    }

    public ArrayList<NewItem> getLists()
    {
        return lists;
    }
    public void clear()
    {
        lists.clear();
    }
    public void storeData()
    {
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();

        for(int i=0;i<lists.size();i++)
        {
            editor.putString("url"+i,lists.get(i).url);
            editor.putString("title"+i,lists.get(i).title);
            editor.putString("time"+i,lists.get(i).time);
        }
        editor.putInt(LIST_SIZE,lists.size());
        editor.commit();
    }
    public void recoveryData()
    {
        //ArrayList<NewItem> p_lists=new ArrayList<>();
        //SharedPreferences.Editor editor=preferences.edit();
        for(int i=0;i<preferences.getInt(LIST_SIZE,0);i++)
        {
            String title,time,url;
            url=preferences.getString("url"+i,"");
            title=preferences.getString("title"+i,"");
            time=preferences.getString("time"+i,"");
            NewItem item=new NewItem(title,url,time);
            lists.add(item);
        }
    }
}
