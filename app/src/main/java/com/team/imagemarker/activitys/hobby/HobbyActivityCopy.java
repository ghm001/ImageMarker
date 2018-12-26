package com.team.imagemarker.activitys.hobby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.saying.SendMoodActivity;
import com.team.imagemarker.adapters.saying.CommonRecyclerAdapter;
import com.team.imagemarker.adapters.saying.CommonRecyclerHolder;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.hobby.HotPointModel;
import com.team.imagemarker.entitys.hobby.share;
import com.team.imagemarker.utils.CircleImageView;
import com.team.imagemarker.utils.ToastUtil;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HobbyActivityCopy extends Activity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener, View.OnClickListener, XRecyclerView.LoadingListener{
    private TextView title;
    private ImageView leftIcon,rightIcon;
    private RelativeLayout titleBar;

    private TextView userNick;
    private CircleImageView userHead;

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfaHelper;

    private XRecyclerView hot;
    private String[] imgTag = {"测试标签", "图片标签描述", "图片描述"};
    private static List<HotPointModel> list = new ArrayList<>();
    private CommonRecyclerAdapter<share> mAdapter;
    private static int startCount = 0;
    private int count = 2;//每次加载两条

    private ToastUtil toastUtil = new ToastUtil();
    private View headView;

    private UserModel userModel;
    private static List<share> itemList = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
//                    setDate();

                    loadData(true);//加载数据
                    mAdapter = new CommonRecyclerAdapter<share>(HobbyActivityCopy.this, itemList, R.layout.item_hobby_hot) {
                        @Override
                        public void convert(CommonRecyclerHolder holder, share item, int position, boolean isScrolling) {
                            holder.setImageByUrl(R.id.avatar, item.getUserPhotoUrl());
                            holder.setText(R.id.text_name, item.getUserNickName());
                            holder.setText(R.id.text_job_title, item.getSayingType());
                            String[] collectionTime = item.getUptime().split("-");
                            String time = collectionTime[0] + "-" + collectionTime[1] + "-" + collectionTime[2] + " " + collectionTime[3] + ":" + collectionTime[4] + ":" + collectionTime[5];
                            holder.setText(R.id.text_date, time);
                            holder.setText(R.id.hobby_content, item.getTitle());
                            holder.setTags(R.id.hobby_tag, item.getUseHobby().split("-"));
                            List<String> picModels = new ArrayList<>();
                            if(!item.getImageUrl1().equals("")){
                                picModels.add(item.getImageUrl1());
                            }else if(!item.getImageUrl2().equals("")){
                                picModels.add(item.getImageUrl2());
                            }else if(!item.getImageUrl3().equals("")){
                                picModels.add(item.getImageUrl3());
                            }else if(!item.getImageUrl4().equals("")){
                                picModels.add(item.getImageUrl4());
                            }else if(!item.getImageUrl5().equals("")){
                                picModels.add(item.getImageUrl5());
                            }else if(!item.getImageUrl6().equals("")){
                                picModels.add(item.getImageUrl6());
                            }
                            ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
                            if (picModels != null && picModels.size() != 0) {
                                for (String picModel : picModels) {
                                    ImageInfo imageInfo = new ImageInfo();
                                    imageInfo.setThumbnailUrl(picModel);
                                    imageInfo.setBigImageUrl(picModel);
                                    imageInfoList.add(imageInfo);
                                }
                            }
                            holder.setNineGridAdapter(R.id.hobby_img, new NineGridViewClickAdapter(HobbyActivityCopy.this, imageInfoList));
                        }
                    };
                    hot.setAdapter(mAdapter);


//                    mAdapter = null;
                }
                break;
            }
        }
    };


/*
    String tag = object.optString("tag");
if (tag.equals("success")){
        JSONArray jsonArray = object.optJSONArray("result");
        for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject object1 = jsonArray.getJSONObject(i);
        HotPointModel model = new HotPointModel();
        model.setUserHead(object1.optString("userHeadIcon"));
        model.setUserName(object1.optString("userName"));
        model.setUserJob(object1.optString("userJob"));
        model.setUserTime(object1.optString("userTime"));
        model.setUserHobbyTags(object1.optString("userHobbyTag").split(","));
        model.setUserContent(object1.optString("userContent"));
        List<String> imgList = new ArrayList<String>();
        for (int j = 1; j <= 6; j++) {
        if(!object1.optString("userImg" + j).equals("")){
        imgList.add(object1.optString("userImg" + j));
        }
        }
        model.setUserImgList(imgList);
        list.add(model);
        }
 */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby);
        headView = LayoutInflater.from(HobbyActivityCopy.this).inflate(R.layout.hobby_head, null);

        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        title.setText("兴趣社区");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);

        userNick = (TextView) headView.findViewById(R.id.user_name);
        userHead = (CircleImageView) headView.findViewById(R.id.man_head);

        hot = (XRecyclerView) findViewById(R.id.hot_recycleview);
        rfaLayout = (RapidFloatingActionLayout) findViewById(R.id.rfa_layout);
        rfaButton = (RapidFloatingActionButton) findViewById(R.id.rfa_button);

        UserDbHelper.setInstance(this);
        userModel = UserDbHelper.getInstance().getUserInfo();

        initData(0);
        setDate();
        setFlaotButton();//设置悬浮按钮

        userNick.setText(userModel.getUserNickName());
        if(!userModel.getUserHeadImage().equals("")){
            Glide.with(this)
                    .load(userModel.getUserHeadImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(userHead);
        }else{
            userHead.setImageResource(R.mipmap.man_head);
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

    private void initData(int count) {
        String url = Constants.Hobby_Commity_Get_User_Item_Content;
        Map<String, String> map = new HashMap<>();
        map.put("maxId", count + "");
        VolleyRequestUtil.RequestPost(this, url, "getUserHobbyHot", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.optJSONArray("share");
                    itemList = stringToArrayTwo(array.toString(),share[].class);
//                    mAdapter.notifyDataSetChanged();
                    Log.e("tag", "onSuccess: " + itemList.size() + "\n" + itemList.get(0).toString());
                    handler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("tag", "onError: 服务器连接错误");
            }
        });
    }

    private void setDate() {
//        loadData(true);//加载数据
//        mAdapter = new CommonRecyclerAdapter<share>(HobbyActivity.this, itemList, R.layout.item_hobby_hot) {
//            @Override
//            public void convert(CommonRecyclerHolder holder, share item, int position, boolean isScrolling) {
//                holder.setImageByUrl(R.id.avatar, item.getUserPhotoUrl());
//                holder.setText(R.id.text_name, item.getUserNickName());
//                holder.setText(R.id.text_job_title, item.getSayingType());
//                String[] collectionTime = item.getUptime().split("-");
//                String time = collectionTime[0] + "-" + collectionTime[1] + "-" + collectionTime[2] + " " + collectionTime[3] + ":" + collectionTime[4] + ":" + collectionTime[5];
//                holder.setText(R.id.text_date, time);
//                holder.setText(R.id.hobby_content, item.getTitle());
//                holder.setTags(R.id.hobby_tag, item.getUseHobby().split("-"));
//                List<String> picModels = new ArrayList<>();
//                if(!item.getImageUrl1().equals("")){
//                    picModels.add(item.getImageUrl1());
//                }else if(!item.getImageUrl2().equals("")){
//                    picModels.add(item.getImageUrl2());
//                }else if(!item.getImageUrl3().equals("")){
//                    picModels.add(item.getImageUrl3());
//                }else if(!item.getImageUrl4().equals("")){
//                    picModels.add(item.getImageUrl4());
//                }else if(!item.getImageUrl5().equals("")){
//                    picModels.add(item.getImageUrl5());
//                }else if(!item.getImageUrl6().equals("")){
//                    picModels.add(item.getImageUrl6());
//                }
//                ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
//                if (picModels != null && picModels.size() != 0) {
//                    for (String picModel : picModels) {
//                        ImageInfo imageInfo = new ImageInfo();
//                        imageInfo.setThumbnailUrl(picModel);
//                        imageInfo.setBigImageUrl(picModel);
//                        imageInfoList.add(imageInfo);
//                    }
//                }
//                holder.setNineGridAdapter(R.id.hobby_img, new NineGridViewClickAdapter(HobbyActivity.this, imageInfoList));
//            }
//        };
//
//        hot.setAdapter(mAdapter);
        hot.setLayoutManager(new LinearLayoutManager(HobbyActivityCopy.this, LinearLayoutManager.VERTICAL, false));
        hot.addHeaderView(headView);
        hot.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        hot.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        hot.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        hot.setLoadingListener(this);
    }

    private void getSomeData() {
        List<String> imgList = new ArrayList<>();
        imgList.add("http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img1.jpg");
        imgList.add("http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img2.jpg");
        list.add(new HotPointModel(Constants.Test_Img1, "Tom1", "软件工程师", "2017-01-01 08:59:00", imgTag, "车到山前必有路", imgList));
        list.add(new HotPointModel(Constants.Test_Img1, "Bao", "Android开发", "2017-01-01 08:59:00", imgTag, "测试内容", imgList));
    }

    private void loadData(final boolean isRefresh) {
        if(isRefresh){
            startCount = 0;
        }else{
            startCount += 5;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    /**
     * 设置悬浮按钮
     */
    private void setFlaotButton() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("选择兴趣")
                .setResId(R.mipmap.ico_test_b)
                .setIconNormalColor(0xff056f00)
                .setIconPressedColor(0xff0d5302)
                .setLabelColor(getResources().getColor(R.color.font_light))
                .setWrapper(0)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel("发表图文")
                .setResId(R.mipmap.ico_test_d)
                .setIconNormalColor(0xffff9100)
                .setIconPressedColor(0xffff9100)//FFB74D FFB74D
                .setLabelColor(getResources().getColor(R.color.font_light))
                .setWrapper(1)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(this, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(this, 5));

        rfaHelper = new RapidFloatingActionHelper(this, rfaLayout, rfaButton, rfaContent).build();
    }

    /**
     * 悬浮按钮标签点击
     * @param position
     * @param item
     */
    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (position){
            case 0:{
                Intent intent = new Intent(this, HobbySelectActivity.class);
                startActivityForResult(intent, 1);
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case 1:{
                startActivity(new Intent(this, SendMoodActivity.class));
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
        rfaHelper.toggleContent();
    }

    /**
     * 悬浮按钮图标点击
     * @param position
     * @param item
     */
    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        switch (position){
            case 0:{
                Intent intent = new Intent(this, HobbySelectActivity.class);
                startActivityForResult(intent, 1);
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case 1:{
                Intent intent = new Intent(this, SendMoodActivity.class);
                startActivityForResult(intent, 2);
                this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
        rfaHelper.toggleContent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1){
            list.clear();
//            initData();
            mAdapter.notifyDataSetChanged();
            Log.e("tag", "onActivityResult: 兴趣选择界面值获取成功!");
        }else if(requestCode == 2 && resultCode == 2){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str=sdf.format(new Date());
            String sayContent = data.getStringExtra("sayContent");
            Log.e("tag", "onActivityResult: 内容："+sayContent);
            List<String> imgList = new ArrayList<>();
            imgList.add("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-计算机-树木-草坪.jpg");
            imgList.add("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-水杯-桌子-手机.jpg");
            HotPointModel item = new HotPointModel("", "老马识图", "计算机专业", str, new String[]{"风景", "植物", "建筑"}, sayContent, imgList);//http://obs.myhwclouds.com/look.admin.image/腾讯/2017-5-23/路-道路-树木-落叶.jpg
            list.add(0, item);
            mAdapter.notifyDataSetChanged();
            Log.e("tag", "onActivityResult: 添加成功");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(list != null){
            list.clear();
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable(){
            public void run() {
                list.clear();
                loadData(true);
                initData(0);
//                mAdapter.notifyDataSetChanged();
                hot.refreshComplete();//刷新完成
            }
        }, 1000);

    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable(){
            public void run() {
                loadData(false);
                initData(startCount);
//                mAdapter.notifyDataSetChanged();
                hot.loadMoreComplete();//加载更多完成
            }
        }, 2000);
    }


    public static <T> List<T> stringToArrayTwo(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }
}


/*
    String tag = object.optString("tag");
if (tag.equals("success")){
        JSONArray jsonArray = object.optJSONArray("result");
        for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject object1 = jsonArray.getJSONObject(i);
        HotPointModel model = new HotPointModel();
        model.setUserHead(object1.optString("userHeadIcon"));
        model.setUserName(object1.optString("userName"));
        model.setUserJob(object1.optString("userJob"));
        model.setUserTime(object1.optString("userTime"));
        model.setUserHobbyTags(object1.optString("userHobbyTag").split(","));
        model.setUserContent(object1.optString("userContent"));
        List<String> imgList = new ArrayList<String>();
        for (int j = 1; j <= 6; j++) {
        if(!object1.optString("userImg" + j).equals("")){
        imgList.add(object1.optString("userImg" + j));
        }
        }
        model.setUserImgList(imgList);
        list.add(model);
        }
 */
