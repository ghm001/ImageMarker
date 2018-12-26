package com.team.imagemarker.utils.imageloder;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegrid.NineGridView;
import com.team.imagemarker.R;

/**
 * Created by Lmy on 2017/6/13.
 * email 1434117404@qq.com
 */

public class NineGridViewLoader implements NineGridView.ImageLoader {
    /**
     * 九宫格图片加载
     */
    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_default_image)
                    .error(R.mipmap.default_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.2f)
                    .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
