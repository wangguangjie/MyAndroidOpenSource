package com.org.wangguangjie.application3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.org.wangguangjie.R;

import java.security.cert.CRL;
import java.util.List;

/**
 * Created by wangguangjie on 2017/6/22.
 */

public class CrimeListFragment extends Fragment {

    private ListView mCrimesListView;
    private List<Crime> mCrimeList;
    public CrimeListFragment()
    {
        mCrimeList=CrimeLab.get(getActivity()).getCrimes();
    }
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.application3_crimelistfragment,container,false);
        mCrimesListView=(ListView)view.findViewById(R.id.crime_list);
        init();
        Adapter adapter=new Adapter(mCrimeList,inflater);
        mCrimesListView.setAdapter(adapter);
        return view;
    }
    private void init()
    {
        //Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
        mCrimesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),CrimeDetailActivity.class);
                intent.putExtra(CrimeDetailActivity.id,mCrimeList.get(position).getId());
                startActivity(intent);
            }
        });
    }
}
