package com.team.imagemarker.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自定义GridView为了使GridView不能滑动
 * Created by Lmy on 2017/4/2.
 * email 1434117404@qq.com
 */

public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}