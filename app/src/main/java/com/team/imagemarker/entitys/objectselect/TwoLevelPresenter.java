package com.team.imagemarker.entitys.objectselect;


import com.team.imagemarker.bases.ObjectSelectTwoLevelContract;

/**
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public class TwoLevelPresenter implements ObjectSelectTwoLevelContract.Presenter {
    private ObjectSelectTwoLevelContract.View cityView;

    public TwoLevelPresenter(ObjectSelectTwoLevelContract.View cityview) {
        this.cityView = cityview;
        cityView.setPresenter(this);
    }

    @Override
    public void start() {
        showCity();
    }

    private void showCity() {
        cityView.showCity();
    }

}
