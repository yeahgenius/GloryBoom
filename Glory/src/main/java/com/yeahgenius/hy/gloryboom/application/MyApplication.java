package com.yeahgenius.hy.gloryboom.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.datatype.BmobGeoPoint;
import okhttp3.OkHttpClient;

public class MyApplication extends Application
{
    private static MyApplication context;
    public static BmobGeoPoint lastPoint;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();

        OkHttpUtils.initClient(okHttpClient);
        if (getApplicationInfo().packageName.equals(getMyProcessName()))
        {
            //im初始化
            BmobIM.init(this);
        }
    }
    public static MyApplication getInstance()
    {
        return context;
    }

    public static Typeface loadFont(Context context)
    {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/SverigeScriptDecorated_Demo.ttf");
        return font;
    }

    /**
     * 获取当前运行的进程名
     * @return
     */
    public static String getMyProcessName()
    {
        try
        {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
