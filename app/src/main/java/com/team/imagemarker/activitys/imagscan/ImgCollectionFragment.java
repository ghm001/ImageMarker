package com.team.imagemarker.activitys.imagscan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.team.imagemarker.R;
import com.team.imagemarker.adapters.task.ItemDecoration;
import com.team.imagemarker.adapters.task.PictureCollection;
import com.team.imagemarker.adapters.task.TimeLineAdapter;
import com.team.imagemarker.bases.RefrshDataToImgCollection;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.team.imagemarker.activitys.imagscan.DayRecommendFragment.stringToArray;

/**
 * 图片收藏
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public class ImgCollectionFragment extends Fragment implements RefrshDataToImgCollection {
    private View view;
    private static final String ARG_TITLE = "title";
    private String mTitle;

    private RecyclerView recyclerView;

    private List<PictureCollection> mList = new ArrayList<>();
    private TimeLineAdapter mAdapter;

    private List<PictureCollection> detailList = new ArrayList<PictureCollection>();
    private UserModel userModel;
//    private SwipeRefreshLayout refreshLayout;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    mAdapter = new TimeLineAdapter(getContext(), detailList);
                    recyclerView.setAdapter(mAdapter);
                }
                break;
            }
        }
    };

    public static ImgCollectionFragment getInstance(String title){
        ImgCollectionFragment fragment =  new ImgCollectionFragment();
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

        LookPictureActivity.setRefrshDataToImgCollection(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_img_look_img_collection, null);
//        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_collection);
        recyclerView = (RecyclerView) view.findViewById(R.id.img_collection_recycleview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
//        refreshLayout.setProgressBackgroundColor(R.color.theme);
//        refreshLayout.setProgressViewOffset(false,0,80);
//        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new ItemDecoration(getContext(), 50));
        getDataFromNet();
    }

    private void getDataFromNet(){
        String url = Constants.Img_Scan_My_Collection;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userModel.getId()));
        VolleyRequestUtil.RequestPost(getContext(), url, "ImgCollectionFragment", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    detailList= stringToArray(result.toString(),PictureCollection[].class);
                    Log.e("tag", "onSuccess: " + detailList.toString());
                    handler.sendEmptyMessage(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("ImgCollectionFragment", "onError: " + error.toString());
                getDataFromNet();
            }
        });
    }

    @Override
    public void refrshData() {
        getDataFromNet();
    }
}
