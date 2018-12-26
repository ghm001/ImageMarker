package com.team.imagemarker.adapters.task;

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

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {
    private Context mContext;
    private List<PictureCollection> mList;
    private int[] colors = {0xffFFAD6C, 0xff62f434, 0xffdeda78, 0xff7EDCFF, 0xff58fdea, 0xfffdc75f};//颜色组

    public TimeLineAdapter(Context context, List<PictureCollection> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_task_timeline, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //2000-08-31-20-21-30
        String[] collectionTime = mList.get(position).getSaveTime().split("-");
        holder.time.setText(collectionTime[0] + "-" + collectionTime[1] + "-" + collectionTime[2] + " " + collectionTime[3] + ":" + collectionTime[4] + ":" + collectionTime[5]);
        int num = (int)(Math.random() * 6 + 1) % colors.length;
        holder.time.setTextColor(colors[num]);
        holder.textView.setTextColor(colors[num]);

        Glide.with(mContext)
                .load(mList.get(position).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.8f)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView textView;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            time = (TextView) view.findViewById(R.id.time);
            textView = (TextView) view.findViewById(R.id.text);
            image = (ImageView) view.findViewById(R.id.category_img);
        }
    }
}
