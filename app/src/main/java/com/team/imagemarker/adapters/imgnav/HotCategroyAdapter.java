package com.team.imagemarker.adapters.imgnav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.home.CategoryModel;

import java.util.List;

/**
 * Created by Lmy on 2017/5/4.
 * email 1434117404@qq.com
 */

public class HotCategroyAdapter extends BaseAdapter {
    private Context context;
    private List<CategoryModel> list;

    public HotCategroyAdapter(Context context, List<CategoryModel> list) {
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
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hot_categroy, parent, false);
            viewHolder.background = (RelativeLayout) convertView.findViewById(R.id.root_background);
            viewHolder.categroyBackground = (ImageView) convertView.findViewById(R.id.background);
            viewHolder.categroyName = (TextView) convertView.findViewById(R.id.hot_category_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CategoryModel model = list.get(position);
        viewHolder.background.setBackgroundResource(model.getImgId1());
        viewHolder.categroyBackground.setImageResource(model.getImgId());
        viewHolder.categroyName.setText(model.getName());
        return convertView;
    }

    class ViewHolder{
        public RelativeLayout background;
        public ImageView categroyBackground;
        public TextView categroyName;
    }
}
