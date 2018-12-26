package com.team.imagemarker.adapters.task;

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
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.utils.CircleImageView;
import com.wangjie.rapidfloatingactionbutton.textlabel.LabelView;

import java.util.List;

/**
 * Created by Lmy on 2017/5/1.
 * email 1434117404@qq.com
 */

public class TaskBoxAdapter extends BaseAdapter {
    private Context context;
    private List<UserModel> list;

    public TaskBoxAdapter(Context context, List<UserModel> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task_box, null);
            viewHolder.categoryImg = (ImageView) convertView.findViewById(R.id.category_img);
            viewHolder.userHead = (CircleImageView) convertView.findViewById(R.id.user_head);
            viewHolder.userRanking = (LabelView) convertView.findViewById(R.id.user_ranking);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.userIntegral = (TextView) convertView.findViewById(R.id.integral);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UserModel model = list.get(position);

        if(position == 0){
            viewHolder.categoryImg.setImageResource(R.mipmap.task2);
            viewHolder.userRanking.setText("第一名");
        }else if(position == 1){
            viewHolder.categoryImg.setImageResource(R.mipmap.task1);
            viewHolder.userRanking.setText("第二名");
        }else if(position == 2){
            viewHolder.categoryImg.setImageResource(R.mipmap.task3);
            viewHolder.userRanking.setText("第三名");
        }

        Glide.with(context)
                .load(model.getUserHeadImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.userHead);
        viewHolder.userName.setText(model.getUserNickName());
        viewHolder.userIntegral.setText("积分:" + model.getIntegral());
        return convertView;
    }

    class ViewHolder{
        public ImageView categoryImg;
        public CircleImageView userHead;
        public LabelView userRanking;
        public TextView userName;
        public TextView userIntegral;
    }
}
