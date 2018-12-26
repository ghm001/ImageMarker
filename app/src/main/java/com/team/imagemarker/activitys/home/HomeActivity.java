package com.team.imagemarker.activitys.home;

import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.team.imagemarker.R;
import com.team.imagemarker.fragments.home.FirstPageFragment;
import com.team.imagemarker.fragments.home.ImageNavFragment;
import com.team.imagemarker.fragments.home.UserCenterFragment;
import com.team.imagemarker.utils.ToastUtil;
import com.team.imagemarker.utils.common.NetReceiver;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 主界面
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class HomeActivity extends FragmentActivity{
    private String[] tabNames = {"兴趣导航", "个人中心"};
    private int[] tabIcons = {R.mipmap.hobby_nav_icon, R.mipmap.user_center_icon};
    private SpaceNavigationView tabs;
    private ImageNavFragment imageNavFragment;
    private UserCenterFragment userCenterFragment;
    private FirstPageFragment firstPageFragment;
    private FragmentManager fragmentManager;
    private final int CONTENT_ID = R.id.main_content;
    private static Boolean isExit = false;
    private NetReceiver mReceiver;
    private IntentFilter mFilter;
    private ToastUtil toastUtil = new ToastUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setTabs();

        mReceiver = new NetReceiver();
        mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(mReceiver, mFilter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        tabs.onSaveInstanceState(outState);
    }

    /**
     * 设置导航栏
     */
    private void setTabs() {
        tabs = (SpaceNavigationView) findViewById(R.id.main_tab);

        //初次加载首页
        fragmentManager = getSupportFragmentManager();
        firstPageFragment = new FirstPageFragment();
        fragmentManager.beginTransaction().add(CONTENT_ID, firstPageFragment).commit();
        for (int i = 0; i < tabNames.length; i++) {
            tabs.addSpaceItem(new SpaceItem(tabNames[i], tabIcons[i]));
        }
        tabs.showIconOnly();//设置只显示图片
        tabs.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                gotoCenterFragment();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                gotoOtherFragment(itemName);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                gotoOtherFragment(itemName);
            }
        });

    }

    /**
     * 进入首页
     */
    private void gotoCenterFragment() {
        hideFragment();
        if (firstPageFragment == null) {
            firstPageFragment = new FirstPageFragment();
            fragmentManager.beginTransaction().add(CONTENT_ID, firstPageFragment).commit();
        } else {
            fragmentManager.beginTransaction().show(firstPageFragment).commit();
        }
    }

    /**
     * 点击tab显示相应的界面
     * @param itemName
     */
    private void gotoOtherFragment(String itemName) {
        hideFragment();
        switch (itemName) {
            case "兴趣导航":
                if (imageNavFragment == null) {
                    imageNavFragment = new ImageNavFragment();
                    fragmentManager.beginTransaction().add(CONTENT_ID, imageNavFragment).commit();
                } else {
                    fragmentManager.beginTransaction().show(imageNavFragment).commit();
                }
                break;
            case "个人中心":
                if (userCenterFragment == null) {
                    userCenterFragment = new UserCenterFragment();
                    fragmentManager.beginTransaction().add(CONTENT_ID, userCenterFragment).commit();
                } else {
                    fragmentManager.beginTransaction().show(userCenterFragment).commit();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideFragment() {
        if (firstPageFragment != null) {
            fragmentManager.beginTransaction().hide(firstPageFragment).commit();
        }
        if (userCenterFragment != null) {
            fragmentManager.beginTransaction().hide(userCenterFragment).commit();
        }
        if (imageNavFragment != null) {
            fragmentManager.beginTransaction().hide(imageNavFragment).commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Timer timer = null;
            if(isExit == false){
                isExit = true;
                toastUtil.Short(this, "再按一次退出应用").show();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                       isExit = false;
                    }
                }, 2000);
            }else{
                HomeActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                System.exit(0);
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //广播解除绑定
        this.unregisterReceiver(mReceiver);
    }
}