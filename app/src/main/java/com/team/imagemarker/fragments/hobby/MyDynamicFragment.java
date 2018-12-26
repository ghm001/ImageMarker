package com.team.imagemarker.fragments.hobby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.imagemarker.R;

/**
 * Created by Lmy on 2017/6/10.
 * email 1434117404@qq.com
 */

public class MyDynamicFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("tag", "onCreateView: 我的动态");
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_my_dynamic, null);
        return view;
    }
}
