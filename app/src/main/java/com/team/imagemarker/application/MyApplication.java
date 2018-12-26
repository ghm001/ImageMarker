package com.team.imagemarker.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.ninegrid.NineGridView;
import com.team.imagemarker.utils.GlideImageLoader;
import com.team.imagemarker.utils.imageloder.NineGridViewLoader;

import cn.sharesdk.framework.ShareSDK;

/**
 *
 * Created by Lmy on 2017/4/30.
 * email 1434117404@qq.com
 */

public class MyApplication extends Application{
    private static MyApplication myApplication;
    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 初始化全局对象
         */
         myApplication = this;

        /**
         * 建立Volley请求队列
         */
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        /**
         * 系统图片多选ImagePicker初始化
         */
        initImagePicker();

        /**
         * 九宫格图片展示及加载方式初始化
         */
        NineGridView.setImageLoader(new NineGridViewLoader());

        /**
         * 初始化第三方分享
         */
        ShareSDK.initSDK(getApplicationContext());
    }

    /**
     * 防止dex方法数限制
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 得到需要分配的缓存大小
     */
    public int getMemoryCacheSize() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        return maxMemory / 8;
    }

    /**
     * 获取Application Context
     */
    public static Context getAppContext() {
        return myApplication != null ? myApplication.getApplicationContext() : null;
    }

    /**
     * 设置Volley网络请求队列接口
     */
    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }

    /**
     * 系统图片多选ImagePicker初始化
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }
}