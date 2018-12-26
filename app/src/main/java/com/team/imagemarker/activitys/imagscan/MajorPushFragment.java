package com.team.imagemarker.activitys.imagscan;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.imagemarker.R;
import com.team.imagemarker.activitys.home.DividerItemDecoration;
import com.team.imagemarker.adapters.home.MoreCateGoryAdaper;
import com.team.imagemarker.entitys.home.CateGoryInfo;
import com.team.imagemarker.entitys.home.SelectCategoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 领域推送
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public class MajorPushFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View view;
    private static final String ARG_TITLE = "title";
    private String mTitle;

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView moreCategory;
    private List<SelectCategoryModel> list = new ArrayList<>();
    private MoreCateGoryAdaper adapter;

    public static MajorPushFragment getInstance(String title){
        MajorPushFragment fragment =  new MajorPushFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more_category, null);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.more_category_refresh);
        moreCategory = (RecyclerView) view.findViewById(R.id.more_category_recycleview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
        refreshLayout.setProgressBackgroundColor(R.color.theme);
        refreshLayout.setOnRefreshListener(this);
        setData();
    }

    private void setData() {
        List<CateGoryInfo> itemList = new ArrayList<>();
        itemList.add(new CateGoryInfo("种类一", "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-21/狗-海滩-海水-日落.jpg"));
        itemList.add(new CateGoryInfo("种类二", "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-22/橙子-水杯-果盘-果汁.jpg"));
        itemList.add(new CateGoryInfo("种类三", "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-22/樱桃-蛋糕-花-篮子-甜点-水果.jpg"));
        itemList.add(new CateGoryInfo("种类四", "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-22/冰淇淋-草莓-勺子-奶油蛋糕.jpg"));
        itemList.add(new CateGoryInfo("种类五", "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-22/草莓-蓝莓-橙子-猕猴桃-盘子.jpg"));
        itemList.add(new CateGoryInfo("种类一", "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-22/香蕉-草地-小花.jpg"));
        itemList.add(new CateGoryInfo("种类二", "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-21/白云岩-高山-水-天空-树.jpg"));
        itemList.add(new CateGoryInfo("种类三", "http://obs.myhwclouds.com/look.admin.image/华为/2017-5-21/天空-云朵-船-树-湖水.jpg"));
        list.add(new SelectCategoryModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-21/天空-云朵-船-树-湖水.jpg", "山水美景", itemList));
        list.add(new SelectCategoryModel("http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-21/狗-海滩-海水-日落.jpg", "山水美景", itemList));
        list.add(new SelectCategoryModel("http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-21/高山-树木-建筑-草地-天空.jpg", "山水美景", itemList));
        list.add(new SelectCategoryModel("http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-20/洛杉矶-市中心-音乐厅.jpg", "山水美景", itemList));
        list.add(new SelectCategoryModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-计算机-树木-草坪.jpg", "山水美景", itemList));

        list.add(new SelectCategoryModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-21/天空-云朵-船-树-湖水.jpg", "山水美景", itemList));
        list.add(new SelectCategoryModel("http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-21/狗-海滩-海水-日落.jpg", "山水美景", itemList));
        list.add(new SelectCategoryModel("http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-21/高山-树木-建筑-草地-天空.jpg", "山水美景", itemList));
        list.add(new SelectCategoryModel("http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-20/洛杉矶-市中心-音乐厅.jpg", "山水美景", itemList));
        list.add(new SelectCategoryModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-计算机-树木-草坪.jpg", "山水美景", itemList));
        adapter = new MoreCateGoryAdaper(getContext(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        moreCategory.setLayoutManager(layoutManager);
        moreCategory.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
        moreCategory.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);//刷新完成
            }
        }, 1000);
    }
}