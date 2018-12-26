package com.team.imagemarker.activitys.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.adapters.home.DetailGroupAdapter;
import com.team.imagemarker.bases.BaseActivity;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.entitys.MarkerModel;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailGroupActivity extends BaseActivity implements View.OnClickListener{
    private TextView title, subTitle;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private ListView detailGroup;
    private List<MarkerModel> list = new ArrayList<>();
    private DetailGroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_group);

        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        subTitle = (TextView) findViewById(R.id.sub_title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        detailGroup = (ListView) findViewById(R.id.detail_group_list);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("图组选择");
        subTitle.setVisibility(View.INVISIBLE);
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
        getDataFromNet();

        detailGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DetailGroupActivity.this, MarkHomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pageTag", "imgNavPage");
                bundle.putSerializable("item", list.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
                DetailGroupActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void getDataFromNet() {
        String url = Constants.Hobby_Nav_Hot_Category;
        Map<String, String> map = new HashMap<String, String>();
        map.put("Flag", getIntent().getStringExtra("type"));
        Log.e("tag", "getDataFromNet: 当前得到的种类为：" + getIntent().getStringExtra("type"));
        VolleyRequestUtil.RequestPost(this, url, "guessYouLike", map, new VolleyListenerInterface() {
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
                        list.add(model);
                    }
                    adapter = new DetailGroupAdapter(DetailGroupActivity.this, list);
                    detailGroup.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("DetailGroupActivity", "onError: getDataFromNet:" + error.toString());
                getDataFromNet();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(list != null){
            list = null;
        }
    }
}
