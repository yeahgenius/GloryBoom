package com.yeahgenius.hy.gloryboom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.yeahgenius.hy.gloryboom.R;
import com.yeahgenius.hy.gloryboom.adapter.FavPageAdapter;
import com.yeahgenius.hy.gloryboom.bean.FavPageBean;
import com.yeahgenius.hy.gloryboom.util.DbUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavPageActivity extends AppCompatActivity
{
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.lsv_fav_page_activity) DynamicListView listView;
    private String title;
    private List<FavPageBean> lists;
    private FavPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_page);
        ButterKnife.bind(this);
        DbUtils.createDb(this, "favpage");
        initToolbar();
        initListView();
    }

    private void initListView()
    {
        lists = new ArrayList<>();
        adapter = new FavPageAdapter(this, lists);
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(listView);
        listView.setAdapter(animationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(FavPageActivity.this, WebViewctivity.class);
                intent.putExtra("url", adapter.getItem(i).getUrl());
                intent.putExtra("title", adapter.getItem(i).getTitle());
                startActivity(intent);
                finish();
            }
        });
        listView.enableSwipeToDismiss(new OnDismissCallback() {
            @Override
            public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions)
            {
                for (final int position : reverseSortedPositions)
                {
                    new MaterialDialog.Builder(FavPageActivity.this)
                            .title("删除")
                            .content("您确定要删除此收藏么？")
                            .positiveText("确定")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                                {
                                    DbUtils.deleteWhere(FavPageBean.class, "title", new String[]{adapter.getItem(position).getTitle()});
                                    adapter.remove(position);
                                }
                            })
                            .negativeText("再想想")
                            .show();
                }
            }
        });
    }

    private void initToolbar()
    {
        title = "个人收藏";
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        refresh();
    }

    private void refresh()
    {
        new Thread(){
            @Override
            public void run()
            {
                List<FavPageBean> list = DbUtils.getQueryAll(FavPageBean.class);
                adapter.addAll(list, true);
            }
        }.start();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
