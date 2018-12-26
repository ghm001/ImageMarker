package com.team.imagemarker.adapters;


import android.support.v7.widget.CardView;

/**
 * Created by Lmy on 2017/4/7.
 * email 1434117404@qq.com
 */

public interface CardAdapter {
    int MAX_ELEVATION_FACTOR = 1;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
