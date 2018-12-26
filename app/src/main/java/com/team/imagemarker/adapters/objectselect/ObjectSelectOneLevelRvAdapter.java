package com.team.imagemarker.adapters.objectselect;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.bases.ItemClickListener;

import java.util.List;

/**
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public class ObjectSelectOneLevelRvAdapter extends ObjectSelectBaseAdapter<String> {
    private int clickPositon;

    public ObjectSelectOneLevelRvAdapter(Context context, List list, ItemClickListener listener) {
        super(context, list,listener);
    }

     public  void setClickPositon(int position){
        clickPositon=position;
        notifyDataSetChanged();//更新view，否则点击背景不换
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ProvinceHolder(view,viewType,listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_object_select_one_level;
    }

    private class ProvinceHolder extends RvHolder<String>{
        private TextView textView;
        private View view;
        public ProvinceHolder(View itemView, int type,ItemClickListener listener) {
            super(itemView,type, listener);
             view=itemView;
            textView= (TextView) view.findViewById(R.id.tv_province);
        }

        @Override
        public void bindHolder(String s, int position) {
            if (position==clickPositon) {
                view.setBackgroundColor(Color.parseColor("#ff252e39"));
                textView.setTextColor(Color.parseColor("#ffffff"));
            } else {
                view.setBackgroundColor(Color.parseColor("#00FFFFFF"));//设置为透明的，因为白色会覆盖分割线
                textView.setTextColor(Color.parseColor("#464e76"));
            }
            textView.setText(s);
        }
    }
}
