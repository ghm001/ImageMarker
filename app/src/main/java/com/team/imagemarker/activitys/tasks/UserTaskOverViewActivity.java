package com.team.imagemarker.activitys.tasks;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.task.PictureCollection;
import com.team.imagemarker.adapters.task.TimeLineAdapter;
import com.team.imagemarker.entitys.task.TaskHistory;
import com.team.imagemarker.entitys.task.TimeLineModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UserTaskOverViewActivity extends Activity implements View.OnClickListener{
    private TextView title;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private RecyclerView mRecyclerView;
    private  List<PictureCollection> mList = new ArrayList<>();
    private TimeLineAdapter mAdapter;

    String[] times = {
            "2017-06-24 13:21:00",
            "2017-06-23 09:51:00",
            "2017-06-23 12:05:22",
            "2017-06-23 16:00:25",
            "2017-06-22 18:54:45",
            "2017-06-21 19:09:05",
            "2017-06-21 17:02:01",
            "2017-06-24 13:21:00"
    };
    String[] events = new String[]{
            "兴趣推送",
            "系统推送",
            "系统推送",
            "热门种类",
            "猜你喜欢",
            "热门分类",
            "热门分类",
            "兴趣推送"
    };

    int[] resId = {
            R.mipmap.hot1,
            R.mipmap.hot2,
            R.mipmap.hot3,
            R.mipmap.hot4,
            R.mipmap.hot5,
            R.mipmap.hot6
    };

    String[] imgs = {
        "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-21/狗-海滩-海水-日落.jpg",
        "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-22/橙子-水杯-果盘-果汁.jpg",
        "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-22/樱桃-蛋糕-花-篮子-甜点-水果.jpg",
        "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-22/冰淇淋-草莓-勺子-奶油蛋糕.jpg",
        "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-22/草莓-蓝莓-橙子-猕猴桃-盘子.jpg",
        "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-22/香蕉-草地-小花.jpg",
        "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-21/白云岩-高山-水-天空-树.jpg",
        "http://obs.myhwclouds.com/look.admin.image/华为/2017-5-21/天空-云朵-船-树-湖水.jpg",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_task_over_view);
        bindView();
//        setTimeLine();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        mRecyclerView = (RecyclerView) findViewById(R.id.timeline_recycleview);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("任务概览");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
    }

//    private void setTimeLine() {
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.addItemDecoration(new ItemDecoration(this, 50));
//
//        for (int i = 0; i < times.length; i++) {
//            PictureCollection event = new PictureCollection();
//            event.setTime(times[i]);
//            event.setEvent(events[i]);
//            event.setImgUrl(imgs[i]);
//            mList.add(event);
//        }
//
//        mAdapter = new TimeLineAdapter(this, mList);
//        mRecyclerView.setAdapter(mAdapter);
//    }

    private void getDataFromNet() {
        try {
            InputStream in = getAssets().open("taskHistory.json");
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);
                TimeLineModel timeLineModel = new TimeLineModel();
                timeLineModel.setDate(jsonObject.optString("mData"));
                timeLineModel.setStatus("ACTIVE");
                JSONArray jsonArray1 = jsonObject.optJSONArray("historyRecord");
                List<TaskHistory> histories = new ArrayList<>();
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.optJSONObject(j);
                    TaskHistory taskHistory = new TaskHistory();
                    taskHistory.setTitle(jsonObject1.optString("title"));
                    taskHistory.setOperatorType(jsonObject1.optString("operatorType"));
                    histories.add(taskHistory);
                }
//                timeLineModel.setHistories(histories);
//                mDataList.add(timeLineModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}