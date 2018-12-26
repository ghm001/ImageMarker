package com.team.imagemarker.activitys.integral;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.team.imagemarker.R;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.utils.StepArcView;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntegralMainActivity extends FragmentActivity implements TabLayout.OnTabSelectedListener, ShoppingFragment.CallBack{
    private StepArcView cc;

    private TabLayout loginTabs;
    private ViewPager loginViewPager;

    private TabViewPagerAdapter viewPagerAdapter;
    private ImageView leftIcon;

    private TextView rightIcon;

    private TextView todayIntegral, myRank;

    private UserModel userModel;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    JSONObject object = (JSONObject) msg.obj;
                    todayIntegral.setText(object.optString("TodayInteger") + "");
                    cc.setCurrentCount(10000, Integer.parseInt(object.optString("allInteger") + ""));
                    myRank.setText(object.optString("userRank") + "");
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_integral);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (TextView) findViewById(R.id.sub_title);
        todayIntegral = (TextView) findViewById(R.id.today_integral);
        myRank = (TextView) findViewById(R.id.my_rank);
        cc = (StepArcView) findViewById(R.id.cc);
        cc.setCurrentCount(270, 30);

        //初始化控件
        loginTabs = (TabLayout) findViewById(R.id.login_tabs);
        loginViewPager = (ViewPager) findViewById(R.id.task_viewpager);
        setTabViewPager();

        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =new Intent(IntegralMainActivity.this, IntegralDetail.class);
                startActivity(intent);
                IntegralMainActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        UserDbHelper.setInstance(this);
        userModel = UserDbHelper.getInstance().getUserInfo();

        getUserInformationFromNet();
    }

    private void setTabViewPager() {
        viewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ShoppingFragment(), "积分商城");
        viewPagerAdapter.addFragment(new RankFragment(), "积分排行");
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
    public void setData(int count) {
//        cc.setCurrentCount(270, Integer.parseInt(cc.getCurrentCounts()) - count);
    }

    /**
     * 重新刷新界面
     */
    @Override
    public void refresh() {
        getUserInformationFromNet();
    }

    private void getUserInformationFromNet(){
        String url = Constants.Integral_Shopping_User_Information;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userModel.getId()));
        VolleyRequestUtil.RequestPost(this, url, "getUserInformation", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    Message message = new Message();
                    message.what = 1;
                    message.obj = object;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("IntegralMainActivity", "onError: getUserInformationFromNet:" + error.toString());
                getUserInformationFromNet();
            }
        });
    }


    class TabViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public TabViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
