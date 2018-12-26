package com.team.imagemarker.activitys.imagscan;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.team.imagemarker.R;
import com.team.imagemarker.bases.BaseActivity;
import com.team.imagemarker.utils.view.CoordinatorTabLayout;

import java.util.ArrayList;

/**
 * 美图浏览
 * Created by Lmy on 2017/5/27.
 * email 1434117404@qq.com
 */

public class ImgScanMainActivity extends BaseActivity {
    private CoordinatorTabLayout mCoordinatorTabLayout;
    private int[] mImageArray, mColorArray;
    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"每日推荐", "领域推送", "猜你喜欢", "图片收藏"};
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_img_scan_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initFragments();
        initViewPager();
        mImageArray = new int[]{R.mipmap.hot4,R.mipmap.hot1,R.mipmap.hot3,R.mipmap.hot6};
        mColorArray = new int[]{android.R.color.holo_blue_light, R.color.bgColor4, R.color.bgColor3, R.color.bgColor2};
        mCoordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
        mCoordinatorTabLayout.setTitle("美图浏览")
                .setBackEnable(true)
                .setImageArray(mImageArray, mColorArray)
                .setupWithViewPager(mViewPager);
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(DayRecommendFragment.getInstance(mTitles[0]));
        mFragments.add(DayRecommendFragmentCopy.getInstance(mTitles[1]));
        mFragments.add(GuessYouLikeFragment.getInstance(mTitles[2]));
        mFragments.add(ImgCollectionFragment.getInstance(mTitles[3]));
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.img_scan);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new ImgScanPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
