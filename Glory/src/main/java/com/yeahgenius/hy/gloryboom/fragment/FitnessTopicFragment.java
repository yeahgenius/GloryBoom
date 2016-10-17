package com.yeahgenius.hy.gloryboom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.yeahgenius.hy.gloryboom.adapter.TopicRecyclerViewAdapter;
import com.yeahgenius.hy.gloryboom.bean.TopicBean;
import com.yeahgenius.hy.gloryboom.callback.TopicCallback;
import com.yeahgenius.hy.gloryboom.constant.Constant;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class FitnessTopicFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.recyclerview) RecyclerView recyclerviewTopic;
    @BindView(R.id.swiperefreshlayout) SwipeRefreshLayout swiperefreshlayoutTopic;
    private TopicRecyclerViewAdapter adapter;
    private List<TopicBean.ResultBean> resultBeens;
    private LinearLayoutManager layoutManager;
    private int lastVisibleItemPosition;
    private String url = Constant.GUO_KE_URL;
    private int page = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 1:
                    swiperefreshlayoutTopic.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };

    public FitnessTopicFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_fitness_topic, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initSwipeLayout();
        return view;
    }

    private void initSwipeLayout()
    {
        swiperefreshlayoutTopic.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);
        swiperefreshlayoutTopic.setColorSchemeResources(R.color.colorTextPrimary);
        swiperefreshlayoutTopic.setProgressViewEndTarget(true, 100);
        swiperefreshlayoutTopic.setOnRefreshListener(this);
    }

    private void initRecyclerView()
    {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerviewTopic.setLayoutManager(layoutManager);
        recyclerviewTopic.setHasFixedSize(true);
        resultBeens = new ArrayList<>();
        adapter = new TopicRecyclerViewAdapter(getActivity(), R.layout.recyclerview_item_text_only, resultBeens);
        adapter.openLoadAnimation();
        recyclerviewTopic.setAdapter(adapter);
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i)
            {
                Intent intent = new Intent(getActivity(), WebViewctivity.class);
                intent.putExtra("url", adapter.getItem(i).getUrl());
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
        itemTouchHelper.attachToRecyclerView(recyclerviewTopic);

        // enable swipe items
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(onItemSwipeListener);
    }

    private void recyclerviewLoadMore()
    {
        recyclerviewTopic.addOnScrollListener(new RecyclerView.OnScrollListener(){
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
        OkHttpUtils.get().url(url).addParams("retrieve_type", "by_group").addParams("group_id", "228")
                .addParams(Constant.KEY_LIMIT, String.valueOf(Constant.DEFAULT_COUNT_TEXT_ONLY))
                .addParams(Constant.KEY_OFFSET, page * Constant.DEFAULT_COUNT_TEXT_ONLY + "").build()
                .execute(new TopicCallback(){
                    @Override
                    public void onError(Call call, Exception e, int i)
                    {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onResponse(TopicBean topicBean, int i)
                    {
                        adapter.notifyDataChangedAfterLoadMore(topicBean.getResult(), true);
                    }
                });
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    private void refresh()
    {
        OkHttpUtils.get().url(url).addParams("retrieve_type", "by_group").addParams("group_id", "228")
                .addParams(Constant.KEY_LIMIT, String.valueOf(Constant.DEFAULT_COUNT_TEXT_ONLY))
                .addParams(Constant.KEY_OFFSET, "0").build()
                .execute(new TopicCallback(){
                    @Override
                    public void onError(Call call, Exception e, int i)
                    {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onResponse(TopicBean topicBean, int i)
                    {
                        adapter.setNewData(topicBean.getResult());
                    }
                });
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
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
