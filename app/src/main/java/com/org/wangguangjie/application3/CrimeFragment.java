package com.org.wangguangjie.application3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.org.wangguangjie.R;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * Created by wangguangjie on 2017/6/22.
 */

public class CrimeFragment extends Fragment{
    /**
     * Fragment 容器内的组件
     */
    private Crime mCrime;
    private EditText mTitleField;
    private CheckBox mCheckBox;
    private Button mDateButton;
    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        //mCrime=new Crime();
        UUID id=(UUID)getActivity().getIntent().getSerializableExtra(CrimeDetailActivity.id);
        CrimeLab crimeLab=CrimeLab.get(getActivity());
        mCrime=crimeLab.getCrime(id);
    }

    /**
     * 创建fragment实例时的初始化参数;
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //获取布局文件;
        View v=inflater.inflate(R.layout.application3_fragment_crime,container,false);
        mTitleField=(EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        //设置监听器;
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               String title=s.toString();
                mCrime.setTitle(title);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mCheckBox=(CheckBox)v.findViewById(R.id.crime_solved);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        mCheckBox.setChecked(mCrime.isSolved());
        mDateButton=(Button)v.findViewById(R.id.crime_date);
        SimpleDateFormat f1=new SimpleDateFormat("E");
        SimpleDateFormat f2=new SimpleDateFormat();
        mDateButton.setText(f1.format(mCrime.getDate())+" "+f2.format(mCrime.getDate()));
        return v;
    }
}
