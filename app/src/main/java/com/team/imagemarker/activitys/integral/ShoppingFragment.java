package com.team.imagemarker.activitys.integral;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.adapters.ShoppingAdapter;
import com.team.imagemarker.adapters.task.TaskBoxAdapter;
import com.team.imagemarker.bases.RefrshDataToAllHistory;
import com.team.imagemarker.bases.RefrshIntegral;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.fragments.history.Wite;
import com.team.imagemarker.utils.MyGridView;
import com.team.imagemarker.utils.WrappingGridLayoutManager;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;
import com.team.loading.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Lmy on 2017/6/20.
 * email 1434117404@qq.com
 */

public class ShoppingFragment extends Fragment {
    private View view;
    private CallBack callBack;

    private RecyclerView shopping;
    private List<CategoryModel> list;

    private MyGridView taskBox;
    private List<CategoryModel> systemPushList = new ArrayList<>();
    private TaskBoxAdapter adapterSystem;

    private FloatingActionButton luck;

    private UserModel userModel;

    private List<UserModel> userModelList = new ArrayList<>();

    //刷新积分排行
    public static RefrshDataToAllHistory refrshDataToAllHistory;

    public static void setRefrshDataToAllHistory(RefrshDataToAllHistory refrshDataToAllHistory) {
        ShoppingFragment.refrshDataToAllHistory = refrshDataToAllHistory;
    }

    //刷新个人中心用户的积分
    public static RefrshIntegral refrshIntegral;

    public static void setRefrshIntegral(RefrshIntegral refrshIntegral) {
        ShoppingFragment.refrshIntegral = refrshIntegral;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    adapterSystem = new TaskBoxAdapter(getContext(), userModelList);
                    taskBox.setAdapter(adapterSystem);
                    taskBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });
                    //刷新个人中心用户的积分
                    refrshIntegral.getIntegral();
                }
                break;
                case 2:{
                    //头部刷新
                    callBack.refresh();

                    //红人榜刷新
                    userModelList.clear();
                    getSomeUserRank();

                    //刷新积分排行
                    refrshDataToAllHistory.refrshAllHistory();
                }
                break;
            }
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBack = (CallBack) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping, null);
        taskBox = (MyGridView) view.findViewById(R.id.task_box);
        luck = (FloatingActionButton) view.findViewById(R.id.luck);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UserDbHelper.setInstance(getContext());
        userModel = UserDbHelper.getInstance().getUserInfo();
        bindView();
//        setTaskBox();
        luck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LuckDrawActivity.class);
                startActivityForResult(intent, 1);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                callBack.setData(10);
            }
        });

        getSomeUserRank();

    }

    private void bindView() {
        getDataFromNet();
        ShoppingAdapter shoppingAdapter = new ShoppingAdapter(getContext(), list);
        WrappingGridLayoutManager manager = new WrappingGridLayoutManager(getContext(),2);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        shopping = (RecyclerView) view.findViewById(R.id.shopping);
        shopping.setLayoutManager(manager);
        shopping.setNestedScrollingEnabled(false);
        shopping.setHasFixedSize(true);
        shopping.setAdapter(shoppingAdapter);
        shoppingAdapter.setOnItemClickListner(new ShoppingAdapter.ItemClickListener() {
            @Override
            public void onItemClick(final View view) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("积分兑换")
                        .setContentText("该件商品将消耗10积分，确认兑换吗？")
                        .setCancelText("确 认")
                        .setConfirmText("取 消")
                        .showConfirmButton(true)
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                submitRequest();
                                sDialog.setTitleText("兑换成功!")
                                        .setContentText("稍后我们将与您取得联系!")
                                        .showConfirmButton(false)
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                Timer timer=new Timer();
                                timer.schedule(new Wite(sDialog), 1500);
//                                integral.setText((Integer.parseInt(integral.getText().toString()) - 10) + "");
                                callBack.setData(10);
                                ((TextView) view).setText((Integer.parseInt(((TextView) view).getText().toString().substring(0, 1)) + 1) + "人已兑换");
                                ((TextView) view).setTextColor(Color.parseColor("#FF5C5D"));
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
        });
    }

    private void submitRequest() {
        String url = Constants.Integral_Shopping_Exchange_Commodity;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userModel.getId()));
        VolleyRequestUtil.RequestPost(getContext(), url, "getUserInformation", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "onSuccess: 兑换成功");
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onError(VolleyError error) {
                submitRequest();
            }
        });
    }

    private void getDataFromNet(){
        list = new ArrayList<>();
        list.add(new CategoryModel(R.mipmap.img1, "this is a taxt", "5人已兑换"));
        list.add(new CategoryModel(R.mipmap.img2, "this is a taxt", "5人已兑换"));
        list.add(new CategoryModel(R.mipmap.img3, "this is a taxt", "5人已兑换"));
        list.add(new CategoryModel(R.mipmap.img4, "this is a taxt", "5人已兑换"));
        list.add(new CategoryModel(R.mipmap.img1, "this is a taxt", "5人已兑换"));
        list.add(new CategoryModel(R.mipmap.img3, "this is a taxt", "5人已兑换"));
    }

    private void getSomeUserRank(){
        String url = Constants.Integral_Shopping_Top_Three;
        VolleyRequestUtil.RequestGet(getContext(), url, "getSomeUserInfomation", new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.optJSONArray("list");
                    Gson gson = null;
                    for (int i = 0; i < 3; i++) {
                        JSONObject object1 = array.optJSONObject(i);
                        gson = new Gson();
                        UserModel model = gson.fromJson(object1.toString(), UserModel.class);
                        userModelList.add(model);
                    }
                    handler.sendEmptyMessage(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                getSomeUserRank();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
//            callBack.setData(10);
            submitRequest();
        }
    }

    public interface CallBack{
        public void setData(int count);

        public void refresh();
    }
}
