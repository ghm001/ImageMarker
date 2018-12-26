package com.team.imagemarker.adapters.imgnav;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.entitys.home.SelectCategoryModel;
import com.team.imagemarker.utils.tag.TagColor;
import com.team.imagemarker.utils.tag.TagGroup;

import java.util.List;

/**
 * Created by Lmy on 2017/5/13.
 * email 1434117404@qq.com
 */

public class TestSelectCategoryAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater;
    private Context context;
    private ExpandableListView listView;
    private List<SelectCategoryModel> list;

    public TestSelectCategoryAdapter(Context context, ExpandableListView listView, List<SelectCategoryModel> list){
        this.context = context;
        this.listView = listView;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getCategoryTag();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 设置子条目需要响应点击事件，返回true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_parent_select_category, parent, false);
            parentViewHolder = new ParentViewHolder(convertView);
            convertView.setTag(parentViewHolder);
        }else{
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }
        parentViewHolder.tv_collocation_name.setText(TextUtils.isEmpty(list.get(groupPosition).getCategoryTitle()) ? "无标题" : list.get(groupPosition).getCategoryTitle());
        parentViewHolder.iv_status.setImageResource(isExpanded ? R.mipmap.icon_top : R.mipmap.icon_bottom);
        parentViewHolder.v_space.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
        parentViewHolder.hsv_goods_list.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
        parentViewHolder.hsv_goods_list.setFocusable(false);//设置后解决父条目无法正常展开的bug
        if(!isExpanded){
            //条目折叠显示种类
            showCategory(parentViewHolder, groupPosition);
        }
        Log.e("父布局", "父布局显示了");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_child_select_category, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        }else{
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        int tagSize = list.get(groupPosition).getCategoryTag().length;//每次显示8个
        List<TagColor> colors = TagColor.getRandomColors(tagSize);
        childViewHolder.tagGroup.setTags(colors, list.get(groupPosition).getCategoryTag());
        Log.e("孩子列表", "孩子列表设置了...");

        return convertView;
    }

    private void showCategory(ParentViewHolder parentViewHolder, final int groupPosition) {
        View category;
        ImageView sdv_cart_image;
        TextView title_name;
        LinearLayout rootview = new LinearLayout(context);
        for (int i = 0; i < list.get(groupPosition).getCateGoryInfos().size(); i++) {
            category = inflater.inflate(R.layout.item_simple_select_category, null);
            sdv_cart_image = (ImageView) category.findViewById(R.id.sdv_cart_image);
            title_name = (TextView) category.findViewById(R.id.title_name);
            Glide.with(context)
                    .load(list.get(groupPosition).getCateGoryInfos().get(i).getImgUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into( sdv_cart_image);
            title_name.setText(list.get(groupPosition).getCateGoryInfos().get(i).getName());
            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listView.expandGroup(groupPosition);//手动实现展开操作
                }
            });
            rootview.addView(category);
        }
        parentViewHolder.hsv_goods_list.removeAllViews();
        parentViewHolder.hsv_goods_list.addView(rootview);
    }

    /**
     * 父布局列表
     */
    class ParentViewHolder{
        private View v_space;
        private ImageView iv_status;
        private HorizontalScrollView hsv_goods_list;
        private TextView tv_collocation_name;

        private ParentViewHolder (View view) {
            v_space = view.findViewById(R.id.v_space);
            iv_status = (ImageView) view.findViewById(R.id.iv_status);
            hsv_goods_list = (HorizontalScrollView) view.findViewById(R.id.hsv_goods_list);
            tv_collocation_name = (TextView) view.findViewById(R.id.tv_collocation_name);
        }
    }

    /**
     * 子布局列表
     */
    class ChildViewHolder{
        private TagGroup tagGroup;
        private ChildViewHolder(View view){
            tagGroup = (TagGroup) view.findViewById(R.id.select_tag);
        }
    }



}
