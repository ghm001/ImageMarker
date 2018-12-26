package com.team.imagemarker.entitys.hobby;

import java.io.Serializable;

/**
 * Created by Lmy on 2017/8/31.
 * email 1434117404@qq.com
 */

public class share implements Serializable{

    private int id;
    //用户id
    private int uId;
    //用户描述
    private String title;
    //用户分享图片地址
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private String imageUrl5;
    private String imageUrl6;
    //用户本条分享图片数目
    private int imageNum;
    //用户昵称
    private String userNickName;
    //用户头像地址
    private String userPhotoUrl;
    //分享日期
    private String uptime;
    //本条说说分享的种类  可以多选 以“-”分开 这里根据用户的兴趣来进行筛选  样式 “1-4” hobby表的id
    private String sayingType;

    private String useHobby;

    public share(){

    }

    public share(int id, int uId, String title, String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4, String imageUrl5, String imageUrl6, int imageNum, String userNickName, String userPhotoUrl, String uptime, String sayingType, String useHobby) {
        this.id = id;
        this.uId = uId;
        this.title = title;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.imageUrl3 = imageUrl3;
        this.imageUrl4 = imageUrl4;
        this.imageUrl5 = imageUrl5;
        this.imageUrl6 = imageUrl6;
        this.imageNum = imageNum;
        this.userNickName = userNickName;
        this.userPhotoUrl = userPhotoUrl;
        this.uptime = uptime;
        this.sayingType = sayingType;
        this.useHobby = useHobby;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getSayingType() {
        return sayingType;
    }

    public void setSayingType(String sayingType) {
        this.sayingType = sayingType;
    }

    public String getUseHobby() {
        return useHobby;
    }

    public void setUseHobby(String useHobby) {
        this.useHobby = useHobby;
    }

    @Override
    public String toString() {
        return "share{" +
                "id=" + id +
                ", uId=" + uId +
                ", title='" + title + '\'' +
                ", imageUrl1='" + imageUrl1 + '\'' +
                ", imageUrl2='" + imageUrl2 + '\'' +
                ", imageUrl3='" + imageUrl3 + '\'' +
                ", imageUrl4='" + imageUrl4 + '\'' +
                ", imageUrl5='" + imageUrl5 + '\'' +
                ", imageUrl6='" + imageUrl6 + '\'' +
                ", imageNum=" + imageNum +
                ", userNickName='" + userNickName + '\'' +
                ", userPhotoUrl='" + userPhotoUrl + '\'' +
                ", uptime='" + uptime + '\'' +
                ", sayingType=" + sayingType +
                ", useHobby='" + useHobby + '\'' +
                '}';
    }
}
