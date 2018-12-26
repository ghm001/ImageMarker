package com.team.imagemarker.entitys.marker;

/**
 * Created by Lmy on 2017/5/21.
 * email 1434117404@qq.com
 */

public class MarkerItemModel {
    private String showImgUrl;//需要标注的图片
    private String[] tag;//对应图片的标签

    public MarkerItemModel() {
    }

    public MarkerItemModel(String showImgUrl, String[] tag) {
        this.showImgUrl = showImgUrl;
        this.tag = tag;
    }

    public String getShowImgUrl() {
        return showImgUrl;
    }

    public void setShowImgUrl(String showImgUrl) {
        this.showImgUrl = showImgUrl;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }
}
