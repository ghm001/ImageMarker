package com.team.imagemarker.adapters.mark;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Lmy on 2017/5/14.
 * email 1434117404@qq.com
 */

public class ImgShowAdapter extends PagerAdapter {
    private List<String> list;
    private Context context;

    public ImgShowAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(context);
        Glide.with(context)
                .load(list.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}