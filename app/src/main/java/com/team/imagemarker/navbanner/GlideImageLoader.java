package com.team.imagemarker.navbanner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * Created by Lmy on 2017/4/28.
 * email 1434117404@qq.com
 */

public class GlideImageLoader implements BannerLayout.ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).centerCrop().into(imageView);
    }
}
