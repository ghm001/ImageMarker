package com.team.imagemarker.entitys.home;

/**
 * Created by Lmy on 2017/5/13.
 * email 1434117404@qq.com
 */

public class CateGoryInfo {
    private String mainTitle;
    private String name;
    private String imgUrl;

    public CateGoryInfo(){

    }

    public CateGoryInfo(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public CateGoryInfo(String mainTitle, String name, String imgUrl) {
        this.mainTitle = mainTitle;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
