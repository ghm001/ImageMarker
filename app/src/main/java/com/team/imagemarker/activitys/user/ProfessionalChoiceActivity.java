package com.team.imagemarker.activitys.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.objectselect.ObjectSelectOneLevelRvAdapter;
import com.team.imagemarker.bases.BaseActivity;
import com.team.imagemarker.bases.CheckListener;
import com.team.imagemarker.bases.ItemClickListener;
import com.team.imagemarker.entitys.objectselect.TwoLevelPresenter;
import com.team.imagemarker.utils.objectselect.ItemHeaderDecoration;
import com.team.imagemarker.utils.objectselect.MyDividerItemDecoration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public class ProfessionalChoiceActivity extends BaseActivity implements View.OnClickListener, CheckListener, ObjectSelectTwoLevelFragment.CallBack{
    private RelativeLayout titleBar;
    private TextView title;
    private ImageView leftIcon, rightIcon;

    public  ObjectSelectOneLevelRvAdapter adapter;
    private RecyclerView recycleView;
    private ObjectSelectTwoLevelFragment cityFragment;
    private LinearLayoutManager manager;
    private int mposition;
    private String objectContent;//用户选择的具体内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_choice);
        bindView();
        initData();
        new TwoLevelPresenter(cityFragment);
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        recycleView = (RecyclerView) findViewById(R.id.rv_left);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("专业选择");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
        }
    }

    private void initData() {
        manager=new LinearLayoutManager(this);
        recycleView.setLayoutManager(manager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.addItemDecoration(new MyDividerItemDecoration(this));

        String [] province=getResources().getStringArray(R.array.province);//获取一级领域
        final List<String> list= Arrays.asList(province);

        //适配数据和设置监听事件
        adapter=new ObjectSelectOneLevelRvAdapter(this, list, new ItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                mposition=position;
                startMove(position,true);
                Log.e("tag", "onItemClick: 一级标题、、、、、、");
                moveToCenter(position);
            }
        });
        recycleView.setAdapter(adapter);
        addRightData();
    }


    /**
     * 将当前选中的item居中
     */
    public   void moveToCenter(int position) {

        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        int itemPosition=position-manager.findFirstVisibleItemPosition();

        //当往上滑动太快，会出现itemPosition为-1的情况。做下判断
        if (0<itemPosition&&itemPosition<manager.getChildCount()) {
            View childAt = recycleView.getChildAt(position - manager.findFirstVisibleItemPosition());
            int y = (childAt.getTop() - recycleView.getHeight() / 2);
            recycleView.smoothScrollBy(0, y);
        }
    }

    private void startMove(int position, boolean isLeft) {
        int counts=0;
        for (int i=0;i<position;i++){//position 为点击的position
            counts+=cityFragment.citylist.get(i).length;//计算需要滑动的城市数目
        }
        if (isLeft) {
            cityFragment.setCounts(counts+position);//加上title（省份）数目
        }
        else{
            ItemHeaderDecoration.setCurrentTag(String.valueOf(position));
        }
        adapter.setClickPositon(position);//设置点击的位置，改变省份点击背景
    }

    private void addRightData() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        cityFragment= new ObjectSelectTwoLevelFragment();
        cityFragment.setCheck(this);
        ft.add(R.id.fl_right,cityFragment);
        ft.commit();
    }

    @Override
    public void check(int position, boolean isScroll) {
        startMove(position,isScroll);
        moveToCenter(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void setData(String objectSelect) {
        objectContent = objectSelect;
        Intent intent = new Intent();
        intent.putExtra("objectSelect", objectSelect);
        this.setResult(RESULT_OK, intent);
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
