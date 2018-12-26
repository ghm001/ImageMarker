package com.team.imagemarker.adapters.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.entitys.home.SelectCategoryModel;
import com.team.imagemarker.utils.RoundAngleImageView;

import java.util.List;

/**
 * Created by Lmy on 2017/5/14.
 * email 1434117404@qq.com
 */

public class MoreCateGoryAdaper extends RecyclerView.Adapter<MoreCateGoryAdaper.ViewHolder>{
    private Context context;
    private List<SelectCategoryModel> list;

    public MoreCateGoryAdaper(Context context, List<SelectCategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_more_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getBackgroundUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.itemBackground);
        holder.itemName.setText(list.get(position).getCategoryTitle());
        RecyclerView recyclerView = holder.itemRecycleView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//设置水平显示
        recyclerView.setLayoutManager(layoutManager);

        MoreCategoryItemAdatper itemAdatper = new MoreCategoryItemAdatper(context, list.get(position).getCateGoryInfos());
        recyclerView.setAdapter(itemAdatper);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RoundAngleImageView itemBackground;
        private TextView itemName;
        private RecyclerView itemRecycleView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemBackground = (RoundAngleImageView) itemView.findViewById(R.id.item_background);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemRecycleView = (RecyclerView) itemView.findViewById(R.id.more_item_recycleview);
        }
    }
}