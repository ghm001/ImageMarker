package com.team.imagemarker.entitys.saying;

/**
 * 说说实体类：规定用户的发送图片的组大数目为6张
 * Created by Lmy on 2017/4/30.
 * email 1434117404@qq.com
 */

public class SayingModel {
//    private int userId;
//    private int sayingId;
//    private String userHeadImg;//用户头像
//    private String nickName;//用户昵称
//    private String sendTime;//发送时间
//    private String sayingContent;//说说内容
//    private int sayingType;//说说类型
//    private String sayingImg1;//图片1
//    private String sayingImg2;//图片2
//    private String sayingImg3;//图片3
//    private String sayingImg4;//图片4
//    private String sayingImg5;//图片5
//    private String sayingImg6;//图片6

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
    private int sayingType;

    public SayingModel() {
    }

    public SayingModel(int id, int uId, String title, String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4, String imageUrl5, String imageUrl6, int imageNum, String userNickName, String userPhotoUrl, String uptime, int sayingType) {
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

    public int getSayingType() {
        return sayingType;
    }

    public void setSayingType(int sayingType) {
        this.sayingType = sayingType;
    }
}
