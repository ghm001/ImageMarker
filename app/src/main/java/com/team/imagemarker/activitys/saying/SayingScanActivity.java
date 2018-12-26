package com.team.imagemarker.activitys.saying;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.team.imagemarker.R;
import com.team.imagemarker.adapters.saying.SayingAdapter;
import com.team.imagemarker.entitys.saying.SayingModel;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import java.util.ArrayList;
import java.util.List;

public class SayingScanActivity extends Activity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private RelativeLayout titleBar;
    private TextView title;
    private ImageView leftIcon, rightIcon;

    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private List<SayingModel> sayingList;
    private SayingAdapter adapter;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saying_scan);
        bindView();
        initDate();//初始化数据
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.saying_scan_fresh);
        listView = (ListView) findViewById(R.id.saying_scan_liseview);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme));
        title.setText("动态浏览");
        rightIcon.setImageResource(R.mipmap.send_mood);
        leftIcon.setOnClickListener(this);
        rightIcon.setOnClickListener(this);

        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
        refreshLayout.setProgressBackgroundColor(R.color.theme);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initDate() {
        handler = new Handler();
//        listView.setPullLoadEnable(true);
//        listView.setXListViewListener(this);
//        getSayingDate();//进行联网请求数据

        //模拟
        sayingList = new ArrayList<>();
//        sayingList.add(new SayingModel(1, 1, Constants.User_Head1, "Sharon", "2017-04-30 11:07:00", "今天好开心，今天好开心今天好开心", 1, Constants.Test_Img1, Constants.Test_Img2, "", "", "", ""));
//        sayingList.add(new SayingModel(2, 1, Constants.User_Head2, "Sophia", "2017-04-30 11:07:00", "今天好开心，今天好开心今天好开心", 1, Constants.Test_Img4, Constants.Test_Img5, Constants.Test_Img6, "", "", ""));
//        sayingList.add(new SayingModel(3, 1, Constants.User_Head3, "Kelly", "2017-04-30 11:07:00", "今天好开心，今天好开心今天好开心", 1, Constants.Test_Img4, Constants.Test_Img5, Constants.Test_Img1, "", "", ""));
//        sayingList.add(new SayingModel(4, 1, Constants.User_Head4, "Allen", "2017-04-30 11:07:00", "今天好开心，今天好开心今天好开心", 1, Constants.Test_Img1, Constants.Test_Img6, Constants.Test_Img1, "", "", ""));
//        sayingList.add(new SayingModel(5, 1, Constants.User_Head5, "Richard", "2017-04-30 11:07:00", "今天好开心，今天好开心今天好开心", 1, Constants.Test_Img3, Constants.Test_Img4, Constants.Test_Img1, Constants.Test_Img3, Constants.Test_Img4, Constants.Test_Img1));
        adapter = new SayingAdapter(this, sayingList);
        listView.setAdapter(adapter);
    }

    private void getSayingDate() {
        String url = "";
        VolleyRequestUtil.RequestGet(this, url, "getSayingDate", new VolleyListenerInterface(this, VolleyListenerInterface.mSuccessListener, VolleyListenerInterface.mErrorListener) {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(SayingScanActivity.this, "网络发生错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.right_icon:{
                startActivity(new Intent(SayingScanActivity.this, SendMoodActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                refreshLayout.setRefreshing(false);//刷新完成
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}