package com.team.imagemarker.adapters.imgscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.entitys.imgscan.Comment;
import com.team.imagemarker.utils.CircleImageView;

import java.util.List;

/**
 * Created by Lmy on 2017/5/13.
 * email 1434117404@qq.com
 */

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<Comment> list;

    public CommentAdapter(Context context, List<Comment> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_comment, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context)
                .load(list.get(position).getUserHeadImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.userHead);
        viewHolder.userNick.setText(list.get(position).getUserName());
        String[] collectionTime = list.get(position).getSayTime().split("-");
        viewHolder.sendTime.setText(collectionTime[0] + "-" + collectionTime[1] + "-" + collectionTime[2] + " " + collectionTime[3] + ":" + collectionTime[4] + ":" + collectionTime[5]);
        viewHolder.commentContent.setText(list.get(position).getCommentTitle());
        return convertView;
    }

    class ViewHolder{
        public CircleImageView userHead;
        public TextView userNick;
        public TextView sendTime;
        public TextView commentContent;

        public ViewHolder(View view) {
            userHead = (CircleImageView) view.findViewById(R.id.comment_image);
            userNick = (TextView) view.findViewById(R.id.comment_name);
            sendTime = (TextView) view.findViewById(R.id.comment_time);
            commentContent = (TextView) view.findViewById(R.id.comment_text);
        }
    }
}
