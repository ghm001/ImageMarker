package com.team.imagemarker.fragments.history;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.adapters.history.ShowHistoryAdapter;
import com.team.imagemarker.bases.RefrshDataToAllHistory;
import com.team.imagemarker.bases.btnClickListener;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.MarkerModel;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.fragments.history.AllHistoryFragment.RefrshCompleteFragment;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;
import com.team.loading.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 已完成的历史记录
 * Created by Lmy on 2017/4/29.
 * email 1434117404@qq.com
 */

public class CompletFragment extends Fragment implements btnClickListener, SwipeRefreshLayout.OnRefreshListener, RefrshCompleteFragment, RefrshDataToAllHistory{
    private View view;
    private ListView listView;
    private ImageView noHistory;

    private static List<MarkerModel> list = new ArrayList<>();
    private ShowHistoryAdapter adapter;

    private SwipeRefreshLayout refreshLayout;

    private View customDialog;
    private TextView showMessage;
    private Button delete, cancel;
    private Dialog dialogOne, dialogTwo;

    public static RefrshDataToAllHistory refrshDataToAllHistoryTwo;

    public static void setRefrshDataToAllHistoryTwo(RefrshDataToAllHistory refrshDataToAllHistoryTwo) {
        CompletFragment.refrshDataToAllHistoryTwo = refrshDataToAllHistoryTwo;
    }

    private UserModel userModel;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:{
                    if(list.size() == 0){
                        noHistory.setVisibility(View.VISIBLE);
                    }else{
                        noHistory.setVisibility(View.GONE);
                    }
                    adapter = new ShowHistoryAdapter(getContext(), list, 1);//1表示已完成的记录
                    listView.setAdapter(adapter);
                    adapter.setListener(CompletFragment.this);
                }
                break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_complete_history, null);
        Log.e("tag", "onCreateView: 已完成");
        listView = (ListView) view.findViewById(R.id.complete_record_liseview);
        noHistory = (ImageView) view.findViewById(R.id.no_history);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.complete_record_fresh);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
        refreshLayout.setProgressBackgroundColor(R.color.theme);
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UserDbHelper.setInstance(getContext());
        userModel = UserDbHelper.getInstance().getUserInfo();
        getDataFromToHistory();
        AllHistoryFragment.setRefrshCompleteFragment(this);
        NoCompleteFragment.setRefrshCompleteHistory(this);
//        adapter.setListener(this);
    }

    /**
     * 继续操作
     * @param position 点击的位置
     */
    @Override
    public void btnEditClick(final int position) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("该次操作已经提交!")
                .setContentText("您可以选择查看此次操作!")
                .setCancelText("查 看")
                .setConfirmText("取 消")
                .showConfirmButton(true)
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        Intent intent = new Intent(getContext(), MarkHomeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("pageTag", "completeHistory");
                        bundle.putSerializable("completeData", list.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 删除记录
     * @param position 点击的位置
     */
    @Override
    public void btnDeleteClick(final View view, final int position) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("该次操作已经提交!")
                .setContentText("您可以删除此次操作!")
                .setCancelText("删 除")
                .setConfirmText("取 消")
                .showConfirmButton(true)
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.setTitleText("删除成功")
                                .setContentText("")
                                .showConfirmButton(false)
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        Timer timer=new Timer();
                        timer.schedule(new Wite(sDialog), 2000);
                        deleteHistory(view, position);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                })
                .show();
    }

    private void deleteHistory(final View view, final int position) {
        String url = Constants.USER_HISTORY_DELETE;
        Map<String, String> map = new HashMap<>();
        map.put("id", list.get(position).getId() + "");
        VolleyRequestUtil.RequestPost(getContext(), url, "deleteHistory", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                list.remove(position);
                adapter.notifyDataSetChanged();
                refrshDataToAllHistoryTwo.refrshAllHistory();
                if(list.size() == 0){
                    noHistory.setVisibility(View.VISIBLE);
                }else{
                    noHistory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    /**
     * 上拉刷新
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                getDataFromToHistory();
//                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    /**
     * 刷新当前Fragment的数据
     */
    @Override
    public void reFrshDataToComplete() {
        Log.e("tag", "reFrshDataToComplete: 已完成中的数据刷新了");
        list.clear();
        getDataFromToHistory();
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void refrshAllHistory() {
        list.clear();
        getDataFromToHistory();
    }

    class Wait extends TimerTask {
        @Override
        public void run() {
            dialogTwo.dismiss();
        }
    }

    private void getDataFromToHistory(){
        String url = Constants.USER_ALL_HISTORY;
        Map<String, String> userHistory = new HashMap<String, String>();
        userHistory.put("userId", String.valueOf(userModel.getId()));
        Log.e("tag", "getDataFromToHistory: 当前用户的ID为：" + userModel.getId());
        VolleyRequestUtil.RequestPost(getContext(), url, "completHistory", userHistory, new VolleyListenerInterface() {
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
                        if(model.getFlag().equals("T")){
                            list.add(model);
                        }
                        handler.sendEmptyMessage(2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("tag", "onError: " + error.toString());
                getDataFromToHistory();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(list != null){
            list.clear();
        }
    }
}