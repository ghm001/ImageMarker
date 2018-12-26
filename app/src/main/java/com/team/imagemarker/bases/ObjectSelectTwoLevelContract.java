package com.team.imagemarker.bases;

/**
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public interface ObjectSelectTwoLevelContract {
    interface View
    {
        void setPresenter(Presenter presenter);
        void showSnackBar();
        void showCity();
    }
    interface Presenter {
        void start();
    }
}
