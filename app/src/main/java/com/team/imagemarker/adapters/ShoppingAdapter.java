package com.team.imagemarker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.utils.CircleImageView;

import java.util.List;

/**
 * Created by Lmy on 2017/6/1.
 * email 1434117404@qq.com
 */

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    private Context context;
    private List<CategoryModel> list;
    private ItemClickListener itemClickListener;

    public ShoppingAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopping_select, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
//        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        Glide.with(context)
//                .load(list.get(position).getImgUrl())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.shoppingBg);
        holder.shoppingBg.setImageResource(list.get(position).getImgId());
        holder.headImg.setImageResource(R.drawable.head);
        holder.describe.setText(list.get(position).getName());
        holder.scanCount.setText(list.get(position).getSimpleMessage());
        holder.shoppingBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener != null){
                    itemClickListener.onItemClick(holder.scanCount);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
//
//    @Override
//    public void onClick(View v) {
//        if(itemClickListener != null){
//            itemClickListener.onItemClick(v);
//        }
//    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView shoppingBg;
        public CircleImageView headImg;
        public TextView scanCount;
        public TextView describe;

        public ViewHolder(View itemView) {
            super(itemView);
            shoppingBg = (ImageView) itemView.findViewById(R.id.shopping_bg);
            headImg = (CircleImageView) itemView.findViewById(R.id.headImg);
            scanCount = (TextView) itemView.findViewById(R.id.scanCount);
            describe = (TextView) itemView.findViewById(R.id.describe);

        }
    }

    public interface ItemClickListener{
        void onItemClick(View view);
    }

    public void setOnItemClickListner(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

}
