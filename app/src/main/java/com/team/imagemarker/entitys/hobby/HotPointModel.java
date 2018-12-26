package com.team.imagemarker.entitys.hobby;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lmy on 2017/6/12.
 * email 1434117404@qq.com
 */

public class HotPointModel implements Serializable {
    private String userHead;
    private String userName;
    private String userJob;
    private String userTime;
    private String[] userHobbyTags;
    private String userContent;
    private List<String> userImgList;

    //用户本条分享图片数目
    private int imageNum;

    private int id;
    //用户id
    private int uId;

    public HotPointModel() {
    }

    public HotPointModel(String userHead, String userName, String userJob, String userTime, String[] userHobbyTags, String userContent, List<String> userImgList) {
        this.userHead = userHead;
        this.userName = userName;
        this.userJob = userJob;
        this.userTime = userTime;
        this.userHobbyTags = userHobbyTags;
        this.userContent = userContent;
        this.userImgList = userImgList;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public String[] getUserHobbyTags() {
        return userHobbyTags;
    }

    public void setUserHobbyTags(String[] userHobbyTags) {
        this.userHobbyTags = userHobbyTags;
    }

    public String getUserContent() {
        return userContent;
    }

    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }

    public List<String> getUserImgList() {
        return userImgList;
    }

    public void setUserImgList(List<String> userImgList) {
        this.userImgList = userImgList;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
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

    @Override
    public String toString() {
        return "HotPointModel{" +
                "userHead='" + userHead + '\'' +
                ", userName='" + userName + '\'' +
                ", userJob='" + userJob + '\'' +
                ", userTime='" + userTime + '\'' +
                ", userHobbyTags=" + Arrays.toString(userHobbyTags) +
                ", userContent='" + userContent + '\'' +
                ", userImgList=" + userImgList +
                ", imageNum=" + imageNum +
                ", id=" + id +
                ", uId=" + uId +
                '}';
    }
}
