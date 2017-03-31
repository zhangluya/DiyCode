package com.zly.diycode.main;

import com.zly.diycode.common.Navigation;
import com.zly.diycode.data.topics.TopicsRemoteData;
import com.zly.diycode.main.base.BaseListFragment;
import com.zly.diycode.main.base.BaseListPresenter;
import com.zly.diycode.topics.EntitiesContract;

/**
 * Created by zhangluya on 2017/3/31.
 */

public class TopicsFragment extends BaseListFragment {

    @Override
    protected BaseListPresenter createPresenter() {
        return new BaseListPresenter<>(TopicsRemoteData.getInstance(), this);
    }

    @Override
    public void onItemClick(EntitiesContract.Topics topics, int position) {
        Navigation.getInstance().openDetails(getActivity(), topics.getId());
    }
}
