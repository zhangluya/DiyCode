package com.zly.diycode.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangluya on 2017/3/22.
 */

public class FooterAdapter extends BaseAdapter {

    private Item mFooter;

    private List<? extends Item> mRealDatas;

    public FooterAdapter(Context context, @NonNull Item footer) {
        super(context);
        mFooter = footer;
        mDataList = new ArrayList<>();
        mDataList.add(mFooter);
    }

    @Override
    public <T extends Item> void setDataList(List<T> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
        mDataList.add(mFooter);
        notifyDataSetChanged();
    }

    @Override
    public void add(Item data) {
        int position = mDataList.size() - 1;
        mDataList.add(position, data);
        notifyItemChanged(position);
    }

    @Override
    public <T extends Item> void addAll(List<T> datas) {
        int position = mDataList.size() - 1;
        mDataList.addAll(position, datas);
        notifyItemChanged(position);
    }
}
