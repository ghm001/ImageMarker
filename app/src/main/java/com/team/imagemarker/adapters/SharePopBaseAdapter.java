package com.team.imagemarker.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.share.SharePopBean;

import java.util.List;


/**
 * 分享的PopupWindow的适配器
 * Created by Lmy on 2017/4/23.
 * email 1434117404@qq.com
 */

public class SharePopBaseAdapter extends BaseAdapter {
    private Context context;
    private List<SharePopBean> list;

    public SharePopBaseAdapter(Context context, List<SharePopBean> list) {
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
            convertView  = View.inflate(context, R.layout.item_share_grid, null);
            viewHolder.shareIcon = (ImageView) convertView.findViewById(R.id.share_icon);
            viewHolder.shareName = (TextView) convertView.findViewById(R.id.share_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SharePopBean shareBean = list.get(position);
        viewHolder.shareIcon.setImageResource(shareBean.getIconId());
        viewHolder.shareName.setText(shareBean.getName());
        return convertView;
    }

    class ViewHolder{
        public ImageView shareIcon;
        public TextView shareName;
    }
}
