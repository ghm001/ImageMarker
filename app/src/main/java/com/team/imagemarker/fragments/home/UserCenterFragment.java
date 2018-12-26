package com.team.imagemarker.fragments.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.history.HistoryRecordActivity;
import com.team.imagemarker.activitys.hobby.HobbyActivity;
import com.team.imagemarker.activitys.home.MainActivity;
import com.team.imagemarker.activitys.imagscan.ImgScanMainActivity;
import com.team.imagemarker.activitys.integral.IntegralMainActivity;
import com.team.imagemarker.activitys.integral.ShoppingFragment;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.activitys.tasks.UserTaskActivity;
import com.team.imagemarker.activitys.user.FeedBackActivity;
import com.team.imagemarker.activitys.user.UpdateUserMessageActivity;
import com.team.imagemarker.adapters.SharePopBaseAdapter;
import com.team.imagemarker.bases.RefrshIntegral;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.share.SharePopBean;
import com.team.imagemarker.fragments.history.Wite;
import com.team.imagemarker.utils.CircleImageView;
import com.team.imagemarker.utils.DataCleanManager;
import com.team.imagemarker.utils.FileInfoUtils;
import com.team.imagemarker.utils.PaperButton;
import com.team.imagemarker.utils.ToastUtil;
import com.team.imagemarker.utils.scrollview.TranslucentScrollView;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;
import com.team.loading.SweetAlertDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static com.tencent.open.utils.Global.getFilesDir;

/**
 * Created by Lmy on 2017/4/28.
 * email 1434117404@qq.com
 */

public class UserCenterFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, RefrshIntegral, MarkHomeActivity.RefrshData {
    private View view, positionView;
    private static final int UPDATE_USER_MESSAGE = 1;
    private TextView tvName, tvIntegral;
    private CircleImageView headImg;

    private TranslucentScrollView translucentScrollView;
    private View zoomView;

    private RelativeLayout updateUserMessage, historyRecord, tasksAchieve, feedBack, shareApp, cleanCacheLayout;

//    private SlideSwitch systemPush;

    private PaperButton loginOut;
    private ImageView imageView;

    private TextView cleanCache;

    private LinearLayout imgScan, hobbyForum, integralMall;

    //分享App
    private PopupWindow pw;
    private View popView;
    private GridView gv;
    private TextView carName;
//    private String[] names = {"QQ", "新浪", "微信"};
    private String[] names = {"微信", "朋友圈", "QQ", "QQ空间", "新浪"};
    private int[] iconId = {R.mipmap.wechat_share, R.mipmap.friend_share,
            R.mipmap.qq_share, R.mipmap.qq_kongjian_share, R.mipmap.sina_share};
    private List<SharePopBean> shareBeanList = new ArrayList<SharePopBean>();
    private SharePopBaseAdapter shareBaseAdapter;

    private ToastUtil toastUtil = new ToastUtil();

    private UserModel userModel;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:{
                    showCacheSize();
                    handler.sendEmptyMessageDelayed(1, 10000);
                }
                break;
                case 2:{
                    Log.e("tag", "当前积分为：" + msg.obj);
                    tvIntegral.setText("当前积分为：" + msg.obj);
                }
                break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_center, null);
        popView=LayoutInflater.from(getActivity()).inflate(R.layout.share_grid,null);
//        getActivity().getWindow() .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//        positionView = view.findViewById(R.id.positionView);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvIntegral = (TextView) view.findViewById(R.id.tv_fans);
        headImg = (CircleImageView) view.findViewById(R.id.head_img);

        translucentScrollView = (TranslucentScrollView) view.findViewById(R.id.pullzoom_scrollview);
        zoomView = view.findViewById(R.id.lay_header);

        imgScan = (LinearLayout) view.findViewById(R.id.img_scan);
        hobbyForum = (LinearLayout) view.findViewById(R.id.hobby_forum);
        integralMall = (LinearLayout) view.findViewById(R.id.integral_mall);

        updateUserMessage = (RelativeLayout) view.findViewById(R.id.to_update_user_message);
        historyRecord = (RelativeLayout) view.findViewById(R.id.to_history_record);
        tasksAchieve = (RelativeLayout) view.findViewById(R.id.to_tasks_achievement);
        cleanCacheLayout = (RelativeLayout) view.findViewById(R.id.clean_cache_layout);
//        systemPush = (SlideSwitch) view.findViewById(R.id.system_slide_switch);
        shareApp = (RelativeLayout) view.findViewById(R.id.share_app);
        feedBack = (RelativeLayout) view.findViewById(R.id.feed_back);
        cleanCache = (TextView) view.findViewById(R.id.clean_cache);

        loginOut = (PaperButton) view.findViewById(R.id.login_out);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("tag", "onActivityCreated: 个人中心初始化了");
        setData();
        setShareApp();
        showCacheSize();
        UserDbHelper.setInstance(getContext());
        userModel = UserDbHelper.getInstance().getUserInfo();
        tvName.setText(TextUtils.isEmpty(userModel.getUserNickName()) ? "Limynl" : userModel.getUserNickName());
        tvIntegral.setText(userModel.getIntegral() <= 0 ? "当前积分为：0" : ("当前积分为：" + userModel.getIntegral() + ""));
        if(!userModel.getUserHeadImage().equals("")){
            Glide.with(getContext())
                    .load(userModel.getUserHeadImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(headImg);
        }else{
            headImg.setImageResource(R.mipmap.man_head);
        }

        MarkHomeActivity.setRefrshIntegral(this);
        ShoppingFragment.setRefrshIntegral(this);

        handler.sendEmptyMessageDelayed(1, 5000);
    }

    private void setData() {
        translucentScrollView.setPullZoomView(zoomView);//关联需要伸缩的视图

        imgScan.setOnClickListener(this);
        hobbyForum.setOnClickListener(this);
        integralMall.setOnClickListener(this);
        updateUserMessage.setOnClickListener(this);
        historyRecord.setOnClickListener(this);
        tasksAchieve.setOnClickListener(this);
        cleanCacheLayout.setOnClickListener(this);
//        systemPush.setOnStateChangedListener(this);
        shareApp.setOnClickListener(this);
        feedBack.setOnClickListener(this);
        loginOut.setOnClickListener(this);
    }

    private void setShareApp() {
        shareBeanList.add(new SharePopBean(iconId[0], names[0]));
        shareBeanList.add(new SharePopBean(iconId[1], names[1]));
        shareBeanList.add(new SharePopBean(iconId[2], names[2]));
        shareBeanList.add(new SharePopBean(iconId[3], names[3]));
        shareBeanList.add(new SharePopBean(iconId[4], names[4]));

        gv=(GridView)popView.findViewById(R.id.share_grid);
        shareBaseAdapter = new SharePopBaseAdapter(getActivity(), shareBeanList);
        gv.setAdapter(shareBaseAdapter);
        gv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_update_user_message:{//用户信息修改
                startActivityForResult(new Intent(getActivity(), UpdateUserMessageActivity.class), UPDATE_USER_MESSAGE);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.img_scan:{//美图浏览
                startActivity(new Intent(getActivity(), ImgScanMainActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.hobby_forum:{//兴趣论坛
                startActivity(new Intent(getContext(), HobbyActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.integral_mall:{
                startActivity(new Intent(getContext(), IntegralMainActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.to_history_record:{//历史记录
                startActivity(new Intent(getActivity(), HistoryRecordActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.to_tasks_achievement:{//任务完成情况
                startActivity(new Intent(getContext(), UserTaskActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.clean_cache_layout:{
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("清除缓存")
                        .setContentText("您将确定要清除应用的缓存吗？")
                        .setCancelText("确 认")
                        .setConfirmText("取 消")
                        .showConfirmButton(true)
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("清除成功!")
                                        .setContentText("")
                                        .showConfirmButton(false)
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                DataCleanManager.cleanInternalCache(getContext());
                                DataCleanManager.cleanExternalCache(getContext());
                                DataCleanManager.cleanFiles(getContext());
                                showCacheSize();
                                Timer timer=new Timer();
                                timer.schedule(new Wite(sDialog), 1500);
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
            case R.id.share_app:{//分享App
                pw = getPopWindow(popView);
            }
            break;
            case R.id.feed_back:{//意见反馈
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.login_out:{//退出登录
                UserDbHelper.getInstance().saveLoginState(true);
                Log.e("tag", "onClick: 用户的登录信息保存成功:" + UserDbHelper.getInstance().getLoginState());
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                getActivity().finish();
            }
            break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = shareBeanList.get(position).getName();
        if(TextUtils.equals(name, "QQ")){
            Platform qq = ShareSDK.getPlatform(getContext(), QQ.NAME);
            QQ.ShareParams sp = new QQ.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle("快来使用图片分类App吧！");
            sp.setTitleUrl("http://139.199.23.142:8080/TestShowMessage1/car.apk");
            qq.share(sp);
            pw.dismiss();
        }else if (TextUtils.equals(name, "新浪")){
            SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
            sp.setText("快来使用图片分类App吧！");
            sp.setImagePath("http://139.199.23.142:8080/TestShowMessage1/car.apk");
            Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
            weibo.share(sp);
            pw.dismiss();
        }else if(TextUtils.equals(name, "微信")){
            Platform platform_wxFriend = ShareSDK.getPlatform(getContext(), Wechat.NAME);
            Wechat.ShareParams sp = new Wechat.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
            sp.setTitle("快来使用图片分类App吧！");
            sp.setText("快来使用图片分类App吧！");
            sp.setUrl("http://139.199.23.142:8080/TestShowMessage1/car.apk");
            sp.setImageData(null);
            sp.setImageUrl("http://139.199.23.142:8080/TestShowMessage1/car.apk");
            sp.setImagePath(null);
//            platform_wxFriend.setPlatformActionListener(this); // 设置分享事件回调
            // 执行图文分享
            platform_wxFriend.share(sp);
            pw.dismiss();
        }else if(TextUtils.equals(name, "朋友圈")){
            Platform platform_circle = ShareSDK.getPlatform(getContext(), WechatMoments.NAME);
            cn.sharesdk.wechat.moments.WechatMoments.ShareParams sp = new cn.sharesdk.wechat.moments.WechatMoments.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
            sp.setTitle("快来使用图片分类App吧!");
            sp.setText("快来使用图片分类App吧!");
            sp.setImagePath(null);
            sp.setUrl("http://139.199.23.142:8080/TestShowMessage1/car.apk");
//            platform_circle.setPlatformActionListener(this); // 设置分享事件回调
            // 执行图文分享
            platform_circle.share(sp);
            pw.dismiss();
        }else if(TextUtils.equals(name, "QQ空间")){
            Platform platform_qzone = ShareSDK.getPlatform(getContext(), QZone.NAME);
            cn.sharesdk.tencent.qzone.QZone.ShareParams sp = new cn.sharesdk.tencent.qzone.QZone.ShareParams();
            sp.setTitle("快来使用图片分类App吧!");
            sp.setText("快来使用图片分类App吧!");
            sp.setTitleUrl("http://139.199.23.142:8080/TestShowMessage1/car.apk");
            sp.setImageUrl("http://139.199.23.142:8080/TestShowMessage1/car.apk");// imageUrl存在的时候，原来的imagePath将被忽略
            sp.setSiteUrl("http://139.199.23.142:8080/TestShowMessage1/car.apk");
//            platform_qzone.setPlatformActionListener(this); // 设置分享事件回调
            // 执行图文分享
            platform_qzone.share(sp);
            pw.dismiss();
        }
    }
//
//    @Override
//    public void onStateChanged(boolean state) {
//        if (state == true) {
//            toastUtil.Short(getContext(), "系统推送已打开").show();
//        } else {
//            toastUtil.Short(getContext(), "系统推送已关闭").show();
//        }
//    }

    /**
     * 设置弹窗
     * @param view
     * @return
     */
    private PopupWindow getPopWindow(View view){
        PopupWindow popupWindow=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.anim_popup_centerbar);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
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
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 显示缓存大小
     */
    private void showCacheSize() {
        long externalCacheSize = 0;
        long cacheSize = 0;
        long filesSize = 0;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                externalCacheSize = FileInfoUtils.getFileSize(getActivity().getExternalCacheDir());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            cacheSize = FileInfoUtils.getFileSize(getActivity().getCacheDir());
            filesSize = FileInfoUtils.getFileSize(getFilesDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cleanCache.setText(FileInfoUtils.FormetFileSize(externalCacheSize + cacheSize + filesSize));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == UPDATE_USER_MESSAGE && resultCode == UPDATE_USER_MESSAGE && data != null){
            Bundle bundle = data.getExtras();
            tvName.setText(bundle.getString("userNick"));
            headImg.setImageBitmap((Bitmap) bundle.getParcelable("userHead"));
        }
    }

    private void getUserCurrentIntegral(){
        String url = Constants.Get_User_Current_Integral;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", String.valueOf(userModel.getId()));
        VolleyRequestUtil.RequestPost(getContext(), url, "hobbyPush", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);

                    Log.e("tag", "onSuccess: 请求成功：" + object.optString("Integer"));
                    tvIntegral.setText("当前积分为：" + object.optString("Integer"));

                    Message message = new Message();
                    message.what = 2;
                    message.obj = object.optString("Integer");
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                getUserCurrentIntegral();
            }
        });
    }

    @Override
    public void onDestroy() {
        ShareSDK.stopSDK(getContext().getApplicationContext());
        super.onDestroy();
    }

    /**
     * 从标签页面刷新数据
     */
    @Override
    public void getIntegral() {
        Log.e("tag", "getIntegral: nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
        getUserCurrentIntegral();
    }

    @Override
    public void getIntegralFromMark() {
        Log.e("tag", "getIntegralFromMark: 执行了、、、、、、、、、");
        getUserCurrentIntegral();
    }

//    @Override
//    public void getIntegralFromMark() {
//        Log.e("tag", "getIntegralFromMark: 执行了、、、、、、、、、");
//        getUserCurrentIntegral();
//    }
}