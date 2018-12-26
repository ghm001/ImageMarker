package com.team.imagemarker.fragments.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mob.tools.utils.UIHandler;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.home.HomeActivity;
import com.team.imagemarker.activitys.user.UserResetPassActivity;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.utils.EditTextWithDel;
import com.team.imagemarker.utils.PaperButton;
import com.team.imagemarker.utils.ToastUtil;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;


/**
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class LoginFragment extends Fragment implements View.OnClickListener, PlatformActionListener, Handler.Callback {
    private View view;

    private EditTextWithDel userPhone, userPassword;//账号密码
    private PaperButton userLogin;//登录
    private LinearLayout resetPassword;
    private ImageView qqLogin, wechatLogin, sinaLogin;//第三方登录
    private Dialog dialog;

    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;
    final private Context context = getActivity();
    private Platform mPf;
    private IWXAPI api;
    private ToastUtil toastUtil = new ToastUtil();

    //需要腾讯提供的一个Tencent类
    private Tencent mTencent;
    //还需要一个IUiListener 的实现类（LogInListener implements IUiListener）
    private LogInListener mListener;
    //用来判断当前是否已经授权登录，若为false，点击登录button时进入授权，否则注销
    private boolean isLogIned = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        initMob();
        userPhone = (EditTextWithDel) view.findViewById(R.id.user_phone);
        userPassword = (EditTextWithDel) view.findViewById(R.id.user_password);
        userLogin = (PaperButton) view.findViewById(R.id.user_login);
        resetPassword = (LinearLayout) view.findViewById(R.id.reset_password);
        qqLogin = (ImageView) view.findViewById(R.id.qq_login);
        wechatLogin = (ImageView) view.findViewById(R.id.wechat_login);
        sinaLogin = (ImageView) view.findViewById(R.id.sina_login);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userLogin.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        wechatLogin.setOnClickListener(this);
        sinaLogin.setOnClickListener(this);
        UserDbHelper.setInstance(getContext());
    }

    /**
     * 第三方平台初始化
     */
    private void initMob() {
        ShareSDK.initSDK(getActivity());//mob初始化
        mTencent = Tencent.createInstance("1106389984", getContext().getApplicationContext());
        mListener = new LogInListener();

        api = WXAPIFactory.createWXAPI(getActivity(), "wx4868b35061f87885", true);//微信登录初始化
        api.registerApp("wx4868b35061f87885");// 将应用的appid注册到微信
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        switch (v.getId()) {
            case R.id.user_login: {//登录
                dialog = new Dialog(getContext());
                builder.setView(LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading, null));
                dialog = builder.create();
                UserLogin();
//                dialog.show();
//                Timer timer=new Timer();
//                timer.schedule(new wait(), 500);
            }
            break;
            case R.id.reset_password: {//重置密码
                startActivity(new Intent(getActivity(), UserResetPassActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.qq_login: {//QQ登录
                thirdQQLogin();
            }
            break;
            case R.id.wechat_login: {//微信登录
                if (!api.isWXAppInstalled()) {
                    toastUtil.Short(getContext(), "请安装微信客户端之后再进行登录").show();
                    return;
                }
                getCode();
            }
            break;
            case R.id.sina_login: {//新浪微博登录
                thirdSinaLogin();
            }
            break;
        }
    }

    private void UserLogin() {
        if (!userPhone.getText().toString().trim().equals("") && !userPassword.getText().toString().trim().equals("")) {
            if (userPhone.getText().toString().length() == 11) {
                dialog.show();
                String url = com.team.imagemarker.constants.Constants.USER_LOGIN;
                Map<String, String> map = new HashMap<>();
                map.put("phoneNumber", userPhone.getText().toString().trim());
                map.put("passWord", userPassword.getText().toString().trim());
                VolleyRequestUtil.RequestPost(getContext(), url, "login", map, new VolleyListenerInterface() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("tag", "onSuccess: json字符串为：" + result.toString());
                        try {
                            JSONObject object = new JSONObject(result);
                            String tag = object.optString("tag");
                            if (tag.equals("success")) {
                                Gson gson = new Gson();
                                UserModel userModel = gson.fromJson(object.optString("user"), UserModel.class);
                                Log.e("tag", "onSuccess: " + userModel.toString());
                                UserDbHelper.setInstance(getContext());
                                UserDbHelper.getInstance().saveUserLoginInfo(userModel);
                                com.team.imagemarker.constants.Constants.USER_ID = userModel.getId();
                                Log.e("tag", "onSuccess: 用户的ID为：" + com.team.imagemarker.constants.Constants.USER_ID);
                                Log.e("tag", "onSuccess: 数据表：" + UserDbHelper.getInstance().getUserInfo().toString());
                                UserDbHelper.getInstance().saveLoginState(false);//保存用户的登录状态
                                Timer timer = new Timer();
                                timer.schedule(new wait(false), 500);
                            } else {
                                toastUtil.Short(getContext(), "账号或密码不正确").show();
                                Timer timer = new Timer();
                                timer.schedule(new wait(true), 1000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toastUtil.Short(getContext(), "服务器连接错误").show();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        toastUtil.Short(getContext(), "服务器连接错误").show();
                    }
                });

            } else {
                toastUtil.Short(getContext(), "电话号码格式不对，请重新输入").show();
            }
        } else {
            toastUtil.Short(getContext(), "账号和密码不能为空").show();
        }
    }

    /**
     * QQ第三方登录
     */
    private void thirdQQLogin() {
        if (!isLogIned) {
            isLogIned = true;
            if (!mTencent.isSessionValid()) {
                mTencent.login(LoginFragment.this, "all", mListener);
            }
        } else {
            mTencent.logout(getContext());
            isLogIned = false;
            mTencent.login(LoginFragment.this, "all", mListener);
        }
    }

    private class LogInListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            Log.e("tag", "onError: 授权成功");
            JSONObject jsonObject = (JSONObject) o;

            //设置openid和token，否则获取不到下面的信息
            String openId = initOpenidAndToken(jsonObject);

            //获取QQ用户的各信息
            getUserInfo(openId);
        }

        @Override
        public void onError(UiError uiError) {
            Log.e("tag", "onError: 授权出错");
        }

        @Override
        public void onCancel() {
            Log.e("tag", "onError: 授权取消");
        }
    }

    private String initOpenidAndToken(JSONObject jsonObject) {
        try {
            String openid = jsonObject.getString("openid");
            String token = jsonObject.getString("access_token");
            String expires = jsonObject.getString("expires_in");
            mTencent.setAccessToken(token, expires);
            mTencent.setOpenId(openid);

            return jsonObject.getString("openid");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void getUserInfo(final String openId) {
        //sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
        QQToken mQQToken = mTencent.getQQToken();
        final UserInfo userInfo = new UserInfo(getContext(), mQQToken);
        userInfo.getUserInfo(new IUiListener() {
                                 @Override
                                 public void onComplete(final Object o) {
                                     JSONObject userInfoJson = (JSONObject) o;
                                     try {
                                         Map<String, String> map = new HashMap<>();
                                         map.put("otherLogin", openId);
                                         map.put("userNickName", userInfoJson.getString("nickname"));
                                         map.put("userHeadImage",userInfoJson.getString("figureurl_qq_2"));
                                         Log.e("tag", "onComplete: 信息获取成功：" + "用户Id为：" + openId + "  " + userInfoJson.getString("nickname") + userInfoJson.getString("figureurl_qq_1"));
                                         threeUserLogin(map);
                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }
                                 }

                                 @Override
                                 public void onError(UiError uiError) {
                                     Log.e("GET_QQ_INFO_ERROR", "获取qq用户信息错误");
                                 }

                                 @Override
                                 public void onCancel() {
                                     Log.e("GET_QQ_INFO_CANCEL", "获取qq用户信息取消");
                                 }
                             }
        );
    }


    //确保能接收到回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
    }

    /**
     * 新浪微博授权，获取用户注册信息
     */
    private void thirdSinaLogin() {
        Platform pf = ShareSDK.getPlatform(context, SinaWeibo.NAME);//初始化新浪平台
        pf.SSOSetting(true);
        pf.setPlatformActionListener(this);//设置监听
        pf.authorize();//出现授权界面，获取登陆用户的信息，如果没有授权，会先授权，然后获取用户信息
    }

    /**
     * 微信登录返回码
     */
    private void getCode() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "carjob_wx_login";
        api.sendReq(req);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(getActivity());
    }

    /**
     * 微博登录返回码
     *
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_TOAST: {
                String text = String.valueOf(msg.obj);
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_ACTION_CCALLBACK: {
                switch (msg.arg1) {
                    case 1: {
                        // 成功, successful notification
                        //http://open.weibo.com/wiki/2/users/show 新浪微博返回结果字段说明
                        Platform pf = ShareSDK.getPlatform(context, SinaWeibo.NAME);
                        //常用返回字段

                        Map<String, String> map = new HashMap<>();
                        map.put("otherLogin", pf.getDb().getUserId());
                        map.put("userNickName", pf.getDb().getUserName());
                        map.put("userHeadImage", pf.getDb().getUserIcon());
                        threeUserLogin(map);

                        Log.e("sharesdk use_id", pf.getDb().getUserId()); //获取用户id
                        Log.e("sharesdk use_name", pf.getDb().getUserName());//获取用户名称
                        Log.e("sharesdk use_icon", pf.getDb().getUserIcon());//获取用户头像
                        Log.e("sharesdk use_gender", pf.getDb().getUserGender());//获取用户性别
                        Log.e("sharesdk use_gender", pf.getDb().getPlatformNname());//获取平台名称
//                        mThirdLoginResult.setText(pf.getDb().getUserId());
                    }
                    break;
                    case 2: {
//                        mThirdLoginResult.setText("登录失败");
                    }
                    break;
                    case 3: {
                        // 取消, cancel notification
//                        mThirdLoginResult.setText("取消授权");
                    }
                    break;
                }
            }
            break;
            case MSG_CANCEL_NOTIFY: {
                NotificationManager nm = (NotificationManager) msg.obj;
                if (nm != null) {
                    nm.cancel(msg.arg1);
                }
            }
            break;
        }
        return false;
    }

    /**
     * 新浪微博授权成功回调页面
     *
     * @param platform
     * @param action
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    /**
     * 授权失败
     *
     * @param platform
     * @param action
     * @param t
     */
    @Override
    public void onError(Platform platform, int action, Throwable t) {
        t.printStackTrace();
        t.getMessage();
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    /**
     * 取消授权
     *
     * @param platform
     * @param action
     */
    @Override
    public void onCancel(Platform platform, int action) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    private void threeUserLogin(Map<String, String> map) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        dialog = new Dialog(getContext());
        builder.setView(LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading, null));
        dialog = builder.create();
        dialog.show();

        String url = Constants.USER_LOGIN_OTHER;
        VolleyRequestUtil.RequestPost(getContext(), url, "login", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                Log.e("tag", "onSuccess: json字符串为：" + result.toString());
                try {
                    JSONObject object = new JSONObject(result);
                    String tag = object.optString("tag");
                    if (tag.equals("success")) {
                        Gson gson = new Gson();
                        UserModel userModel = gson.fromJson(object.optString("user"), UserModel.class);
                        Log.e("tag", "onSuccess: " + userModel.toString());
                        UserDbHelper.setInstance(getContext());
                        UserDbHelper.getInstance().saveUserLoginInfo(userModel);
                        com.team.imagemarker.constants.Constants.USER_ID = userModel.getId();
                        Log.e("tag", "onSuccess: 用户的ID为：" + com.team.imagemarker.constants.Constants.USER_ID);
                        Log.e("tag", "onSuccess: 数据表：" + UserDbHelper.getInstance().getUserInfo().toString());
                        UserDbHelper.getInstance().saveLoginState(false);//保存用户的登录状态
                        Timer timer = new Timer();
                        timer.schedule(new wait(false), 500);
                    } else {
//                        toastUtil.Short(getContext(), "账号或密码不正确").show();
                        Timer timer = new Timer();
                        timer.schedule(new wait(true), 1000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastUtil.Long(getContext(), "网络好像走丢了！").show();
                }
            }

            @Override
            public void onError(VolleyError error) {
                toastUtil.Short(getContext(), "服务器连接错误").show();
            }
        });
    }

    class wait extends TimerTask {

        private boolean isError;

        public wait(boolean isError) {
            this.isError = isError;
        }

        @Override
        public void run() {
            if(isError){
                dialog.dismiss();
            }else{
                dialog.dismiss();
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down);
            }
        }
    }
}
