package com.team.imagemarker.entitys.home;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Lmy on 2017/8/31.
 * email 1434117404@qq.com
 */

public class appAbnormalChangeModel implements Serializable{
    //用户id
    private int userId;
    //图片id
    private int ImageId;
    //所打图片的人数
    private  int num;
    //标签  修改之后将新的标签赋值进去，位置和被修改的标签对应上
    private String[] labels;
    //提示可能异常的标签
    private String[] abnormalsLabel;
    //图片url
    private String ImageUrl;

    public appAbnormalChangeModel(String[] labels, String imageUrl) {
        this.labels = labels;
        ImageUrl = imageUrl;
    }

    public appAbnormalChangeModel(int userId, int imageId, int num, String[] labels, String[] abnormalsLabel, String imageUrl) {
        this.userId = userId;
        ImageId = imageId;
        this.num = num;
        this.labels = labels;
        this.abnormalsLabel = abnormalsLabel;
        ImageUrl = imageUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public String[] getAbnormalsLabel() {
        return abnormalsLabel;
    }

    public void setAbnormalsLabel(String[] abnormalsLabel) {
        this.abnormalsLabel = abnormalsLabel;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "appAbnormalChangeModel{" +
                "userId=" + userId +
                ", ImageId=" + ImageId +
                ", num=" + num +
                ", labels=" + Arrays.toString(labels) +
                ", abnormalsLabel=" + Arrays.toString(abnormalsLabel) +
                ", ImageUrl='" + ImageUrl + '\'' +
                '}';
    }
}
