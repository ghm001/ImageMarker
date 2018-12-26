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
import com.team.imagemarker.entitys.MarkerModel;
import com.team.imagemarker.utils.CircleImageView;

import java.util.List;

/**
 * Created by Lmy on 2017/5/1.
 * email 1434117404@qq.com
 */

public class SystemPushAdapter extends BaseAdapter {
    private Context context;
    private List<MarkerModel> list;

    public SystemPushAdapter(Context context, List<MarkerModel> list) {
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
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, null);
            viewHolder.categoryImg = (ImageView) convertView.findViewById(R.id.category_img);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_name);
            viewHolder.categorySimapleMessage = (TextView) convertView.findViewById(R.id.category_simple_message);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MarkerModel model = list.get(position);
        try {
            Glide.with(context)
                    .load(model.getImageUrl1())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .thumbnail(0.8f)
                    .into(viewHolder.categoryImg);
//            new String( model.getImageUrl1().getBytes("ISO-8859-1"),"UTF-8")
        } catch (Exception e) {
            e.printStackTrace();
        }
//        viewHolder.categoryImg.setImageResource(model.getImageUrl1());
//        viewHolder.categoryName.setText("这是标题");
//        viewHolder.categorySimapleMessage.setText("这是简要信息");
        return convertView;
    }

    class ViewHolder{
        public ImageView categoryImg;
        public TextView categoryName;
        public TextView categorySimapleMessage;
        public CircleImageView managerHead;
    }
}