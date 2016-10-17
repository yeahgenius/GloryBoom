package com.yeahgenius.hy.gloryboom.callback;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yeahgenius.hy.gloryboom.bean.ZhuanLanBean;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;

public abstract class ZhuanlanCallback extends Callback<List<ZhuanLanBean>>
{
    @Override
    public List<ZhuanLanBean> parseNetworkResponse(Response response, int i) throws Exception
    {
        String string = response.body().string();
        Type collectionType = new TypeToken<List<ZhuanLanBean>>(){}.getType();
        List<ZhuanLanBean> zhuanLanBeen = (List<ZhuanLanBean>) new Gson().fromJson(string, collectionType);
        return zhuanLanBeen;
    }
}
