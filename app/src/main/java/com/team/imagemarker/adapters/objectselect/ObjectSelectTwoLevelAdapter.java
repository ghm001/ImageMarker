package com.team.imagemarker.adapters.objectselect;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.bases.ItemClickListener;
import com.team.imagemarker.entitys.objectselect.TwoLevelBean;

import java.util.List;

/**
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public class ObjectSelectTwoLevelAdapter extends ObjectSelectBaseAdapter<TwoLevelBean> {
    public ObjectSelectTwoLevelAdapter(Context context, List<TwoLevelBean> list, ItemClickListener listener) {
        super(context, list, listener);
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new CityHolder(view,viewType,listener);
    }

    @Override
    public int getItemViewType(int position) {
        return  list.get(position).isTitle() ? 0 : 1;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return viewType==0 ? R.layout.item_object_select_two_level_title :R.layout.item_object_select_two_level;
    }

    private class CityHolder extends RvHolder<TwoLevelBean> {
        private TextView title;
        private TextView city;

        public CityHolder(View itemView, int type,ItemClickListener listener) {
            super(itemView,type, listener);
            switch (type)
            {
                case 0:
                    title= (TextView) itemView.findViewById(R.id.tv_title);
                    break;
                case 1:
                    city= (TextView) itemView.findViewById(R.id.tv_city);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void bindHolder(TwoLevelBean cityBean, int position) {
            int itemViewTtpe=ObjectSelectTwoLevelAdapter.this.getItemViewType(position);
            switch (itemViewTtpe) {
                case 0:
                    title.setText(list.get(position).getProvince());
                    break;
                case 1:
                    city.setText(list.get(position).getCity());
                    break;
                case 2:
                    break;
            }
        }
    }

}
