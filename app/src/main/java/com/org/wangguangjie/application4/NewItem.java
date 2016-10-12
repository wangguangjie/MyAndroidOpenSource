package com.org.wangguangjie.application4;

/**
 * Created by wangguangjie on 16/9/28.
 */

public class NewItem {
    public String url;
    public String title;
    public String time;
    public NewItem(String title,String url,String time)
    {
        this.title=title;
        this.url=url;
        this.time=time;
    }
    public void setUrl(String url)
    {
        this.url=url;
    }
    public String getUrl()
    {
        return url;
    }
    public void setTitle(String title)
    {
        this.title=title;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTime(String t)
    {
        this.time=t;
    }
    public String getTime()
    {
        return time;
    }
}
