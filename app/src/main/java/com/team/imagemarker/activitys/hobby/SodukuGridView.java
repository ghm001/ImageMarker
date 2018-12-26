package com.team.imagemarker.activitys.hobby;

/**
 * Created by Lmy on 2017/9/3.
 * email 1434117404@qq.com
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 自定义的“九宫格”——用在显示帖子详情的图片集合
 * 解决的问题：GridView显示不全，只显示了一行的图片，比较奇怪，尝试重写GridView来解决
 *
 * @author lichao
 * @since 2014-10-16 16:41
 *
 */
public class SodukuGridView extends GridView {

    public SodukuGridView(Context context) {
        super(context);
    }



    public SodukuGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SodukuGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpace = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpace);
    }


}