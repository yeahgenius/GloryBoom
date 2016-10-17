package com.yeahgenius.hy.gloryboom.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.yeahgenius.hy.gloryboom.R;
import com.yeahgenius.hy.gloryboom.bean.ZhuanLanBean;

import java.util.List;

public class ZhuanlanRecyclerViewAdapter extends BaseQuickAdapter<ZhuanLanBean>
{
    private Context context;

    public ZhuanlanRecyclerViewAdapter(Context context, int layoutResId, List<ZhuanLanBean> data)
    {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ZhuanLanBean zhuanLanBean)
    {
        baseViewHolder.setText(R.id.tv_relax_fragment_item_view_title, zhuanLanBean.getTitle());
        if (TextUtils.isEmpty(zhuanLanBean.getTitleImage()))
        {
            Picasso.with(context).load(R.mipmap.default_profile_image).resize(100, 100)
                    .centerCrop()
                    .into((ImageView) baseViewHolder.convertView.findViewById(R.id.iv_relax_fragment_item_view_image));
        }
        else
        {
            Picasso.with(context).load(zhuanLanBean.getTitleImage()).resize(100, 100)
                    .centerCrop()
                    .into((ImageView) baseViewHolder.convertView.findViewById(R.id.iv_relax_fragment_item_view_image));
        }
    }
}
