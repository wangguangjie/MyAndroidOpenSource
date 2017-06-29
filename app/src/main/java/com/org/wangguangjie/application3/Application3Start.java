package com.org.wangguangjie.application3;


import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by wangguangjie on 16/9/14.
 */
public class Application3Start extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
