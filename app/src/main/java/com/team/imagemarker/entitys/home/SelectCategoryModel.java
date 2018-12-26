package com.team.imagemarker.entitys.home;

import java.util.List;

/**
 * 精选分类
 * Created by Lmy on 2017/5/13.
 * email 1434117404@qq.com
 */

public class SelectCategoryModel {
    private String categoryTitle;
    private String backgroundUrl;
    private String[] categoryTag;
    private List<CateGoryInfo> cateGoryInfos;

    public SelectCategoryModel(String categoryTitle, String[] categoryTag, List<CateGoryInfo> cateGoryInfos) {
        this.categoryTitle = categoryTitle;
        this.categoryTag = categoryTag;
        this.cateGoryInfos = cateGoryInfos;
    }

    public SelectCategoryModel(String backgroundUrl, String categoryTitle, List<CateGoryInfo> cateGoryInfos) {
        this.backgroundUrl = backgroundUrl;
        this.categoryTitle = categoryTitle;
        this.cateGoryInfos = cateGoryInfos;
    }

    public SelectCategoryModel(String categoryTitle, List<CateGoryInfo> cateGoryInfos) {
        this.categoryTitle = categoryTitle;
        this.cateGoryInfos = cateGoryInfos;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String[] getCategoryTag() {
        return categoryTag;
    }

    public void setCategoryTag(String[] categoryTag) {
        this.categoryTag = categoryTag;
    }

    public List<CateGoryInfo> getCateGoryInfos() {
        return cateGoryInfos;
    }

    public void setCateGoryInfos(List<CateGoryInfo> cateGoryInfos) {
        this.cateGoryInfos = cateGoryInfos;
    }

}
