package com.team.imagemarker.activitys.mark;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.bases.BaseActivity;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.home.appAbnormalChangeModel;
import com.team.imagemarker.utils.tag.TagColor;
import com.team.imagemarker.utils.tag.TagGroup;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;
import com.team.loading.SweetAlertDialog;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ErrorCollectionActivity extends BaseActivity implements View.OnClickListener{
    private TextView title, subTitle;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private ImageView showErrorImg;
    private TagGroup showModefyTag;
    private TagGroup showErrorTag;
    private TextView showMarkNum;

    private String[] tempTags;

    private static appAbnormalChangeModel model;

    private UserModel userModel;

    private Handler handler  =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    new SweetAlertDialog(ErrorCollectionActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("图片纠错完成")
                            .setContentText("请选择您的操作！")
                            .setCancelText("提 交")
                            .setConfirmText("取 消")
                            .showConfirmButton(true)
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sendModefyEdData();
                                    sDialog.setTitleText("提交成功")
                                            .setContentText("已赠送30积分到您的账户!")
                                            .showConfirmButton(false)
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    Timer timer=new Timer();
                                    timer.schedule(new wait(sDialog), 3000);
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
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_collection);
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        subTitle = (TextView) findViewById(R.id.sub_title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        showErrorImg = (ImageView) findViewById(R.id.error_img);
        showModefyTag = (TagGroup) findViewById(R.id.modify_tag);
        showErrorTag = (TagGroup) findViewById(R.id.error_tag);
        showMarkNum = (TextView) findViewById(R.id.mark_num);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("图片纠错");
        subTitle.setText("完成");
        rightIcon.setVisibility(View.GONE);
        subTitle.setVisibility(View.VISIBLE);
        leftIcon.setOnClickListener(this);
        subTitle.setOnClickListener(this);

        UserDbHelper.setInstance(this);
        userModel = UserDbHelper.getInstance().getUserInfo();

        setData();
        getModifyTag();
    }

    private void setData() {
        model = (appAbnormalChangeModel) getIntent().getExtras().getSerializable("errorItem");
        Glide.with(this)
                .load(model.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(showErrorImg);
        showModefyTag.setTags(TagColor.getRandomColors(7), model.getLabels());
        showErrorTag.setTags(TagColor.getRandomColors(7), model.getAbnormalsLabel());
        showMarkNum.setText(model.getNum() + "人");
    }

    private void getModifyTag() {
        showModefyTag.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
            /**
             * 如果是新增加标签就在末尾添加
             */
            @Override
            public void onAppend(TagGroup tagGroup, String tag) {
                tempTags = model.getLabels();//保存先前的标签
                tempTags = insertTag(tempTags, tag);//将新打的标签插入到原先的标签数组中
                model.setLabels(tempTags);
                tempTags = null;//清空数组
            }

            /**
             * 如果用户删除了一个标签，则将当前位置放 “”
             */
            @Override
            public void onDelete(TagGroup tagGroup, String tag) {
                tempTags = model.getLabels();
                tempTags = deleteTag(tempTags, tag);
                model.setLabels(tempTags);
                tempTags = null;
            }
        });
    }

    /**
     * 添加标签
     */
    private static String[] insertTag(String[] arr, String str) {
        int size = arr.length;
        String[] tmp = new String[size + 1];
        System.arraycopy(arr, 0, tmp, 0, size);
        tmp[size] = str;
        return tmp;
    }

    /**
     * 删除标签
     */
    private static String[] deleteTag(String[] arr, String str){
        List<String> tags = new LinkedList<String>();
        for(String tag : arr){
            tags.add(tag);
        }
        for (int i = 0; i < tags.size(); i++) {
            if(tags.get(i).equals(str)){
                tags.remove(str);
                tags.add(i, "");
            }
        }
        String[] newTags = {};
        return tags.toArray(newTags);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.sub_title:{
                handler.sendEmptyMessage(1);
            }
            break;
        }
    }

    private void sendModefyEdData() {
        String url = Constants.Img_Error_Collection;
        Gson gson = new Gson();
        model.setUserId(userModel.getId());
        String itemJson = gson.toJson(model);
        Map<String, String> map = new HashMap<>();
        map.put("change", itemJson);
        VolleyRequestUtil.RequestPost(this, url, "sendModefyLabel", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    class wait extends TimerTask {
        private SweetAlertDialog sDialog;

        public wait(SweetAlertDialog sDialog){
            this.sDialog = sDialog;
        }

        @Override
        public void run() {
            sDialog.dismiss();
            Intent intent = new Intent();
            ErrorCollectionActivity.this.setResult(RESULT_OK, intent);
            ErrorCollectionActivity.this.finish();
            ErrorCollectionActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
        }
    }
}
