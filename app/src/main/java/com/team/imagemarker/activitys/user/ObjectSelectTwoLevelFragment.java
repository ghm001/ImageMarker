package com.team.imagemarker.activitys.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.objectselect.ObjectSelectTwoLevelAdapter;
import com.team.imagemarker.bases.CheckListener;
import com.team.imagemarker.bases.ItemClickListener;
import com.team.imagemarker.bases.ObjectSelectTwoLevelContract;
import com.team.imagemarker.entitys.objectselect.TwoLevelBean;
import com.team.imagemarker.utils.objectselect.ItemHeaderDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public class ObjectSelectTwoLevelFragment extends Fragment implements ObjectSelectTwoLevelContract.View, CheckListener {
    private ObjectSelectTwoLevelContract.Presenter mpresenter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ObjectSelectTwoLevelAdapter adapter;
    private List<TwoLevelBean> list = new ArrayList<>();
    private int moveCounts;
    public List<String[]> citylist;
    public boolean move = false;
    private CheckListener checkListener;
    private Context context;
    private String[] oneLevel;
    private CallBack callBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBack = (CallBack) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_object_select_two_level, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_city);
        recyclerView.addOnScrollListener(new RecyclerViewListener());
        mpresenter.start();
        return view;
    }

    @Override
    public void setPresenter(ObjectSelectTwoLevelContract.Presenter presenter) {
        mpresenter = presenter;
    }

    @Override
    public void showSnackBar() {
    }

    @Override
    public void showCity() {
        context = getActivity();
        oneLevel = context.getResources().getStringArray(R.array.province);
        initData(oneLevel);
        gridLayoutManager = new GridLayoutManager(context, 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return list.get(position).isTitle ? 4 : 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ObjectSelectTwoLevelAdapter(context, list, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.root: {
                    }
                    break;
                    case R.id.ll_city: {
                        String object = oneLevel[Integer.parseInt(list.get(position).getTag())] + "-" + list.get(position).getCity();
                        callBack.setData(object);
                    }
                    break;
                }
            }
        });
        ItemHeaderDecoration decoration = new ItemHeaderDecoration(context, list);
        decoration.setData(list);
        recyclerView.addItemDecoration(decoration);
        decoration.setCheckListener(checkListener);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 获取左边点击，右边滑动的距离
     */
    public void setCounts(int counts) {
        moveCounts = counts;
        moveToPosition(moveCounts);
    }

    /**
     * 移动到指定位置
     */
    private void moveToPosition(int moveCounts) {
        int firstItem = gridLayoutManager.findFirstVisibleItemPosition();//获取屏幕可见的第一个item的position
        int lastItem = gridLayoutManager.findLastVisibleItemPosition();//获取屏幕可见的最后一个item的position
        if (moveCounts < firstItem) {
            recyclerView.scrollToPosition(moveCounts);
        } else if (moveCounts < lastItem) {
            View aimsView = recyclerView.getChildAt(moveCounts - firstItem);
            int top = aimsView.getTop();
            recyclerView.scrollBy(0, top);
        } else {
            //当往下滑动的position大于可见的最后一个item的时候，调用 recyclerView.scrollToPosition(moveCounts);只能将item滑动到屏幕的底部。
            recyclerView.scrollToPosition(moveCounts);
            move = true;
        }
    }

    private void initData(final String[] province) {
        String[] twoLevel = context.getResources().getStringArray(R.array.two_level);
        citylist = new ArrayList<>();
        for (int i = 0; i < twoLevel.length; i++) {
            String temp = twoLevel[i];
            citylist.add(temp.split("-"));
        }
        for (int i = 0; i < province.length; i++) {
            TwoLevelBean titleBean = new TwoLevelBean();
            titleBean.setProvince(province[i]);
            titleBean.setTitle(true);//设置为title
            titleBean.setTag(String.valueOf(i));//设置tag，方便获取position
            list.add(titleBean);

            for (int j = 0; j < citylist.get(i).length; j++) {
                TwoLevelBean cityBean = new TwoLevelBean();
                cityBean.setCity(citylist.get(i)[j]);
                cityBean.setTag(String.valueOf(i));//设置成和一级领域一样的tag，将一级领域与二级领域绑定。
                list.add(cityBean);
            }
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        checkListener.check(position, isScroll);
    }

    public void setCheck(CheckListener listener) {
        this.checkListener = listener;
    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener {

        /**
         * 监听回调，滑动结束回调。
         */
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //在这里进行第二次滚动
            if (move) {
                move = false;

                //获取要置顶的项在当前屏幕的位置，moveCount是记录的要置顶项在RecyclerView中的位置
                int n = moveCounts - gridLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < recyclerView.getChildCount()) {

                    //获取要置顶的项顶部离RecyclerView顶部的距离
                    int top = recyclerView.getChildAt(n).getTop();

                    //最后的移动
                    recyclerView.scrollBy(0, top);
                }
            }
        }

        /**
         * 监听回调，滑动状态改变回调
         */
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = moveCounts - gridLayoutManager.findFirstVisibleItemPosition();
                if (0 <= n && n < recyclerView.getChildCount()) {
                    int top = recyclerView.getChildAt(n).getTop();
                    recyclerView.scrollBy(0, top);
                }
            }
        }
    }

    public interface CallBack{
        public void setData(String objectSelect);
    }

}
