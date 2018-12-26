package com.team.imagemarker.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自适应ListView
 * ScrollView中嵌套ListView，解决ListView的高度不能自适应
 * Created by Lmy on 2017/8/23.
 * email 1434117404@qq.com
 */

public class AdaptiveListView extends ListView {
    public AdaptiveListView(Context context) {
        super(context);
    }

    public AdaptiveListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdaptiveListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 对listView进行测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
