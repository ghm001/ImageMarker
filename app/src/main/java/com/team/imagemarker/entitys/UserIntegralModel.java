package com.team.imagemarker.entitys;

/**
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class UserIntegralModel {
    private String userHeadUrl;
    private String userNickName;
    private String userIntegralCount;

    public UserIntegralModel() {
    }

    public UserIntegralModel(String userHeadUrl, String userNickName, String userIntegralCount) {
        this.userHeadUrl = userHeadUrl;
        this.userNickName = userNickName;
        this.userIntegralCount = userIntegralCount;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserIntegralCount() {
        return userIntegralCount;
    }

    public void setUserIntegralCount(String userIntegralCount) {
        this.userIntegralCount = userIntegralCount;
    }

    @Override
    public String toString() {
        return "UserIntegralModel{" +
                "userHeadUrl='" + userHeadUrl + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userIntegralCount='" + userIntegralCount + '\'' +
                '}';
    }
}
