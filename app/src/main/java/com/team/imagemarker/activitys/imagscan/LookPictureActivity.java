package com.team.imagemarker.activitys.imagscan;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.adapters.SharePopBaseAdapter;
import com.team.imagemarker.adapters.imgscan.CommentAdapter;
import com.team.imagemarker.bases.BaseActivity;
import com.team.imagemarker.bases.RefrshDataToImgCollection;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.image.CommentInfoModel;
import com.team.imagemarker.entitys.imgscan.BrowsePictuerModel;
import com.team.imagemarker.entitys.imgscan.Comment;
import com.team.imagemarker.entitys.share.SharePopBean;
import com.team.imagemarker.utils.AdaptiveListView;
import com.team.imagemarker.utils.SoftInputMethodUtil;
import com.team.imagemarker.utils.ToastUtil;
import com.team.imagemarker.utils.WavyLineView;
import com.team.imagemarker.utils.tag.TagColor;
import com.team.imagemarker.utils.tag.TagGroup;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tencent.open.utils.Global.getContext;

/**
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public class LookPictureActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemClickListener{
    private TextView title;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private ViewPager imgViewPager;
    private TextView imgIndicator;
    private TagGroup imgTagGroup;
    private AdaptiveListView imgComment;
    private TextView sendComment;
    private EditText editTextComment;
    private int tagIndex = 0;
    private WavyLineView mWavyLine;

    private TextView noContent;

//    private List<LookDetailModel> list = new ArrayList<LookDetailModel>();
    private List<CommentInfoModel> commentList = new ArrayList<CommentInfoModel>();
    private CommentAdapter commentAdapter;

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private PopupWindow popupWindow;//弹窗视图
    private View menuView;//需要加载的视图
    private TextView shareImg;//一键分享
    private TextView collectionImg;//一键收藏
    private TextView setWallpaper;//设为壁纸

    private PopupWindow pw;
    private View popView;
    private GridView gv;
    private TextView carName;
    private String[] names = {"微信", "朋友圈", "QQ", "QQ空间", "新浪"};
    private int[] iconId = {R.mipmap.wechat_share, R.mipmap.friend_share, R.mipmap.qq_share,
            R.mipmap.qq_kongjian_share, R.mipmap.sina_share};
    private List<SharePopBean> shareBeanList = new ArrayList<SharePopBean>();
    private SharePopBaseAdapter shareBaseAdapter;
    private ToastUtil toastUtil = new ToastUtil();

    private List<BrowsePictuerModel> detailList = new ArrayList<>();
    private static int currentPosition = 0;//当前图片的位置

    public static RefrshDataToImgCollection refrshDataToImgCollection;

    public static void setRefrshDataToImgCollection(RefrshDataToImgCollection refrshDataToImgCollection) {
        LookPictureActivity.refrshDataToImgCollection = refrshDataToImgCollection;
    }

    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_picture);
        popView= LayoutInflater.from(this).inflate(R.layout.share_grid, null);
        menuView = LayoutInflater.from(this).inflate(R.layout.look_picture_select_menu, null);
        bindView();//初始化视图
        setData();//加载数据
        SoftInputMethodUtil.HideSoftInput(editTextComment.getWindowToken());//隐藏软键盘
        setShareApp();
        UserDbHelper.setInstance(this);
        userModel = UserDbHelper.getInstance().getUserInfo();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        imgViewPager = (ViewPager) findViewById(R.id.detail_image);
        imgIndicator = (TextView) findViewById(R.id.image_indicator);
        imgTagGroup = (TagGroup) findViewById(R.id.image_tag);
        imgComment = (AdaptiveListView) findViewById(R.id.detail_listview);
        sendComment = (TextView) findViewById(R.id.detail_send);
        editTextComment = (EditText) findViewById(R.id.detail_edit);

        noContent = (TextView) findViewById(R.id.no_content);

        shareImg = (TextView) menuView.findViewById(R.id.share_img);
        collectionImg = (TextView) menuView.findViewById(R.id.collection_img);
        setWallpaper = (TextView) menuView.findViewById(R.id.set_wallpaper);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("图组详情");
        rightIcon.setImageResource(R.mipmap.three_more);
        leftIcon.setOnClickListener(this);
        rightIcon.setOnClickListener(this);
        editTextComment.setOnTouchListener(this);
        sendComment.setOnClickListener(this);

        shareImg.setOnClickListener(this);
        collectionImg.setOnClickListener(this);
        setWallpaper.setOnClickListener(this);

        mWavyLine = (WavyLineView) findViewById(R.id.release_wavyLine);
        mWavyLine.setPeriod((float) (2 * Math.PI / 60));
        mWavyLine.setAmplitude(5);
        mWavyLine.setStrokeWidth(2);
    }

    private void setData() {
        Bundle bundle = getIntent().getExtras();
        detailList = (List<BrowsePictuerModel>) bundle.getSerializable("detailGroup");

        imgViewPager.setOffscreenPageLimit(3);
        imgViewPager.setAdapter(new PictureScanAdapter());
        imgViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                updateIndicator(position);//当滑动时指示器改变，并且标签变化
                updateUserComment(position);//更新用户的评论
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        updateIndicator(imgViewPager.getCurrentItem());//为第一张图片设置标签，并添加指示器
        updateUserComment(0);//更新用户的评论
    }

    /**
     * 图片浏览的指示器
     * @param position
     */
    private void updateIndicator(int position){
        int currentItem = imgViewPager.getCurrentItem() + 1;//得到当前ViewPager的位置
        int totalNum = imgViewPager.getAdapter().getCount();//得到总数
        imgIndicator.setText(String.valueOf(currentItem) + "/" + String.valueOf(totalNum));
        setImgTag(position);//设置标签
    }

    /**
     * 为每一张图片设置标签
     * @param position
     */
    private void setImgTag(int position) {
        List<TagColor> colors = TagColor.getRandomColors(7);//随机生成标签颜色
        imgTagGroup.setTags(colors, detailList.get(position).getLabel());
    }

    /**
     * 更新用户的评论
     * @param position
     */
    private void updateUserComment(int position) {
        if(detailList.get(position).getCommentList().size() == 0){
            noContent.setVisibility(View.VISIBLE);
            imgComment.setVisibility(View.GONE);

            //第一次、第一张图片没有评论是也要初始化adapter
            commentAdapter = new CommentAdapter(this, detailList.get(position).getCommentList());
            imgComment.setAdapter(commentAdapter);
            return;
        }
        imgComment.setVisibility(View.VISIBLE);
        noContent.setVisibility(View.GONE);
        commentAdapter = new CommentAdapter(this, detailList.get(position).getCommentList());
        imgComment.setAdapter(commentAdapter);
        commentAdapter.notifyDataSetChanged();
    }

    /**
     * 显示系统软键盘
     */
    private void showKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) editTextComment.getContext().getSystemService(INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editTextComment, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.right_icon:{
                initSelectMenu(v);
            }
            break;
            case R.id.detail_send:{
                String str=sdf.format(new Date());
                String commentContent = editTextComment.getText().toString();
                if(!commentContent.equals("")){
                    Comment comment = new Comment(0, userModel.getUserNickName(), commentContent,userModel.getUserHeadImage(), detailList.get(currentPosition).getImageId(), str);
                    sendUserComment(comment);
                    detailList.get(currentPosition).getCommentList().add(0, comment);
                    commentAdapter.notifyDataSetChanged();
                    editTextComment.setText("");
                    updateUserComment(currentPosition);//更新当前用户评论
                }else{
                    toastUtil.Short(this, "评论内容不能为空!").show();
                }
            }
            break;
            case R.id.share_img:{//一键分享
                pw = getPopWindow(popView);
                popupWindow.dismiss();
            }
            break;
            case R.id.collection_img:{//收藏图片
                collectionImg();
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
            break;
            case R.id.set_wallpaper:{
                Toast.makeText(this, "设为壁纸", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
            break;
        }
    }

    /**
     * 收藏当前显示的图片
     */
    private void collectionImg() {
        String url = Constants.Img_Scan_Dtail_Group_Collection_Img;
        Map<String, String> map = new HashMap<>();
        map.put("userId", userModel.getId() + "");
        map.put("ImageUrl", detailList.get(currentPosition).getImageUrl());
        map.put("ImageId", detailList.get(currentPosition).getImageId() + "");
        VolleyRequestUtil.RequestPost(this, url, "collection", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "收藏成功");
                refrshDataToImgCollection.refrshData();
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("tag", "onError: 服务器内部异常");
            }
        });
    }

    /**
     * 发送用户的评论
     */
    private void sendUserComment(Comment comment) {
        String url = Constants.Img_Scan_Detail_Group_User_Comment;
        Gson gson = new Gson();
        String commentInfo = gson.toJson(comment);
        Map<String, String> map = new HashMap<>();
        map.put("Comment", commentInfo);
        VolleyRequestUtil.RequestPost(getContext(), url, "sendComment", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "onSuccess: 发送成功");
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("tag", "onError: 服务器连接错误");
            }
        });
    }

    /**
     * 选择菜单
     */
    private void initSelectMenu(View view) {
        popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(menuView);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        popupWindow.showAsDropDown(view, 0, 0);
    }

    /**
     *触摸编辑框弹出软键盘
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId() == R.id.detail_edit){
            showKeyboard();//弹出软键盘
        }
        return false;
    }

    @Override
    public void onBackPressed() {
            this.finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = shareBeanList.get(position).getName();
        if(TextUtils.equals(name, "微信")){
            Toast.makeText(this, "微信", Toast.LENGTH_SHORT).show();
//            Platform qq = ShareSDK.getPlatform(this, QQ.NAME);
//            QQ.ShareParams sp = new QQ.ShareParams();
//            sp.setShareType(Platform.SHARE_WEBPAGE);
//            sp.setTitle("老马识图——美图分享");
//            sp.setTitleUrl("http://139.199.23.142:8080/TestShowMessage1/car.apk");
//            qq.share(sp);
            pw.dismiss();
        }else if (TextUtils.equals(name, "朋友圈")){
            Toast.makeText(this, "朋友圈", Toast.LENGTH_SHORT).show();
//            SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
//            sp.setText("老马识图——美图分享");
//            sp.setImagePath("http://139.199.23.142:8080/TestShowMessage1/logo.png");
//            Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//            weibo.share(sp);
            pw.dismiss();
        }else if(TextUtils.equals(name, "QQ")){
            Toast.makeText(this, "QQ", Toast.LENGTH_SHORT).show();
            pw.dismiss();
        }else if(TextUtils.equals(name, "QQ空间")){
            Toast.makeText(this, "QQ空间分享", Toast.LENGTH_SHORT).show();
            pw.dismiss();
        }else if(TextUtils.equals(name, "新浪")){
            Toast.makeText(this, "新浪", Toast.LENGTH_SHORT).show();
            pw.dismiss();
        }
    }

    /**
     * 图片浏览适配器
     */
    class PictureScanAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return detailList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(LookPictureActivity.this);

//            imageView.setBackgroundResource(list.get(position).getImgId());

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(LookPictureActivity.this)
                    .load(detailList.get(position).getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 设置弹窗
     */
    private PopupWindow getPopWindow(View view){
        PopupWindow popupWindow=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.anim_popup_centerbar);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        backgroundAlpha((float) 0.5);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha((float) 1.0);
            }
        });
        return popupWindow;
    }

    /**
     * 背景半透明
     * @param bgAlpha
     */
    private void backgroundAlpha(float bgAlpha){
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }

    private void setShareApp() {
        shareBeanList.add(new SharePopBean(iconId[0], names[0]));
        shareBeanList.add(new SharePopBean(iconId[1], names[1]));
        shareBeanList.add(new SharePopBean(iconId[2], names[2]));
        shareBeanList.add(new SharePopBean(iconId[3], names[3]));
        shareBeanList.add(new SharePopBean(iconId[4], names[4]));

        gv=(GridView)popView.findViewById(R.id.share_grid);
        shareBaseAdapter = new SharePopBaseAdapter(this, shareBeanList);
        gv.setAdapter(shareBaseAdapter);
        gv.setOnItemClickListener(this);
    }
}