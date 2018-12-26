package com.team.imagemarker.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.team.imagemarker.application.MyApplication;

/**
 * 软键盘控制
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public class SoftInputMethodUtil {

    public static boolean isHideInput(View v, MotionEvent ev) {
        if (v != null ) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public static void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager imm = (InputMethodManager) MyApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(token, 0);
        }
    }
}
