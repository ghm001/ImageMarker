package com.team.imagemarker.activitys.integral;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.adapters.UserIntegralAdapter;
import com.team.imagemarker.bases.RefrshDataToAllHistory;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/6/20.
 * email 1434117404@qq.com
 */

public class RankFragment extends Fragment implements RefrshDataToAllHistory {
    private View view;
    private ListView listView;
    private UserIntegralAdapter adapter;
    private List<UserModel> userModelList = new ArrayList<>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    adapter = new UserIntegralAdapter(getContext(), userModelList);
                    listView.setAdapter(adapter);
                }
                break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rank, null);
        listView = (ListView) view.findViewById(R.id.user_integral_list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getSomeUserRank();

        ShoppingFragment.setRefrshDataToAllHistory(this);
    }

    private void getSomeUserRank(){
        String url = Constants.Integral_Shopping_All_User_Rank;
        VolleyRequestUtil.RequestGet(getContext(), url, "getAllUserInfomation", new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.optJSONArray("list");
                    Gson gson = null;
                    for (int i = 0; i < array.length(); i++) {
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
    public void refrshAllHistory() {
        userModelList.clear();
        getSomeUserRank();
    }
}
