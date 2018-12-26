package com.team.imagemarker.activitys.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.team.imagemarker.bases.BaseListAdapter;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.utils.CircleImageView;
import com.team.imagemarker.utils.CommonAdapter;
import com.team.imagemarker.utils.CropOption;
import com.team.imagemarker.utils.FileUtils;
import com.team.imagemarker.utils.MyGridView;
import com.team.imagemarker.utils.PaperButton;
import com.team.imagemarker.utils.ToastUtil;
import com.team.imagemarker.utils.ViewHolder;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tencent.open.utils.Global.getContext;


/**
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class UpdateUserMessageActivity extends Activity implements View.OnClickListener {
    private static final int UPDATE_USER_MESSAGE = 1;
    private UserModel userModel;
    private RelativeLayout titleBar;
    private TextView title;
    private ImageView leftIcon, rightIcon;
    private LinearLayout popupWindowLayout;//弹窗视图。为了改变颜色

    private CircleImageView userHeadImg;
    private EditText userNick, userAge, userPhone;
    private TextView userSex, userHobby;
    private RelativeLayout selectHead, selectSex, selectObject;
    private PaperButton userMessageSubmit;

    private Dialog dialog;//弹框
    private Button chooseOne, chooseTwo, cancelDialog;
    private View viewDialog;//弹框视图

    private RelativeLayout userHobbySelect;//用户兴趣爱好选择图标
    private View contentView;//弹框视图
    private PopupWindow popupWindow;

    private PaperButton hobbySubmit;
    private TextView userWaring;//提示信息
    private ViewPager vp_details;//兴趣爱好标签选择ViewPager
    private MyGridView grid_selected;
    private ParentIconAdapter parentIconAdapter = new ParentIconAdapter();
    private ChildrenIconAdapter adapter_one, adapter_two;//三页ViewPager中的数据适配器 adapter_three
    private List<String> parentList = new ArrayList<String>();//选中的标签
    final int length = 3;
    private MyGridView[] mGridView = new MyGridView[length];//对应于3个ViewPager
    private MyPagerAdapter mMyadapter;
    private List<View> listViews;
    private ArrayList<String> data_one, data_two;//各个ViewPager对应的GridView中的数据 data_three
    private int mSumOne = 1;//换一批数目调整
    private TextView changeData;//换一批

    private final int PHOTO_PICKED_FROM_CAMERA = 1; // 用来标识头像来自系统拍照
    private final int PHOTO_PICKED_FROM_FILE = 2; // 用来标识从相册获取头像
    private final int CROP_FROM_CAMERA = 3;//剪切图片
    private Uri imgUri; // 用于存储图片
    private byte[] bitmapByte;//用户头像的byte数组
    private static Bitmap userHeadBitmap;
    private String userHeadPhoto = "";//用户的头像字节数据流

    //拍照
    private String mFilePath;
    private String mFileName;

    private ToastUtil toastUtil = new ToastUtil();
    private final int OBJECT_SELECT = 4;
    private TextView userObject;//只在本地显示
    private String userObjectContent = "";//将该值上传服务器
    private String userHobbyId = "";
    private List<String> hobbyContentList;

    //更新父表与子表中的数据
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:// 删除
                    adapter_one.notifyDataSetChanged();
                    adapter_two.notifyDataSetChanged();
                    parentIconAdapter.notifyDataSetChanged();
                    break;
                case 2:// 添加
                    parentIconAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_message);
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        userHeadImg = (CircleImageView) findViewById(R.id.user_head_img);
        userNick = (EditText) findViewById(R.id.user_nickname);
        userAge = (EditText) findViewById(R.id.user_age);
        userSex = (TextView) findViewById(R.id.user_sex);
        userPhone = (EditText) findViewById(R.id.user_number_phone);
        userHobby = (TextView) findViewById(R.id.user_hobby);
        selectHead = (RelativeLayout) findViewById(R.id.select_head);
        selectSex = (RelativeLayout) findViewById(R.id.select_sex);
        selectObject = (RelativeLayout) findViewById(R.id.select_object);
        userHobbySelect = (RelativeLayout) findViewById(R.id.user_hobby_select);
        userMessageSubmit = (PaperButton) findViewById(R.id.user_message_submit);
        userObject = (TextView) findViewById(R.id.zhuanye);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("个人资料");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
        selectHead.setOnClickListener(this);
        selectSex.setOnClickListener(this);
        selectObject.setOnClickListener(this);
        userHobbySelect.setOnClickListener(this);
        userMessageSubmit.setOnClickListener(this);

        initData();

        String[] hobby = getResources().getStringArray(R.array.hobby);
        hobbyContentList = Arrays.asList(hobby);

        //显示PopupWindow
        setPopupWindow();

        UserDbHelper.setInstance(this);
        UserModel userModelHistory = UserDbHelper.getInstance().getUserInfo();
        Log.e("tag", "onCreate: 接受到的用户信息为：" + userModelHistory.toString());
        Glide.with(this)
                .load(userModelHistory.getUserHeadImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(userHeadImg);
        userNick.setText(userModelHistory.getUserNickName() + "");
        userAge.setText(userModelHistory.getUserAge() + "");
        userSex.setText(userModelHistory.getUserSex() + "");
        userPhone.setText(userModelHistory.getPhoneNumber() + "");

        userObjectContent = userModelHistory.getUserHobby();
        if(userObjectContent.split("-").length == 0){
            userObject.setText(userObjectContent);
        }else{
            userObject.setText(userObjectContent.split("-")[1]);
        }

        String[] hobbyHistory = new String[0];
        if(userModelHistory.getUserFlag().contains("-")){
            hobbyHistory = userModelHistory.getUserFlag().split("-");
        }else if(userModelHistory.getUserFlag().contains("、")){
            hobbyHistory = userModelHistory.getUserFlag().split("、");
        }

        String hobbyHistoryText = "";
        Log.e("tag", "onCreate: 可选择的兴趣为：" + hobbyContentList.toString());
        for (int i = 0; i < hobbyHistory.length; i++) {
            if(hobbyContentList.contains(hobbyHistory[i])){
                userHobbyId += (hobbyContentList.indexOf(hobbyHistory[i]) + 1) + "-";
            }
            hobbyHistoryText += hobbyHistory[i] + "、";
        }

        Log.e("tag", "onCreate: 兴趣爱好为：" + userHobbyId);
        userHobby.setText(hobbyHistoryText.subSequence(0, hobbyHistoryText.length() - 1));

        FileUtils.init();//初始化手机拍照
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_icon: {//返回
                onBackPressed();
            }
            break;
            case R.id.select_head: {//设置头像
                showDialog("拍照", "从相册获取");
                chooseOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getIconFromCamera();//拍照获取图片
                        dialog.dismiss();
                    }
                });
                chooseTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getIconFromPhoto();//从相册获取图片
                        dialog.dismiss();
                    }
                });
                cancelDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();//显示对话框主题
            }
            break;
            case R.id.select_sex: {//设置性别
                showDialog("男", "女");
                chooseOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userSex.setText("男");
                        dialog.dismiss();
                    }
                });
                chooseTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userSex.setText("女");
                        dialog.dismiss();
                    }
                });
                cancelDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();//显示对话框主题
            }
            break;
            case R.id.select_object: {//专业领域选择
                startActivityForResult(new Intent(UpdateUserMessageActivity.this, ProfessionalChoiceActivity.class), OBJECT_SELECT);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.user_hobby_select: {//用户兴趣爱好标签选择
                showCenterPopupWindow(v);
                parentList.clear();//清空上一次选中的数据
                mHandler.sendEmptyMessage(2);//更新父GridView的数据
                userWaring.setVisibility(View.VISIBLE);
            }
            break;
            case R.id.tv_change_items: {//换一批
                if (getDataOne().size() == 0) {
                    Toast.makeText(getApplicationContext(), "没有兴趣种类选择", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (getDataOne().size() <= 12) {
                        vp_details.setCurrentItem(0);
                        changeData.setVisibility(View.GONE);
                    } else if (getDataOne().size() > 12 && getDataOne().size() <= 24) {
                        if (mSumOne < 2) {
                            vp_details.setCurrentItem(mSumOne);
                            mSumOne++;
                        } else {
                            mSumOne = 0;
                            vp_details.setCurrentItem(0);
                        }
                    } else {
                        if (mSumOne < 3) {
                            vp_details.setCurrentItem(mSumOne);
                            mSumOne++;
                        } else {
                            mSumOne = 0;
                            vp_details.setCurrentItem(0);
                        }
                    }
                }
            }
            break;
            case R.id.user_hobby_submit: {//确认用户兴趣标签
                userHobbyId = "";
                if (parentList.size() == 0) {
                    Toast.makeText(this, "请至少选择一种兴趣爱好", Toast.LENGTH_SHORT).show();
                    return;
                }
                String userHobbyContent = "";
                for (int i = 0; i < parentList.size(); i++) {
                    if(hobbyContentList.contains(parentList.get(i))){
                        userHobbyId += (hobbyContentList.indexOf(parentList.get(i)) + 1) + "-";
                    }
                    userHobbyContent += parentList.get(i).toString().trim() + "、";
                }
                Log.e("tag", "onClick: " + userHobbyId);
                userHobby.setText(userHobbyContent.subSequence(0, userHobbyContent.length() - 1));
                popupWindow.dismiss();
            }
            break;
            case R.id.user_message_submit: {
                userModel = new UserModel();
                userModel.setId(UserDbHelper.getInstance().getInegerConfig("userId"));
                userModel.setUserNickName(userNick.getText().toString().trim());
                userModel.setUserAge(userAge.getText().toString().trim());
                userModel.setUserSex(userSex.getText().toString().trim());
                userModel.setPhoneNumber(userPhone.getText().toString().trim());
                userModel.setUserHobby(userObjectContent);
                userModel.setUserFlag(userHobbyId);
                userModel.setUserHeadImage(userHeadPhoto);
                userModel.setPassWord(UserDbHelper.getInstance().getStringConfig("userPassword"));
                userModel.setIntegral(UserDbHelper.getInstance().getInegerConfig("userIntegral"));
                userModel.setNum(UserDbHelper.getInstance().getInegerConfig("userTaskNum"));
                userModel.setPushFlag(UserDbHelper.getInstance().getStringConfig("PushFlag"));
                userModel.setOtherLogin(UserDbHelper.getInstance().getStringConfig("OtherLogin"));
                Gson gson = new Gson();
                String userInfo = gson.toJson(userModel);
                sendUserInfo(userInfo);
            }
            break;
        }
    }

    private void sendUserInfo(String userInfo) {
        String url = Constants.USER_UPDATE_MESSAGE;
        Map<String, String> map = new HashMap<>();
        map.put("user", userInfo);
        VolleyRequestUtil.RequestPost(getContext(), url, "login", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String tag = object.optString("tag");
                    if (tag.equals("success")) {
                        toastUtil.Short(UpdateUserMessageActivity.this, "修改成功").show();
                        String userImg = object.optString("url");
                        UserDbHelper.setInstance(UpdateUserMessageActivity.this);
                        userModel.setUserHeadImage(userImg);
                        userModel.setUserFlag(userHobby.getText().toString().trim());
                        UserDbHelper.getInstance().saveUserLoginInfo(userModel);
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("userNick", userNick.getText().toString().trim());
                        bundle.putParcelable("userHead", userHeadBitmap == null ? null : userHeadBitmap);
                        intent.putExtras(bundle);
                        UpdateUserMessageActivity.this.setResult(UPDATE_USER_MESSAGE, intent);
                        UpdateUserMessageActivity.this.finish();
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    } else {
                        Toast.makeText(UpdateUserMessageActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("tag", "onError: 服务器连接错误");
            }
        });
    }

    /**
     * 弹框显示
     */
    private void showDialog(String value1, String value2) {
        viewDialog = View.inflate(this, R.layout.photo_choose_dialog, null);
        chooseOne = (Button) viewDialog.findViewById(R.id.choose_one);
        chooseOne.setText(value1);
        chooseTwo = (Button) viewDialog.findViewById(R.id.choose_two);
        chooseTwo.setText(value2);
        cancelDialog = (Button) viewDialog.findViewById(R.id.cancel);

        dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(viewDialog, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.anim_popup_centerbar);// 设置显示动画
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = window.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);// 设置显示位置
        dialog.setCanceledOnTouchOutside(true);// 设置点击外围解散
    }

    /**
     * 初始化待选项数据
     */
    private void initData() {
        listViews = new ArrayList<View>();
        listViews.add(LayoutInflater.from(this).inflate(R.layout.purchase_gridview, null));
        listViews.add(LayoutInflater.from(this).inflate(R.layout.purchase_gridview, null));
        listViews.add(LayoutInflater.from(this).inflate(R.layout.purchase_gridview, null));
    }

    /**
     * 设置兴趣选择弹框
     */
    private void setPopupWindow() {
        contentView = LayoutInflater.from(this).inflate(R.layout.user_hobby_layout, null);
        popupWindowLayout = (LinearLayout) contentView.findViewById(R.id.popupwindow_layout);
        hobbySubmit = (PaperButton) contentView.findViewById(R.id.user_hobby_submit);
        userWaring = (TextView) contentView.findViewById(R.id.waring_user);
        popupWindowLayout.setBackgroundColor(Color.parseColor("#e0e0e0"));
        changeData = (TextView) contentView.findViewById(R.id.tv_change_items);
        changeData.setOnClickListener(this);
        hobbySubmit.setOnClickListener(this);
        grid_selected = (MyGridView) contentView.findViewById(R.id.grid_selected);
        vp_details = (ViewPager) contentView.findViewById(R.id.vp_details);

        if (parentList.size() > 0) {
            userWaring.setVisibility(View.GONE);
        }

        //加载ViewPager
        mMyadapter = new MyPagerAdapter(listViews);//GridView的list集合
        vp_details.setAdapter(mMyadapter);//加载三页的Viewpager
        vp_details.setCurrentItem(0);//初始化为第一页

        data_one = new ArrayList<String>();
        data_two = new ArrayList<String>();
        adapter_one = new ChildrenIconAdapter(UpdateUserMessageActivity.this, data_one);
        adapter_two = new ChildrenIconAdapter(UpdateUserMessageActivity.this, data_two);
        for (int i = 0; i < 2; i++) {
            mGridView[i] = (MyGridView) listViews.get(i).findViewById(R.id.gridview);
        }

        //为各个ViewPager添加数据
        if (getDataOne().size() <= 12) {
            changeData.setVisibility(View.GONE);//将换一批标志隐藏
            for (int i = 0; i < getDataOne().size(); i++) {
                data_one.add(getDataOne().get(i).toString().trim());
            }
        } else if (getDataOne().size() > 12 && getDataOne().size() <= 24) {
            for (int i = 0; i < 12; i++) {
                data_one.add(getDataOne().get(i).toString().trim());
            }
            for (int i = 12; i < getDataOne().size(); i++) {
                data_two.add(getDataOne().get(i).toString().trim());
            }
        }

        for (int i = 0; i < length; i++) {
            if (i == 0) {
                mGridView[i].setAdapter(adapter_one);
            }
            if (i == 1) {
                mGridView[i].setAdapter(adapter_two);
            }
        }
        grid_selected.setAdapter(parentIconAdapter);
    }

    /**
     * 显示用户兴趣爱好选择弹框
     *
     * @param view
     */
    public void showCenterPopupWindow(View view) {
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
        wlBackground.alpha = 0.4f;      // 0.0 完全不透明,1.0完全透明
        getWindow().setAttributes(wlBackground);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {// 当PopupWindow消失时,恢复其为原来的颜色
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                getWindow().setAttributes(wlBackground);
            }
        });
        popupWindow.setAnimationStyle(R.style.anim_popup_centerbar);//设置PopupWindow进入和退出动画
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);// 设置PopupWindow显示在中间
    }

    /**
     * 兴趣集合
     * @return
     */
    private List<String> getDataOne() {
        String[] hobby = getResources().getStringArray(R.array.hobby);
        return  Arrays.asList(hobby);
    }

    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = mListViews.get(position);
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            container.addView(v);
            return v;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

    }

    /**
     * childgridview数据适配
     * 待选择数据
     */
    private class ChildrenIconAdapter extends BaseListAdapter<String> {
        private List<String> mData;

        public ChildrenIconAdapter(Context context, List<String> mData) {
            super(context, mData);
            this.mData = mData;
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        private class ViewHolder {
            ImageView iv_delete;
            TextView tv_iconname;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_custom_grid, null);
                holder.tv_iconname = (TextView) convertView.findViewById(R.id.tv_intent_iconname);
                holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.iv_delete.setVisibility(View.GONE);
            if (parentList.contains(mData.get(position))) {
                holder.tv_iconname.setEnabled(false);
                holder.tv_iconname.setTextColor(Color.parseColor("#808080"));
            } else {
                holder.tv_iconname.setEnabled(true);
                holder.tv_iconname.setTextColor(Color.parseColor("#424242"));
            }
            holder.tv_iconname.setText(mData.get(position));
            holder.tv_iconname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (parentList.size() >= 20) {
                        Toast.makeText(getApplicationContext(), "最多可选择3个特色标签", Toast.LENGTH_LONG).show();
                        return;
                    }

                    v.setEnabled(false);//将其设置为不能操作
                    parentList.add(mData.get(position));
                    if (parentList.size() > 0) {
                        userWaring.setVisibility(View.GONE);
                    }
                    mHandler.sendEmptyMessage(1);//更新父GridView的数据
                }
            });
            return convertView;
        }

        @Override
        protected View getItemView(View convertView, int position) {
            return null;
        }

        public void update(List<String> values) {
            mData = values;
            notifyDataSetInvalidated();
            notifyDataSetChanged();
        }
    }

    /**
     * parentgridview图片适配
     * 已选择的数据
     */
    private class ParentIconAdapter extends BaseAdapter {
        @Override
        public String getItem(int position) {
            return parentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return parentList.size();
        }

        private class ViewHolder {
            ImageView iv_delete;
            TextView tv_iconname;
            RelativeLayout rl_item;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_home_custom_grid, null);
                holder.tv_iconname = (TextView) convertView.findViewById(R.id.tv_intent_iconname);
                holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete_item);
                holder.rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_iconname.setBackgroundResource(R.drawable.shape_brand_selected);
            holder.iv_delete.setVisibility(View.VISIBLE);

            holder.tv_iconname.setText(parentList.get(position));
            holder.rl_item.setClickable(true);
            holder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentList.remove(position);
                    if (parentList.size() == 0) {
                        userWaring.setVisibility(View.VISIBLE);
                    }
                    mHandler.sendEmptyMessage(1);
                }
            });
            return convertView;
        }
    }


    /**
     * 从相册获取图片
     */
    private void getIconFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_PICKED_FROM_FILE);
    }

    /**
     * 拍照获取图片(适配到7.0)
     */
    private void getIconFromCamera() {
        mFilePath = FileUtils.getFileDir() + File.separator;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = new File(mFilePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            mFileName = "avatar_" + String.valueOf(System.currentTimeMillis()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), mFileName);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imgUri = FileUtils.getUriForFile(this, file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            startActivityForResult(intent, PHOTO_PICKED_FROM_CAMERA);
        } else {
            Log.e("UpdateUserMessage", "文件目录不存在");
        }

    }

    /**
     * 剪切图片
     */
    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<>();
        final Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "当前不支持裁剪图片", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setDataAndType(imgUri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        //只有一张
        if (size == 1) {
            Intent intent1 = new Intent(intent);
            ResolveInfo res = list.get(0);
            intent1.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            startActivityForResult(intent1, CROP_FROM_CAMERA);
        } else {
            for (ResolveInfo res : list) {
                CropOption co = new CropOption();
                co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                co.appIntent = new Intent(intent);
                co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                cropOptions.add(co);
            }
            CommonAdapter<CropOption> adapter = new CommonAdapter<CropOption>(this, cropOptions, R.layout.layout_crop_selector) {
                @Override
                public void convert(ViewHolder holder, CropOption item) {
                    holder.setImageDrawable(R.id.iv_icon, item.icon);
                    holder.setText(R.id.tv_name, item.title);
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("choose a app");
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(cropOptions.get(which).appIntent, CROP_FROM_CAMERA);
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (imgUri != null) {
                        getContentResolver().delete(imgUri, null, null);
                        imgUri = null;
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    /**
     * 处理剪切后的图片，将其显示到头像处并转换为字节数据流
     *
     * @param picData
     */
    private void setCropImg(Intent picData) {
        Bundle bundle = picData.getExtras();
        if (bundle != null) {
            userHeadBitmap = bundle.getParcelable("data");
            userHeadImg.setImageBitmap(userHeadBitmap);
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                userHeadBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);//将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
                bitmapByte = baos.toByteArray();
                userHeadPhoto = Base64.encodeToString(bitmapByte, 0, bitmapByte.length, Base64.DEFAULT);//将图片的字节流数据加密成base64字符输出
                Log.e("lmy", "setCropImg: " + userHeadPhoto);
                baos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PHOTO_PICKED_FROM_CAMERA:
                Log.e("tag", "onActivityResult: 开始剪切图片");
                doCrop();
                break;
            case PHOTO_PICKED_FROM_FILE:
                imgUri = data.getData();
                doCrop();
                break;
            case CROP_FROM_CAMERA:
                if (data != null) {
                    setCropImg(data);
                }
                break;
            case OBJECT_SELECT: {//为用户的专业领域赋值
                if(data != null && resultCode == RESULT_OK){
                    userObjectContent = data.getStringExtra("objectSelect");
                    userObject.setText(userObjectContent.split("-")[1]);
                }
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UpdateUserMessageActivity.this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}