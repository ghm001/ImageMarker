package com.team.imagemarker.entitys;

import java.io.Serializable;

/**
 * Created by Lmy on 2017/6/14.
 * email 1434117404@qq.com
 */

public class MarkerModel implements Serializable {
    private int userId;
    private int id;
    private int imageNum;
    private int imageId1;
    private int imageId2;
    private int imageId3;
    private int imageId4;
    private int imageId5;
    private int imageId6;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private String imageUrl5;
    private String imageUrl6;
    private String label1;
    private String label2;
    private String label3;
    private String label4;
    private String label5;
    private String label6;
    private String setTime;
    private String titleName;
    private String pushWay;
    private String Flag;
    private String SecondlabelName;

    public MarkerModel() {
    }

    public MarkerModel(String imageUrl1, String SecondlabelName){
        this.imageUrl1 = imageUrl1;
        this.SecondlabelName = SecondlabelName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    public int getImageId1() {
        return imageId1;
    }

    public void setImageId1(int imageId1) {
        this.imageId1 = imageId1;
    }

    public int getImageId2() {
        return imageId2;
    }

    public void setImageId2(int imageId2) {
        this.imageId2 = imageId2;
    }

    public int getImageId3() {
        return imageId3;
    }

    public void setImageId3(int imageId3) {
        this.imageId3 = imageId3;
    }

    public int getImageId4() {
        return imageId4;
    }

    public void setImageId4(int imageId4) {
        this.imageId4 = imageId4;
    }

    public int getImageId5() {
        return imageId5;
    }

    public void setImageId5(int imageId5) {
        this.imageId5 = imageId5;
    }

    public int getImageId6() {
        return imageId6;
    }

    public void setImageId6(int imageId6) {
        this.imageId6 = imageId6;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

    public String getImageUrl4() {
        return imageUrl4;
    }

    public void setImageUrl4(String imageUrl4) {
        this.imageUrl4 = imageUrl4;
    }

    public String getImageUrl5() {
        return imageUrl5;
    }

    public void setImageUrl5(String imageUrl5) {
        this.imageUrl5 = imageUrl5;
    }

    public String getImageUrl6() {
        return imageUrl6;
    }

    public void setImageUrl6(String imageUrl6) {
        this.imageUrl6 = imageUrl6;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getLabel3() {
        return label3;
    }

    public void setLabel3(String label3) {
        this.label3 = label3;
    }

    public String getLabel4() {
        return label4;
    }

    public void setLabel4(String label4) {
        this.label4 = label4;
    }

    public String getLabel5() {
        return label5;
    }

    public void setLabel5(String label5) {
        this.label5 = label5;
    }

    public String getLabel6() {
        return label6;
    }

    public void setLabel6(String label6) {
        this.label6 = label6;
    }

    public String getSetTime() {
        return setTime;
    }

    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getPushWay() {
        return pushWay;
    }

    public void setPushWay(String pushWay) {
        this.pushWay = pushWay;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getSecondlabelName() {
        return SecondlabelName;
    }

    public void setSecondlabelName(String secondlabelName) {
        SecondlabelName = secondlabelName;
    }

    @Override
    public String toString() {
        return "MarkerModel{" +
                "userId=" + userId +
                ", id=" + id +
                ", imageNum=" + imageNum +
                ", imageId1=" + imageId1 +
                ", imageId2=" + imageId2 +
                ", imageId3=" + imageId3 +
                ", imageId4=" + imageId4 +
                ", imageId5=" + imageId5 +
                ", imageId6=" + imageId6 +
                ", imageUrl1='" + imageUrl1 + '\'' +
                ", imageUrl2='" + imageUrl2 + '\'' +
                ", imageUrl3='" + imageUrl3 + '\'' +
                ", imageUrl4='" + imageUrl4 + '\'' +
                ", imageUrl5='" + imageUrl5 + '\'' +
                ", imageUrl6='" + imageUrl6 + '\'' +
                ", label1='" + label1 + '\'' +
                ", label2='" + label2 + '\'' +
                ", label3='" + label3 + '\'' +
                ", label4='" + label4 + '\'' +
                ", label5='" + label5 + '\'' +
                ", label6='" + label6 + '\'' +
                ", setTime='" + setTime + '\'' +
                ", titleName='" + titleName + '\'' +
                ", pushWay='" + pushWay + '\'' +
                ", Flag='" + Flag + '\'' +
                ", SecondlabelName='" + SecondlabelName + '\'' +
                '}';
    }
}
