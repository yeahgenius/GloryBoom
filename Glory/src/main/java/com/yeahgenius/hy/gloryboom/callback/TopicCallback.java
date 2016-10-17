package com.yeahgenius.hy.gloryboom.callback;

import com.google.gson.Gson;
import com.yeahgenius.hy.gloryboom.bean.TopicBean;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

public abstract class TopicCallback extends Callback<TopicBean>
{
    @Override
    public TopicBean parseNetworkResponse(Response response, int i) throws Exception
    {
        String string = response.body().string();
        TopicBean bean = new Gson().fromJson(string, TopicBean.class);
        return bean;
    }
}
