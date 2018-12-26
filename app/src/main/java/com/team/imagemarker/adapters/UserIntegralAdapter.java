package com.team.imagemarker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.utils.CircleImageView;

import java.util.List;


/**
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class UserIntegralAdapter extends BaseAdapter {
    private Context context;
    private List<UserModel> list;

    public UserIntegralAdapter(Context context, List<UserModel> list) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_integral_list, null);
            viewHolder.headImageView = (CircleImageView) convertView.findViewById(R.id.integral_user_header);
            viewHolder.userNickName = (TextView) convertView.findViewById(R.id.integral_user_nick_name);
//            viewHolder.userIntegralFlag = (ImageView) convertView.findViewById(R.id.user_integral_flag);
            viewHolder.rank = (TextView) convertView.findViewById(R.id.user_integral_flag);
            viewHolder.userIntegralItem = (RelativeLayout) convertView.findViewById(R.id.user_integral_item_layout);
            viewHolder.userIntegral = (TextView) convertView.findViewById(R.id.integral_user_count);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        UserModel model = list.get(position);
//        if(position == 0){
//            viewHolder.userIntegralFlag.setImageResource(R.mipmap.number_one);
//            Log.d("UserIntegralAdapter", "第一名");
//        }else if(position == 1){
//            viewHolder.userIntegralFlag.setImageResource(R.mipmap.number_two);
//            Log.d("UserIntegralAdapter", "第二名");
//        }else if(position == 2){
//            viewHolder.userIntegralFlag.setImageResource(R.mipmap.number_three);
//            Log.d("UserIntegralAdapter", "第三名");
//        }else{
//            viewHolder.userIntegralFlag.setVisibility(View.GONE);
//            Log.d("UserIntegralAdapter", "其余名次");
//        }

//        Glide.with(context)
//                .load(model.getUserHeadUrl())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(viewHolder.headImageView);

        Glide.with(context)
                .load(model.getUserHeadImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.headImageView);
//        ImageLoader imageLoader = new ImageLoader.Builder().url(model.getUserHeadUrl()).imgView(viewHolder.headImageView).build();
//        ImageLoaderUtil.getInstance().loadImage(context, imageLoader);
        viewHolder.rank.setText((position + 1) + "");
        viewHolder.userNickName.setText(model.getUserNickName());
        viewHolder.userIntegral.setText(model.getIntegral() + "积分");

        return convertView;
    }

    class ViewHolder{
        private CircleImageView headImageView;
        private TextView userNickName;
        private ImageView userIntegralFlag;
        private TextView userIntegral;
        private RelativeLayout userIntegralItem;
        private TextView rank;
    }

}
