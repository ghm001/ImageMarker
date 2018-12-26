package com.team.imagemarker.activitys.tasks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.adapters.task.TaskBoxAdapter;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.MarkerModel;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.entitys.marker.ItemEntity;
import com.team.imagemarker.utils.chart.LineChartView;
import com.team.imagemarker.utils.chart.SlimChart;
import com.team.imagemarker.utils.scrollview.MyHorizontalScrollView;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;
import com.wangjie.rapidfloatingactionbutton.textlabel.LabelView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Lmy on 2017/5/18.
 * email 1434117404@qq.com
 */

public class UserTaskActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView title, subTitle;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private TextView completeNum, noCompleteNum, allNum;

    private SlimChart slimChart;
    private LineChartView chartView;
    private MyHorizontalScrollView recentTask;
    private List<CategoryModel> taskList = new ArrayList<>();

    private List<String> dateList = new ArrayList<>();// 日期
    private List<Double> earnList = new ArrayList<>();// 图片数目 共8组值
    private List<CategoryModel> systemPushList = new ArrayList<>();
    private TaskBoxAdapter adapterSystem;

    private static List<ItemEntity> dataList = new ArrayList<>();

    private List<MarkerModel> detailList = new ArrayList<>();//近一周详细信息

    private UserModel userModel;//得到用户的信息

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    completeNum.setText(String.valueOf(msg.arg1) + "次");
                    noCompleteNum.setText(String.valueOf(msg.arg2) + "次");
                    allNum.setText((String) msg.obj + "次");
                    slimChart.setText((String) msg.obj + "");
                    getDataFromNetToDetailHistory();
                }
                break;
                case 2:{
                    if(detailList.size() != 0){
                        setTaskBox();
                        getDataFromNetToSevenDayNum();
                    }
                }
                break;
                case 3:{
                    JSONObject object = (JSONObject) msg.obj;
                    String[] times = object.optString("time").trim().split("a");
                    String[] numbers = object.optString("number").trim().split("a");

//                    dateList.add("06-24");
//                    dateList.add("06-23");
//                    dateList.add("06-22");
//                    dateList.add("06-21");
//                    dateList.add("06-20");
//                    dateList.add("06-19");
//                    dateList.add("06-18");

                    dateList = Arrays.asList(times);
//                    for (int i = 0; i < times.length; i++) {
//                        dateList.add(times[i]);
//                    }
                    for (int i = 0; i < numbers.length; i++) {
                        earnList.add(Double.valueOf(numbers[i].trim()).doubleValue());
                    }
                    Log.e("tag", "handleMessage: times:" + dateList.toString());
                    Log.e("tag", "handleMessage: numbers:" + earnList.toString());
                    chartView.setTextSize(25, 25, 25, 25);
                    chartView.setData(earnList,dateList,true);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_task);
        bindView();
        setSlimChart();//设置Slimart圆圈
//        setLineChart();//设置折现统计图
//        setTaskBox();
//        initDataList();

        UserDbHelper.setInstance(this);
        userModel = UserDbHelper.getInstance().getUserInfo();

        getDataFromNetToTaskNum();//得到用户的任务数量
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        subTitle = (TextView) findViewById(R.id.sub_title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        completeNum = (TextView) findViewById(R.id.complete_num);
        noCompleteNum = (TextView) findViewById(R.id.no_complete_num);
        allNum = (TextView) findViewById(R.id.all_num);

        slimChart = (SlimChart) findViewById(R.id.slimChart);
        chartView = (LineChartView) findViewById(R.id.chartView);
//        taskBox = (MyGridView) findViewById(R.id.task_box);
//        taskViewPager = (ViewPager) findViewById(R.id.card_viewpager);
        recentTask = (MyHorizontalScrollView) findViewById(R.id.recent_task);
//        hobbyRecycle = (RecyclerView) findViewById(R.id.hobby_push);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("任务情况");
        subTitle.setVisibility(View.GONE);
//        subTitle.setText("概览");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
        subTitle.setOnClickListener(this);
    }

    private void setSlimChart() {
        //设置颜色
        int[] colors = new int[4];
        colors[0] = Color.parseColor("#4dd0e1");
        colors[1] = Color.parseColor("#4fc3f7");
        colors[2] = Color.parseColor("#29b6f6");
        colors[3] = Color.parseColor("#03a9f4");
        slimChart.setColors(colors);

        //设置状态
        final float[] stats = new float[4];
        stats[0] = 100;
        stats[1] = 85;
        stats[2] = 40;
        stats[3] = 25;
        slimChart.setStats(stats);

        //动画显示
        slimChart.setStartAnimationDuration(3500);
        slimChart.playStartAnimation();

        //设置文本
        slimChart.setStrokeWidth(13);
        slimChart.setText("0");
        slimChart.setTextColorInt(Color.parseColor("#464e76"));
        slimChart.setRoundEdges(true);
    }

    private void setTaskBox() {
        for (int i = 0; i < detailList.size(); i++) {
            taskList.add(new CategoryModel(R.mipmap.task2, R.mipmap.task, "", "", ""));
            taskList.add(new CategoryModel(R.mipmap.task1, R.mipmap.task, "", "", ""));
            taskList.add(new CategoryModel(R.mipmap.task3, R.mipmap.task, "", "", ""));
        }
        View recentItem = null;
        final ViewHolder viewHolder = new ViewHolder();
        LinearLayout rootview = new LinearLayout(this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(370, ViewGroup.LayoutParams.WRAP_CONTENT);//SizeUtils.px2dip(this, 130),SizeUtils.px2dip(this, 160)250,340
        param.setMargins(20, 0, 20, 0);
        for ( int count = 0; count < detailList.size(); count++) {
            recentItem = LayoutInflater.from(this).inflate(R.layout.item_recent_task, null);
            viewHolder.categoryImg = (ImageView) recentItem.findViewById(R.id.category_img);//背景图片
            viewHolder.userHead = (ImageView) recentItem.findViewById(R.id.user_head);//第一张图片
            viewHolder.userRanking = (LabelView) recentItem.findViewById(R.id.user_ranking);//时间
            viewHolder.category = (TextView) recentItem.findViewById(R.id.category);//所属种类
            viewHolder.opterator = (TextView) recentItem.findViewById(R.id.opterator);//操作类型
            viewHolder.toMark = (Button) recentItem.findViewById(R.id.toMark);//查看
            viewHolder.categoryImg.setImageResource(taskList.get(count).getImgId());
            Glide.with(this)
                    .load(detailList.get(count).getImageUrl1())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.userHead);
            String[] time = detailList.get(count).getSetTime().split(" ")[0].split("-");
            viewHolder.userRanking.setText(time[1] + "-" + time[2]);
            viewHolder.toMark.setTag(count);
            viewHolder.category.setText("所选类别: " + detailList.get(count).getSecondlabelName());
            viewHolder.opterator.setText("操作类型: " + detailList.get(count).getPushWay());
            viewHolder.toMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Intent intent = new Intent(UserTaskActivity.this, MarkHomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("pageTag", "userTask");
                    bundle.putSerializable("item", detailList.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    UserTaskActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
            rootview.addView(recentItem, param);
        }
        recentTask.removeAllViews();
        recentTask.addView(rootview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.sub_title:{
                Intent intent = new Intent(this, UserTaskOverViewActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    class ViewHolder{
        public ImageView categoryImg;
        public ImageView userHead;
        public LabelView userRanking;
        public TextView category;
        public TextView opterator;
        public Button toMark;
    }

    private void initDataList() {
        try {
            InputStream in = getAssets().open("task.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                    ItemEntity itemEntity = new ItemEntity(itemJsonObject);
                    dataList.add(itemEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDataFromNetToTaskNum(){
        String url = Constants.Task_Manager_All_Num;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userModel.getId()));
        VolleyRequestUtil.RequestPost(this, url, "hobbyPush", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    Message message = new Message();
                    message.what = 1;
                    message.arg1 = Integer.parseInt(object.optString("Finish"));
                    message.arg2 = Integer.parseInt(object.optString("Save"));
                    message.obj = object.optString("ALL");
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("UserTaskActivity", "onError: getDataFromNetToTaskNum:" + error.toString());
                getDataFromNetToTaskNum();
            }
        });
    }

    private void getDataFromNetToDetailHistory(){
        String url = Constants.Task_Manager_Every_Week_Detail;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userModel.getId()));
        Log.e("tag", "getDataFromNetToDetailHistory: 当前用户Id：" + userModel.getId());
        VolleyRequestUtil.RequestPost(this, url, "detailHistory", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    Log.e("tag", "onSuccess: " + result);
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.optJSONArray("picture");
                    Gson gson = null;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.optJSONObject(i);
                        gson = new Gson();
                        MarkerModel model = gson.fromJson(object1.toString(), MarkerModel.class);
                        detailList.add(model);
                    }
                    handler.sendEmptyMessage(2);
                    Log.e("tag", "onSuccess: detailList" + detailList.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("UserTaskActivity", "onError: getDataFromNetToDetailHistory:" + error.toString());
                getDataFromNetToDetailHistory();
            }
        });
    }

    private void getDataFromNetToSevenDayNum(){
        String url = Constants.Task_Manager_Every_Day_Num;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userModel.getId()));
        VolleyRequestUtil.RequestPost(this, url, "SevenDayNum", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    Log.e("tag", "onSuccess: 接受到的数据是：" + result);
                    JSONObject object = new JSONObject(result);
                    Message message = new Message();
                    message.what = 3;
                    message.obj = object;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("UserTaskActivity", "onError: getDataFromNetToSevenDayNum:" + error.toString());
                getDataFromNetToSevenDayNum();
            }
        });
    }

}