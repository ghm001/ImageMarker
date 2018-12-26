package com.team.imagemarker.bases;

import android.view.View;

/**
 * Created by Lmy on 2017/8/31.
 * email 1434117404@qq.com
 */

public interface OnItemActionListener {
    /**
     * 为图组设置监听事件
     * @param view 操作的视图
     * @param position 数据的位置
     */
    public void OnItemClickListener(View view, int position);
}
