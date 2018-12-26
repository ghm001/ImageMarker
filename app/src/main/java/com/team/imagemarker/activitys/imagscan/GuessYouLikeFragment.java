package com.team.imagemarker.activitys.imagscan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.team.imagemarker.R;
import com.team.imagemarker.adapters.imgscan.PictureGroupAdapter;
import com.team.imagemarker.bases.OnItemActionListener;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.PictureGroupModel;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.imgscan.BrowsePictuerModel;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team.imagemarker.activitys.imagscan.DayRecommendFragment.stringToArray;


/**
 * 猜你喜欢
 * Created by Lmy on 2017/4/15.
 * email 1434117404@qq.com
 */

public class GuessYouLikeFragment extends Fragment implements View.OnClickListener, OnItemActionListener, SwipeRefreshLayout.OnRefreshListener{
    private View view;
    private static final String ARG_TITLE = "title";
    private String mTitle;
//    private FloatingActionButton toTop;
    private RecyclerView recyclerView;
    private List<PictureGroupModel> list = new ArrayList<PictureGroupModel>();
    private PictureGroupAdapter adapter;

    private List<List<BrowsePictuerModel>> detailList=new ArrayList<List<BrowsePictuerModel>>();
    private UserModel userModel;
    private SwipeRefreshLayout refreshLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    adapter = new PictureGroupAdapter(getContext(), detailList);
                    recyclerView.setHasFixedSize(true);//当确定数据的变化不会影响RecycleView布局的大小时，设置该属性提高性能
                    recyclerView.setAdapter(adapter);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//防止Item切换、闪烁、跳页现象
                    recyclerView.setLayoutManager(layoutManager);//使用不规则的网格布局，实现瀑布流效果
                    adapter.setOnItemActionListener(GuessYouLikeFragment.this);
                }
                break;
            }
        }
    };

    public static GuessYouLikeFragment getInstance(String title){
        GuessYouLikeFragment fragment = new GuessYouLikeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = bundle.getString(ARG_TITLE);

        UserDbHelper.setInstance(getContext());
        userModel = UserDbHelper.getInstance().getUserInfo();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_picture_group_scan, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.picture_group_recycle);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_guess_you_like);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
        refreshLayout.setProgressBackgroundColor(R.color.theme);
        refreshLayout.setProgressViewOffset(false,0,80);
        refreshLayout.setOnRefreshListener(this);
        getDataFromNet();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_top:{//回到顶部
                recyclerView.smoothScrollToPosition(0);
            }
            break;
        }
    }

    /**
     * recycleView中条目点击事件
     * @param view 操作的视图
     * @param position 数据的位置
     */
    @Override
    public void OnItemClickListener(View view, int position) {
        Intent intent = new Intent(getContext(), LookPictureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailGroup", (Serializable) detailList.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void getDataFromNet(){
        String url = com.team.imagemarker.constants.Constants.Img_Scan_Guess_You_Like;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userModel.getId()));
        VolleyRequestUtil.RequestPost(getContext(), url, "GuessYouLikeFragment", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {

                    JSONObject SH=new JSONObject(result);
                    JSONArray S=SH.optJSONArray("list");
                    for (int i = 0; i < S.length(); i++) {
                        JSONArray Q=S.getJSONArray(i);
                        List<BrowsePictuerModel> temp= stringToArray(Q.toString(),BrowsePictuerModel[].class);
                        detailList.add(temp);
                    }
                    handler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("GuessYouLikeFragment", "onError:" + error.toString());
                getDataFromNet();
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(detailList != null && detailList.size() > 0){
                    detailList.clear();
                    getDataFromNet();
                    refreshLayout.setRefreshing(false);
                }
            }
        }, 1500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(detailList != null){
            detailList.clear();
            detailList = null;
        }
    }
}