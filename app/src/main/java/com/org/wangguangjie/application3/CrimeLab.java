package com.org.wangguangjie.application3;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by wangguangjie on 2017/6/22.
 */

public class CrimeLab {
    public static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
    public static CrimeLab get(Context context)
    {
        if(sCrimeLab==null)
        {
            sCrimeLab=new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context)
    {
        mCrimes=new ArrayList<>();
        for(int i=0;i<100;i++)
        {
            Crime crime=new Crime();
            crime.setTitle("Crime #"+i);
            Random r=new Random();
            crime.setSolved(r.nextBoolean());
            mCrimes.add(crime);
        }
    }
    public Crime getCrime(UUID id)
    {
        for(int i=0;i<mCrimes.size();i++)
        {
            if(mCrimes.get(i).getId().equals(id))
                return mCrimes.get(i);
        }
        return null;
    }
    public List<Crime> getCrimes()
    {
        return mCrimes;
    }
}
