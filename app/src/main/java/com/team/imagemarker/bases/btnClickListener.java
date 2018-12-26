package com.team.imagemarker.bases;

import android.view.View;

/**
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public interface btnClickListener {
    /**
     * 继续操作
     * @param position 点击的位置
     */
    public void btnEditClick(int position);

    /**
     * 删除操作
     * @param position 点击的位置
     */
    public void btnDeleteClick(View view, int position);
}
