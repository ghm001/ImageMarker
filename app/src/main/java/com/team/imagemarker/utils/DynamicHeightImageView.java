package com.team.imagemarker.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 动态调整ImageView的高度
 * Created by Lmy on 2017/4/18.
 * email 1434117404@qq.com
 */

public class DynamicHeightImageView extends ImageView {
    //图片高宽比（高/宽）
    private double hwRatio;

    public DynamicHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHeightImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);//获取当前ImageView分配的宽度(即Item项的宽度)
        if(widthSize!=0&& hwRatio !=0) {
            setMeasuredDimension(widthSize, (int)(widthSize* hwRatio));//根据高宽比，计算出ImagView需要的高度widthSize* hwRatio，并设置其大小
        }
        else{
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if(drawable!=null){
            if(drawable.getIntrinsicWidth()!=0)
                hwRatio = drawable.getIntrinsicHeight()/(double)drawable.getIntrinsicWidth();//获取图片的高宽比（高/宽）
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        BitmapFactory.Options options = new BitmapFactory.Options();//获取图片的高宽比（高/宽）
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeResource(getResources(),resId,options);
        hwRatio = options.outHeight/(double)options.outWidth;
        bmp.recycle();
    }
}
