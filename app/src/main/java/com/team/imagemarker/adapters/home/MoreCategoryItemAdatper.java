package com.team.imagemarker.adapters.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.entitys.home.CateGoryInfo;
import com.team.imagemarker.utils.RoundAngleImageView;

import java.util.List;

/**
 * Created by Lmy on 2017/5/14.
 * email 1434117404@qq.com
 */

public class MoreCategoryItemAdatper extends RecyclerView.Adapter<MoreCategoryItemAdatper.ViewHolder> {
    private Context context;
    private List<CateGoryInfo> list;

    public MoreCategoryItemAdatper(Context context, List<CateGoryInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_simple_select_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getImgUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgView);
        holder.textView.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RoundAngleImageView imgView;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imgView = (RoundAngleImageView) itemView.findViewById(R.id.sdv_cart_image);
            textView = (TextView) itemView.findViewById(R.id.title_name);
        }
    }
}