package com.team.imagemarker.entitys;

/**
 * Created by Lmy on 2017/9/5.
 * email 1434117404@qq.com
 */

public class opinion {
    private int id;
    //用户id
    private int uId;
    //建议
    private String title;
    //日期
    private String uptime;
    //用户头像地址
    private String userImageUrl;
    //用户昵称
    private String userNickname;

    public opinion() {
    }

    public opinion(int id, int uId, String title, String uptime, String userImageUrl, String userNickname) {
        this.id = id;
        this.uId = uId;
        this.title = title;
        this.uptime = uptime;
        this.userImageUrl = userImageUrl;
        this.userNickname = userNickname;
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

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    @Override
    public String toString() {
        return "opinion{" +
                "id=" + id +
                ", uId=" + uId +
                ", title='" + title + '\'' +
                ", uptime='" + uptime + '\'' +
                ", userImageUrl='" + userImageUrl + '\'' +
                ", userNickname='" + userNickname + '\'' +
                '}';
    }
}
