package com.yeahgenius.hy.gloryboom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.orhanobut.logger.Logger;
import com.yeahgenius.hy.gloryboom.R;
import com.yeahgenius.hy.gloryboom.activity.WebViewctivity;
import com.yeahgenius.hy.gloryboom.adapter.ZhuanlanRecyclerViewAdapter;
import com.yeahgenius.hy.gloryboom.bean.ZhuanLanBean;
import com.yeahgenius.hy.gloryboom.callback.ZhuanlanCallback;
import com.yeahgenius.hy.gloryboom.constant.Constant;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class SelfFitnessFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout) SwipeRefreshLayout swiperefreshlayout;
    private ZhuanlanRecyclerViewAdapter adapter;
    private List<ZhuanLanBean> lists;
    private GridLayoutManager layoutManager;
    private int lastVisibleItemPosition;
    private String url = Constant.SELF_FITNESS;
    private int page = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    swiperefreshlayout.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };

    public SelfFitnessFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_lose_weight_and_gain_muscle, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initSwipeLayout();
        return view;
    }

    private void initRecyclerView()
    {
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setHasFixedSize(true);
        lists = new ArrayList<>();
        adapter = new ZhuanlanRecyclerViewAdapter(getActivity(), R.layout.recyclerview_item_with_image_relax_fragment, lists);
        adapter.openLoadAnimation();
        recyclerview.setAdapter(adapter);
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i)
            {
                Intent intent = new Intent(getActivity(), WebViewctivity.class);
                intent.putExtra("href", adapter.getItem(i).getUrl());
                intent.putExtra("title", adapter.getItem(i).getTitle());
                startActivity(intent);
            }
        });
        refresh();
        recyclerviewLoadMore();
        dragAndSwipe();
    }

    private void dragAndSwipe()
    {
        OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {}
            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {}
            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {}
        };

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerview);

        // enable swipe items
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(onItemSwipeListener);
    }

    private void recyclerviewLoadMore()
    {
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == adapter.getItemCount())
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run()
                        {
                            addMore();
                            handler.sendEmptyMessage(1);
                        }
                    }).start();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void addMore()
    {
        page++;
        OkHttpUtils.get().url(url)
                .addParams(Constant.KEY_LIMIT, String.valueOf(Constant.DEFAULT_COUNT))
                .addParams(Constant.KEY_OFFSET, page * Constant.DEFAULT_COUNT + "").build()
                .execute(new ZhuanlanCallback(){
                    @Override
                    public void onError(Call call, Exception e, int i)
                    {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onResponse(List<ZhuanLanBean> zhuanLanBeen, int i)
                    {
                        adapter.notifyDataChangedAfterLoadMore(zhuanLanBeen, true);
                    }
                });
    }

    private void refresh()
    {
        OkHttpUtils.get().url(url)
                .addParams(Constant.KEY_LIMIT, String.valueOf(Constant.DEFAULT_COUNT))
                .addParams(Constant.KEY_OFFSET, "0").build()
                .execute(new ZhuanlanCallback(){
                    @Override
                    public void onError(Call call, Exception e, int i)
                    {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onResponse(List<ZhuanLanBean> zhuanLanBeen, int i)
                    {
                        adapter.setNewData(zhuanLanBeen);
                    }
                });
    }

    private void initSwipeLayout()
    {
        swiperefreshlayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        swiperefreshlayout.setColorSchemeResources(R.color.colorTextPrimary);
        swiperefreshlayout.setProgressViewEndTarget(true, 100);
        swiperefreshlayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh()
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                refresh();
                page = 0;
                handler.sendEmptyMessage(1);
            }
        }).start();
    }
}
