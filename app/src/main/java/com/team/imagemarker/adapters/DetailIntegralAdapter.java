package com.team.imagemarker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.IntegralHistory;

import java.util.List;

/**
 * Created by Lmy on 2017/9/1.
 * email 1434117404@qq.com
 */

public class DetailIntegralAdapter extends BaseAdapter {
    private Context context;
    private List<IntegralHistory> list;

    public DetailIntegralAdapter(Context context, List<IntegralHistory> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_integral_detail, viewGroup, false);
            holder.title = (TextView) view.findViewById(R.id.detail_integral_title);
            holder.time = (TextView) view.findViewById(R.id.detail_integral_time);
            holder.num = (TextView) view.findViewById(R.id.detail_integral_num);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        IntegralHistory history = list.get(i);

        String[] collectionTime = history.getProdata().split("-");
        holder.time.setText(collectionTime[0] + "-" + collectionTime[1] + "-" + collectionTime[2] + " " + collectionTime[3] + ":" + collectionTime[4] + ":" + collectionTime[5]);
        holder.title.setText(history.getExplan());
        if(history.getOperation().equals("+")){
            holder.num.setText("+" + history.getOperationNum());
        }else if(history.getOperation().equals("-")){
            holder.num.setText("-" + history.getOperationNum());
        }
        return view;
    }

    class ViewHolder{
        public TextView title;
        public TextView time;
        public TextView num;
    }
}
