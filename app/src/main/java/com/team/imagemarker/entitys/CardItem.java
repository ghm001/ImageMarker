package com.team.imagemarker.entitys;

/**
 * Created by Lmy on 2017/4/2.
 * email 1434117404@qq.com
 */

public class CardItem {

    private int imgId;
    private String imgName;//图组描述
    private String imgUrl;//图组地址

    public CardItem(int imgId, String imgName){
        this.imgId = imgId;
        this.imgName = imgName;
    }

    public CardItem(String imgUrl, String imgName){
        this.imgUrl = imgUrl;
        this.imgName = imgName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
