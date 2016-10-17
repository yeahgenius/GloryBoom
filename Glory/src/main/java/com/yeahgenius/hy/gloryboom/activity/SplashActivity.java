package com.yeahgenius.hy.gloryboom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hanks.htextview.HTextView;
import com.yeahgenius.hy.gloryboom.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity
{
    @BindView(R.id.htv_splash_slogan_text) HTextView sloganHTextView;
    @BindView(R.id.timer_splashActivity) Button timerSplashActivity;

    private String[] resource = new String[]{"Movement is the ", "source of all life.", "- Leonardo da Vinci", "天霸动霸Tua"};
    private int index = -1;
    private Timer timer;
    private Handler handler;
    private int time = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        reload();
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                switch (msg.what) {
                    case 1:
                        index = next(); //取得下标值
                        updateText();   //更新HtextView显示内容
                        break;
                }
            }
        };
    }

    private int next()
    {
        int flag = index + 1;
        if (flag > resource.length - 1) {
            timer.cancel();
            sloganHTextView.setVisibility(View.GONE);
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            flag = flag - resource.length;
        }
        return flag;
    }

    private void updateText()
    {
        sloganHTextView.animateText(resource[index]);
        timerSplashActivity.setText("跳过 " + time);
        time--;
    }

    private void reload()
    {
        if (timer == null) {
            timer = new Timer();
        }
        timer.scheduleAtFixedRate(new MyTask(), 10, 1000);
    }

    @OnClick(R.id.timer_splashActivity)
    public void onClick()
    {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        timer.cancel();
    }

    private class MyTask extends TimerTask
    {
        @Override
        public void run()
        {
            handler.sendEmptyMessage(1);
        }
    }
}
