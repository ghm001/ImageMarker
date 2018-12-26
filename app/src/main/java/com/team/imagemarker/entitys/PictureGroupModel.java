package com.team.imagemarker.entitys;

/**
 * Created by Lmy on 2017/4/15.
 * email 1434117404@qq.com
 */

public class PictureGroupModel {
    private String imgUrl;//图片的地址
    private String imgContent;//图组的描述
    private String imgMarkCount;//图组标记人数

    private int imgId;

    public PictureGroupModel(String imgUrl, String imgContent, String imgMarkCount) {
        this.imgUrl = imgUrl;
        this.imgContent = imgContent;
        this.imgMarkCount = imgMarkCount;
    }

    public PictureGroupModel(int imgId, String imgContent) {
        this.imgId = imgId;
        this.imgContent = imgContent;
    }

    public PictureGroupModel(String imgUrl, String imgContent){
        this.imgUrl = imgUrl;
        this.imgContent = imgContent;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgContent() {
        return imgContent;
    }

    public void setImgContent(String imgContent) {
        this.imgContent = imgContent;
    }

    public String getImgMarkCount() {
        return imgMarkCount;
    }

    public void setImgMarkCount(String imgMarkCount) {
        this.imgMarkCount = imgMarkCount;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
