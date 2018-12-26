package com.team.imagemarker.activitys.imagscan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.adapters.imgscan.DayRecommendAdapter;
import com.team.imagemarker.bases.OnItemActionListener;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.imgscan.BrowsePictuerModel;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lmy on 2017/5/27.
 * email 1434117404@qq.com
 */

public class DayRecommendFragment extends Fragment implements OnItemActionListener, SwipeRefreshLayout.OnRefreshListener{
    private static final String ARG_TITLE = "title";
    private String mTitle;
    private RecyclerView recyclerView;
    private DayRecommendAdapter adapter;

    private SwipeRefreshLayout refreshLayout;

    private List<List<BrowsePictuerModel>> detailList=new ArrayList<List<BrowsePictuerModel>>();

    private UserModel userModel;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    adapter = new DayRecommendAdapter(getContext(), detailList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL_LIST));
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemActionListener(DayRecommendFragment.this);
                }
                break;
            }
        }
    };

    public static DayRecommendFragment getInstance(String title) {
        DayRecommendFragment fra = new DayRecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = bundle.getString(ARG_TITLE);

        UserDbHelper.setInstance(getContext());
        userModel = UserDbHelper.getInstance().getUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day_recommend, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.day_recommend);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh_img);
        return v;
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

    private void getDataFromNet(){
        String url = Constants.Img_Scan_Every_Day_Push;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userModel.getId()));
        VolleyRequestUtil.RequestPost(getContext(), url, "DayRecommendFragment", map, new VolleyListenerInterface() {
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
                Log.e("DayRecommendFragment", "onError:" + error.toString());
                getDataFromNet();
            }
        });
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }

    @Override
    public void OnItemClickListener(View view, int position) {
        Intent intent = new Intent(getContext(), LookPictureActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detailGroup", (Serializable) detailList.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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