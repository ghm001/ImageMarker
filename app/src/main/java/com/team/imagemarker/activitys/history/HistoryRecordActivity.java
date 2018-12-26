package com.team.imagemarker.activitys.history;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewPropertyAnimator;
import com.team.imagemarker.R;
import com.team.imagemarker.fragments.history.AllHistoryFragment;
import com.team.imagemarker.fragments.history.CompletFragment;
import com.team.imagemarker.fragments.history.NoCompleteFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史记录
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public class HistoryRecordActivity extends FragmentActivity implements View.OnClickListener {
    private RelativeLayout titleBar;
    private TextView title;
    private ImageView leftIcon, rightIcon;

    private TextView allRecord, completeRecord, noCompleteRecord;
    private ViewPager historyViewPager;
    private View line;
    private int line_length;

    private List<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_record);
        bindView();
        setViewPager();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        allRecord = (TextView) findViewById(R.id.all_history);
        completeRecord = (TextView) findViewById(R.id.complete);
        noCompleteRecord = (TextView) findViewById(R.id.no_complete);
        line = findViewById(R.id.line);
        historyViewPager = (ViewPager) findViewById(R.id.history_viewpager);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("历史记录");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);

        allRecord.setOnClickListener(this);
        completeRecord.setOnClickListener(this);
        noCompleteRecord.setOnClickListener(this);

    }

    private void setViewPager() {
        //初始化TextView动画
        ViewPropertyAnimator.animate(allRecord).scaleX(1.2f).setDuration(0);
        ViewPropertyAnimator.animate(allRecord).scaleY(1.2f).setDuration(0);

        //初始化Fragment
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new NoCompleteFragment());
        fragmentList.add(new CompletFragment());
        fragmentList.add(new AllHistoryFragment());
        line_length = this.getWindowManager().getDefaultDisplay().getWidth() / fragmentList.size();
        line.getLayoutParams().width = line_length;
        line.requestLayout();
        historyViewPager.setOffscreenPageLimit(2);
        historyViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        historyViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                changeState(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                float tagerX = arg0 * line_length + arg2 / fragmentList.size();
                ViewPropertyAnimator.animate(line).translationX(tagerX).setDuration(0);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    /**
     * 根据传入的值来改变状态
     *
     * @param position viewPager中选中的状态
     */
    private void changeState(int position) {
        if (position == 0) {
            allRecord.setTextColor(Color.parseColor("#7ADFB8"));
            completeRecord.setTextColor(Color.parseColor("#ffffff"));
            noCompleteRecord.setTextColor(Color.parseColor("#ffffff"));
            ViewPropertyAnimator.animate(allRecord).scaleX(1.2f).setDuration(200);
            ViewPropertyAnimator.animate(allRecord).scaleY(1.2f).setDuration(200);
            ViewPropertyAnimator.animate(completeRecord).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(completeRecord).scaleY(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(noCompleteRecord).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(noCompleteRecord).scaleY(1.0f).setDuration(200);
        } else if (position == 1) {
            allRecord.setTextColor(Color.parseColor("#ffffff"));
            completeRecord.setTextColor(Color.parseColor("#7ADFB8"));
            noCompleteRecord.setTextColor(Color.parseColor("#ffffff"));
            ViewPropertyAnimator.animate(allRecord).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(allRecord).scaleY(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(completeRecord).scaleX(1.2f).setDuration(200);
            ViewPropertyAnimator.animate(completeRecord).scaleY(1.2f).setDuration(200);
            ViewPropertyAnimator.animate(noCompleteRecord).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(noCompleteRecord).scaleY(1.0f).setDuration(200);
        } else if (position == 2) {
            allRecord.setTextColor(Color.parseColor("#ffffff"));
            completeRecord.setTextColor(Color.parseColor("#ffffff"));
            noCompleteRecord.setTextColor(Color.parseColor("#7ADFB8"));
            ViewPropertyAnimator.animate(completeRecord).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(completeRecord).scaleY(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(allRecord).scaleX(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(allRecord).scaleY(1.0f).setDuration(200);
            ViewPropertyAnimator.animate(noCompleteRecord).scaleX(1.2f).setDuration(200);
            ViewPropertyAnimator.animate(noCompleteRecord).scaleY(1.2f).setDuration(200);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_icon: {//返回
                onBackPressed();
            }
            break;
            case R.id.all_history: {//所有历史记录
                historyViewPager.setCurrentItem(0);
            }
            break;
            case R.id.complete: {//已完成历史记录
                historyViewPager.setCurrentItem(1);
            }
            break;
            case R.id.no_complete: {//未完成历史记录
                historyViewPager.setCurrentItem(2);
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fragmentList != null) {
            fragmentList.clear();
            fragmentList = null;
        }
    }
}