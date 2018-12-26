package com.team.imagemarker.activitys.integral;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.adapters.DetailIntegralAdapter;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.IntegralHistory;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IntegralDetail extends Activity implements View.OnClickListener{
    private TextView title;
    private ImageView leftIcon,rightIcon;
    private RelativeLayout titleBar;

    private ListView listView;
    private DetailIntegralAdapter adapter;
    private List<IntegralHistory> list;

    private UserModel userModel;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    adapter = new DetailIntegralAdapter(IntegralDetail.this, list);
                    listView.setAdapter(adapter);
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_detail);
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        title.setText("积分明细");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.integral_detail);

        UserDbHelper.setInstance(this);
        userModel = UserDbHelper.getInstance().getUserInfo();

        setData();
    }

    private void setData() {
        String url = Constants.Integral_Shopping_Score;
        Map<String, String> map = new HashMap<>();
        map.put("userId", userModel.getId() + "");
        VolleyRequestUtil.RequestPost(this, url, "getIntegralDetail", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                list = stringToArrayFour(result.toString(),IntegralHistory[].class);
                Log.e("tag", "onSuccess: " + list);
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public static <T> List<T> stringToArrayFour(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr);
    }
}
