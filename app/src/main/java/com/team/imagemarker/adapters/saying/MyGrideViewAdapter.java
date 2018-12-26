package com.team.imagemarker.adapters.saying;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;

import java.util.List;

/**
 * Created by Lmy on 2017/9/3.
 * email 1434117404@qq.com
 */

public class MyGrideViewAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public MyGrideViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_grid_adapter, viewGroup, false);
            holder.imageView = (ImageView) view.findViewById(R.id.user_img);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        Glide.with(context)
                .load(list.get(i))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        return view;
    }

    class ViewHolder{
        public ImageView imageView;
    }
}
