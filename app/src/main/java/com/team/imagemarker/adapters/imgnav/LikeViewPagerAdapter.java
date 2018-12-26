package com.team.imagemarker.adapters.imgnav;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Lmy on 2017/5/7.
 * email 1434117404@qq.com
 */

public class LikeViewPagerAdapter extends PagerAdapter {
    private List<View> viewList;

    public LikeViewPagerAdapter(List<View> viewList){
        this.viewList = viewList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public int getCount() {
        if(viewList == null){
            return 0;
        }
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
