package com.team.imagemarker.activitys.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.TabViewPagerAdapter;
import com.team.imagemarker.fragments.IntegralFragment;
import com.team.imagemarker.fragments.TaskFragment;

/**
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class UserMissionActivity extends FragmentActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener{
    private TabLayout tasksTabs;
    private ViewPager tasksViewPager;
    private TabViewPagerAdapter viewPagerAdapter;
    private RelativeLayout titleBar;

    private TextView title;
    private ImageView leftIcon,rightIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mission);
        tasksTabs = (TabLayout) findViewById(R.id.task_tabs);
        tasksViewPager = (ViewPager) findViewById(R.id.task_viewpager);

        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(getResources().getColor(R.color.titleBar));
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        title.setText("任务中心");
//        title.setTextColor(Color.parseColor("#ffffff"));
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);

        setTabViewPager();

    }

    private void setTabViewPager() {
        viewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TaskFragment(), "任务完成");
        viewPagerAdapter.addFragment(new IntegralFragment(), "积分排行");
        tasksViewPager.setAdapter(viewPagerAdapter);
        tasksTabs.setupWithViewPager(tasksViewPager);
        //为TabLayout添加点击事件
        tasksTabs.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int location = tab.getPosition();
        tasksViewPager.setCurrentItem(location);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.left_icon){
            UserMissionActivity.this.finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }
}
