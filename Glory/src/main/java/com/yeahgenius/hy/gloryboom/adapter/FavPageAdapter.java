package com.yeahgenius.hy.gloryboom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yeahgenius.hy.gloryboom.R;
import com.yeahgenius.hy.gloryboom.bean.FavPageBean;

import java.util.List;

public class FavPageAdapter extends BaseAdapter
{
    private Context context;
    private List<FavPageBean> dataSource;
    LayoutInflater inflater;

    public FavPageAdapter(Context context, List<FavPageBean> dataSource)
    {
        super();
        this.context = context;
        this.dataSource = dataSource;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return dataSource.size();
    }

    @Override
    public FavPageBean getItem(int position)
    {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder vh;
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
            vh = new ViewHolder();
            vh.tvTitle = (TextView) convertView.findViewById(R.id.tv_listview_title);
            vh.tvUrl = (TextView) convertView.findViewById(R.id.tv_listview_url);
            convertView.setTag(vh);
        }
        else
        {
            vh = (ViewHolder) convertView.getTag();
        }
        FavPageBean bean = getItem(position);
        vh.tvTitle.setText(bean.getTitle());
        vh.tvUrl.setText(bean.getUrl());
        return convertView;
    }

    public void addAll(List<FavPageBean> list, boolean flag)
    {
        if(flag)
        {
            dataSource.clear();
        }
        dataSource.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position)
    {
        dataSource.remove(position);
        notifyDataSetChanged();
    }

    class ViewHolder
    {
        TextView tvTitle, tvUrl;
    }
}
