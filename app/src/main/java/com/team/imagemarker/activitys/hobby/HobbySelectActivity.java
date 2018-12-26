package com.team.imagemarker.activitys.hobby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.fragments.hobby.DragAdapter;
import com.team.imagemarker.fragments.hobby.DragGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HobbySelectActivity extends Activity implements View.OnClickListener{
    private RelativeLayout titleBar;
    private TextView title;
    private ImageView leftIcon, rightIcon;

    private ArrayList<String> channels = new ArrayList<>();
    private ArrayList<String> channels_other = new ArrayList<>();
    private DragGridView gridView;
    private DragGridView gridView_other;
    private DragAdapter dragAdapter;
    private DragAdapter other_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby_select);
        bindView();

        initData();
        initDataOther();

        gridView = (DragGridView) findViewById(R.id.gridView_channel);
        gridView_other = (DragGridView) findViewById(R.id.gridView_channel_other);

        gridView.setNumColumns(4);
        dragAdapter = new DragAdapter(this, channels);
        gridView.setAdapter(dragAdapter);

        other_adapter = new DragAdapter(this, channels_other);
        gridView_other.setAdapter(other_adapter);
        gridView_other.setNumColumns(4);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String channel = channels.get(position);
                channels.remove(position);
                channels_other.add(channel);
                dragAdapter.notifyDataSetChanged();
                other_adapter.notifyDataSetChanged();
            }
        });
        gridView_other.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String channel = channels_other.get(position);
                channels_other.remove(position);
                channels.add(channel);
                dragAdapter.notifyDataSetChanged();
                other_adapter.notifyDataSetChanged();
            }
        });
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("兴趣管理");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
    }

    private void initDataOther() {
        channels_other.add("植物");
        channels_other.add("动物");
        channels_other.add("风景");
        channels_other.add("山水");
        channels_other.add("生活");
        channels_other.add("都市");
        channels_other.add("科技");
        channels_other.add("建筑");
        channels_other.add("工具");
        channels_other.add("机械");
        channels_other.add("教育");
        channels_other.add("汽车");
        channels_other.add("人文");
        channels_other.add("历史");
        channels_other.add("游戏");
        channels_other.add("影视");
        channels_other.add("动漫");
        channels_other.add("运动");
        channels_other.add("文艺");
        channels_other.add("数码");
        channels_other.add("美食");
        channels_other.add("旅行");
        channels_other.add("工业");
        channels_other.add("化学");
    }

    private void initData() {
//        channels.add("");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{//返回时进行刷新接界面
                String url= Constants.Hobby_Commity_Search_Content;
                Map<String, String> map = new HashMap<>();
                map.put("Flag", "");



                Intent intent = new Intent();
                intent.putExtra("resultCode", "test");
                this.setResult(1, intent);
                this.finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
}