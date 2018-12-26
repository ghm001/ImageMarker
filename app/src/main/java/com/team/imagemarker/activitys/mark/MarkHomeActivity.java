package com.team.imagemarker.activitys.mark;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.bases.BaseActivity;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.MarkerModel;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.marker.ItemEntity;
import com.team.imagemarker.utils.marker.FadeTransitionImageView;
import com.team.imagemarker.utils.marker.HorizontalTransitionLayout;
import com.team.imagemarker.utils.marker.PileLayout;
import com.team.imagemarker.utils.tag.TagColor;
import com.team.imagemarker.utils.tag.TagGroup;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;
import com.team.loading.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lmy on 2017/5/18.
 * email 1434117404@qq.com
 */

public class MarkHomeActivity extends BaseActivity implements View.OnClickListener{
    private TextView title, subTitle;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private PileLayout pileLayout;
    private static List<ItemEntity> dataList = new ArrayList<>();

    private int lastDisplay = -1;

    private ObjectAnimator transitionAnimator;
    private float transitionValue;
    private HorizontalTransitionLayout countIndicator;
    private FadeTransitionImageView markImg;
    private Animator.AnimatorListener animatorListener;
    private TagGroup tag;
    private String[] tempTags;//暂存每张图片的先前的标签
    private List<TagColor> colors = TagColor.getRandomColors(15);//随机生成标签颜色

    private String pageFlag;//页面标志，用于判断是哪一个界面进入的
    private MarkerModel item = new MarkerModel();//用于最后提交的图片对象
    private UserModel userModel;//得到用户的信息

    public static RefrshData refrshIntegralFromMark;

    public static void setRefrshIntegral(RefrshData refrshIntegral) {
        MarkHomeActivity.refrshIntegralFromMark = refrshIntegral;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_home);
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        subTitle = (TextView) findViewById(R.id.sub_title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("图片标注");
        rightIcon.setVisibility(View.GONE);
        subTitle.setVisibility(View.VISIBLE);
        leftIcon.setOnClickListener(this);
        subTitle.setOnClickListener(this);

        countIndicator = (HorizontalTransitionLayout) findViewById(R.id.countIndicator);
        pileLayout = (PileLayout) findViewById(R.id.pileLayout);
        markImg = (FadeTransitionImageView) findViewById(R.id.mark_img);
        tag = (TagGroup) findViewById(R.id.image_tag);

        UserDbHelper.setInstance(this);
        userModel = UserDbHelper.getInstance().getUserInfo();

        initAnimationListener();//初始化动画属性

        Bundle bundle = getIntent().getExtras();
        pageFlag = bundle.getString("pageTag");//判断页面来源
        if (pageFlag.equals("completeHistory")){//历史记录已完成，查看标签
            tag.setAppendMode(false);//将标签转换成不可编辑状态
            title.setText("历史标注");
            subTitle.setVisibility(View.GONE);
            handelMessge((MarkerModel) bundle.getSerializable("completeData"));
//            dataList = (List<ItemEntity>) bundle.getSerializable("completeData");
        }else if(pageFlag.equals("noCompleteHistory")){//历史记录未完成，继续打标签
//            dataList = (List<ItemEntity>) bundle.getSerializable("noCompleteData");
            handelMessge((MarkerModel) bundle.getSerializable("noCompleteData"));
        }else if(pageFlag.equals("firstPage")){
            handelMessge((MarkerModel) bundle.getSerializable("item"));
        }else if(pageFlag.equals("imgNavPage")){
            handelMessge((MarkerModel) bundle.getSerializable("item"));
        }else if(pageFlag.equals("searchHobby")){
            dataList = (List<ItemEntity>) bundle.getSerializable("item");
        }else if(pageFlag.equals("userTask")){
            tag.setAppendMode(false);//将标签转换成不可编辑状态
            title.setText("历史标注");
            subTitle.setVisibility(View.GONE);
            handelMessge((MarkerModel) bundle.getSerializable("item"));
        }
        pileLayout.getInstance(this);
        pileLayout.setAdapter(new Adapter());//设置底部图片滚动数据
    }

    /**
     * 初始化动画属性
     */
    private void initAnimationListener() {
        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                countIndicator.onAnimationEnd();
                markImg.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        };
    }

    /**
     * 设置需要标注图片缩略图的适配器
     */
    class Adapter extends PileLayout.Adapter {

        @Override
        public int getLayoutId() {
            return R.layout.item_layout;
        }

        @Override
        public void bindView(View view, int position) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
                view.setTag(viewHolder);
            }
            Glide.with(MarkHomeActivity.this).load(dataList.get(position).getCoverImageUrl()).into(viewHolder.imageView);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        @Override
        public void displaying(final int position) {
            if (lastDisplay < 0) {
                initSecene(position);
                lastDisplay = 0;
            } else if (lastDisplay != position) {
                transitionSecene(position);
                lastDisplay = position;
            }

            //监听标签改变事件
            tag.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
                /**
                 * 添加标签
                 */
                @Override
                public void onAppend(TagGroup tagGroup, String tag) {
                    tempTags = dataList.get(position).getTags();//保存先前的标签
                    tempTags = insertTag(tempTags, tag);//将新打的标签插入到原先的标签数组中
                    dataList.get(position).setTags(tempTags);
                    tempTags = null;//清空数组
                }

                /**
                 * 删除标签
                 */
                @Override
                public void onDelete(TagGroup tagGroup, String tag) {
                    tempTags = dataList.get(position).getTags();
                    tempTags = deleteTag(tempTags, tag);
                    dataList.get(position).setTags(tempTags);
                    tempTags = null;
                }
            });
        }

        @Override
        public void onItemClick(View view, int position) {
            super.onItemClick(view, position);
        }
    }

    /**
     * 初始显示数据
     */
    private void initSecene(int position) {
        countIndicator.firstInit((position + 1) + "/" + dataList.size());
        markImg.firstInit(dataList.get(position).getCoverImageUrl());
        Log.e("tag", "initSecene: " + "颜色：" + colors.size() + "标签：" + dataList.get(position).getTags().length);
        tag.setTags(colors, dataList.get(position).getTags());
    }

    /**
     * 当前图片转换到下一张图片
     */
    private void transitionSecene(int position) {
        List<TagColor> tagColors = TagColor.getRandomColors(7);
        if (transitionAnimator != null) {
            transitionAnimator.cancel();
        }
        countIndicator.saveNextPosition(position, (position + 1) + "/" + dataList.size());
        markImg.saveNextPosition(position, dataList.get(position).getCoverImageUrl());
        tag.setTags(tagColors, dataList.get(position).getTags());

        transitionAnimator = ObjectAnimator.ofFloat(this, "transitionValue", 0.0f, 1.0f);
        transitionAnimator.setDuration(300);
        transitionAnimator.start();
        transitionAnimator.addListener(animatorListener);
    }

    /**
     * 从asset中读取文件json数据，模拟网络传输数据
     */
    private void initDataList() {
        try {
            InputStream in = getAssets().open("mark.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                    ItemEntity itemEntity = new ItemEntity(itemJsonObject);
                    dataList.add(itemEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 属性动画
     */
    public void setTransitionValue(float transitionValue) {
        this.transitionValue = transitionValue;
        countIndicator.duringAnimation(transitionValue);
        markImg.duringAnimation(transitionValue);
    }

    public float getTransitionValue() {
        return transitionValue;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.sub_title:{
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("图片标记完成")
                        .setContentText("请选择您的操作！")
                        .setCancelText("提 交")
                        .setConfirmText("保 存")
                        .showConfirmButton(true)
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                String imgUrl = Constants.USER_SUBMIT_TAG;
                                submitTags(imgUrl, true);//提交操作
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
                                String url = Constants.USER_SAVE_TAG;
                                submitTags(url, false);//保存操作
                                sDialog.setTitleText("保存成功")
                                        .setContentText("您可以在历史记录中查询，并继续修改")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                Timer timer=new Timer();
                                timer.schedule(new wait(sDialog), 3000);

                            }
                        })
                        .show();
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

    class ViewHolder{
        public ImageView imageView;
    }

    class wait extends TimerTask {
        private SweetAlertDialog sDialog;

        public wait(SweetAlertDialog sDialog){
            this.sDialog = sDialog;
        }

        @Override
        public void run() {
            sDialog.dismiss();
            if(pageFlag.equals("noCompleteHistory")){
                Log.e("执行了", "run: ");
                Intent intent = new Intent();
                MarkHomeActivity.this.setResult(RESULT_OK, intent);
                MarkHomeActivity.this.finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }else{
                MarkHomeActivity.this.finish();
                MarkHomeActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        }
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
        if(tags.contains(str)){
            tags.remove(str);
        }
        String[] newTags = {};
        return tags.toArray(newTags);
    }

    /**
     * 转换标签
     */
    private String changeTags(String[] tags){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < tags.length; i++) {
            buffer.append(tags[i] + "-");
        }
        return buffer.toString().equals("") || buffer == null ? "" : buffer.deleteCharAt(buffer.length() - 1).toString();
    }

    /**
     * 上传标签:提交、保存
     */
    private void submitTags(String url, final boolean flag) {
        if(flag){
            for (ItemEntity itemImag : dataList){
                if(itemImag.getTags() == null || itemImag.getTags().equals("")){
                    Toast.makeText(this, "您还有为标签化的图片，请全部完成后再提交!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        item.setUserId(userModel.getId());
        item.setLabel1(changeTags(dataList.get(0).getTags()));
        item.setLabel2(changeTags(dataList.get(1).getTags()));
        item.setLabel3(changeTags(dataList.get(2).getTags()));
        item.setLabel4(changeTags(dataList.get(3).getTags()));
        item.setLabel5(changeTags(dataList.get(4).getTags()));
        item.setLabel6(changeTags(dataList.get(5).getTags()));
        Log.e("tag", "submitTags: 用户提交的数据为: " + item.toString());
        Gson gson = new Gson();
        String imgMessage = gson.toJson(item);
        Map<String, String> submitMap = new HashMap<String, String>();
        submitMap.put("appImageGrouping", imgMessage);
        VolleyRequestUtil.RequestPost(this, url, "submitImgMessage", submitMap, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String tag = object.optString("tag");
                    if(tag.equals("success")){
//                        Toast.makeText(MarkHomeActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }

                    if(flag){
                        Log.e("tag", "submitTags: 执行了。。");
                        refrshIntegralFromMark.getIntegralFromMark();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    /**
     * 处理从各个页面传来的数据，并进行界面填充
     */
    private void handelMessge(MarkerModel markerModel){
        item = markerModel;
        dataList.add(new ItemEntity(item.getImageUrl1(), item.getLabel1().equals("") ? new String[]{} : item.getLabel1().split("-")));
        dataList.add(new ItemEntity(item.getImageUrl2(), item.getLabel2().equals("") ? new String[]{} : item.getLabel2().split("-")));
        dataList.add(new ItemEntity(item.getImageUrl3(), item.getLabel3().equals("") ? new String[]{} : item.getLabel3().split("-")));
        dataList.add(new ItemEntity(item.getImageUrl4(), item.getLabel4().equals("") ? new String[]{} : item.getLabel4().split("-")));
        dataList.add(new ItemEntity(item.getImageUrl5(), item.getLabel5().equals("") ? new String[]{} : item.getLabel5().split("-")));
        dataList.add(new ItemEntity(item.getImageUrl6(), item.getLabel6().equals("") ? new String[]{} : item.getLabel6().split("-")));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dataList != null){
            dataList.clear();
//            dataList = null;
        }
    }

    public static interface RefrshData{
        void getIntegralFromMark();
    }
}