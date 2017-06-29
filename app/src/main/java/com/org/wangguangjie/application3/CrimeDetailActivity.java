package com.org.wangguangjie.application3;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by wangguangjie on 2017/6/23.
 */

public class CrimeDetailActivity extends SingleFragmentActivity{


    final static String id="crime_id";
    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
