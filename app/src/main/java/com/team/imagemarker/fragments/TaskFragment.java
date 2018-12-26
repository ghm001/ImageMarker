package com.team.imagemarker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.imagemarker.R;


/**
 * 任务完成情况
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class TaskFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_task, null);
        return view;
    }
}
