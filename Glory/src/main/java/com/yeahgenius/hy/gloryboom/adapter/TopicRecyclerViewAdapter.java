package com.yeahgenius.hy.gloryboom.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yeahgenius.hy.gloryboom.R;
import com.yeahgenius.hy.gloryboom.bean.TopicBean;

import java.util.List;

public class TopicRecyclerViewAdapter extends BaseQuickAdapter<TopicBean.ResultBean>
{
    private Context context;

    public TopicRecyclerViewAdapter(Context context, int layoutResId, List<TopicBean.ResultBean> data)
    {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TopicBean.ResultBean resultBean)
    {
        baseViewHolder.setText(R.id.tv_topic_fragment_item_view_title, resultBean.getTitle());
        baseViewHolder.setText(R.id.tv_topic_fragment_item_view_follow, resultBean.getReplies_count() + "");
    }
}
