package com.team.imagemarker.adapters.home;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.home.CategoryModel;

import java.util.List;

/**
 * Created by Lmy on 2017/5/1.
 * email 1434117404@qq.com
 */

public class HobbyPushAdapter extends RecyclerView.Adapter<HobbyPushAdapter.HobbyViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private List<CategoryModel> list;

    public HobbyPushAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public HobbyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_category_two, parent, false);
        HobbyViewHolder viewHolder = new HobbyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HobbyViewHolder holder, int position) {
        holder.categoryImg.setImageResource(list.get(position).getImgId());
        holder.categoryName.setText(list.get(position).getName());
        holder.categorySimpleMessage.setText(list.get(position).getSimpleMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HobbyViewHolder extends RecyclerView.ViewHolder {
        public ImageView categoryImg;
        public TextView categoryName;
        public TextView categorySimpleMessage;

        public HobbyViewHolder(View item) {
            super(item);
            categoryImg = (ImageView) item.findViewById(R.id.category_img);
            categoryName = (TextView) item.findViewById(R.id.category_name);
            categorySimpleMessage = (TextView) item.findViewById(R.id.category_simple_message);
        }
    }
}


