package com.yeahgenius.hy.gloryboom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.yeahgenius.hy.gloryboom.R;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity
{
    protected Toolbar toolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
        initToolbar();
    }

    protected void initToolbar()
    {
        this.toolbar = ButterKnife.findById(this, R.id.toolbar);
        if (toolbar != null)
        {
            this.setSupportActionBar(toolbar);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    //写一些界面跳转的方法
    //简单的界面跳转，不需要利用Intent传递参数
    public void jumpTo(Class<?> clazz,boolean isFinish)
    {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
        if(isFinish)
        {
            finish();
        }
    }

    //界面跳转时，需要Intent携带参数
    public void jumpTo(Intent intent,boolean isFinish)
    {
        startActivity(intent);
        if(isFinish)
        {
            finish();
        }
    }
}
