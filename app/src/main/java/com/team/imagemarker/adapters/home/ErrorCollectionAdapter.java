package com.team.imagemarker.adapters.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.bases.OnItemActionListener;
import com.team.imagemarker.entitys.home.appAbnormalChangeModel;

import java.util.List;

/**
 * Created by Lmy on 2017/8/31.
 * email 1434117404@qq.com
 */

public class ErrorCollectionAdapter extends BaseAdapter {
    private Context context;
    private List<appAbnormalChangeModel> list;

    private OnItemActionListener onItemActionListener;

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public ErrorCollectionAdapter(Context context, List<appAbnormalChangeModel> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_error_collection, viewGroup, false);
            holder.errorImg = (ImageView) view.findViewById(R.id.show_error_img);
            holder.errorText = (TextView) view.findViewById(R.id.show_error_tag);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        appAbnormalChangeModel model = list.get(i);

        Glide.with(context)
                .load(model.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.errorImg);

        String label = "";
        for (int j = 0; j < model.getLabels().length; j++) {
            label += model.getLabels()[j] + "、";
        }

        holder.errorText.setText("原始标签为：" + label.subSequence(0, label.length() - 1));

        holder.errorImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemActionListener.OnItemClickListener(view, i);
            }
        });

        return view;
    }

    class ViewHolder{
        public ImageView errorImg;
        public TextView errorText;
    }
}
