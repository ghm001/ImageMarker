package com.team.imagemarker.adapters.saying;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.utils.RoundAngleImageView;

import java.util.List;

/**
 * Created by Lmy on 2017/4/30.
 * email 1434117404@qq.com
 */

public class SayingGradAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
//    private ImageLoader mImageLoader; // imageLoader对象，用来初始化NetworkImageView

    public SayingGradAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
//        mImageLoader = new ImageLoader(MyApplication.requestQueue, new VolleyBitmapCache()); // 初始化一个loader对象，可以进行自定义配置
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.saying_grid_img, parent, false);
            viewHolder.sayingImg = (RoundAngleImageView) convertView.findViewById(R.id.saying_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(parent.getChildCount() == position){
            String urlImg = list.get(position);
//        NetworkImageView networkImageView = (NetworkImageView) viewHolder.sayingImg;
//        networkImageView.setDefaultImageResId(R.drawable.ic_default_image);
//        networkImageView.setErrorImageResId(R.drawable.ic_default_image);
//        if(urlImg != null && !urlImg.equals("")){
//            networkImageView.setImageUrl(urlImg, mImageLoader);
//        }

            Glide.with(context)
                    .load(urlImg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.sayingImg);
        }else{

        }


        return convertView;
    }

    class ViewHolder{
        RoundAngleImageView sayingImg;
    }
}