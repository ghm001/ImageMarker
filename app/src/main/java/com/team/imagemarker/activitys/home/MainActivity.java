package com.team.imagemarker.activitys.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.TabViewPagerAdapter;
import com.team.imagemarker.bases.BaseActivity;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.fragments.user.LoginFragment;
import com.team.imagemarker.fragments.user.RegisterFragment;


/**
 * 登录、注册主界面
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, RegisterFragment.CallBack{
    private TabLayout loginTabs;
    private ViewPager loginViewPager;
    private TabViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //初始化控件
        loginTabs = (TabLayout) findViewById(R.id.login_tabs);
        loginViewPager = (ViewPager) findViewById(R.id.login_viewpager);

        //显示tabViewPager
        setTabViewPager();

        Log.e("tag", "onCreate: 当前服务器IP地址为" + Constants.HOST_PORT);
    }

    private void setTabViewPager() {
        viewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new LoginFragment(), "登录");
        viewPagerAdapter.addFragment(new RegisterFragment(), "注册");
        loginViewPager.setAdapter(viewPagerAdapter);
        loginTabs.setupWithViewPager(loginViewPager);
        //为TabLayout添加点击事件
        loginTabs.setOnTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int location = tab.getPosition();
        loginViewPager.setCurrentItem(location);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void setShowFragment() {
        loginViewPager.setCurrentItem(0);
    }
}