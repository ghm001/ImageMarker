package com.team.imagemarker.activitys.hobby;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.team.imagemarker.R;
import com.team.imagemarker.fragments.hobby.HotPointFragment;
import com.team.imagemarker.fragments.hobby.MyDynamicFragment;

import java.util.ArrayList;
import java.util.List;

public class HobbyMainActivity extends FragmentActivity implements View.OnClickListener{
    private List<Fragment> mFragments;
    private Fragment content;//存储上一次fragment
    private int position;//标记fragment的选中状态
    private ImageView back;
    private RadioGroup rg_hobby;
    private RadioButton hotPoint;
    private RadioButton myDynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby_main);
        bindView();
        initFragment();

    }

    private void bindView() {
        back = (ImageView) findViewById(R.id.hobby_back);
        rg_hobby = (RadioGroup) findViewById(R.id.rg_hobby);
        hotPoint = (RadioButton) findViewById(R.id.hot_point);
        myDynamic = (RadioButton) findViewById(R.id.my_dynamic);

        back.setOnClickListener(this);
        hotPoint.setOnClickListener(this);
        myDynamic.setOnClickListener(this);

    }

    private void initFragment() {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new HotPointFragment());
        mFragments.add(new MyDynamicFragment());

        //fragment状态改变监听，并且初始化第一次要显示的Fragment
        rg_hobby.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rg_hobby.check(R.id.hot_point);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hobby_back:{
                onBackPressed();
            }
            break;
        }
    }

    /**
     * 切换fragment
     */
    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.hot_point://兴趣热点
                    position = 0;
                    break;
                case R.id.my_dynamic://我的动态
                    position = 1;
                    break;
                default://默认被选中的
                    position = 0;
                    break;
            }
            Fragment toFragment = mFragments.get(position);
            switchFragment (content, toFragment);
        }
    }

    /**
     * 转换fragment，其中对fragment进行了优化，防止每次切换fragment都要重新加载
     * @param fromFragment：上一个fragment
     * @param toFragment：要显示的下一个fragment
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if(fromFragment != toFragment) {
            content = toFragment;//存储上一个Fragment
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(!toFragment.isAdded()) {//判断要显示的Fragment是否被添加
                //toFragment没被添加，先将fromFragment隐藏,再添加toFragment
                if(fromFragment != null) {//先进行判空操作(前一个fragment是否被添加)，防止空指针异常
                    ft.hide(fromFragment);
                }
                if(toFragment != null) {
                    ft.add(R.id.found_fl_content,toFragment).commit();
                }
            }else{
                //toFragment已经被添加，直接将fromFragment隐藏
                if(fromFragment != null) {
                    ft.hide(fromFragment);
                }
                if(toFragment != null) {//显示toFragment
                    ft.show(toFragment).commit();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
