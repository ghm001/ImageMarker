package com.team.imagemarker.entitys.imgscan;

/**
 * Created by Lmy on 2017/5/29.
 * email 1434117404@qq.com
 */

public class DayRecommendModel {
    private int background;
    private String imgTag;
    private String bgUrl;

    public DayRecommendModel(int background, String imgTag){
        this.background = background;
        this.imgTag = imgTag;
    }

    public DayRecommendModel(String bgUrl, String imgTag){
        this.bgUrl = bgUrl;
        this.imgTag = imgTag;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getImgTag() {
        return imgTag;
    }

    public void setImgTag(String imgTag) {
        this.imgTag = imgTag;
    }

    public String getBgUrl() {
        return bgUrl;
    }

    public void setBgUrl(String bgUrl) {
        this.bgUrl = bgUrl;
    }
}
