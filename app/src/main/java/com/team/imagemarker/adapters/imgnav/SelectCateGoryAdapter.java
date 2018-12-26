package com.team.imagemarker.adapters.imgnav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.entitys.home.SelectCategoryModel;
import com.team.imagemarker.utils.scrollview.MyHorizontalScrollView;

import java.util.List;

/**
 * Created by Lmy on 2017/5/19.
 * email 1434117404@qq.com
 */

public class SelectCateGoryAdapter extends BaseAdapter {
    private Context context;
    private List<SelectCategoryModel> list;

    public SelectCateGoryAdapter(Context context, List<SelectCategoryModel> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_category, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mainTitle.setText(list.get(position).getCategoryTitle());
        showCategory(viewHolder, position);

        return convertView;
    }

    private void showCategory(ViewHolder viewHolder, int position) {
        View category;
        ImageView sdv_cart_image;
        TextView title_name;
        LinearLayout rootview = new LinearLayout(context);
        for (int i = 0; i < list.get(position).getCateGoryInfos().size(); i++) {
            category = LayoutInflater.from(context).inflate(R.layout.item_simple_select_category, null);
            sdv_cart_image = (ImageView) category.findViewById(R.id.sdv_cart_image);
            title_name = (TextView) category.findViewById(R.id.title_name);
            Glide.with(context)
                    .load(list.get(position).getCateGoryInfos().get(i).getImgUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(sdv_cart_image);
            title_name.setText(list.get(position).getCateGoryInfos().get(i).getName());
            rootview.addView(category);
        }
        viewHolder.scrollView.removeAllViews();
        viewHolder.scrollView.addView(rootview);
    }

    class ViewHolder {
        public TextView mainTitle;
        private MyHorizontalScrollView scrollView;
        public ViewHolder(View view) {
            mainTitle = (TextView) view.findViewById(R.id.main_title);
            scrollView = (MyHorizontalScrollView) view.findViewById(R.id.category_item);
        }
    }
}