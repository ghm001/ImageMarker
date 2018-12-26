package com.team.imagemarker.activitys.user;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.bases.BaseActivity;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.opinion;
import com.team.imagemarker.utils.PaperButton;
import com.team.imagemarker.utils.ToastUtil;
import com.team.imagemarker.utils.WavyLineView;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户反馈
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class FeedBackActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout titleBar;
    private TextView title;
    private ImageView leftIcon, rightIcon;

    private PaperButton submit;
    private WavyLineView mWavyLine;
    private ToastUtil toastUtil = new ToastUtil();

    private UserModel userModel;

    private EditText feedBackContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        bindView();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        mWavyLine = (WavyLineView) findViewById(R.id.release_wavyLine);
        submit = (PaperButton) findViewById(R.id.submit_feed_back);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("意见反馈");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
        submit.setOnClickListener(this);

        feedBackContent = (EditText) findViewById(R.id.feed_back_content);

        // 波浪线设置
        mWavyLine.setColor(Color.parseColor("#ef6c00"));
        mWavyLine = (WavyLineView) findViewById(R.id.release_wavyLine);
        mWavyLine.setPeriod((float) (2 * Math.PI / 60));
        mWavyLine.setAmplitude(5);
        mWavyLine.setStrokeWidth(2);//ScreenUtil.dp2px(initStrokeWidth)

        UserDbHelper.setInstance(this);
        userModel = UserDbHelper.getInstance().getUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.submit_feed_back:{
                if(!feedBackContent.getText().toString().equals("")){
                    sendContentToNet();
                }else{
                    Toast.makeText(this, "输入的内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    private void sendContentToNet(){
        String url = Constants.User_Center_Feed_Back;
        opinion opinionItem = new opinion();
        opinionItem.setId(0);
        opinionItem.setUptime("");
        opinionItem.setuId(userModel.getId());
        opinionItem.setTitle(feedBackContent.getText().toString());
        opinionItem.setUserImageUrl(userModel.getUserHeadImage());
        opinionItem.setUserNickname(userModel.getUserNickName());
        Gson gson = new Gson();
        String feedBackInfo = gson.toJson(opinionItem);
        Map<String, String> map = new HashMap<>();
        map.put("opinion", feedBackInfo);
        VolleyRequestUtil.RequestPost(this, url, "sendFeedBack", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                toastUtil.Short(FeedBackActivity.this, "提交成功").show();
                FeedBackActivity.this.finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FeedBackActivity.this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
