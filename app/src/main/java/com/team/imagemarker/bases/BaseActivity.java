package com.team.imagemarker.bases;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.team.imagemarker.R;
import com.team.imagemarker.utils.StatusBarCompat;

import java.util.List;

/**
 * Created by Lmy on 2017/8/23.
 * email 1434117404@qq.com
 */

public class BaseActivity extends AppCompatActivity {
    public static boolean isForeground = false;
    private String TAG = "BaseActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(this, getResources().getColor(R.color.theme1));
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Glide.with(this) != null) {
            Glide.with(this).pauseRequests();
        }
        if (!isAppOnFreground()) {
        }
    }

    @Override
    protected void onDestroy() {
        hideSoftInput();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * 收起键盘
     */
    private void hideSoftInput() {
        try {
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.i(TAG, "----" + e.toString());
        }
    }

    /**
     * 是否在后台
     *
     * @return
     */
    public boolean isAppOnFreground() {
        ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        String curPackageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> app = am.getRunningAppProcesses();
        if (app == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo a : app) {
            if (a.processName.equals(curPackageName)
                    && a.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }


    /** ------------------------------------点击屏幕空白地方隐藏软键盘---------------------------------------- **/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 当前焦点的对象为TextView或者EditText时，根据View所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     * 当软键盘显示的时候，用户再次点击相应的View时不隐藏
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText || v instanceof TextView)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
//                v.clearFocus();
                return true;
            }
        }
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
