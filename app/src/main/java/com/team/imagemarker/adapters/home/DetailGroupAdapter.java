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
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.entitys.MarkerModel;
import com.team.imagemarker.utils.RoundAngleImageView;

import java.util.List;

/**
 * Created by Lmy on 2017/8/28.
 * email 1434117404@qq.com
 */

public class DetailGroupAdapter extends BaseAdapter {
    private Context context;
    private List<MarkerModel> list;

    public DetailGroupAdapter(Context context, List<MarkerModel> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_detail_group, null);
            holder.title = (TextView) view.findViewById(R.id.detail_group_title);
            holder.showImg = (RoundAngleImageView) view.findViewById(R.id.detail_group_show_img);
            holder.tag = (ImageView) view.findViewById(R.id.detail_group_tag);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        MarkerModel model = list.get(i);
        Glide.with(context)
                .load(model.getImageUrl1())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.8f)
                .into(holder.showImg);
        if(model.getSecondlabelName().equals("")){
            holder.title.setText("这是标题");
        }else{
            holder.title.setText(model.getSecondlabelName());
        }

        holder.tag.setColorFilter(Constants.tagColors[(int)(Math.random() * 7 + 1) % Constants.tagColors.length]);

        return view;
    }

    class ViewHolder{
        public TextView title;
        public RoundAngleImageView showImg;
        public ImageView tag;
    }
}
