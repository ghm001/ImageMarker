package com.team.imagemarker.fragments.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.home.DetailGroupActivity;
import com.team.imagemarker.activitys.mark.ErrorCollectionActivity;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.activitys.user.UserSearchActivity;
import com.team.imagemarker.adapters.home.ErrorCollectionAdapter;
import com.team.imagemarker.adapters.imgnav.GridViewLikeAdapter;
import com.team.imagemarker.adapters.imgnav.HotCategroyAdapter;
import com.team.imagemarker.adapters.imgnav.LikeViewPagerAdapter;
import com.team.imagemarker.bases.OnItemActionListener;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.MarkerModel;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.entitys.home.appAbnormalChangeModel;
import com.team.imagemarker.navbanner.BannerLayout;
import com.team.imagemarker.navbanner.GlideImageLoader;
import com.team.imagemarker.utils.EditTextWithDel;
import com.team.imagemarker.utils.MyGridView;
import com.team.imagemarker.utils.scrollview.MyScrollView;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by Lmy on 2017/4/28.
 * email 1434117404@qq.com
 */

public class ImageNavFragment extends Fragment implements View.OnClickListener, MyScrollView.ScrollViewListener, SwipeRefreshLayout.OnRefreshListener, OnItemActionListener {
    private View view;

    private UserModel userModel;

    //搜索框与banner
    private SwipeRefreshLayout refreshLayout;
    private int bannerHeight;//banner高度
    private MyScrollView navScrollView;
    private BannerLayout topBanner;
    private LinearLayout titleBarRoot;
    private RelativeLayout titleBar;
    private EditTextWithDel userSearch;

    //热门分类
    private MyGridView hotCategory;
    private List<CategoryModel> hotList = new ArrayList<>();
    private HotCategroyAdapter hotCategroyAdapter;

    //猜你喜欢
    private ViewPager likeViewPager;
    private LinearLayout dot;//指示器
    private List<View> pagerList;
    private List<CategoryModel> likeList = new ArrayList<>();
    private List<MarkerModel> guessItemList = new ArrayList<>();
    private LayoutInflater inflater;
    private LikeViewPagerAdapter likeViewPagerAdapter;
    private int pageCount;//总页数
    private int pageSize = 4;//每页显示的条目数
    private int currentIndex = 0;//显示当前页数

//    //精选种类
//    private ListView selectListView;
//    private static List<SelectCategoryModel> selectCategoryList = new ArrayList<SelectCategoryModel>();;
//    private SelectCateGoryAdapter selectCateGoryAdapter;

    //图片纠错
    private MyGridView errorGridView;
    private ErrorCollectionAdapter errorCollectionAdapter;
    private List<appAbnormalChangeModel> errorList = new ArrayList<>();


    private static List<MarkerModel> itemList = new ArrayList<>();

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    inflater = LayoutInflater.from(getContext());
                    pageCount = (int) Math.ceil(guessItemList.size() * 1.0 / pageSize);//计算总页数
                    Log.e(TAG, "handleMessage: 当前页数：" + pageCount);
                    pagerList = new ArrayList<>();
                    for (int i = 0; i < pageCount; i++) {
                        GridView gridView = (GridView) inflater.inflate(R.layout.like_gridview, likeViewPager, false);
                        gridView.setAdapter(new GridViewLikeAdapter(getContext(), guessItemList, i, pageSize));
                        pagerList.add(gridView);

                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                int pos = position + currentIndex * pageSize;
                                Intent intent = new Intent(getContext(), MarkHomeActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("pageTag", "imgNavPage");
                                bundle.putSerializable("item", guessItemList.get(pos));
                                intent.putExtras(bundle);
                                startActivity(intent);
                                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            }
                        });
                    }
                    likeViewPagerAdapter = new LikeViewPagerAdapter(pagerList);
                    likeViewPager.setAdapter(likeViewPagerAdapter);
                    //设置指示器
                    setOvalLayout(pageCount);
                }
                break;
                case 2:{
                    errorCollectionAdapter = new ErrorCollectionAdapter(getContext(), errorList);
                    errorGridView.setAdapter(errorCollectionAdapter);
//                    errorGridView.setOnItemClickListener(ImageNavFragment.this);
                    errorCollectionAdapter.setOnItemActionListener(ImageNavFragment.this);


                }
                break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image_nav, null);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.img_nav_refresh);
        topBanner = (BannerLayout) view.findViewById(R.id.top_banner);
        navScrollView = (MyScrollView) view.findViewById(R.id.nav_scrollview);
        titleBarRoot = (LinearLayout) view.findViewById(R.id.search_bar_root);
        titleBar = (RelativeLayout) view.findViewById(R.id.search_bar);
        userSearch = (EditTextWithDel) view.findViewById(R.id.user_search);

        hotCategory = (MyGridView) view.findViewById(R.id.hot_category);

        likeViewPager = (ViewPager) view.findViewById(R.id.like_viewpager);
        dot = (LinearLayout) view.findViewById(R.id.viewpager_indicator);

//        selectListView = (ListView) view.findViewById(R.id.select_category);
        errorGridView = (MyGridView) view.findViewById(R.id.error_correction);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UserDbHelper.setInstance(getContext());
        userModel = UserDbHelper.getInstance().getUserInfo();

//        getDataFromToHotCategroy();//热门分类数据
        getDataFromToGuessYouLike();//猜你喜欢
//        getDataFromToSelectCategroy();
        getDataFromToErrorCollection();//图片纠错
        //初始化刷新控件
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
        refreshLayout.setProgressBackgroundColor(R.color.theme);
        refreshLayout.setProgressViewOffset(false,0,200);
        refreshLayout.setOnRefreshListener(this);

        titleBarRoot.setGravity(Gravity.CENTER);
        userSearch.setEnabled(false);
        titleBarRoot.setOnClickListener(this);
        setTopBanner();//viewpager滚动
        setHotCateGroy();//热门分类
        setLikeViewpager();//猜你喜欢
//        setSelectCategory();//精选分类
    }

    /**
     * 设置顶部banner
     */
    private void setTopBanner() {
        List<String> bannerList = new ArrayList<>();
        bannerList.add(Constants.bannerImg[0]);
        bannerList.add(Constants.bannerImg[1]);
        bannerList.add(Constants.bannerImg[2]);
        bannerList.add(Constants.bannerImg[3]);
        bannerList.add(Constants.bannerImg[4]);
        bannerList.add(Constants.bannerImg[5]);
        topBanner.setImageLoader(new GlideImageLoader());
        topBanner.setViewUrls(bannerList);
        topBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

        //获取顶部图片高度后，获取滚动监听
        ViewTreeObserver treeObserver = topBanner.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                topBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bannerHeight = topBanner.getHeight();
                navScrollView.setScrollViewListener(ImageNavFragment.this);

            }
        });
    }

    private void setHotCateGroy() {
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg1, R.mipmap.hot1, "食物"));
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg2, R.mipmap.hot2, "学习"));
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg3, R.mipmap.hot3, "动物"));
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg4, R.mipmap.hot4, "植物"));
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg5, R.mipmap.hot5, "生活"));
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg6, R.mipmap.hot6, "风景"));

        hotCategroyAdapter = new HotCategroyAdapter(getActivity(), hotList);
        hotCategory.setAdapter(hotCategroyAdapter);
        hotCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailGroupActivity.class);
                intent.putExtra("type", hotList.get(position).getName());
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void setLikeViewpager() {
        String url = Constants.Hobby_Nav_Guess_You_Like;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userModel.getId()));
        VolleyRequestUtil.RequestPost(getContext(), url, "guessYouLike", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.optJSONArray("picture");
                    Gson gson = null;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.optJSONObject(i);
                        gson = new Gson();
                        MarkerModel model = gson.fromJson(object1.toString(), MarkerModel.class);
                        guessItemList.add(model);
                    }
                    handler.sendEmptyMessageDelayed(1, 200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("ImageNavFragment", "onError: setLikeViewpager:" + error.toString());
                setLikeViewpager();
            }
        });
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                guessItemList.clear();
                setLikeViewpager();
                refreshLayout.setRefreshing(false);
            }
        }, 1500);
    }

    /**
     * 设置指示器(猜你喜欢)
     */
    private void setOvalLayout(int page) {
        if (page == 0){
            return;
        }
        dot.removeAllViews();
        for (int i = 0; i < page; i++) {
            dot.addView(inflater.inflate(R.layout.dot, null));
        }
        dot.getChildAt(0).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_selected);
        likeViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 取消圆点选中
                dot.getChildAt(currentIndex).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                dot.getChildAt(position).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_selected);
                currentIndex = position;
            }

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_bar_root:{
                startActivity(new Intent(getActivity(), UserSearchActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
    }

    /**
     * 滑动监听
     */
    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {   //设置标题的背景颜色
            titleBar.setBackgroundColor(Color.argb((int) 0, 37,46,57));
        } else if (y > 0 && y <= bannerHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / bannerHeight;
            float alpha = (255 * scale);
            titleBar.setBackgroundColor(Color.argb((int) alpha, 37,46,57));
        } else {    //滑动到banner下面设置普通颜色
            titleBar.setBackgroundColor(Color.argb((int) 255, 37,46,57));
        }
    }

    private void getDataFromToGuessYouLike() {
        String url = Constants.GUESS_YOU_LIKE_DATA;
        VolleyRequestUtil.RequestGet(getContext(), url, "guessYouLike", new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    JSONArray array = object.optJSONArray("picture");
                    Gson gson = null;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.optJSONObject(i);
                        gson = new Gson();
                        MarkerModel model = gson.fromJson(object1.toString(), MarkerModel.class);
                        guessItemList.add(model);
                        likeViewPagerAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), ErrorCollectionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("errorItem", (Serializable) errorList.get(position));
        intent.putExtras(bundle);
        startActivityForResult(intent, 10);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void getDataFromToErrorCollection(){
        String url = Constants.Hobby_Nav_Img_Error;
        VolleyRequestUtil.RequestGet(getContext(), url, "getErrorImg", new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.optJSONArray("errorPicture");
                    errorList = stringToArrayThree(array.toString(),appAbnormalChangeModel[].class);
                    handler.sendEmptyMessage(2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                getDataFromToErrorCollection();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode == RESULT_OK && data != null){
            getDataFromToErrorCollection();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(guessItemList != null){
            guessItemList = null;
        }
        if(hotList != null){
            hotList = null;
        }
    }

    public static <T> List<T> stringToArrayThree(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }
}