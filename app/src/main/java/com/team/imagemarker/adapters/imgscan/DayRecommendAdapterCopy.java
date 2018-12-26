package com.team.imagemarker.adapters.imgscan;

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
import com.team.imagemarker.entitys.imgscan.DayRecommendModel;

import java.util.List;

/**
 * Created by Lmy on 2017/5/29.
 * email 1434117404@qq.com
 */

public class DayRecommendAdapterCopy extends BaseAdapter {
    private Context context;
    private List<DayRecommendModel> list;

    public DayRecommendAdapterCopy(Context context, List<DayRecommendModel> list){
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_day_recommend, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imgTag.setText(list.get(position).getImgTag());
        Glide.with(context)
                .load(list.get(position).getBgUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.showImg);
        return convertView;
    }

    class ViewHolder{
        public ImageView showImg;
        public TextView imgTag;

        public ViewHolder(View view) {
            imgTag = (TextView) view.findViewById(R.id.img_tag);
            showImg = (ImageView) view.findViewById(R.id.show_image);
        }
    }
}
