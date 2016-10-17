package com.yeahgenius.hy.gloryboom.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yeahgenius.hy.gloryboom.bean.ZhuanLanBean;

public class MultipleItem extends MultiItemEntity
{
    public static final int IMAGE = 1;
    public static final int TEXT = 2;

    private ZhuanLanBean zhuanLanBean;

    public ZhuanLanBean getZhuanLanBean()
    {
        return zhuanLanBean;
    }

    public void setZhuanLanBean(ZhuanLanBean zhuanLanBean)
    {
        this.zhuanLanBean = zhuanLanBean;
    }
}
