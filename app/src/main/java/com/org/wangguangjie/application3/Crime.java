package com.org.wangguangjie.application3;

import java.util.Date;
import java.util.UUID;

/**
 * Created by wangguangjie on 2017/6/22.
 */

public class Crime {

    private String mTitle;
    private UUID mId;
    private Date mDate;
    private boolean mSolved;

    public Crime()
    {
        mId= UUID.randomUUID();
        mDate=new Date();
    }
    public void setDate(Date date) {
        mDate = date;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Date getDate() {
        return mDate;
    }
    public boolean isSolved() {
        return mSolved;
    }

    public String getTitle() {
        return mTitle;
    }

    public UUID getId() {
        return mId;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setId(UUID id) {
        mId = id;
    }
}
