package com.team.imagemarker.adapters.imgscan;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.team.imagemarker.R;
import com.team.imagemarker.bases.OnItemActionListener;
import com.team.imagemarker.entitys.imgscan.BrowsePictuerModel;

import java.util.List;

/**
 * 图组选择界面适配器
 * Created by Lmy on 2017/4/15.
 * email 1434117404@qq.com
 */

public class PictureGroupAdapter extends RecyclerView.Adapter<PictureGroupAdapter.PictureViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private List<List<BrowsePictuerModel>> list;

    private OnItemActionListener onItemActionListener;

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public PictureGroupAdapter(Context context, List<List<BrowsePictuerModel>> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_picture_group, parent, false);
        PictureViewHolder viewHolder = new PictureViewHolder(view);
//        viewHolder.setIsRecyclable(true);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PictureViewHolder viewHolder, int position) {
        BrowsePictuerModel model = list.get(position).get(0);
        String[] lables = model.getLabel();
        String detailLables = "";
        for (int i = 0; i < lables.length; i++) {
            detailLables += lables[i] + "、";
        }
        viewHolder.imgTitle.setText(detailLables.subSequence(0, detailLables.length() - 1));
//        viewHolder.imgTitle.setText(list.get(position).get(0).getLabel());

//        int width = ((Activity)viewHolder.firstImg.getContext()).getWindowManager().getDefaultDisplay().getWidth();
//        ViewGroup.LayoutParams params = viewHolder.firstImg.getLayoutParams();
//        params.width = width / 3;
//        params.height = (int)(200 + Math.random() * 400);
//        viewHolder.firstImg.setLayoutParams(params);

        Glide.with(context)
                .load(model.getImageUrl())
                .asBitmap()
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(viewHolder.firstImg) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette p) {
                                int vibrant = p.getLightVibrantColor(0x000000);
                                viewHolder.detailMessage.setBackgroundColor(vibrant);
                                viewHolder.detailMessage.setAlpha((float) 0.8);
//                                viewHolder.imgTitle.setAlpha((float) 0.8);
                            }
                        });
                    }
                });

        if (onItemActionListener != null) {
            viewHolder.firstImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里使用viewHolder.getPosition()是为了保证动画没有使用NotifyDatasetChanged更新位置数据
                    onItemActionListener.OnItemClickListener(v, viewHolder.getPosition());//暴露接口供外部调用
                }
            });
        }
    }

    /**
     * 设置监听事件
     */
//    public interface OnItemActionListener{
//        /**
//         * 为图组设置监听事件
//         * @param view 操作的视图
//         * @param position 数据的位置
//         */
//        public void OnItemClickListener(View view, int position);
//    }


    class PictureViewHolder extends RecyclerView.ViewHolder {
        public TextView imgTitle;
        public ImageView firstImg;
        public RelativeLayout detailMessage;

        public PictureViewHolder(View item) {
            super(item);
            imgTitle = (TextView) item.findViewById(R.id.picture_group_title);
            firstImg = (ImageView) item.findViewById(R.id.item_image);
            detailMessage = (RelativeLayout) item.findViewById(R.id.detail_message);
        }
    }
}