package com.team.imagemarker.adapters.imgscan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.bases.OnItemActionListener;
import com.team.imagemarker.entitys.imgscan.BrowsePictuerModel;

import java.util.List;

/**
 * Created by Lmy on 2017/5/29.
 * email 1434117404@qq.com
 */

public class DayRecommendAdapter extends RecyclerView.Adapter<DayRecommendAdapter.ViewHolder> {
    private Context context;
    private List<List<BrowsePictuerModel>> list;

    private OnItemActionListener onItemActionListener;

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public DayRecommendAdapter(Context context, List<List<BrowsePictuerModel>> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.item_day_recommend, null);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BrowsePictuerModel model = list.get(position).get(0);
        String[] lables = model.getLabel();
        String detailLables = "";
        for (int i = 0; i < lables.length; i++) {
            detailLables += lables[i] + "、";
        }
        holder.imgTag.setText(detailLables.subSequence(0, detailLables.length() - 1));
        Glide.with(context)
                .load(list.get(position).get(0).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.showImg);
        if(onItemActionListener != null){
            holder.showImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemActionListener.OnItemClickListener(view, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView showImg;
        public TextView imgTag;

        public ViewHolder(View view) {
            super(view);
            imgTag = (TextView) view.findViewById(R.id.img_tag);
            showImg = (ImageView) view.findViewById(R.id.show_image);
        }
    }

//    public interface
}
