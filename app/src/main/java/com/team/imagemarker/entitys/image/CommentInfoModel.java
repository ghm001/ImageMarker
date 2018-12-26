package com.team.imagemarker.entitys.image;

/**
 * 用户评论
 * Created by Lmy on 2017/5/12.
 * email 1434117404@qq.com
 */

public class CommentInfoModel {
    private String userHead;//用户头像
    private String nickName;//用户名
    private String sendTime;//发送时间
    private String sendContent;//发送的内容

    public CommentInfoModel(String userHead, String nickName, String sendTime, String sendContent) {
        this.userHead = userHead;
        this.nickName = nickName;
        this.sendTime = sendTime;
        this.sendContent = sendContent;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendContent() {
        return sendContent;
    }

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
    }
}
