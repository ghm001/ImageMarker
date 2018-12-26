package com.team.imagemarker.adapters.imgnav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.entitys.MarkerModel;
import com.team.imagemarker.utils.CircleImageView;

import java.util.List;


/**
 * Created by Lmy on 2017/5/7.
 * email 1434117404@qq.com
 */

public class GridViewLikeAdapter extends BaseAdapter {
    private Context context;
    private List<MarkerModel> datas;
    private LayoutInflater inflater;
    private int currentindex;//当前页数
    private int pageSize;//每一页显示的个数

    public GridViewLikeAdapter(Context context, List<MarkerModel> datas, int currentindex, int pageSize){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.datas = datas;
        this.currentindex = currentindex;
        this.pageSize = pageSize;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - curIndex * pageSize);(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return datas.size() > (currentindex + 1) * pageSize ? pageSize : (datas.size() - currentindex * pageSize);
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position + currentindex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + currentindex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_category, null);
            viewHolder.managerHead = (CircleImageView) convertView.findViewById(R.id.manager_head_img);
            viewHolder.categoryImg = (ImageView) convertView.findViewById(R.id.category_img);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_name);
            viewHolder.categorySimapleMessage = (TextView) convertView.findViewById(R.id.category_simple_message);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MarkerModel model = datas.get(position + currentindex * pageSize);
        viewHolder.managerHead.setVisibility(View.GONE);
            Glide.with(context)
                    .load(model.getImageUrl1())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.8f)
                .into(viewHolder.categoryImg);

//        viewHolder.categoryImg.setImageResource(model.getImgId());
//        viewHolder.categoryName.setText(model.getName());
//        viewHolder.categorySimapleMessage.setText(model.getSimpleMessage());
        return convertView;
    }

    class ViewHolder{
        public CircleImageView managerHead;
        public ImageView categoryImg;
        public TextView categoryName;
        public TextView categorySimapleMessage;
    }

}
