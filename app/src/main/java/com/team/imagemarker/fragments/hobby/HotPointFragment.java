package com.team.imagemarker.fragments.hobby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.hobby.HobbySelectActivity;
import com.team.imagemarker.adapters.saying.CommonRecyclerAdapter;
import com.team.imagemarker.adapters.saying.CommonRecyclerHolder;
import com.team.imagemarker.entitys.hobby.HotPointModel;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/6/10.
 * email 1434117404@qq.com
 */

public class HotPointFragment extends Fragment implements View.OnClickListener{
    private View view;
    private FloatingActionButton addHobby;
    private XRecyclerView hot;
    private String[] imgTag = {"测试标签", "图片标签描述", "图片描述"};
    private List<HotPointModel> list = new ArrayList<>();
    private CommonRecyclerAdapter<HotPointModel> mAdapter;
    private Context context;
    private int startCount = 0;
    private int count = 2;//每次加载两条
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("tag", "onCreateView: 兴趣热点");
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_hot_point, null);
        addHobby  =  (FloatingActionButton) view.findViewById(R.id.add_hobby);
        hot = (XRecyclerView) view.findViewById(R.id.hot_recycleview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addHobby.setOnClickListener(this);
        initData();
        setDate();
    }

    private void initData() {
//        List<String> imgList = new ArrayList<>();
//        imgList.add("http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img1.jpg");
//        imgList.add("http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img2.jpg");
//        imgList.add("http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img3.jpg");
//        list.add(new HotPointModel(Constants.Test_Img1, "Tom", "软件工程师", "2017-01-01 08:59:00", imgTag, "这是测试内容, 这是测试内容, 这是测试内容, 这是测试内容", imgList));
//        list.add(new HotPointModel(Constants.Test_Img1, "Jack", "Android开发", "2017-01-01 08:59:00", imgTag, "这是测试内容, 这是测试内容, 这是测试内容, 这是测试内容", imgList));
//        list.add(new HotPointModel(Constants.Test_Img1, "Limynl", "Java开发", "2017-01-01 08:59:00", imgTag, "这是测试内容, 这是测试内容, 这是测试内容, 这是测试内容", imgList));
//        list.add(new HotPointModel(Constants.Test_Img1, "Rose", "前端工程师", "2017-01-01 08:59:00", imgTag, "这是测试内容, 这是测试内容, 这是测试内容, 这是测试内容", imgList));
//        list.add(new HotPointModel(Constants.Test_Img1, "Json", "大数据分析师", "2017-01-01 08:59:00", imgTag, "这是测试内容, 这是测试内容, 这是测试内容, 这是测试内容", imgList));
//        list.add(new HotPointModel(Constants.Test_Img1, "Gson", "软件架构师", "2017-01-01 08:59:00", imgTag, "这是测试内容, 这是测试内容, 这是测试内容, 这是测试内容", imgList));

        String url = "http://139.199.23.142:8080/TestShowMessage1/marker/data/hobbyHot.json";//兴趣热点地址
        VolleyRequestUtil.RequestGet(context, url, "hobbyHot", new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(new String( result.getBytes("ISO-8859-1"),"UTF-8"));
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
                    }else{
                        Toast.makeText(getContext(), "互数据接受错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(getContext(), "服务器错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDate() {
        loadData(true);//加载数据
        mAdapter = new CommonRecyclerAdapter<HotPointModel>(getContext(), list, R.layout.item_hobby_hot) {
            @Override
            public void convert(CommonRecyclerHolder holder, HotPointModel item, int position, boolean isScrolling) {
                holder.setImageByUrl(R.id.avatar, item.getUserHead());
                holder.setText(R.id.text_name, item.getUserName());
                holder.setText(R.id.text_job_title, item.getUserJob());
                holder.setText(R.id.text_date, item.getUserTime());
                holder.setText(R.id.hobby_content, item.getUserContent());
                holder.setTags(R.id.hobby_tag, item.getUserHobbyTags());
                ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
                List<String> picModels = item.getUserImgList();
                if (picModels != null && picModels.size() != 0) {
                    for (String picModel : picModels) {
                        ImageInfo imageInfo = new ImageInfo();
                        imageInfo.setThumbnailUrl(picModel);
                        imageInfo.setBigImageUrl(picModel);
                        imageInfoList.add(imageInfo);
                    }
                }
                holder.setNineGridAdapter(R.id.hobby_img, new NineGridViewClickAdapter(context, imageInfoList));
            }
        };

        hot.setAdapter(mAdapter);
        hot.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        hot.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.hobby_head, null));
        hot.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        hot.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        hot.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        hot.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                loadData(true);
//                SystemClock.sleep(3000);
//                hot.refreshComplete();//刷新完成
            }

            @Override
            public void onLoadMore() {
//                getSomeData();
//                loadData(false);
//                SystemClock.sleep(3000);
//                hot.loadMoreComplete();//加载更多完成
            }
        });
    }

    private void loadData(final boolean isRefresh) {
        if(isRefresh){
            startCount = 0;
        }else{
            startCount += count;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_hobby:{
                startActivity(new Intent(getContext(), HobbySelectActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        list.clear();
    }
}