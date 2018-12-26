package com.team.imagemarker.adapters.objectselect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.imagemarker.bases.ItemClickListener;

import java.util.List;

/**
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public abstract class ObjectSelectBaseAdapter<T> extends RecyclerView.Adapter<ObjectSelectBaseAdapter.RvHolder> {
    private Context context;
    protected List<T> list;
    protected ItemClickListener listener;

    public ObjectSelectBaseAdapter(Context context, List<T> list, ItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public ObjectSelectBaseAdapter() {

    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        return getHolder(view, viewType);
    }

    protected abstract RvHolder getHolder(View view, int viewType);

    protected abstract int getLayoutId(int viewType);

    /*
    展示数据被回调
     */
    @Override
    public void onBindViewHolder(RvHolder holder, int position) {
        holder.bindHolder(list.get(position), position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public abstract class RvHolder<T> extends RecyclerView.ViewHolder {
        protected ItemClickListener clickListener;

        public RvHolder(View itemView, int type, ItemClickListener listener) {
            super(itemView);
            clickListener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v, (int) v.getTag());
                }
            });
        }

        public abstract void bindHolder(T t, int position);
    }
}
