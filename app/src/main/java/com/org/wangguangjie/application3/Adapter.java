package com.org.wangguangjie.application3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.org.wangguangjie.R;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wangguangjie on 2017/6/23.
 */

public class Adapter extends BaseAdapter{

    private List<Crime> mCrimeList;
    private LayoutInflater mLayoutInflater;
    public Adapter(List<Crime> list,LayoutInflater inflater)
    {
        mCrimeList=list;
        mLayoutInflater=inflater;
    }
    @Override
    public int getCount() {
        return mCrimeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCrimeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=mLayoutInflater.inflate(R.layout.application3_item,parent,false);
        TextView textView=(TextView)view.findViewById(R.id.item_crime_title);
        CheckBox checkBox=(CheckBox)view.findViewById(R.id.checkBox);
        TextView textView1=(TextView)view.findViewById(R.id.item_crime_date);
        textView.setText(mCrimeList.get(position).getTitle());
        checkBox.setChecked(mCrimeList.get(position).isSolved());
        SimpleDateFormat f1=new SimpleDateFormat();
        SimpleDateFormat f2=new SimpleDateFormat("E");
        textView1.setText(f2.format(mCrimeList.get(position).getDate())+" "+f1.format(mCrimeList.get(position).getDate()));
        return view;
    }
}
