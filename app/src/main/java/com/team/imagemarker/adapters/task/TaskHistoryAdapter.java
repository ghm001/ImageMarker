package com.team.imagemarker.adapters.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.task.TaskHistory;

import java.util.List;

/**
 * Created by Lmy on 2017/5/26.
 * email 1434117404@qq.com
 */

public class TaskHistoryAdapter extends BaseAdapter {
    private Context context;
    private List<TaskHistory> list;

    public TaskHistoryAdapter(Context context, List<TaskHistory> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task_history, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.opterateType.setText(list.get(position).getOperatorType());
        return convertView;
    }
    class ViewHolder{
        public TextView title;
        public TextView opterateType;

        public ViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.history_title);
            opterateType = (TextView) view.findViewById(R.id.history_type);
        }
    }
}
