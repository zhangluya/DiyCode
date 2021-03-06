package com.zly.diycode.list;

import com.zly.diycode.common.feature.IView;

import java.util.List;

/**
 * Created by zhangluya on 2017/3/31.
 */

public interface BaseListView<T> extends IView {

    void show(List<T> datas);

    void add(List<T> datas);

    void showEmptyView();

    void loadMoreComplete();

}
